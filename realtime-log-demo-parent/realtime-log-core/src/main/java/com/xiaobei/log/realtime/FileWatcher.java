package com.xiaobei.log.realtime;

import com.sun.nio.file.SensitivityWatchEventModifier;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

/**
 * TODO
 *
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-04-14 22:01:01
 */
public class FileWatcher {
    /**
     * 监视的文件目录
     */
    private Path watchDirectory;
    /**
     * 监视的文件类型正则表达式
     */
    private FilenameFilter filenameFilter;
    /**
     * 监视到的文件监听器
     */
    private FileWatchedListener fileWatchedListener;

    /**
     * 当前日志变动的行号
     */
    private Integer currentLineNum;

    /**
     * 当前日志变动的行的行首字符的位置
     */
    private Integer currentLineFirstCharIndex;

    /**
     * 上次日志结束时的行号
     */
    private AtomicInteger lastLogLineNum;

    /**
     * 上一次日志结束时的行的首字符的位置
     */
    private Integer lastLogLineFirstCharIndex;

    public FileWatcher(Path watchDirectory, FilenameFilter filenameFilter, FileWatchedListener fileWatchedListener) {
        this.watchDirectory = watchDirectory;
        this.filenameFilter = filenameFilter;
        this.fileWatchedListener = fileWatchedListener;
    }

    public FileWatcher(Path watchDirectory, FileWatchedListener fileWatchedListener) {
        this(watchDirectory, null, fileWatchedListener);
    }

    /**
     * 由于监听文件目录并不会处理已经存在与目录中的历史文件，所以需要单独处理
     */
    private void executeHistoryFiles() {
        try {
            Stream<Path> stream = Files.list(this.watchDirectory);
            if (this.filenameFilter != null) {
                stream = stream.filter(path -> {
                    String fileName = path.getFileName().toString();
                    return filenameFilter.accept(this.watchDirectory.toFile(), fileName);
                });
            }
            stream.forEach(path -> {
                this.fileWatchedListener.onCreate(path);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听目录，根据文件的变化处理文件
     */
    public void watch() {
        // 先处理历史文件
        executeHistoryFiles();
        // 监听新文件
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            this.watchDirectory.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE);
            while (true) {
                WatchKey watchKey = watchService.take();
                for (WatchEvent event : watchKey.pollEvents()) {
                    WatchEvent.Kind eventKind = event.kind();
                    if (eventKind == StandardWatchEventKinds.OVERFLOW) {
                        continue;
                    }
                    String fileName = event.context().toString();
                    //文件名不匹配
                    if (this.filenameFilter != null && !this.filenameFilter.accept(this.watchDirectory.toFile(), fileName)) {
                        continue;
                    }
                    Path file = Paths.get(this.watchDirectory.toString(), fileName);
                    if (eventKind == StandardWatchEventKinds.ENTRY_CREATE) {
                        this.fileWatchedListener.onCreate(file);
                    } else if (eventKind == StandardWatchEventKinds.ENTRY_MODIFY) {
                        this.fileWatchedListener.onModify(file);
                    } else if (eventKind == StandardWatchEventKinds.ENTRY_DELETE) {
                        this.fileWatchedListener.onDelete(file);
                    }
                }
                boolean isKeyValid = watchKey.reset();
                if (!isKeyValid) {
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void watchFileModify() {
        // 监听文件变化
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            this.watchDirectory.register(watchService,
                    new WatchEvent.Kind[]{StandardWatchEventKinds.ENTRY_MODIFY},
                    SensitivityWatchEventModifier.HIGH);
            while (true) {
                WatchKey watchKey = watchService.take();
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    WatchEvent.Kind<?> eventKind = event.kind();
                    if (eventKind == StandardWatchEventKinds.OVERFLOW) {
                        continue;
                    }
                    String fileName = event.context().toString();
                    //文件名不匹配
                    if (this.filenameFilter != null && !this.filenameFilter.accept(this.watchDirectory.toFile(), fileName)) {
                        continue;
                    }
                    Path path = Paths.get(this.watchDirectory.toString(), fileName);
                    if (eventKind == StandardWatchEventKinds.ENTRY_MODIFY) {
                        try {
                            File file = path.toFile();
//                            String currentLastNum = readLastLine(file, "UTF-8");
//                            System.out.println(currentLastNum);
                            System.out.println("line: " + getLineNum(file, file.length()));
                            this.fileWatchedListener.onModify(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                /*
                 * WatchKey 有两个状态：
                 * {@link sun.nio.fs.AbstractWatchKey.State.READY ready} 就绪状态：表示可以监听事件
                 * {@link sun.nio.fs.AbstractWatchKey.State.SIGNALLED signalled} 有信息状态：表示已经监听到事件，不可以继续监听事件
                 * 每次处理完事件后，必须调用 reset 方法重置 watchKey 的状态为 ready，否则 watchKey 无法继续监听事件
                 */
                boolean isKeyValid = watchKey.reset();
                if (!isKeyValid) {
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static String readLastLine(File file, String charset) throws IOException {
        if (!file.exists() || file.isDirectory() || !file.canRead())
            return null;
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "r");
            long len = raf.length();//当前字符数
            if (len == 0L)
                return "";
            long pos = len - 1;
            while (pos > 0) {
                pos--;
                raf.seek(pos);
                if (raf.readByte() == '\n')
                    break;
            }
            // 当前行首字符位置
            System.out.println("当前行首字符位置为：" + pos);
            if (pos == 0)
                raf.seek(0);

            byte[] bytes = new byte[(int) (pos - 1)];
            raf.read(bytes);

            if (charset == null) {
                return new String(bytes);
            }


            String lineFirstChar = new String(bytes, charset);
            System.out.println("当前行首字符为：" + lineFirstChar);
            return lineFirstChar;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (Exception e2) {
                }
            }
        }
        return null;
    }

    public Integer getLineNum(File file, long fileLength) throws IOException {
        Integer linenumber = 0;
        if(file.exists()){
            LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));
            lineNumberReader.skip(fileLength);
            int lines = lineNumberReader.getLineNumber();
            // 保存当前行数据
            currentLineNum = lines;
            int lastLogLine = lastLogLineNum.get();
            // 思路：记录当前行的首个字符位置以及上次日志输出时的首个字符位置，然后将这其中的内容全部输出
            lineNumberReader.close();
            return lines;
        }
        return linenumber;
    }

    public static void main(String[] args) {
        try {
            Path path = Paths.get("D:\\logs");
            FileWatcher fileWatchedService = new FileWatcher(path, new FileWatchedListenerAdapter());
            fileWatchedService.watchFileModify();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

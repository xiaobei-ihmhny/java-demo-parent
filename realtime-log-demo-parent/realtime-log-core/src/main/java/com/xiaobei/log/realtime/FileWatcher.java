package com.xiaobei.log.realtime;

import com.sun.nio.file.SensitivityWatchEventModifier;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * {@link FileWatcher} 是实际处理类
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-04-14 22:01:01
 */
public class FileWatcher {
    /**
     * 监视的文件目录
     */
    private final Path watchDirectory;
    /**
     * 监视的文件类型正则表达式
     */
    private final FilenameFilter filenameFilter;
    /**
     * 监视到的文件监听器
     */
    private final FileWatchedListener fileWatchedListener;

    /**
     * 监听的文件名称
     */
    private final String watchedFileName;

    /**
     * 当前日志变动的行号
     */
    private Integer currentLineNum = 0;

    /**
     * 当前日志变动的行的行首字符的位置
     */
    private Long currentLineFirstCharIndex = 0L;

    /**
     * 上次日志结束时的行号
     */
    private AtomicInteger lastLogLineNum = new AtomicInteger(0);

    /**
     * 上一次日志结束时的行的首字符的位置
     */
    private Long lastLogLineFirstCharIndex;

    public FileWatcher(Path watchDirectory,
                       FilenameFilter filenameFilter,
                       FileWatchedListener fileWatchedListener) {
        this.watchDirectory = watchDirectory;
        this.filenameFilter = filenameFilter;
        this.fileWatchedListener = fileWatchedListener;
        this.watchedFileName = null;
    }

    public FileWatcher(Path watchDirectory, FileWatchedListener fileWatchedListener) {
        this(watchDirectory, null, fileWatchedListener);
    }

    public FileWatcher(String filePath) {
        int fileAndDirIndex = filePath.lastIndexOf(File.separator);
        if(fileAndDirIndex == -1) {
            throw new IllegalArgumentException("文件路径异常：" + filePath);
        }
        String path = filePath.substring(0, fileAndDirIndex);
        this.watchedFileName = filePath.substring(fileAndDirIndex + 1);
        this.watchDirectory = Paths.get(path);
        this.fileWatchedListener = new FileWatchedListenerAdapter();
        this.filenameFilter = null;
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
            stream.forEach(this.fileWatchedListener::onCreate);
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
                    if(watchedFileName != null && !watchedFileName.equals(fileName)) {
                        continue;
                    }
                    //文件名不匹配
                    if (this.filenameFilter != null && !this.filenameFilter.accept(this.watchDirectory.toFile(), fileName)) {
                        continue;
                    }
                    Path path = Paths.get(this.watchDirectory.toString(), fileName);
                    if (eventKind == StandardWatchEventKinds.ENTRY_MODIFY) {
                        try {
                            File file = path.toFile();
                            String outStr = getOutStr(file, file.length(), "UTF-8");
                            if(StringUtils.hasText(outStr)) {
                                this.fileWatchedListener.onModify(outStr);
                            }
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

    public String getOutStr(File file, long fileLength, String charset) throws IOException {
        if(file.exists()){
            LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));
            lineNumberReader.skip(fileLength);
            int lines = lineNumberReader.getLineNumber();
            int lastLogLine = lastLogLineNum.get();
            if(currentLineNum == 0
                    || lastLogLine == 0
                    || currentLineNum < lastLogLine) {// 说明日志又开始从头写了
                lastLogLineNum.set(lines);
                lastLogLineFirstCharIndex = getLineFirstCharIndex(file);
            }
            // 保存当前行数据
            if(lines > currentLineNum) {// 说明出现的新的一行数据了，此时需要记录行首字符下标
                currentLineNum = lines;
                currentLineFirstCharIndex = getLineFirstCharIndex(file);
            }
            // 思路：记录当前行的首个字符位置以及上次日志输出时的首个字符位置，然后将这其中的内容全部输出
            if(currentLineNum - lastLogLineNum.get() > 0) {// 说明至少打印了完整的一行日志数据了，可以考虑向外输出了
                // 更新lastLogLineNum
                lastLogLineNum.set(currentLineNum);
                // 返回指定的字符串
                String resultStr;
                RandomAccessFile outFile = new RandomAccessFile(file, "r");
                int currentIndex = currentLineFirstCharIndex.intValue();
                int lastIndex = lastLogLineFirstCharIndex.intValue();
                byte[] bytes = new byte[currentIndex - lastIndex];
                outFile.seek(lastIndex);
                outFile.read(bytes, 0, currentIndex - lastIndex);
                if (charset == null) {
                    resultStr = new String(bytes);
                }else {
                    resultStr = new String(bytes, charset);
                }
                // 重置lastCharIndex
                lastLogLineFirstCharIndex = getLineFirstCharIndex(file);
                return resultStr;
            }
            lineNumberReader.close();
            return null;
        }
        return null;
    }

    /**
     * 获取行首字符下标
     * @param file
     * @return
     * @throws IOException
     */
    private static Long getLineFirstCharIndex(File file) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        long len = raf.length();//当前字符数
        if (len == 0L)
            return 0L;
        long pos = len - 1;
        while (pos > 0) {
            pos--;
            raf.seek(pos);
            if (raf.readByte() == '\n')
                break;
        }
        return pos;
    }

}

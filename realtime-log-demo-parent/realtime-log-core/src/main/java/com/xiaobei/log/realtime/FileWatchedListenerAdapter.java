package com.xiaobei.log.realtime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO
 *
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-04-14 22:28:28
 */
public class FileWatchedListenerAdapter implements FileWatchedListener {

    @Override
    public void onCreate(Path file) {
        System.out.println(String.format("文件【%s】被创建，时间：%s", file, LocalDateTime.now()));
    }

    @Override
    public void onModify(Path file) {
        System.out.println(String.format("文件【%s】被修改，时间：%s", file, LocalDateTime.now()));
    }

    @Override
    public void onDelete(Path file) {
        System.out.println(String.format("文件【%s】被删除，时间：%s", file, LocalDateTime.now()));
    }

    @Override
    public void onOverflowed(Path file) {
        System.out.println(String.format("文件【%s】被丢弃，时间：%s", file, LocalDateTime.now()));
    }

    @Override
    public void onModify(String str) {
        System.out.println("当前新的日志内容为：" + str);
    }


}

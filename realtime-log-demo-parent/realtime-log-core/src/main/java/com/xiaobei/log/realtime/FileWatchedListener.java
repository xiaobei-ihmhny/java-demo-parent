package com.xiaobei.log.realtime;

import java.nio.file.Path;

/**
 * TODO
 *
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-04-14 22:18:18
 */
public interface FileWatchedListener {

    /**
     * 在监视目录中新增文件时的处理操作
     * @param file
     */
    void onCreate(Path file);

    /**
     * 在监视目录中修改文件时的处理操作
     * @param file
     */
    void onModify(Path file);

    /**
     * 在监视目录中删除文件时的处理操作
     * @param file
     */
    void onDelete(Path file);

    /**
     *
     * @param file
     */
    void onOverflowed(Path file);

    void onModify(String str);
}

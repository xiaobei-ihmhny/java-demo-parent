package com.xiaobei.log.realtime;

import java.nio.file.Path;

/**
 * 把日志信息发送到服务端
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-04-16 22:45:45
 */
public class SocketIOFileWatcherListener implements FileWatchedListener {

    @Override
    public void onModify(String str) {
        // TODO 先把日志信息发送到服务端
    }

    @Override
    public void onCreate(Path file) {

    }

    @Override
    public void onModify(Path file) {

    }

    @Override
    public void onDelete(Path file) {

    }

    @Override
    public void onOverflowed(Path file) {

    }
}

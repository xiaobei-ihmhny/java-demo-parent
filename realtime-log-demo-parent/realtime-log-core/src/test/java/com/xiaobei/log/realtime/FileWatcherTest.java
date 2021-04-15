package com.xiaobei.log.realtime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * {@link FileWatcher} 测试
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-04-15 22:45:45
 */
//@ExtendWith(SpringExtension.class)
public class FileWatcherTest {

    @Test
    public void fileWatcher() {
        try {
            String filePath = "D:\\logs\\business.log";
            FileWatcher fileWatchedService = new FileWatcher(filePath);
            fileWatchedService.watchFileModify();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void filePath() {
        String filePath = "D:\\logs\\business.log";
        int fileAndDirIndex = filePath.lastIndexOf(File.separator);
        String path = filePath.substring(0, fileAndDirIndex);
        String fileName = filePath.substring(fileAndDirIndex + 1);
        System.out.println(path);
        System.out.println(fileName);
    }
}

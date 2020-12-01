package com.xiaobei.easyexcel.demo;

import com.alibaba.excel.EasyExcel;
import com.xiaobei.easyexcel.demo.domain.LessonInfo;
import com.xiaobei.easyexcel.demo.listener.LessonInfoListener;
import org.junit.Test;

import java.time.LocalTime;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-01 13:45:45
 */
public class LessonInfoTest {

    @Test
    public void testLessonInfo() {
        String fileName = "D:\\guopao\\2019\\2019期架构师视频文档.xlsx";
        EasyExcel.read(fileName, LessonInfo.class, new LessonInfoListener(30)).sheet().doRead();
    }

    @Test
    public void testLocalTimePlus() {
        LocalTime time1 = LocalTime.of(1, 30, 12);
        LocalTime time2 = LocalTime.of(1, 40, 52);
        System.out.println(111);
    }
}
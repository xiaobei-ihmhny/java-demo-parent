package com.xiaobei.easyexcel.demo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.xiaobei.easyexcel.demo.domain.LessonInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-01 13:33:33
 */
public class LessonInfoListener extends AnalysisEventListener<LessonInfo> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LessonInfoListener.class);

    private static final List<LessonInfo> LESSON_INFOS = new ArrayList<>(512);

    /**
     * 计算学习的天数
     */
    private final int plannedDays;

    public LessonInfoListener(int plannedDays) {
        this.plannedDays = plannedDays;
    }


    /**
     * 解析数据
     * @param data
     * @param context
     */
    @Override
    public void invoke(LessonInfo data, AnalysisContext context) {
//        LOGGER.info("解析到的数据为：{}", data);
        if(data != null && data.getDuration() != null) {
            LESSON_INFOS.add(data);
            // 计算当前课程的学习时间
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
//        LOGGER.info("数据解析完成，完成事件触发...同，课程信息为：{}", LESSON_INFOS);
        // 课程总时长
        long totalCostSeconds = LESSON_INFOS.stream()
                .map(LessonInfo::getDuration)
                .collect(Collectors.summarizingLong(LocalTime::toSecondOfDay)).getSum();
        double totalCostMinute = totalCostSeconds / 60.0;
        // 所有课程需要耗费的总时长（小时）
        double totalCostHour = totalCostMinute / 60.0;
        // 每天需要学习的总秒数
        double needStudySecondsEveryDay = totalCostSeconds * 1.0 / plannedDays;
        // 每天需要学习的总时长（分钟）
        double needStudyMinuteEveryDay = totalCostMinute / plannedDays;
        // 每天需要学习的总小时数
        double needStudyHoursEveryDay = totalCostHour / plannedDays;
        System.out.printf("每天需要学习的总小时：%s， 总分钟：%s\n", needStudyHoursEveryDay, needStudyMinuteEveryDay);
        AtomicInteger sumSeconds = new AtomicInteger(0);
        AtomicInteger plusDaysNum = new AtomicInteger(0);
        LocalDate now = LocalDate.now();
        // 计算每天学习的具体课程信息
        LESSON_INFOS.forEach(lessonInfo -> {
            int currentSeconds = lessonInfo.getDuration().toSecondOfDay();
            String time = lessonInfo.getDuration().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            int currentSum = sumSeconds.addAndGet(currentSeconds);
            if(currentSum >= needStudySecondsEveryDay) {
                // 说明当前课程之前的课程总时长已达标，需要切换学习日期
                int daysToAdd = plusDaysNum.addAndGet(1);
                sumSeconds.set(0);
                lessonInfo.setLearningTime(now.plusDays(daysToAdd)
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            } else {
                lessonInfo.setLearningTime(now.plusDays(plusDaysNum.get()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            System.out.println(lessonInfo.getName() + " | " + time + " | " + lessonInfo.getLearningTime() + " | ");
        });
    }
}
package com.xiaobei.easyexcel.demo.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.xiaobei.easyexcel.demo.converter.StringToLocalTimeConverter;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-01 13:28:28
 */
public class LessonInfo implements Serializable {

    private static final long serialVersionUID = 9094695785610865965L;

    /**
     * 课程名称
     */
    @ExcelProperty(index = 0)
    private String name;

    /**
     * 视频名称
     */
    @ExcelProperty(index = 1)
    private String videoName;

    /**
     * 视频时长
     */
    @ExcelProperty(index = 2, converter = StringToLocalTimeConverter.class)
    private LocalTime duration;

    private String learningTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public String getLearningTime() {
        return learningTime;
    }

    public LessonInfo setLearningTime(String learningTime) {
        this.learningTime = learningTime;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LessonInfo{");
        sb.append("name='").append(name).append('\'');
        sb.append(", videoName='").append(videoName).append('\'');
        sb.append(", duration=").append(duration);
        sb.append(", learningTime='").append(learningTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
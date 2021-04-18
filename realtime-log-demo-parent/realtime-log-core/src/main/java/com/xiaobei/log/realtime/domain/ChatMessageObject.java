package com.xiaobei.log.realtime.domain;

/**
 * TODO
 *
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-04-16 22:47:47
 */
public class ChatMessageObject {

    private String filePath;

    private String message;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ChatMessageObject{" +
                "filePath='" + filePath + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

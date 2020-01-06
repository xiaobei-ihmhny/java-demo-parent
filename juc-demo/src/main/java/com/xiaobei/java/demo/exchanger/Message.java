package com.xiaobei.java.demo.exchanger;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-01-06 22:26:26
 */
public class Message {

    private String v;

    public Message(String v) {
        this.v = v;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return "Message{" +
                "v='" + v + '\'' +
                '}';
    }
}

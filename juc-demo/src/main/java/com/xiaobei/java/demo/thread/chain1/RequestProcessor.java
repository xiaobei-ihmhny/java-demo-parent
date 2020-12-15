package com.xiaobei.java.demo.thread.chain1;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-15 13:57:57
 */
public interface RequestProcessor {

    /**
     * 处理请求
     * @param request
     */
    void processRequest(Request request);
}

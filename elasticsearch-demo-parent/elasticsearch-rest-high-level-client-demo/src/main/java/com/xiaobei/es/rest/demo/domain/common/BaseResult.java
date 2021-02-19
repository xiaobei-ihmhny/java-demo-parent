package com.xiaobei.es.rest.demo.domain.common;

import com.xiaobei.es.rest.demo.domain.model.BookModel;

/**
 * TODO
 *
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-19 19:44:44
 */
public class BaseResult {
    public static BaseResult error() {
        return null;
    }

    public static BaseResult ok(Page<BookModel> page) {
        return null;
    }

    public static BaseResult error(String errMsg) {
        return null;
    }

    public static BaseResult ok(BookModel book) {
        return null;
    }

    public static BaseResult ok() {
        return null;
    }
}

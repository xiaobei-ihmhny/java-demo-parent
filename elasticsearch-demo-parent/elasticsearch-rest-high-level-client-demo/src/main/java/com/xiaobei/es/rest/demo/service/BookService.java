package com.xiaobei.es.rest.demo.service;

import com.xiaobei.es.rest.demo.domain.model.BookModel;
import com.xiaobei.es.rest.demo.domain.common.Page;
import com.xiaobei.es.rest.demo.domain.vo.BookRequestVO;

/**
 * TODO
 *
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-19 19:42:42
 */
public interface BookService {

    Page<BookModel> list(BookRequestVO bookRequestVO);


    void save(BookModel bookModel);

    void update(BookModel bookModel);

    void delete(int id);

    BookModel detail(int id);

}

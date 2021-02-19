package com.xiaobei.es.rest.demo.domain.model;

import java.io.Serializable;

/**
 * 图书对象 {@link BookModel}
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-19 19:38:38
 */
public class BookModel implements Serializable {

    private static final long serialVersionUID = 7562877110373544076L;

    private Integer id;         //  图书ID

    private String name;        //  图书名称

    private String author;      //  作者

    private Integer category;   //  图书分类

    private Double price;       //  图书价格

    private String sellReason;  //  上架理由

    private String sellTime;      //  上架时间

    private Integer status;     //  状态（1：可售，0：不可售）

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSellReason() {
        return sellReason;
    }

    public void setSellReason(String sellReason) {
        this.sellReason = sellReason;
    }

    public String getSellTime() {
        return sellTime;
    }

    public void setSellTime(String sellTime) {
        this.sellTime = sellTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BookModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", sellReason='" + sellReason + '\'' +
                ", sellTime='" + sellTime + '\'' +
                ", status=" + status +
                '}';
    }
}

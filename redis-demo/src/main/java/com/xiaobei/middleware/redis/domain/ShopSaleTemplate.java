package com.xiaobei.middleware.redis.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 销售模板配置
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-06-01 14:00:00
 */
public class ShopSaleTemplate implements Serializable {

    private static final long serialVersionUID = 4819462265386791366L;
    /**
     * 模板id
     */
    private Long id;

    /**
     * 模板名称
     */
    private String name;

    public Long getId() {
        return id;
    }

    public ShopSaleTemplate setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ShopSaleTemplate setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "ShopSaleTemplate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
package com.xiaobei.middleware.redis.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-04-17 17:10:10
 */
public class ShopInfo implements Serializable {

    private static final long serialVersionUID = 6904687932055666100L;

    private Long shopId;

    private String shopName;

    /**
     * 当前店铺下包含的sku信息集合
     */
    private List<SkuInfo> skuInfoList;

    public Long getShopId() {
        return shopId;
    }

    public ShopInfo setShopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }

    public String getShopName() {
        return shopName;
    }

    public ShopInfo setShopName(String shopName) {
        this.shopName = shopName;
        return this;
    }

    public List<SkuInfo> getSkuInfoList() {
        return skuInfoList;
    }

    public ShopInfo setSkuInfoList(List<SkuInfo> skuInfoList) {
        this.skuInfoList = skuInfoList;
        return this;
    }

    @Override
    public String toString() {
        return "ShopInfo{" +
                "shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", skuInfoList=" + skuInfoList +
                '}';
    }
}
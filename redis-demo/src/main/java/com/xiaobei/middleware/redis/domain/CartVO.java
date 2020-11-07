package com.xiaobei.middleware.redis.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-04-17 17:02:02
 */
public class CartVO implements Serializable {

    private static final long serialVersionUID = 1542107342640242095L;

    private Long userId;

    /**
     * 店铺信息列表
     */
    private List<ShopInfo> shopInfos;

    /**
     * 订单应付总金额
     */
    private BigDecimal totalPayPrice;

    /**
     * 购物车中商品总数
     */
    private Integer totalSkuCount;

    /**
     * 购物车中选中的商品总数
     */
    private Integer checkedTotalSkuCount;

    private Map<Long, Long> skuIdShopId;

    public Long getUserId() {
        return userId;
    }

    public CartVO setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public List<ShopInfo> getShopInfos() {
        return shopInfos;
    }

    public CartVO setShopInfos(List<ShopInfo> shopInfos) {
        this.shopInfos = shopInfos;
        return this;
    }

    public BigDecimal getTotalPayPrice() {
        return totalPayPrice;
    }

    public CartVO setTotalPayPrice(BigDecimal totalPayPrice) {
        this.totalPayPrice = totalPayPrice;
        return this;
    }

    public Integer getTotalSkuCount() {
        return totalSkuCount;
    }

    public CartVO setTotalSkuCount(Integer totalSkuCount) {
        this.totalSkuCount = totalSkuCount;
        return this;
    }

    public Integer getCheckedTotalSkuCount() {
        return checkedTotalSkuCount;
    }

    public CartVO setCheckedTotalSkuCount(Integer checkedTotalSkuCount) {
        this.checkedTotalSkuCount = checkedTotalSkuCount;
        return this;
    }

    public Map<Long, Long> getSkuIdShopId() {
        return skuIdShopId;
    }

    public CartVO setSkuIdShopId(Map<Long, Long> skuIdShopId) {
        this.skuIdShopId = skuIdShopId;
        return this;
    }

    @Override
    public String toString() {
        return "CartVO{" +
                "userId=" + userId +
                ", shopInfos=" + shopInfos +
                ", totalPayPrice=" + totalPayPrice +
                ", totalSkuCount=" + totalSkuCount +
                ", checkedTotalSkuCount=" + checkedTotalSkuCount +
                ", skuIdShopId=" + skuIdShopId +
                '}';
    }
}
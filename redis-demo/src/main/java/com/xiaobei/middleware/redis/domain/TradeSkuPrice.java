package com.xiaobei.middleware.redis.domain;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 商品阶梯价
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-04-22 16:44:44
 */
public class TradeSkuPrice implements Serializable {

    private static final long serialVersionUID = 6043126912739420987L;

    private Long platformId;

    private Long itemId;

    private Long skuId;

    private Long shopId;

    private BigDecimal sellPrice;

    private String areaId;

    private Long minNum;

    private Long maxNum;

    public Long getPlatformId() {
        return platformId;
    }

    public TradeSkuPrice setPlatformId(Long platformId) {
        this.platformId = platformId;
        return this;
    }

    public Long getItemId() {
        return itemId;
    }

    public TradeSkuPrice setItemId(Long itemId) {
        this.itemId = itemId;
        return this;
    }

    public Long getSkuId() {
        return skuId;
    }

    public TradeSkuPrice setSkuId(Long skuId) {
        this.skuId = skuId;
        return this;
    }

    public Long getShopId() {
        return shopId;
    }

    public TradeSkuPrice setShopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public TradeSkuPrice setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
        return this;
    }

    public String getAreaId() {
        return areaId;
    }

    public TradeSkuPrice setAreaId(String areaId) {
        this.areaId = areaId;
        return this;
    }

    public Long getMinNum() {
        return minNum;
    }

    public TradeSkuPrice setMinNum(Long minNum) {
        this.minNum = minNum;
        return this;
    }

    public Long getMaxNum() {
        return maxNum;
    }

    public TradeSkuPrice setMaxNum(Long maxNum) {
        this.maxNum = maxNum;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TradeSkuPrice that = (TradeSkuPrice) o;
        return Objects.equals(getPlatformId(), that.getPlatformId()) &&
                Objects.equals(getItemId(), that.getItemId()) &&
                Objects.equals(getSkuId(), that.getSkuId()) &&
                Objects.equals(getShopId(), that.getShopId()) &&
                Objects.equals(getSellPrice(), that.getSellPrice()) &&
                Objects.equals(getAreaId(), that.getAreaId()) &&
                Objects.equals(getMinNum(), that.getMinNum()) &&
                Objects.equals(getMaxNum(), that.getMaxNum());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlatformId(),
                getItemId(), getSkuId(),
                getShopId(),
                getSellPrice(),
                getAreaId(),
                getMinNum(),
                getMaxNum());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TradeSkuPrice{");
        sb.append("platformId=").append(platformId);
        sb.append(", itemId=").append(itemId);
        sb.append(", skuId=").append(skuId);
        sb.append(", shopId=").append(shopId);
        sb.append(", sellPrice=").append(sellPrice);
        sb.append(", areaId='").append(areaId).append('\'');
        sb.append(", minNum=").append(minNum);
        sb.append(", maxNum=").append(maxNum);
        sb.append('}');
        return sb.toString();
    }
}
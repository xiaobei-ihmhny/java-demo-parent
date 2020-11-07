package com.xiaobei.middleware.redis.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-04-17 17:32:32
 */
public class SkuInfo implements Serializable {

    private static final long serialVersionUID = 2999941188951211394L;

    private Long shopId;

    private Long itemId;

    private Long skuId;

    /**
     * 销售模板相关信息
     */
    private ShopSaleTemplate shopSaleTemplate;

    private String name;

    /**
     * TODO 规格信息
     */
    private List<String> attributes;

    /**
     * 商品价格信息（阶梯价格）
     */
    private List<TradeSkuPrice> skuPriceList;

    private Integer totalCount;

    /**
     * 单品销售金额
     */
    private BigDecimal singleSalePrice;

    /**
     * 是否选中
     */
    private Boolean checked;

    public Long getShopId() {
        return shopId;
    }

    public SkuInfo setShopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }

    public Long getItemId() {
        return itemId;
    }

    public SkuInfo setItemId(Long itemId) {
        this.itemId = itemId;
        return this;
    }

    public Long getSkuId() {
        return skuId;
    }

    public SkuInfo setSkuId(Long skuId) {
        this.skuId = skuId;
        return this;
    }

    public ShopSaleTemplate getShopSaleTemplate() {
        return shopSaleTemplate;
    }

    public SkuInfo setShopSaleTemplate(ShopSaleTemplate shopSaleTemplate) {
        this.shopSaleTemplate = shopSaleTemplate;
        return this;
    }

    public String getName() {
        return name;
    }

    public SkuInfo setName(String name) {
        this.name = name;
        return this;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public SkuInfo setAttributes(List<String> attributes) {
        this.attributes = attributes;
        return this;
    }

    public List<TradeSkuPrice> getSkuPriceList() {
        return skuPriceList;
    }

    public SkuInfo setSkuPriceList(List<TradeSkuPrice> skuPriceList) {
        this.skuPriceList = skuPriceList;
        return this;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public SkuInfo setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public BigDecimal getSingleSalePrice() {
        return singleSalePrice;
    }

    public SkuInfo setSingleSalePrice(BigDecimal singleSalePrice) {
        this.singleSalePrice = singleSalePrice;
        return this;
    }

    public Boolean getChecked() {
        return checked;
    }

    public SkuInfo setChecked(Boolean checked) {
        this.checked = checked;
        return this;
    }

    /**
     * TODO 测试增量更新购物车信息接口
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkuInfo skuInfo = (SkuInfo) o;
        return Objects.equals(getShopId(), skuInfo.getShopId()) &&
                Objects.equals(getItemId(), skuInfo.getItemId()) &&
                Objects.equals(getSkuId(), skuInfo.getSkuId()) &&
                Objects.equals(getName(), skuInfo.getName()) &&
                Objects.equals(getAttributes(), skuInfo.getAttributes()) &&
                Objects.equals(getSkuPriceList(), skuInfo.getSkuPriceList()) &&
                Objects.equals(getTotalCount(), skuInfo.getTotalCount()) &&
                Objects.equals(getSingleSalePrice(), skuInfo.getSingleSalePrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getShopId(), getItemId(), getSkuId(), getName(),
                getAttributes(), getSkuPriceList(), getTotalCount(),
                getSingleSalePrice());
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SkuInfo{");
        sb.append(", shopId=").append(shopId);
        sb.append(", itemId=").append(itemId);
        sb.append(", skuId=").append(skuId);
        sb.append(", shopSaleTemplate=").append(shopSaleTemplate);
        sb.append(", name='").append(name).append('\'');
        sb.append(", attributes=").append(attributes);
        sb.append(", skuPriceList=").append(skuPriceList);
        sb.append(", totalCount=").append(totalCount);
        sb.append(", singleSalePrice=").append(singleSalePrice);
        sb.append(", checked=").append(checked);
        sb.append('}');
        return sb.toString();
    }
}
package com.xiaobei.easyexcel.demo.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-12-18 15:15
 */
public class SkuInfo implements Serializable {

    private static final long serialVersionUID = 3257850505835514464L;

    /**
    * 主键
    */
    private Long id;

    /**
    * 商品编号,SKU 编号
    */
    private String productSkuNo;

    /**
    * 商品编号，SPU 编号
    */
    private String productNo;

    /**
    * 商品名称
    */
    private String productName;

    /**
    * 商品状态，上架=1，下 架=2
    */
    private String state;

    /**
    * 制造厂家
    */
    private String factoryName;

    /**
    * 质保期
    */
    private String qaDate;

    /**
    * 存储条件
    */
    private String storageCondition;

    /**
    * 交货日期
    */
    private String deliveryDay;

    /**
    * 品牌名称
    */
    private String brandName;

    /**
    * 协议价格
    */
    private String price;

    /**
    * 市场价
    */
    private String marketPrice;

    /**
    * 库存
    */
    private String stock;

    /**
    * 重量(kg)
    */
    private String weight;

    /**
    * 长(cm)
    */
    private String length;

    /**
    * 宽(cm)
    */
    private String width;

    /**
    * 核安全等级
    */
    private String nuclearSafetyLevel;

    /**
    * 质保级别
    */
    private String qaLevel;

    /**
    * 商品描述
    */
    private String productMsg;

    /**
    * 商品型号
    */
    private String productType;

    /**
    * 厂家物料编码
    */
    private String factoryNo;

    /**
    * 计量单位
    */
    private String units;

    /**
    * 最小订货量
    */
    private String minQuantity;

    /**
    * 增量
    */
    private String increment;

    /**
    * 包装量
    */
    private String bagNum;

    /**
    * 默认规格
    */
    private String specDefault;

    /**
    * 税类编码
    */
    private String hsCode;

    /**
    * 分类编号
    */
    private String categoryCode;

    /**
    * 规格属性
    */
    private String specs;

    /**
    * 高(cm)
    */
    private String height;

    /**
    * 创建日期
    */
    private Date created;

    /**
    * 修改日期 
    */
    private Date modified;

    /**
    * 1:有效; 0:删除
    */
    private Integer status;

    
    public Long getId() {
    	return id;
    }

    public void setId(Long id) {
    	this.id = id;
    }

    public String getProductSkuNo() {
    	return productSkuNo;
    }

    public void setProductSkuNo(String productSkuNo) {
    	this.productSkuNo = productSkuNo;
    }

    public String getProductNo() {
    	return productNo;
    }

    public void setProductNo(String productNo) {
    	this.productNo = productNo;
    }

    public String getProductName() {
    	return productName;
    }

    public void setProductName(String productName) {
    	this.productName = productName;
    }

    public String getState() {
    	return state;
    }

    public void setState(String state) {
    	this.state = state;
    }

    public String getFactoryName() {
    	return factoryName;
    }

    public void setFactoryName(String factoryName) {
    	this.factoryName = factoryName;
    }

    public String getQaDate() {
    	return qaDate;
    }

    public void setQaDate(String qaDate) {
    	this.qaDate = qaDate;
    }

    public String getStorageCondition() {
    	return storageCondition;
    }

    public void setStorageCondition(String storageCondition) {
    	this.storageCondition = storageCondition;
    }

    public String getDeliveryDay() {
    	return deliveryDay;
    }

    public void setDeliveryDay(String deliveryDay) {
    	this.deliveryDay = deliveryDay;
    }

    public String getBrandName() {
    	return brandName;
    }

    public void setBrandName(String brandName) {
    	this.brandName = brandName;
    }

    public String getPrice() {
    	return price;
    }

    public void setPrice(String price) {
    	this.price = price;
    }

    public String getMarketPrice() {
    	return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
    	this.marketPrice = marketPrice;
    }

    public String getStock() {
    	return stock;
    }

    public void setStock(String stock) {
    	this.stock = stock;
    }

    public String getWeight() {
    	return weight;
    }

    public void setWeight(String weight) {
    	this.weight = weight;
    }

    public String getLength() {
    	return length;
    }

    public void setLength(String length) {
    	this.length = length;
    }

    public String getWidth() {
    	return width;
    }

    public void setWidth(String width) {
    	this.width = width;
    }

    public String getNuclearSafetyLevel() {
    	return nuclearSafetyLevel;
    }

    public void setNuclearSafetyLevel(String nuclearSafetyLevel) {
    	this.nuclearSafetyLevel = nuclearSafetyLevel;
    }

    public String getQaLevel() {
    	return qaLevel;
    }

    public void setQaLevel(String qaLevel) {
    	this.qaLevel = qaLevel;
    }

    public String getProductMsg() {
    	return productMsg;
    }

    public void setProductMsg(String productMsg) {
    	this.productMsg = productMsg;
    }

    public String getProductType() {
    	return productType;
    }

    public void setProductType(String productType) {
    	this.productType = productType;
    }

    public String getFactoryNo() {
    	return factoryNo;
    }

    public void setFactoryNo(String factoryNo) {
    	this.factoryNo = factoryNo;
    }

    public String getUnits() {
    	return units;
    }

    public void setUnits(String units) {
    	this.units = units;
    }

    public String getMinQuantity() {
    	return minQuantity;
    }

    public void setMinQuantity(String minQuantity) {
    	this.minQuantity = minQuantity;
    }

    public String getIncrement() {
    	return increment;
    }

    public void setIncrement(String increment) {
    	this.increment = increment;
    }

    public String getBagNum() {
    	return bagNum;
    }

    public void setBagNum(String bagNum) {
    	this.bagNum = bagNum;
    }

    public String getSpecDefault() {
    	return specDefault;
    }

    public void setSpecDefault(String specDefault) {
    	this.specDefault = specDefault;
    }

    public String getHsCode() {
    	return hsCode;
    }

    public void setHsCode(String hsCode) {
    	this.hsCode = hsCode;
    }

    public String getCategoryCode() {
    	return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
    	this.categoryCode = categoryCode;
    }

    public String getSpecs() {
    	return specs;
    }

    public void setSpecs(String specs) {
    	this.specs = specs;
    }

    public String getHeight() {
    	return height;
    }

    public void setHeight(String height) {
    	this.height = height;
    }

    public Date getCreated() {
    	return created;
    }

    public void setCreated(Date created) {
    	this.created = created;
    }

    public Date getModified() {
    	return modified;
    }

    public void setModified(Date modified) {
    	this.modified = modified;
    }

    public Integer getStatus() {
    	return status;
    }

    public void setStatus(Integer status) {
    	this.status = status;
    }

    @Override
    public String toString() {
        return "TinamaSku{" +
            "id=" + id +
            ", productSkuNo='" + productSkuNo + '\'' +
            ", productNo='" + productNo + '\'' +
            ", productName='" + productName + '\'' +
            ", state='" + state + '\'' +
            ", factoryName='" + factoryName + '\'' +
            ", qaDate='" + qaDate + '\'' +
            ", storageCondition='" + storageCondition + '\'' +
            ", deliveryDay='" + deliveryDay + '\'' +
            ", brandName='" + brandName + '\'' +
            ", price='" + price + '\'' +
            ", marketPrice='" + marketPrice + '\'' +
            ", stock='" + stock + '\'' +
            ", weight='" + weight + '\'' +
            ", length='" + length + '\'' +
            ", width='" + width + '\'' +
            ", nuclearSafetyLevel='" + nuclearSafetyLevel + '\'' +
            ", qaLevel='" + qaLevel + '\'' +
            ", productMsg='" + productMsg + '\'' +
            ", productType='" + productType + '\'' +
            ", factoryNo='" + factoryNo + '\'' +
            ", units='" + units + '\'' +
            ", minQuantity='" + minQuantity + '\'' +
            ", increment='" + increment + '\'' +
            ", bagNum='" + bagNum + '\'' +
            ", specDefault='" + specDefault + '\'' +
            ", hsCode='" + hsCode + '\'' +
            ", categoryCode='" + categoryCode + '\'' +
            ", specs='" + specs + '\'' +
            ", height='" + height + '\'' +
            ", created='" + created + '\'' +
            ", modified='" + modified + '\'' +
            ", status='" + status + '\'' +
            '}';
    }
}
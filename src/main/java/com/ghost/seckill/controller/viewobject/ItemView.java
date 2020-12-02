package com.ghost.seckill.controller.viewobject;

import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * Description
 *
 * @author Zetian
 * @date 30, 11 2020
 */
public class ItemView {

    private Integer id;
    /**
     * 名称
     */
    private String title;
    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 描述
     */
    private String description;
    /**
     * 销量
     */
    private Integer sale;

    /**
     * 商品图片的URL
     */
    private String imgUrl;

    /**
     * 记录商品是否在秒杀活动中  状态0：表示没有秒杀活动  状态1：表示秒杀活动待开始 状态2：表示正在进行
     * @return
     */
    private Integer promoStatus;

    /**
     * 秒杀活动价格
     * @return
     */
    private BigDecimal promoPrice;

    /**
     * 秒杀活动ID
     * @return
     */
    private Integer promoId;

    /**
     * 秒杀活动开始时间
     * @return
     */
    private DateTime startDate;

    public Integer getPromoStatus() {
        return promoStatus;
    }

    public void setPromoStatus(Integer promoStatus) {
        this.promoStatus = promoStatus;
    }

    public BigDecimal getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(BigDecimal promoPrice) {
        this.promoPrice = promoPrice;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}

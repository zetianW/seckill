package com.ghost.seckill.service.model;

import java.math.BigDecimal;

/**
 * Description
 *用户下单的交易模型
 * @author Zetian
 * @date 02, 12 2020
 */

public class OrderModel {

    private String id;
    /**
     * 交易号
     */
    private Integer UserId;
    /**
     * 购买商品的id
     */
    private Integer itemId;
    /**
     * 若非空，则表示以秒杀形式下单
     */
    private Integer promoId;
    /**
     * 购买商品的单价,若promoPrice非空，则表示以秒杀价格下单
     */
    private BigDecimal itemPrice;
    /**
     * 购买数量
     */
    private Integer amount;
    /**
     * 购买金额，若promoPrice非空，则表示以秒杀价格下单
     */
    private BigDecimal orderPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }
}

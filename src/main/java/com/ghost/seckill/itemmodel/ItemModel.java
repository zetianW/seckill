package com.ghost.seckill.itemmodel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Description
 *
 * @author Zetian
 * @date 30, 11 2020
 */
public class ItemModel {
    private Integer id;
    /**
     * 名称
     */
    @NotBlank(message = "商品名称不能为空")
    private String title;
    /**
     * 价格
     */
    @NotNull(message = "商品价格名称不能为空")
    @Min(value = 0,message = "商品价格必须大于0")
    private BigDecimal price;

    /**
     * 库存
     */
    @NotNull(message = "库存不能填写")
    private Integer stock;

    /**
     * 描述
     */
    @NotBlank(message = "描述信息不能为空")
    private String descriptin;
    /**
     * 销量
     */
    private Integer sale;

    /**
     * 商品图片的URL
     */
    @NotBlank(message = "图片不能为空")
    private String imgUrl;
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

    public String getDescriptin() {
        return descriptin;
    }

    public void setDescriptin(String descriptin) {
        this.descriptin = descriptin;
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

package com.ghost.seckill.service;

import com.ghost.seckill.error.BusinessException;
import com.ghost.seckill.itemmodel.ItemModel;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Description
 *
 * @author Zetian
 * @date 30, 11 2020
 */
public interface ItemService {
    /**
     * 创建商品
     * @param itemModel
     * @return
     */

    ItemModel creatItem(ItemModel itemModel) throws BusinessException;

    /**
     * 商品列表浏览
     * @return
     */
    List<ItemModel> listItem();
    /**
     * 商品详情浏览
     */
    ItemModel getItemById(Integer id);
    /**
     * 库存减扣
     */
    boolean decreaseStock(Integer itemId,Integer amount)throws BusinessException;
    /**
     * 商品销量增加
     */
    void increaseSales(Integer itemId,Integer amount)throws BusinessException;
}

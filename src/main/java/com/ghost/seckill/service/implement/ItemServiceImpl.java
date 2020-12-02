package com.ghost.seckill.service.implement;

import com.ghost.seckill.dao.ItemDOMapper;
import com.ghost.seckill.dao.ItemStockDOMapper;
import com.ghost.seckill.dataobject.ItemDO;
import com.ghost.seckill.dataobject.ItemStockDO;
import com.ghost.seckill.error.BusinessException;
import com.ghost.seckill.error.EmBusinessError;
import com.ghost.seckill.itemmodel.ItemModel;
import com.ghost.seckill.service.ItemService;
import com.ghost.seckill.service.PromoService;
import com.ghost.seckill.service.model.PromoModel;
import com.ghost.seckill.validator.ValidationResult;
import com.ghost.seckill.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description
 *
 * @author Zetian
 * @date 30, 11 2020
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    private ValidatorImpl validator;

    @Resource
    private ItemDOMapper itemDOMapper;

    @Resource
    private PromoService promoService;
    @Resource
    private ItemStockDOMapper itemStockDOMapper;

    private ItemDO convertItemDOFromItemModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel, itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue());
        return itemDO;
    }

    private ItemStockDO convertItemStockDOFromItemModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());
        return itemStockDO;
    }

    @Override
    @Transactional
    public ItemModel creatItem(ItemModel itemModel) throws BusinessException {
        //校验入参
        ValidationResult result = validator.validate(itemModel);
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }
        //转化itemModel->dataObject
        ItemDO itemDO = this.convertItemDOFromItemModel(itemModel);
        //写入数据库
        try {
            itemDOMapper.insertSelective(itemDO);
            itemModel.setId(itemDO.getId());

            ItemStockDO itemStockDO = this.convertItemStockDOFromItemModel(itemModel);

            itemStockDOMapper.insertSelective(itemStockDO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回创建完成的对象
        return this.getItemById(itemModel.getId());
    }

    @Override
    public List<ItemModel> listItem() {
        List<ItemDO> itemDOList = itemDOMapper.listItem();
        List<ItemModel> itemModelList = itemDOList.stream().map(itemDO -> {
            ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
            ItemModel itemModel = this.convertItemDOFromDataObject(itemDO, itemStockDO);
            return itemModel;
        }).collect(Collectors.toList());


        return itemModelList;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        if (itemDO == null) {
            return null;
        }
        //操作获取库存数量
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());

        //将dataObject->model
        ItemModel itemModel = convertItemDOFromDataObject(itemDO, itemStockDO);

        //获取活动商品信息(模型聚合)
        PromoModel promoModel = promoService.getPromoByItemId(itemModel.getId());
        if(promoModel !=null && promoModel.getStatus().intValue() !=3){
            itemModel.setPromoModel(promoModel);
        }
        return itemModel;
    }

    @Override
    public boolean decreaseStock(Integer itemId, Integer amount) {

        int affectedRow = 0;

        try {
            affectedRow = itemStockDOMapper.decreaseStock(itemId, amount);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (affectedRow > 0) {
            //更新库存成功
            return true;
        } else {
            //更新库存失败
            return false;
        }
    }

    @Override
    @Transactional
    public void increaseSales(Integer itemId, Integer amount) {
        itemDOMapper.increaseSales(itemId, amount);
    }


    /**
     * @param itemDO
     * @param itemStockDO
     * @return
     */
    private ItemModel convertItemDOFromDataObject(ItemDO itemDO, ItemStockDO itemStockDO) {
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO, itemModel);
        itemModel.setPrice(BigDecimal.valueOf(itemDO.getPrice()));
        itemModel.setStock(itemStockDO.getStock());

        return itemModel;
    }

}

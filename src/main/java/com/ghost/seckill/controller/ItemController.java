package com.ghost.seckill.controller;

import com.ghost.seckill.controller.viewobject.ItemView;
import com.ghost.seckill.error.BusinessException;
import com.ghost.seckill.itemmodel.ItemModel;
import com.ghost.seckill.response.CommonReturnType;
import com.ghost.seckill.service.ItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author Zetian
 * @date 30, 11 2020
 */
@Controller("/item")
@RequestMapping("/item")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;

    /**
     * 创建商品的controller
     *
     * @param title
     * @param description
     * @param price
     * @param stock
     * @param imgUrl
     * @return
     */
    @RequestMapping(value = "/create", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createItem(@RequestParam(name = "title") String title,
                                       @RequestParam(name = "description") String description,
                                       @RequestParam(name = "price") BigDecimal price,
                                       @RequestParam(name = "stock") Integer stock,
                                       @RequestParam(name = "imgUrl") String imgUrl) throws BusinessException {
        /**
         * 封装service请求，用来创建商品
         */
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescriptin(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);

        ItemModel itemModelForReturn = itemService.creatItem(itemModel);

        ItemView itemView = convertViewFromModel(itemModelForReturn);

        return CommonReturnType.creat(itemView);
    }

    /**
     * 商品详情浏览
     *
     * @return
     */
    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getItem(@RequestParam(name = "id") Integer id) {
        ItemModel itemModel = itemService.getItemById(id);
        ItemView itemView = convertViewFromModel(itemModel);
        return CommonReturnType.creat(itemView);
    }

    /**
     * 获取商品列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listItem() {

        List<ItemModel> itemModelList = itemService.listItem();

        //使用steam api将List内的itemModel转化为ItemView
        List<ItemView> itemViewList = new ArrayList<>();
        for (ItemModel itemModel : itemModelList) {
            ItemView itemView = this.convertViewFromModel(itemModel);
            itemViewList.add(itemView);
        }
        System.out.println(itemViewList);
        return CommonReturnType.creat(itemViewList);

    }

    private ItemView convertViewFromModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemView itemView = new ItemView();
        BeanUtils.copyProperties(itemModel, itemView);
        return itemView;
    }
}

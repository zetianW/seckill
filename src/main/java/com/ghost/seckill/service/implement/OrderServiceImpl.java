package com.ghost.seckill.service.implement;

import com.ghost.seckill.dao.OrderDOMapper;
import com.ghost.seckill.dao.SequenceDOMapper;
import com.ghost.seckill.dataobject.OrderDO;
import com.ghost.seckill.dataobject.SequenceDO;
import com.ghost.seckill.error.BusinessException;
import com.ghost.seckill.error.EmBusinessError;
import com.ghost.seckill.itemmodel.ItemModel;
import com.ghost.seckill.service.ItemService;
import com.ghost.seckill.service.OrderService;
import com.ghost.seckill.service.UserService;
import com.ghost.seckill.service.model.OrderModel;
import com.ghost.seckill.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description
 *
 * @author Zetian
 * @date 02, 12 2020
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDOMapper orderDOMapper;

    private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException {
        //校验下单状态（商品，用户，购买数量是否存在、正确）
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "商品信息不存在");
        }

        UserModel userModel = userService.getUserById(userId);
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户信息不存在");
        }
        if (amount <= 0 || amount > 99) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "购买数量信息超过限定购买额度");

        }
        //2、下单减少库存
        boolean result = itemService.decreaseStock(itemId, amount);
        if (!result) {
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }
        //3、订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setItemPrice(itemModel.getPrice());
        orderModel.setAmount(amount);
        orderModel.setOrderPrice(itemModel.getPrice().multiply(new BigDecimal(amount)));

        //生成交易流水号
        orderModel.setId(generateOrderNo());
        OrderDO orderDO = convertFromOrderModel(orderModel);
        try {
            orderDOMapper.insertSelective(orderDO);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //4、返回给前端
        //加上商品销量
        itemService.increaseSales(itemId, amount);
        return orderModel;
    }

    /**
     * 生成订单号的执行方法
     *
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String generateOrderNo() {
        StringBuilder stringBuilder = new StringBuilder();
        //订单号有16位
        //前八位为时间信息
        stringBuilder.append(SDF.format(new Date()));
        //中间6位自增
        //获取当前sequence
        int sequence = 0;
        try{
            SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
            sequence = sequenceDO.getCurrentValue();
            sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());
            sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
            String sequenceStr = String.valueOf(sequence);
            for (int i = 0; i < 6 - sequenceStr.length(); i++) {
                stringBuilder.append(0);
            }
            stringBuilder.append(sequenceStr);
            //最后两位为分库分表位(写死的)
            stringBuilder.append("00");

        }catch (Exception e){
            e.printStackTrace();
        }
        return stringBuilder.toString();
        }


    private OrderDO convertFromOrderModel(OrderModel orderModel) throws BusinessException {
        if (orderModel == null) {
            return null;
        }
        OrderDO orderDO = new OrderDO();
        orderDO.setId(orderModel.getId());
        orderDO.setAmount(orderModel.getAmount());
        orderDO.setItemId(orderModel.getItemId());
        orderDO.setItemPrice(orderModel.getItemPrice());
        orderDO.setOrderPrice(orderModel.getOrderPrice());
        orderDO.setUserId(orderModel.getUserId());
        //返回前端
        return orderDO;
    }
}

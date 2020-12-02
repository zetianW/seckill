package com.ghost.seckill.service;

import com.ghost.seckill.error.BusinessException;
import com.ghost.seckill.service.model.OrderModel;

/**
 * Description
 *
 * @author Zetian
 * @date 02, 12 2020
 */
public interface OrderService {
    OrderModel createOrder(Integer userId,Integer itemId,Integer amount) throws BusinessException;
}

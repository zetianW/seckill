package com.ghost.seckill.controller;

import com.ghost.seckill.error.BusinessException;
import com.ghost.seckill.error.EmBusinessError;
import com.ghost.seckill.response.CommonReturnType;
import com.ghost.seckill.service.OrderService;
import com.ghost.seckill.service.model.OrderModel;
import com.ghost.seckill.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Description
 *
 * @author Zetian
 * @date 02, 12 2020
 */
@Controller("order")
@RequestMapping("/order")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class OrderController extends BaseController{

    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 封装下单请求
     * @param itemId
     * @param amount
     * @return
     */
    @RequestMapping(value = "/createorder", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name ="itemId")Integer itemId,
                                        @RequestParam(name ="amount")Integer amount,
                                        @RequestParam(name ="promoId",required = false)Integer promoId) throws BusinessException {
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(isLogin == null || !isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"用户未登录");
        }
        //获取用户登录信息
        UserModel userModel = (UserModel)httpServletRequest.getSession().getAttribute("LOGIN_USER");

        OrderModel orderModel = orderService.createOrder(userModel.getId(),itemId,promoId,amount);

        return CommonReturnType.create(null);
    }

}

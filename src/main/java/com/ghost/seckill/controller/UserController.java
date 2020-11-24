package com.ghost.seckill.controller;

import com.ghost.seckill.controller.viewobject.UserView;
import com.ghost.seckill.dao.UserDOMapper;
import com.ghost.seckill.response.CommonReturnType;
import com.ghost.seckill.service.UserService;
import com.ghost.seckill.service.implement.UserServiceImpl;
import com.ghost.seckill.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description
 *
 * @author Zetian
 * @date 24, 11 2020
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) {
        //调用service服务获取对应的id的用户对象并返回给前端
        UserModel userModel = userService.getUserById(id);

        //将核心领域模型用户对象转化为可供UI使用的viewObject
        UserView userView = convertFromModel(userModel);
        //返回通用对象
        return CommonReturnType.creat(userView);
    }

    private UserView convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserView userView = new UserView();
        BeanUtils.copyProperties(userModel, userView);
        return userView;
    }
}

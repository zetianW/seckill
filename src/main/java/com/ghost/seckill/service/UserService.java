package com.ghost.seckill.service;

import com.ghost.seckill.error.BusinessException;
import com.ghost.seckill.service.model.UserModel;

/**
 * Description
 *
 * @author Zetian
 * @date 24, 11 2020
 */
public interface UserService {
    /**
     * 通过用户ID获取用户对象的方法
     * @param id
     */
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;
}

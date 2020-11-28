package com.ghost.seckill.service.implement;

import com.alibaba.druid.util.StringUtils;
import com.ghost.seckill.dao.UserDOMapper;
import com.ghost.seckill.dao.UserPasswordDoMapper;
import com.ghost.seckill.dataobject.UserDO;
import com.ghost.seckill.dataobject.UserPasswordDo;
import com.ghost.seckill.error.BusinessException;
import com.ghost.seckill.error.EmBusinessError;
import com.ghost.seckill.service.UserService;
import com.ghost.seckill.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Description
 *
 * @author Zetian
 * @date 24, 11 2020
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDOMapper userDOMapper;

    @Resource
    private UserPasswordDoMapper userPasswordDoMapper;

    @Override
    public UserModel getUserById(Integer id) {
        //调用userdomapper获取到对应的用户dataobject
        // 根据主键ID获取UserDO的对象
        UserDO userDo = userDOMapper.selectByPrimaryKey(id);

        if (userDo == null) {
            return null;
        }
        //通过用户id获取对应的用户加密密码信息
        UserPasswordDo userPasswordDo = userPasswordDoMapper.selectByUserId(userDo.getId());
        return convertFromDataObject(userDo, userPasswordDo);
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        if (StringUtils.isEmpty(userModel.getName()) || userModel.getGender() == null
                || userModel.getAge() == null || StringUtils.isEmpty(userModel.getTelphone())) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }


        //实现model->dataObject方法
        UserDO userDO = convertFromModel(userModel);
        try {
            userDOMapper.insertSelective(userDO);
        } catch (DuplicateKeyException ex) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "手机号已重复注册");
        }

        UserPasswordDo userPasswordDo = convertPasswordFromModel(userModel);
        userPasswordDoMapper.insertSelective(userPasswordDo);
        return;
    }

    private UserPasswordDo convertPasswordFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserPasswordDo userPasswordDo = new UserPasswordDo();
        userPasswordDo.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDo.setUserId(userModel.getId());
        return userPasswordDo;
    }

    private UserDO convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel, userDO);
        return userDO;
    }

    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDo userPasswordDo) {
        if (userDO == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO, userModel);
        if (userPasswordDo == null) {
            return null;
        }
        userModel.setEncrptPassword(userPasswordDo.getEncrptPassword());
        return userModel;
    }
}

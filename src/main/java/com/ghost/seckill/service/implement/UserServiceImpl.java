package com.ghost.seckill.service.implement;

import com.ghost.seckill.dao.UserDOMapper;
import com.ghost.seckill.dao.UserPasswordDoMapper;
import com.ghost.seckill.dataobject.UserDO;
import com.ghost.seckill.dataobject.UserPasswordDo;
import com.ghost.seckill.service.UserService;
import com.ghost.seckill.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        if(userDo == null){
            return null;
        }
        //通过用户id获取对应的用户加密密码信息
        UserPasswordDo userPasswordDo =userPasswordDoMapper.selectByUserId(userDo.getId());
    return convertFromDataObject(userDo,userPasswordDo);
    }
    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDo userPasswordDo){
        if(userDO == null){
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        if(userPasswordDo == null){
            return null;
        }
        userModel.setEncrptPassword(userPasswordDo.getEncrptPassword());
        return userModel;
    }
}

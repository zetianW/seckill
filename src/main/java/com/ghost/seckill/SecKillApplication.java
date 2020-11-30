package com.ghost.seckill;


import com.ghost.seckill.dao.UserDOMapper;
import com.ghost.seckill.dataobject.UserDO;
import com.ghost.seckill.util.CommonUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Ghost
 */

@SpringBootApplication
@RestController
@MapperScan("com.ghost.seckill.dao")
public class SecKillApplication {

    @Autowired
    private UserDOMapper userDOMapper;

    @RequestMapping("/")
    public String home(){
        UserDO userDo = userDOMapper.selectByPrimaryKey(1);
        if(userDo == null){
            return "用户对象不存在";
        }else {
            return userDo.getName();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SecKillApplication.class,args);
        System.err.println(CommonUtils.getTel());
    }

}

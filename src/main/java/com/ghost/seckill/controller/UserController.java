package com.ghost.seckill.controller;

import com.ghost.seckill.controller.viewobject.UserView;
import com.ghost.seckill.error.BusinessException;
import com.ghost.seckill.error.EmBusinessError;
import com.ghost.seckill.response.CommonReturnType;
import com.ghost.seckill.service.UserService;
import com.ghost.seckill.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Description
 *
 * @author Ghost
 * @author Zetian
 * @date 24, 11 2020
 */
@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

//    @Resource
//    private HttpServletRequest httpServletRequest;

    /**
     * 用户注册接口
     */
    @RequestMapping(value = "/register", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(HttpServletRequest httpServletRequest,
                                     @RequestParam(name = "telphone") String telphone,
                                     @RequestParam(name = "otpCode") String otpCode,
                                     @RequestParam(name = "name") String name,
                                     @RequestParam(name = "gender") Integer gender,
                                     @RequestParam(name = "age") Integer age,
                                     @RequestParam(name = "password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //验证手机号和对应的otpCode相符合
        String inSessionOtpCode =(String)httpServletRequest.getSession().getAttribute(telphone);
        if(!com.alibaba.druid.util.StringUtils.equals(otpCode,inSessionOtpCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不符合");
        }

        //用户的注册流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
        userModel.setAge(age);
        userModel.setTelphone(telphone);
        userModel.setRegisterMode("byphone");
        userModel.setEncrptPassword(this.EncodeByMd5(password));
        userService.register(userModel);
        return CommonReturnType.creat(null);
    }
    public String EncodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        //加密字符串
        String newStr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return newStr;
    }

    /**
     * 用户获取otp短信接口
     *
     * @param telphone
     * @return
     */
    @RequestMapping(value = "/getotp", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(HttpServletRequest httpServletRequest,@RequestParam(name = "telphone") String telphone) {
        //需要按照规则生成验证码
        Random random = new Random();
        //此时随机数的取值为【999，10999】
        int randomInt = random.nextInt(999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);

        //将otp验证码同对应的手机号关联,使用httpsession的方式绑定他的手机号与otpCode;
        httpServletRequest.getSession().setAttribute(telphone, otpCode);

        //将OTP验证码通过短信通道发送给用户（需要第三方短信服务）
        // 在一般程序开发中，不能将这些otpCode等敏感信息打印到控制台，此处打印时为了方便查看
        System.out.println("telphone =" + telphone + "****otpCode = " + otpCode);

        return CommonReturnType.creat(null);
    }

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
        //调用service服务获取对应的id的用户对象并返回给前端
        UserModel userModel = userService.getUserById(id);

        //若获取的对应用户信息不存在
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }

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

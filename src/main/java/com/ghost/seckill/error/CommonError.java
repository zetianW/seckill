package com.ghost.seckill.error;

/**
 * Description
 *
 * @author Zetian
 * @date 25, 11 2020
 */
public interface CommonError {
    public int getErrCode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);
}

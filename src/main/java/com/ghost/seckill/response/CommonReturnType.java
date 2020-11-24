package com.ghost.seckill.response;

/**
 * Description
 *
 * @author Zetian
 * @date 24, 11 2020
 */
public class CommonReturnType {
    /**
     * 表名对应请求的返回处理结果“success”或“fail”
     */
    private String status;
    /**
     * 若status=success，则data内返回前端需要的json数据
     * 若status=fail，则data内使用通用的错误码格式
     */
    private Object data;

    /**
     * 定义一个通用的创建方法
     * 这个方法的意义是当controller完成了处理，调用creat方法，如果不带任何status的话，
     * 它对应的status就是success。然后创建一个CommonReturnType对象，并且返回对应的值。
     */
    public static CommonReturnType creat(Object result){
        return CommonReturnType.creat(result,"success");
    }
    public static CommonReturnType creat(Object result,String status){
        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

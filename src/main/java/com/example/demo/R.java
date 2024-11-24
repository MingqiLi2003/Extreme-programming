package com.example.demo;



import java.io.Serializable;

/**
 * 响应信息主体
 *
 */
public class R<T> implements Serializable
{
    private static final long serialVersionUID = 1L;

       /** 成功 */
    public static final int SUCCESS = 200;

    /** 失败 */
    public static final int FAIL = 500;

//    @ApiModelProperty(value = "状态")
    private Integer status;

//    @ApiModelProperty(value = "代码")
    private Integer code;

//    @ApiModelProperty(value = "消息")
    private String msg;

//    @ApiModelProperty(value = "数据")
    private T data;

    public static <T> R<T> ok()
    {
        return restResult(null, SUCCESS, null);
    }

    public static <T> R<T> ok(T data)
    {
        return restResult(data, SUCCESS, null);
    }

    public static <T> R<T> ok(T data, String msg)
    {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> R<T> fail()
    {
        return restResult(null, FAIL, null);
    }

    public static <T> R<T> fail(String msg)
    {
        return restResult(null, FAIL, msg);
    }

    public static <T> R<T> fail(T data)
    {
        return restResult(data, FAIL, null);
    }

    public static <T> R<T> fail(T data, String msg)
    {
        return restResult(data, FAIL, msg);
    }

    public static <T> R<T> fail(T data, String msg,Integer code)
    {
        return restResult(data, FAIL, msg, code);
    }

    public static <T> R<T> fail(Integer status, String msg)
    {
        return restResult(null, status, msg);
    }

    private static <T> R<T> restResult(T data, Integer status, String msg)
    {
        R<T> apiResult = new R<>();
        apiResult.setStatus(status);
        apiResult.setCode(status);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    private static <T> R<T> restResult(T data, Integer status, String msg,Integer code)
    {
        R<T> apiResult = new R<>();
        apiResult.setStatus(status);
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    private void setCode(Integer status) {
        this.code = status;
    }
    public Integer getCode()
    {
        return code;
    }
    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }
}

package org.platform.api.response;

/**
 * 返回数据
 * 
 * @author zhangkui
 * @param <T>
 */
public class BaseResponse<T> {

	/**
	 * 1 成功 0 失败
	 **/
	private int code;
	/** 消息描述 **/
	private String message;

	private T data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}


	/**
	 * 成功消息
	 * @param t
	 * @return
	 */
    public static <T> BaseResponse<T> success(T data) {
    	BaseResponse<T> response = new BaseResponse<T>();
    	response.setCode(1);
    	response.setMessage("成功");
    	response.setData(data);
        return response;
    }
    
    /**
	 * 成功消息
	 * @param t
	 * @return
	 */
    public static <T> BaseResponse<T> success() {
    	BaseResponse<T> response = new BaseResponse<T>();
    	response.setCode(1);
    	response.setMessage("成功");
        return response;
    }
    
    /**
     * 失败消息
     * @param message
     * @return
     */
    public static <T> BaseResponse<T> fail(String message) {
    	BaseResponse<T> response = new BaseResponse<T>();
    	response.setCode(0);
    	response.setMessage(message);
        return response;
    }
    
    /**
     * 自定义返回数据
     * @param code
     * @param data
     * @param message
     * @return
     */
    public static <T> BaseResponse<T> custom(int code,T data,String message) {
    	BaseResponse<T> response = new BaseResponse<T>();
    	response.setCode(code);
    	response.setData(data);
    	response.setMessage(message);
        return response;
    }
    

}

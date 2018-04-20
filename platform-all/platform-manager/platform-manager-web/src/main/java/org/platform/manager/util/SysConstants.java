package org.platform.manager.util;

public interface SysConstants {
	/**
	 * 验证码-存于session中的key
	 */
	String IMAGE_CODE_IN_SESSION ="image_code_in_session";
	
	/**
	 * 管理员登录-存session中的key
	 */
	String ADMIN_LOGIN_IN_SESSION = "admin_user";
	
	/**
	 * 后台根路径
	 */
	String MANAGE_URL = "/views/";
	
	String AUDIT_URL = "/audit/";
	String PORTAL_URL = "/";
	String HTML5_URL = "html5";
	/**
	 * 分页-每页显示的条数
	 */
	int PAGE_LIST_SIZE = 25;
	
	/**
	 * 出库单防止重复提交的token
	 */
	String OUTSTORAGE_NOT_SUBMIT = "outStorage_not_submit";
	
	/**数量**/
	public static int num = 8;
	/**excel_num 11**/
	public static int excel_num = 13;
	
	/**excel_num fs**/
	public static int excel_num_fs = 13;
}

package org.platform.base.datasource.threadLocal;

/**
 * 监控事务是否开启，来处理datasource 切换
 * @author zhang kui
 */
public class DataSourceHandleLocal {

	/**事务处理Local**/
	private static ThreadLocal<String> threadLocal = new ThreadLocal<String>();
	/**写库标识**/
	public static String WRITER = "WRITER";
	/**读库标识**/
	public static String READ = "READ";

	public static ThreadLocal<String> getThreadLocal() {
		return threadLocal;
	}

	/**写库设置**/
	public static void setWriter() {
		threadLocal.set(WRITER);
	}
	
	/**读库设置**/
	public static void setRead() {
		threadLocal.set(READ);
	}
	
	/**清空标识**/
	public static void clearReadOrWriter(){
		threadLocal.remove();
	}
	
}

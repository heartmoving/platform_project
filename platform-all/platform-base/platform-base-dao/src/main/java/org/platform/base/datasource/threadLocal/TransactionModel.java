package org.platform.base.datasource.threadLocal;

/**
 * 获取数据库
 * @author zhang kui
 */
public class TransactionModel {
	
	/***
	 * true 只读   false 开启事务
	 **/
	private Boolean readOnly;

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}
	
}

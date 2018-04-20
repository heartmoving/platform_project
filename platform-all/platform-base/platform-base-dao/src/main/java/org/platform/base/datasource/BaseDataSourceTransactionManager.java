package org.platform.base.datasource;

import org.platform.base.common.util.StringUtils;
import org.platform.base.datasource.threadLocal.DataSourceHandleLocal;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

/**
 * 判断开启事务格式
 * @author zhang kui
 */
@SuppressWarnings("serial")
public class BaseDataSourceTransactionManager extends DataSourceTransactionManager{

	/**针对特殊业务，需要读写库，需要以匹配的前缀开头比如writer,强制写库**/
	private String methodMatch;
	
	public String getMethodMatch() {
		return methodMatch;
	}

	public void setMethodMatch(String methodMatch) {
		this.methodMatch = methodMatch;
	}

	@Override
	protected void doBegin(Object arg0, TransactionDefinition arg1) {
		String name = arg1.getName();
		boolean readOnly = arg1.isReadOnly();
		if(readOnly){
			String[] arryClass = name.split("\\.");
			String methdoName = arryClass[arryClass.length-1];
			if(StringUtils.isNotBlank(methodMatch) && StringUtils.isNotBlank(methdoName) 
							&& methdoName.startsWith(methodMatch)){
				DataSourceHandleLocal.setWriter();
			}else{
				DataSourceHandleLocal.setRead();
			}
		}else{
			DataSourceHandleLocal.setWriter();
		}
		super.doBegin(arg0, arg1);
	}

	
	@Override
	protected void doCleanupAfterCompletion(Object arg0) {
		DataSourceHandleLocal.clearReadOrWriter();
		super.doCleanupAfterCompletion(arg0);
	}

	
}

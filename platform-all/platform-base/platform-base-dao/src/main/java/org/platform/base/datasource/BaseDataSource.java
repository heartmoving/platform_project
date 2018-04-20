package org.platform.base.datasource;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.platform.base.datasource.threadLocal.DataSourceHandleLocal;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 实现datasource 方法，来判断读库或者写库
 * @author zhang kui
 */
public class BaseDataSource  extends AbstractRoutingDataSource{

	/**写库datasource**/
	private Map<String, List<DataSource>> writerDataSource;
	
	/**读库datasource**/
	private Map<String, List<DataSource>> readDataSource;
	
	/**读库规则定义**/
	private DataSourceBusiness dataSourceBusiness;
	
	
	public Map<String, List<DataSource>> getWriterDataSource() {
		return writerDataSource;
	}

	public void setWriterDataSource(Map<String, List<DataSource>> writerDataSource) {
		this.writerDataSource = writerDataSource;
	}

	public Map<String, List<DataSource>> getReadDataSource() {
		return readDataSource;
	}

	public void setReadDataSource(Map<String, List<DataSource>> readDataSource) {
		this.readDataSource = readDataSource;
	}
	
	public DataSourceBusiness getDataSourceBusiness() {
		return dataSourceBusiness;
	}

	public void setDataSourceBusiness(DataSourceBusiness dataSourceBusiness) {
		this.dataSourceBusiness = dataSourceBusiness;
	}

	/**
	 * 通过key 来判断获取那个dataSource
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		
		return null;
	}
	

	@Override
	public void afterPropertiesSet() {
		// TODO Auto-generated method stub
	}

	@Override
	protected DataSource determineTargetDataSource() {
		if(null == writerDataSource || 0 == writerDataSource.size() ){
			return null;
		}
		/**判断当前操作是读库，还是写库**/
		String isMasterReadWriter =  DataSourceHandleLocal.getThreadLocal().get();
		/**写库，进行挑选对应datasource**/
		if(DataSourceHandleLocal.WRITER.equals(isMasterReadWriter) || null == isMasterReadWriter || null == readDataSource  || readDataSource.size() == 0){
			return dataSourceBusiness.accessDataSource(writerDataSource, false);
		}else{
			/**读库进行挑选对应datasource**/
			return dataSourceBusiness.accessDataSource(readDataSource, true);
		}
	}
	
	
}

package org.platform.base.datasource;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;


/**
 * datasource 获取，（通过自己的业务实现获取规则）
 * @author  zhang kui
 */
public interface DataSourceBusiness {

	/**
	 * 通过自定义的规则来获取DataSourceBusiness
	 * @param map 读的时候传入,读库配置，写的时候传入写的库，如果不区分读写，都传入写库
	 * @param isReadOnly true 是读  false 开启事务
	 * @return
	 */
	public DataSource accessDataSource(Map<String, List<DataSource>> map,boolean isReadOnly);
	
}

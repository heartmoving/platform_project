package org.platform.base.datasource;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

/**
 * 自定义处理业务，默认获取第0个
 * @author zhang kui
 */
public class DataSourceBusinessImpl  implements DataSourceBusiness{

	
	/**
	 * 通过自定义的规则来获取DataSourceBusiness
	 * @param map 读的时候传入,读库配置，写的时候传入写的库，如果不区分读写，都传入写库
	 * @param isReadOnly true 是读  false 开启事务
	 * @return 默认获取第0 个
	 */
	@Override
	public DataSource accessDataSource(Map<String, List<DataSource>> map,boolean isReadOnly) {
		Set<Entry<String, List<DataSource>>> entries = map.entrySet();
		Iterator<Entry<String, List<DataSource>>> iterator =  entries.iterator();
		Entry<String, List<DataSource>> entry = iterator.next();
		return entry.getValue().get(0);
	}

}

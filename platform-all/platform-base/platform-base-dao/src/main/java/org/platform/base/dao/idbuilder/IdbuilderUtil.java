package org.platform.base.dao.idbuilder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;
import org.platform.base.common.annotation.Table;
import org.platform.base.domain.IdBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * id 生成器处理
 * @author otvcloud
 */
@Component
public class IdbuilderUtil{
	
	private Logger LOG = Logger.getLogger(IdbuilderUtil.class);
	
	@Autowired
	private IIdBuilderMapper builderMapper;
	
	/**每次拿取自动增长数量**/
	private  long auto = 100;
	
	/**缓存id数量值，存放map值**/
	private  ConcurrentMap<String, AtomicLong> idMap = new ConcurrentHashMap<String, AtomicLong>();
	
	/**存放数据库对应的值，越界处理**/
	private  Map<String, Long> dataValue = new HashMap<String, Long>();
	
	/**
	 * 获取id值
	 * @param key 序列生成器key
	 * @param id 主键id 字段,获取对应的最大值
	 * @return
	 */
	public  Long idValue(String key,String table, String id) throws Exception{
		synchronized (key) {
			AtomicLong atomicLong =	idMap.get(key);
			Long maxId = dataValue.get(key);
			if(null == atomicLong || atomicLong.get() >= maxId){
				atomicLong = incrementId(key,table,id);
			}
			return atomicLong.incrementAndGet();
		}
	}
	
	/**
	 * 通过clazz拿，根据表生成规则来
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public  Long idValue(Class<?> clazz)throws Exception{
		Table table = (Table)clazz.getAnnotations()[0];
		String tableName = table.tableName();
		String id = table.idName();
		String key = tableName.toUpperCase()+"_KEY";
		synchronized (key) {
			AtomicLong atomicLong =	idMap.get(key);
			Long maxId = dataValue.get(key);
			if(null == atomicLong || atomicLong.get() >= maxId){
				atomicLong = incrementId(key,tableName,id);
			}
			return atomicLong.incrementAndGet();
		}
	
	}
	
	/**
	 * 数据库拿序列号处理
	 * @param key
	 */
	private AtomicLong incrementId(String key,String table,String idName) throws Exception{
		try{
			AtomicLong atomicLong = null;
			/**此处查询数据库，判断是否是序号值，比表最大值还小**/
			StringBuilder builder = new StringBuilder("select ");
			builder.append(" max(").append(idName).append(")");
			builder.append(" ").append("from ").append(table);
			/**当前数据库最大id值**/
			Map<String, Object> paramMaxId = new HashMap<String, Object>();
			paramMaxId.put("sql", builder.toString());
			Long dbMaxId = builderMapper.qryBuilderTableMaxId(paramMaxId);
			if(null == dbMaxId){
				dbMaxId = 0l;
			}
			/**查询表处理数据**/
			IdBuilder idBuilder = builderMapper.qryIdBuilder(key);
			if(null == idBuilder){
				long idmaxValue = auto;
				long startValue = dbMaxId;
				if((auto+1) < dbMaxId){
					idmaxValue = dbMaxId + 1 + auto;
				}
				IdBuilder builderdb = new IdBuilder();
				builderdb.setIdType(key);
				builderdb.setIdMaxValue(idmaxValue);
				builderdb.setGmtModify(new Date());
				builderdb.setVersionNum(1l);
				builderdb.setRemark(key);
				builderMapper.save(builderdb);
				atomicLong = new AtomicLong(startValue);
				idMap.put(key, atomicLong);
				dataValue.put(key, idmaxValue);
			}else{
				long idmaxValue = idBuilder.getIdMaxValue() + auto;
				long startValue = idBuilder.getIdMaxValue();
				if((idBuilder.getIdMaxValue()+1) < dbMaxId){
					idmaxValue = dbMaxId + 1 + auto;
					startValue = dbMaxId;
				}
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("idMaxValue", idmaxValue);
				param.put("idType", key);
				param.put("versionNum", idBuilder.getVersionNum());
				int n = builderMapper.updateByBuilder(param);
				/**出现冲突，重新设置**/
				if(0 == n){
					return incrementId(key,table,idName);
				}
				atomicLong = new AtomicLong(startValue);
				idMap.put(key, atomicLong);
				dataValue.put(key, idmaxValue);
			}
			return atomicLong;
		}catch(Exception e){
			e.printStackTrace();
			LOG.error("自动增长id，查询错误,error：",e);
			throw e;
		}
	}
	

}

package org.platform.base.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.platform.base.common.util.StringUtils;
import org.platform.base.dao.IGenericMapper;
import org.platform.base.service.IBaseService;


/**
 * 基础服务类
 * @param <T>
 */
public abstract class BaseServiceImpl<T> implements IBaseService<T> {
	
	private static final Logger logger  = Logger.getLogger(BaseServiceImpl.class);
	
	public abstract IGenericMapper<T>  getGenericMapper();

	@Override
	public int save(T t) {
		try{
			return getGenericMapper().save(t);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("保存数据发生错误",e);
		}
		return 0;
	}

	@Override
	public Object list(T t) {
		return null;
	}

	@Override
	public int deleteById(long id) {
		try{
			return getGenericMapper().deleteById(id);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("根据ID删除数据发生错误",e);
		}
		return 0;
	}

	@Override
	public int deleteByIds(String ids) {
		try{
			if(StringUtils.isNotBlank(ids)){
				List<String> list = new ArrayList<String>();
				if(ids.indexOf(",") > 0){
					String idsArr[] = ids.split(",");
					list = Arrays.asList(idsArr);
				}else{
					list.add(ids);
				}
//				return getGenericMapper().deleteByIds(list);
			}
//			if(StringUtils.isNotBlank(ids)){
//				return genericMapper.d(ids);
//			}
		}catch (Exception e) {
			logger.error("根据多个ID删除数据发生错误",e);
		}
		return 0;
	}

	@Override
	public T findById(long id) {
		try{
			return getGenericMapper().getById(id);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("根据ID查询数据发生错误",e);
		}
		return null;
	}

	@Override
	public int updateById(T t) {
		try{
			return getGenericMapper().updateById(t);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("根据ID查询数据发生错误",e);
		}
		return 0;
	}



}

package org.platform.base.dao.idbuilder;

import java.util.Map;

import org.platform.base.domain.IdBuilder;

/**   
* @Title: IIdBuilderMapper.java 
* @Package com.yfygrp.cms.web.dao
* @Description: 主键Id生成器
* @author zhangkui
* @date 2017-06-20 15:37:23
* @version V1.0   
* create by codeFactory
*/
public interface IIdBuilderMapper{
	
	/**
	 * 通过主键查询IdBuilder
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public IdBuilder qryIdBuilder(String idType) throws Exception;
	
	/**
	 * 修改id 增长数据
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int updateByBuilder(Map<String, Object> param) throws Exception;
	
	/**
	 * 查询数据库对应的最大id值
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public Long qryBuilderTableMaxId(Map<String, Object> param)throws Exception;
	
	/**
	 * 自动增长创建
	 * @param t
	 * @return
	 */
	int save(IdBuilder t);
	
}

package org.platform.base.service;

import java.util.List;
import java.util.Map;


public interface IBaseService<T> {
	/**
	 * 保存信息
	 * @param target
	 * @return
	 */
	int save(T t);
	/**
	 * 分页带条件查询数据
	 * @param target
	 * @return
	 */
	Object list(T t);
	/**
	 * 根据ID删除数据
	 * @param id
	 * @return
	 */
	int deleteById(long id);
	/**
	 * 根据多个ID删除数据
	 * @param ids "1,2,3,4"
	 * @return
	 */
	int deleteByIds(String ids);
	/**
	 * 根据ID查询一条数据
	 * @param id
	 * @return
	 */
	T findById(long id);
	/**
	 * 根据ID修改一条数据
	 * @param target
	 * @return
	 */
	int updateById(T t);
	
	/**查询所有列表*/
	List<T> queryAll();
	
	/**
	 * 根据条件查询列表
	 * @param t
	 * @return
	 */
	List<T> findByCondition(Map<String, Object> map);
	
	/**
	 * 修改记录
	 * 
	 * @param t
	 * @return
	 */
	int updateProductGroup(Map<String, Object> map);
	
}
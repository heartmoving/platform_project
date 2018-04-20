package org.platform.base.dao;

import java.util.List;

/**
* @Title: IGenericMapper.java
* @Description: 公共dao
* @author zhangkui
* @date 2015年12月29日 上午12:02:29
* @version V1.0
 */
public interface IGenericMapper<T> {
	
	/**
	 * @author zhang kui
	 * @Title: getById
	 * @Description: TODO 通过主键查询单个对象
	 * @param  id
	 * @return 设定文件
	 * @throws
	 */
	T getById(Long id);

	/**
	 * 根据条件查询列表
	 * @param map
	 * @return
	 */
	List<T> findByCondition(T t);

	/**
	 * 删除单个数据
	 * @param id
	 * @return
	 */
	int deleteById(Long id);

	/**
	 * 通过一组主键id,删除多个数据
	 * @param ids
	 * @return
	 */
	int deleteByIds(List<Long> ids);
	
	/**
	 * 查询列表
	 * @param t
	 * @return
	 */
	List<T> getForList(T t);

	/**
	 * 统计条数
	 * @param t
	 * @return
	 */
	int getCountForList(T t);

	/**
	 * 保存数据
	 * @param t
	 * @return
	 */
	int save(T t);
	
	/**
	 * 自定义自动增长序列
	 * @param t
	 * @return
	 */
	int saveCreateId(T t);

	/**
	 * 修改记录
	 * @param t
	 * @return
	 */
	int updateById(T t);

	/**
	 * 批量保存插入
	 * @param list
	 * @return
	 */
	void batchInsert(List<T> list);
	
	
}

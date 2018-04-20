package org.platform.base.dao.idbuilder;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.platform.base.domain.IdBuilder;

/**
 * id 生成策略处理类
 * @author otvcloud
 */
public class IdBuilderMapperImpl   implements IIdBuilderMapper{
	
	/**命名空间**/
	private String namespace = "org.platform.base.dao.idbuilder.IIdBuilderMapper.";
	
	private SqlSessionTemplate sqlSessionTemplate;
	
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public IdBuilder qryIdBuilder(String param) throws Exception {
		return sqlSessionTemplate.selectOne(namespace+"qryIdBuilder", param);
	}

	@Override
	public int updateByBuilder(Map<String, Object> param) throws Exception {
		return sqlSessionTemplate.update(namespace+"updateByBuilder", param);
	}

	@Override
	public Long qryBuilderTableMaxId(Map<String, Object> param)throws Exception {
		return sqlSessionTemplate.selectOne(namespace+"qryBuilderTableMaxId", param);
	}

	@Override
	public int save(IdBuilder t) {
		return sqlSessionTemplate.insert(namespace+"save", t);
	}

}

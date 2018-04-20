package org.platform.base.domain;

import java.util.Date;

/**
* @TableName: id_builder 
* @Package: com.yfygrp.cms.web.model
* @Title:IdBuilder.java 
* @Description: 主键Id生成器 
* @author: zhangkui
* @date: 2017-06-19 17:55:22
* @version V1.0    
* create by codeFactory
*/
public class IdBuilder{
	/**
	*@Fields idType :id类型
	*/
	private String idType;
	/**
	*@Fields idMaxValue :id值
	*/
	private Long idMaxValue;
	/**
	*@Fields remark :备注
	*/
	private String remark;
	/**
	*@Fields versionNum :版本号
	*/
	private Long versionNum;
	/**
	*@Fields gmtModify :最后修改时间
	*/
	private Date gmtModify;
	public void setIdType(String idType){
		this.idType=idType;
	}

	public String getIdType(){
		return idType;
	}
	public void setIdMaxValue(Long idMaxValue){
		this.idMaxValue=idMaxValue;
	}

	public Long getIdMaxValue(){
		return idMaxValue;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}

	public String getRemark(){
		return remark;
	}
	public void setVersionNum(Long versionNum){
		this.versionNum=versionNum;
	}

	public Long getVersionNum(){
		return versionNum;
	}
	public void setGmtModify(Date gmtModify){
		this.gmtModify=gmtModify;
	}

	public Date getGmtModify(){
		return gmtModify;
	}

}


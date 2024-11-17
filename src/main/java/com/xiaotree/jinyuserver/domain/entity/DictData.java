package com.xiaotree.jinyuserver.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


/**
 * @author xiaotree
 * @create 2024-09-21 21:36:18 
 * @description DictData 
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictData {

	/**
	 * 字典类型id
	 */
	private Integer typeId;

	/**
	 * 字典标签
	 */
	private String label;

	/**
	 * 字典值
	 */
	private Integer value;

	/**
	 * 排序
	 */
	private Integer orderNum;
}

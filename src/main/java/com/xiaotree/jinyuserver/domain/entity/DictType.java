package com.xiaotree.jinyuserver.domain.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;


/**
 * @author xiaotree
 * @create 2024-09-21 21:39:52 
 * @description DictType 
 */

@Table(value = "xit_dict_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictType implements Serializable {

	@Id(keyType = KeyType.Auto, before = false)
	private Integer id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 标识符
	 */
	private String key;

	/**
	 * 状态
	 */
	private Boolean status;

	/**
	 * 备注
	 */
	private String comment;

}

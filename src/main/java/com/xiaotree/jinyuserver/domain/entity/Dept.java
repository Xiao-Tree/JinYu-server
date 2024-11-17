package com.xiaotree.jinyuserver.domain.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;

/**
 * @author xiaotree
 * @create 2024-09-24 21:13:54 
 * @description Dept 
 */

@Table("xit_dept")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dept {
	@Id(keyType = KeyType.Auto,before = false)
	private Integer id;

	/**
	 * 部门名称
	 */
	private String name;

	/**
	 * 父级id
	 */
	private Integer parentId;

	/**
	 * 排序
	 */
	private Integer orderNum;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	private Timestamp createAt;

	/**
	 * 更新时间
	 */
	private Timestamp updateAt;

}

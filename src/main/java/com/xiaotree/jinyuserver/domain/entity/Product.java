package com.xiaotree.jinyuserver.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


/**
 * @author xiaotree
 * @create  2024-07-14 11:58:40
 * @description  产品信息表
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Integer id;

    /**
     * 品类id
     */
    private Integer categoryId;

    /**
     * 规格
     */
    private Integer standardId;

    /**
     * 品牌id
     */
    private Integer brandId;

    /**
     * 单位id
     */
    private Integer unitId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 重量
     */
    private Double weight;

    /**
     * 进价
     */
    private BigDecimal buyPrice;

    /**
     * 售价
     */
    private BigDecimal sellPrice;

}

package com.xiaotree.jinyuserver.domain.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Table(value = "Dlyndx")
public class Draft {
    @Column(value = "Vchcode")
    private Integer id;
    @Column(value = "Number")
    private String code;
    /**
     * 状态
     */
    @Column(value = "draft")
    private Integer state;
    @Column(value = "VchType")
    private Integer type;
    private String summary;
    private String comment;
    @Column(value = "btypeid")
    private String companyId;
    @Column(value = "ktypeid")
    private String store_id;
    private Date date;
    @Column(value = "Savedate", jdbcType = JdbcType.TIMESTAMP)
    private Timestamp saveDateTime;
    private Float total;
}

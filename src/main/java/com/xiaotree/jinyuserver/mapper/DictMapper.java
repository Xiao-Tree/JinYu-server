package com.xiaotree.jinyuserver.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xiaotree.jinyuserver.domain.entity.DictData;
import com.xiaotree.jinyuserver.domain.entity.DictType;
import com.xiaotree.jinyuserver.domain.vo.DictValue;

import java.util.List;

public interface DictMapper extends BaseMapper<DictType> {
   List<DictValue> selectSimpleDictData(Integer typeId);
   List<DictData> selectDictData(Integer dictId);
   Integer insertDictValues(List<DictData> dictDatas);
   Integer deleteDictValues(List<DictData> dictDatas);
}
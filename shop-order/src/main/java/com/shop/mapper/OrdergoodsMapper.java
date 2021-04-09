package com.shop.mapper;

import com.shop.model.Ordergoods;
import com.shop.model.OrdergoodsExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrdergoodsMapper {
    int countByExample(OrdergoodsExample example);

    int deleteByExample(OrdergoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Ordergoods record);

    int insertSelective(Ordergoods record);

    List<Ordergoods> selectByExample(OrdergoodsExample example);

    Ordergoods selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Ordergoods record, @Param("example") OrdergoodsExample example);

    int updateByExample(@Param("record") Ordergoods record, @Param("example") OrdergoodsExample example);

    int updateByPrimaryKeySelective(Ordergoods record);

    int updateByPrimaryKey(Ordergoods record);
}
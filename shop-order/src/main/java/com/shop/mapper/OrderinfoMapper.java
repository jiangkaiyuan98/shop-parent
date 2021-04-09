package com.shop.mapper;

import com.shop.model.Orderinfo;
import com.shop.model.OrderinfoExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderinfoMapper {
    int countByExample(OrderinfoExample example);

    int deleteByExample(OrderinfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Orderinfo record);

    int insertSelective(Orderinfo record);

    List<Orderinfo> selectByExample(OrderinfoExample example);

    Orderinfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Orderinfo record, @Param("example") OrderinfoExample example);

    int updateByExample(@Param("record") Orderinfo record, @Param("example") OrderinfoExample example);

    int updateByPrimaryKeySelective(Orderinfo record);

    int updateByPrimaryKey(Orderinfo record);

    @Select("select goodsId, sum(goodsNumber) as stock\n" +
            "from shops.orderinfo a\n" +
            "         left join ordergoods b on a.orderState = 1 and a.orderNo = b.orderId\n" +
            "group by goodsId")
    List<Map<String, Object>> queryAllNewOrder();
}
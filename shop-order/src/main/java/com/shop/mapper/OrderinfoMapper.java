package com.shop.mapper;

import com.shop.model.Orderinfo;
import com.shop.model.OrderinfoCuntom;
import com.shop.model.OrderinfoExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
            "         left join ordergoods b on a.orderState in('0','-1') and a.orderNo = b.orderId\n" +
            "group by goodsId")
    List<Map<String, Object>> queryAllNewOrder();

    @Select("select orderState from orderinfo where orderNo=#{orderNo} limit 1")
    String queryOrderInfoByOrderNo(String orderNo);

    @Update("update orderinfo set orderState=#{orderstate} where orderNo=#{orderno}")
    int updateOrderState(OrderinfoCuntom orderinfoCuntom);
}
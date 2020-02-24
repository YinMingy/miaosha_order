package com.cdut.miaosha.dao;

import com.cdut.miaosha.entity.MiaoshaOrder;
import com.cdut.miaosha.entity.OrderInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @author ：yinmy
 * @date ：Created on 2020/2/24 14:57
 */

@Mapper
@Component
public interface OrderDao {


    @Select("select * from miaosha_order where user_id=#{userId} and goods_id=#{goodsId}")
    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId") long userId,@Param("goodsId") long goodsId);

    @Insert("insert order_info(user_id,goods_id,delivery_addr_id,goods_name,goods_count,goods_price,order_channel,status,create_date) values" +
            "(#{userId},#{goodsId},#{deliveryAddrId},#{goodsName},#{goodsCount},#{goodsPrice},#{orderChannel},#{status},#{createDate}) ")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = long.class,before = false,statement = "select last_insert_id()")
    long insert(OrderInfo orderInfo);

    @Insert("insert miaosha_order(user_id,order_id,goods_id) values(" +
            "#{userId},#{orderId},#{goodsId})")
    int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);
}
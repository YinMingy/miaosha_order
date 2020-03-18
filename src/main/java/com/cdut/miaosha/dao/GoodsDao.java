package com.cdut.miaosha.dao;

import com.cdut.miaosha.entity.Goods;
import com.cdut.miaosha.entity.MiaoshaGoods;
import com.cdut.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ：yinmy
 * @date ：Created on 2020/1/28 15:56
 */

@Mapper
@Component
public interface GoodsDao {

    @Select("select g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from miaosha_goods mg left join goods g on mg.goods_id = g.id")
    List<GoodsVo> listGoodsVo();

    @Select("select g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from miaosha_goods mg left join goods g on mg.goods_id = g.id where goods_id = #{goodsId}")
    GoodsVo getGoodsByGoodsId(@Param("goodsId") long goodsId);

    @Update("update miaosha_goods set stock_count = stock_count-1 where goods_id = #{goodsId} and stock_count>0")
    int reduceStock(MiaoshaGoods g);
}

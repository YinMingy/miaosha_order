package com.cdut.miaosha.service;

import com.cdut.miaosha.dao.GoodsDao;
import com.cdut.miaosha.entity.Goods;
import com.cdut.miaosha.entity.MiaoshaGoods;
import com.cdut.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo(){
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsByGoodsId(long goodsId) {
        return goodsDao.getGoodsByGoodsId(goodsId);
    }

    public boolean reduceStock(GoodsVo goodsVo) {

        MiaoshaGoods miaoshaGoods = new MiaoshaGoods();
        miaoshaGoods.setGoodsId(goodsVo.getId());
        int ret = goodsDao.reduceStock(miaoshaGoods);
        return ret>0;
    }


    public void resetStock(List<GoodsVo> goodsList) {
        for(GoodsVo goods : goodsList ) {
            MiaoshaGoods g = new MiaoshaGoods();
            g.setGoodsId(goods.getId());
            g.setStockCount(goods.getStockCount());
            goodsDao.resetStock(g);
        }
    }
}

package com.rayest.secondkill;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface GoodStockMapper {
    @Select("select count(id) from t_stock where good_no = #{goodNo} for update")
    long getStock(String goodNo);

    @Update("update t_stock set stock = stock -1 where good_no = #{goodNo} and stock > 0")
    int deductStock(String goodNo);
}

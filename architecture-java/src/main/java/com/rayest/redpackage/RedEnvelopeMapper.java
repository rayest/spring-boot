package com.rayest.redpackage;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

public interface RedEnvelopeMapper {
    @Insert("insert into t_red_envelope (red_envelope_no, user_no, total_amount, total_number) values (#{redEnvelopeNo}, #{userNo}, #{totalAmount}, #{totalNumber})")
    void save(RedEnvelopeEntity redPackageEntity);

    @Update("update t_red_envelope set stock = stock - 1 where red_envelope_no = #{RedEnvelopeNo}")
    void decreaseStock(String redPackageNo);
}

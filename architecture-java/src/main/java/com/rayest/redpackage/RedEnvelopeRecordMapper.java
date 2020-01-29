package com.rayest.redpackage;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RedEnvelopeRecordMapper {
    @Insert("insert into t_red_envelope_record (red_envelope_no, amount, user_no) values (#{redEnvelopeNo}, #{amount}, #{userNo})")
    void save(RedEnvelopeRecordEntity entity);

    // 需要确保 userNo 和 redPackageNo 为联合唯一索引
    @Select("select * from t_red_envelope_record where user_no = #{userNo} and red_envelope_no = #{redEnvelopeNo} for update")
    RedEnvelopeRecordEntity selectByUserNo(@Param("userNo") String userNo, @Param("redPackageNo") String redPackageNo);

    @Insert("insert into t_red_envelope_record (red_envelope_no, user_no, amount, memo) values (#{redEnvelopeNo}, #{userNo}, #{amount}, #{memo})")
    void add(RedEnvelopeRecordEntity redPackageDetailEntity);

}

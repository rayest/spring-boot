package com.rayest.redpackage;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RedPacketDetailMapper {
    @Insert("insert into t_red_package_detail (red_package_no, amount, user_no) values (#{redPackageNo}, #{amount}, #{userNo})")
    void save(RedPackageDetailEntity redPackageDetailEntity);

    // 需要确保 userNo 和 redPackageNo 为联合唯一索引
    @Select("select * from t_red_package_detail where user_no = #{userNo} and red_package_no = #{redPackageNo} for update")
    RedPackageDetailEntity selectByUserNo(@Param("userNo") String userNo, @Param("redPackageNo") String redPackageNo);
}

package com.rayest.redpackage;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface RedPacketMapper {
    @Insert("insert into t_red_package (red_package_no, user_no, total_amount, total_number) values (#{redPackageNo}, #{userNo}, #{totalAmount}, #{totalNumber})")
    void save(RedPackageEntity redPackageEntity);
}

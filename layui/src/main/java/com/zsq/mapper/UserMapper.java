package com.zsq.mapper;

import com.zsq.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("select * from oc_user where username=#{username} and password=#{password}")
    User login(User user);
    @Update("update oc_user set password = #{password} where username = #{username}")
    int changePassword(User user);
    @Select("select * from oc_user where email=#{email}")
    User selectUserByEmail(String email);

    void addUser(User newUser);
}

package com.ron.usercenterbackend.mapper;

import com.ron.usercenterbackend.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Ron_567
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2023-10-25 16:44:05
* @Entity com.ron.usercenterbackend.model.domain.User
*/

@Mapper
public interface UserMapper extends BaseMapper<User> {

}





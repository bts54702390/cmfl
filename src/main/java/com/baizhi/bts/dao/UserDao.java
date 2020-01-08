package com.baizhi.bts.dao;

import com.baizhi.bts.entity.User;
import com.baizhi.bts.entity.UserDot;
import org.springframework.data.repository.query.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface UserDao extends Mapper<User>, DeleteByIdListMapper<User,String> {
    //查询创建时间的分布
    public Integer selectTime(@Param("sex") String sex,@Param("day") Integer day);
    //地区分布
    public List<UserDot> selectLocation(String sex);

}

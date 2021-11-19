package com.fourwood.toymall.member.dao;

import com.fourwood.toymall.member.entity.MemberLoginLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员登录记录
 * 
 * @author fourwood
 * @email mlj2225042023@gmail.com
 * @date 2021-11-19 19:24:37
 */
@Mapper
public interface MemberLoginLogDao extends BaseMapper<MemberLoginLogEntity> {
	
}

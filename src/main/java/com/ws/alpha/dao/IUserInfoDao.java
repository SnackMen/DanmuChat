package com.ws.alpha.dao;

import com.ws.alpha.entiy.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author laowang
 */
@Repository
public interface IUserInfoDao{

    /**
     * 查询用户信息
     * @param openId
     * @return
     */
    @Select("select * from userinfo t where t.openId = #{openId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "openId", column = "openId"),
            @Result(property = "nickName", column = "nickname"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "language", column = "language"),
            @Result(property = "city", column = "city"),
            @Result(property = "provience", column = "provience"),
            @Result(property = "country", column = "country"),
            @Result(property = "headImgUrl", column = "headImgUrl"),
            @Result(property = "privilege", column = "privilege"),
    })
    UserInfo getUserInfo(String openId);

    /**
     * 插入用户数据
     * @param userInfo
     * @return
     */
    @Insert("insert into userinfo(openId, nickname, sex, language, city, provience, country, headImgUrl, privilege) values(#{openId}, #{nickName}, #{sex}, #{language}, #{city}, #{provience}, #{country}, #{headImgUrl}, #{privilege})")
    void saveUserInfo(UserInfo userInfo);

}

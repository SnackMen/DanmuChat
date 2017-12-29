package com.ws.alpha.service;

import com.ws.alpha.entiy.UserInfo;

/**
 * @author laowang
 */
public interface IUserInfoService {

    /**
     * 依据openId获取用户信息
     * @param openId
     * @return
     */
    UserInfo getUserInfo(String openId);

    /**
     * 插入用户信息
     * @param userInfo
     */
    void saveUserInfo(UserInfo userInfo);

}

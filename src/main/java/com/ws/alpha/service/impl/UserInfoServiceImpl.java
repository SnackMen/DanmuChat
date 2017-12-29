package com.ws.alpha.service.impl;

import com.ws.alpha.dao.IUserInfoDao;
import com.ws.alpha.entiy.UserInfo;
import com.ws.alpha.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author laowang
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService {

    @Autowired
    private IUserInfoDao iUserInfoDao;

    @Override
    public UserInfo getUserInfo(String openId) {
        return iUserInfoDao.getUserInfo(openId);
    }

    @Override
    public void saveUserInfo(UserInfo userInfo) {
        iUserInfoDao.saveUserInfo(userInfo);
    }
}

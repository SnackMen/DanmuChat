package com.ws.alpha.entiy;

import java.io.Serializable;

/**
 * @author laowang
 */
public class UserInfo implements Serializable {

    /**
     * 唯一性序号
     */
    private Integer id;

    /**
     * 每一个用户的openid
     */
    private String openId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户性别 1代表男 2代表女
     */
    private String sex;

    /**
     * 用户使用的语言
     */
    private String language;

    /**
     * 城市
     */
    private String city;

    /**
     * 省份
     */
    private String provience;

    /**
     * 地区
     */
    private String country;

    /**
     * 头像链接
     */
    private String headImgUrl;

    private String privilege;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvience() {
        return provience;
    }

    public void setProvience(String provience) {
        this.provience = provience;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserInfo{");
        sb.append("id=").append(id);
        sb.append(", openId='").append(openId).append('\'');
        sb.append(", nickName='").append(nickName).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", language='").append(language).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", provience='").append(provience).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", headImgUrl='").append(headImgUrl).append('\'');
        sb.append(", privilege='").append(privilege).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

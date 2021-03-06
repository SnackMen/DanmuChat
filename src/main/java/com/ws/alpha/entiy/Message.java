package com.ws.alpha.entiy;

/**
 * @author winson
 */
public class Message {

    /**
     * 微信名
     */
    private String nickName;

    /**
     * 头像
     */
    private String emojiPic;

    /**
     * 发送的信息
     */
    private String message;

    /**
     * 用户唯一标识符
     */
    private String openID;

    public String getOpenID() {
        return openID;
    }

    public void setOpenID(String openID) {
        this.openID = openID;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmojiPic() {
        return emojiPic;
    }

    public void setEmojiPic(String emojiPic) {
        this.emojiPic = emojiPic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

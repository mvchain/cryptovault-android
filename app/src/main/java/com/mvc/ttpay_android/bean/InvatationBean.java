package com.mvc.ttpay_android.bean;

import java.util.ArrayList;

public class InvatationBean {

    /**
     * code : 200
     * data : [{"createdAt":1547639892945,"email":"416350145@qq.com","nickname":"身家","userId":1}]
     * message :
     */

    private int code;
    private String message;
    private ArrayList<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createdAt : 1547639892945
         * email : 416350145@qq.com
         * nickname : 身家
         * userId : 1
         */

        private long createdAt;
        private String email;
        private String nickname;
        private int userId;

        public long getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(long createdAt) {
            this.createdAt = createdAt;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}

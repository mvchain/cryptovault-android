package com.mvc.cryptovault_android.bean;

public class UserInfoBean {

    /**
     * code : 200
     * data : {"headImage":"http://img1.imgtn.bdimg.com/it/u=3136752749,2550944052&fm=26&gp=0.jpg","nickname":"第一个用户","username":"188****8888"}
     * message :
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * headImage : http://img1.imgtn.bdimg.com/it/u=3136752749,2550944052&fm=26&gp=0.jpg
         * nickname : 第一个用户
         * username : 188****8888
         */

        private String headImage;
        private String nickname;
        private String username;

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}

package com.mvc.cryptovault_android.bean;

public class LoginBean {

    /**
     * code : 200
     * data : {"refreshToken":"lIjoiYXBwIiwidZFud2vVciTEBLM5xNzu4sRZaIxdehgnQeFA","token":"eyJ1c2VybmFtZSI6IjE4VCeJ3kAhIev4OHaYxkbHXEIjJmYs","userId":1}
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
         * refreshToken : eyJhbGcidXNlcklkIjoxLCJzZXJ2aWNlIjoiYXBwIiwidHlwZSI6InJlZnJlc2giLCJleHAiOjE1NDM5MjE4Mzh9.48EXkjrQZFud2vVciTEBLM5xNzu4sRZaIxdehgnQeFA
         * token : lcklkIjoxLCJzZXJ2aWNlIjoiYXBwIiwidHlwZSI6InRva2VuIiwiZXhwIjoxNTQzMzIzMDg2fQ.WIN2gtguw3NLm5LVCeJ3kAhIev4OHaYxkbHXEIjJmYs
         * userId : 1
         */

        private String refreshToken;
        private String token;
        private int userId;

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}

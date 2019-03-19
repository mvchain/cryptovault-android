package com.mvc.cryptovault_android.bean;

public class LoginValidBean {

    /**
     * code : 200
     * data : {"result":"{\"success\":1,\"challenge\":\"b6518bb85e4088fdc74696575f999986\",\"gt\":\"72c185418d3e01c021109d660190a5af\"}","status":1,"uid":"c2f42f84-13c6-4446-bbdf-7e0e416d29f8"}
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
         * result : {"success":1,"challenge":"b6518bb85e4088fdc74696575f999986","gt":"72c185418d3e01c021109d660190a5af"}
         * status : 1
         * uid : c2f42f84-13c6-4446-bbdf-7e0e416d29f8
         */

        private String result;
        private int status;
        private String uid;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}

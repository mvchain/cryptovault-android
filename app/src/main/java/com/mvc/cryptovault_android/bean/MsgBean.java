package com.mvc.cryptovault_android.bean;

import java.util.List;

public class MsgBean {

    /**
     * code : 200
     * data : [{"createdAt":1542358128486,"id":2,"message":"测试消息2","messageId":1,"messageType":"TEST","read":0,"status":1,"updatedAt":1542358128486},{"createdAt":1542358128485,"id":1,"message":"测试消息1","messageId":1,"messageType":"TEST","read":1,"status":1,"updatedAt":1542358128485}]
     * message :
     */

    private int code;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createdAt : 1542358128486
         * id : 2
         * message : 测试消息2
         * messageId : 1
         * messageType : TEST
         * read : 0
         * status : 1
         * updatedAt : 1542358128486
         */

        private long createdAt;
        private int id;
        private String message;
        private int messageId;
        private String messageType;
        private int read;
        private int status;
        private long updatedAt;

        public long getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(long createdAt) {
            this.createdAt = createdAt;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getMessageId() {
            return messageId;
        }

        public void setMessageId(int messageId) {
            this.messageId = messageId;
        }

        public String getMessageType() {
            return messageType;
        }

        public void setMessageType(String messageType) {
            this.messageType = messageType;
        }

        public int getRead() {
            return read;
        }

        public void setRead(int read) {
            this.read = read;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(long updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}

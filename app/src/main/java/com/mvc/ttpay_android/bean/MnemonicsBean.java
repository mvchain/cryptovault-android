package com.mvc.ttpay_android.bean;

import java.util.ArrayList;

public class MnemonicsBean {
    /**
     * code : 200
     * data : {"mnemonics":["cabin","control","swamp","amazing","agent","job","fortune","prize","police","cloth","rent","monitor","hole","mixed","jar"],"privateKey":"1fc5ef6c83e04ef016e55aa7a57ad94786cb1c5d"}
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
         * mnemonics : ["cabin","control","swamp","amazing","agent","job","fortune","prize","police","cloth","rent","monitor","hole","mixed","jar"]
         * privateKey : 1fc5ef6c83e04ef016e55aa7a57ad94786cb1c5d
         */
        private String privateKey;
        private ArrayList<String> mnemonics;

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }

        public ArrayList<String> getMnemonics() {
            return mnemonics;
        }

        public void setMnemonics(ArrayList<String> mnemonics) {
            this.mnemonics = mnemonics;
        }
    }
}

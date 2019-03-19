package com.mvc.cryptovault_android.bean;

public class ValidResultBean {

    /**
     * geetest_challenge : c7f4f98118297651626aa1690f2816569d
     * geetest_seccode : 915d44f1d9e8f3b04f2f94da4b8ec0e6|jordan
     * geetest_validate : 915d44f1d9e8f3b04f2f94da4b8ec0e6
     */

    private String geetest_challenge;
    private String geetest_seccode;
    private String geetest_validate;

    public String getGeetest_challenge() {
        return geetest_challenge;
    }

    public void setGeetest_challenge(String geetest_challenge) {
        this.geetest_challenge = geetest_challenge;
    }

    public String getGeetest_seccode() {
        return geetest_seccode;
    }

    public void setGeetest_seccode(String geetest_seccode) {
        this.geetest_seccode = geetest_seccode;
    }

    public String getGeetest_validate() {
        return geetest_validate;
    }

    public void setGeetest_validate(String geetest_validate) {
        this.geetest_validate = geetest_validate;
    }
}

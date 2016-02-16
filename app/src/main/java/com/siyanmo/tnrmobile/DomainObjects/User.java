package com.siyanmo.tnrmobile.DomainObjects;

/**
 * Created by Administrator on 2016-02-15.
 */
public class User {
    private String LoginName;
    private String Password;

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String userName) {
        LoginName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String passWord) {
        Password = passWord;
    }
}

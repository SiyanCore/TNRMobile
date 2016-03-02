package com.siyanmo.tnrmobile.DomainObjects;

/**
 * Created by Administrator on 2016-02-16.
 */
public class SalesExecutive {
    private int SalesExecutiveCode;
    private String SalesExecutiveName;
    private int LoginId;
    private byte[] Image =new byte[0];

    public int getSalesExecutiveCode() {
        return SalesExecutiveCode;
    }

    public void setSalesExecutiveCode(int salesExecutiveCode) {
        SalesExecutiveCode = salesExecutiveCode;
    }

    public String getSalesExecutiveName() {
        return SalesExecutiveName;
    }

    public void setSalesExecutiveName(String salesExecutiveName) {
        SalesExecutiveName = salesExecutiveName;
    }

    public int getLoginId() {
        return LoginId;
    }

    public void setLoginId(int loginId) {
        LoginId = loginId;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image =new byte[image.length];
        Image = image;
    }
}

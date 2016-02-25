package com.siyanmo.tnrmobile.DomainObjects;

/**
 * Created by Administrator on 2016-02-16.
 */
public class SalesExecutive {
    private int SalesExecutiveCode;
    private String SalesExecutiveName;
    private int LoginId;
    private byte[] SalesExecutiveImage=new byte[0];

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

    public byte[] getSalesExecutiveImage() {
        return SalesExecutiveImage;
    }

    public void setSalesExecutiveImage(byte[] salesExecutiveImage) {
        salesExecutiveImage=new byte[salesExecutiveImage.length];
        SalesExecutiveImage = salesExecutiveImage;
    }
}

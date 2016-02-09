package com.siyanmo.tnrmobile.DomainObjects;

/**
 * Created by Lovindu on 2/8/2016.
 */
public class Item {
    private String ItemCode ;
    private Float StockInHandUnits ;
    private Float SellingPrice ;
    private String ItemNameShown ;

    public String getItemCode() {
        return ItemCode;
    }

    public Float getStockInHandUnits() {
        return StockInHandUnits;
    }

    public Float getSellingPrice() {
        return SellingPrice;
    }

    public String getItemNameShown() {
        return ItemNameShown;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public void setStockInHandUnits(Float sqFt) {
        StockInHandUnits = sqFt;
    }

    public void setSellingPrice(Float sellingPrice) {
        SellingPrice = sellingPrice;
    }

    public void setItemNameShown(String itemNameShown) {
        ItemNameShown = itemNameShown;
    }
}

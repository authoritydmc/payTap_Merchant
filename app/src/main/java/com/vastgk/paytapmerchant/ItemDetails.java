package com.vastgk.paytapmerchant;

public class ItemDetails {
    private  String iconUrl="NOURL";
    private  String itemName="itemName";
    private String itemCode="defaultItemCOde";
    private  String itemPrice="0.0";
    private  int itemQuantity=0;

    public ItemDetails(String iconUrl, String itemName, String itemCode, String itemPrice, int itemQuantity) {
        this.iconUrl = iconUrl;
        this.itemName = itemName;
        this.itemCode = itemCode;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}

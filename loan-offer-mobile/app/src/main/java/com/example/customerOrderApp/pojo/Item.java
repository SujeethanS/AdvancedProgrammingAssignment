package com.example.customerOrderApp.pojo;

public class Item {
    private Integer itemId = 0;
    private String itemNo = "n/l";
    private String itemName = "item";
    private Double itemQty = 0.0;
    private String itemCategory = "n/l";
    private String itemSubCategory = "n/l";
    private Double itemSellingPrice = 0.0;
    private Double itemPurchasePrice = 0.0;
    private String itemExpiryDate = "n/l";


    private Double itemReorderQty = 0.0;
    private Double itemMaxDiscount = 0.0;
    private Integer itemMaxDiscountType = 1;
    private String itemUMO = "No";
    private String itemTrackItem = "Y";
    private Double itemDefaultDiscount = 0.0;
    private Integer itemDefaultDiscountType = 1;
    private Integer itemLocationId = 1 ;
    private Double itemFreeQty = 0.0 ;
    private String itemBatchNo = "n/l" ;
    private String supplierId = "n/l" ;

    private Double itemAveCoast=0.0;
    //aditional
    private String itemVatCode = "1";
    private String productType = "P";
    private Integer bid = 0;
    private String itemBidText = "n/l";
    private String allowSales = "1";
    private int industryType = 1;

    private String vatCode1 = "n/l";
    private String vatCode2 = "n/l";
    private String vatCode3 = "n/l";
    private String vatCode4 = "n/l";

    private  boolean selected = false;


    public Item(Integer itemId, String itemNo, String itemName, Double itemQty, String itemCategory, String itemSubCategory, Double itemSellingPrice, Double itemPurchasePrice, Double itemReorderQty, Double itemMaxDiscount, String itemVatCode, String itemUMO, String itemTrackItem) {
        this.itemId = itemId;
        this.itemNo = itemNo;
        this.itemName = itemName;
        this.itemQty = itemQty;
        this.itemCategory = itemCategory;
        this.itemSubCategory = itemSubCategory;
        this.itemSellingPrice = itemSellingPrice;
        this.itemPurchasePrice = itemPurchasePrice;
        this.itemReorderQty = itemReorderQty;
        this.itemMaxDiscount = itemMaxDiscount;
        this.itemVatCode = itemVatCode;
        this.itemUMO = itemUMO;
        this.itemTrackItem = itemTrackItem;
    }

    //with maxDiscountType, defaultDiscount, productType
    public Item(Integer itemId, String itemNo, String itemName, Double itemQty, String itemCategory, String itemSubCategory, Double itemSellingPrice, Double itemPurchasePrice, Double itemReorderQty, Double itemMaxDiscount, String itemVatCode, String itemUMO, String itemTrackItem, Integer itemMaxDiscountType, Double itemDefaultDiscount, String productType, Integer itemDefaultDiscountType, Integer bid) {
        this.itemId = itemId;
        this.itemNo = itemNo;
        this.itemName = itemName;
        this.itemQty = itemQty;
        this.itemCategory = itemCategory;
        this.itemSubCategory = itemSubCategory;
        this.itemSellingPrice = itemSellingPrice;
        this.itemPurchasePrice = itemPurchasePrice;
        this.itemReorderQty = itemReorderQty;
        this.itemMaxDiscount = itemMaxDiscount;
        this.itemVatCode = itemVatCode;
        this.itemUMO = itemUMO;
        this.itemTrackItem = itemTrackItem;
        this.itemMaxDiscountType = itemMaxDiscountType;
        this.itemDefaultDiscount = itemDefaultDiscount;
        this.productType = productType;
        this.itemDefaultDiscountType = itemDefaultDiscountType;
        this.bid = bid;
    }

    public Item(){

    }


    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getItemQty() {
        return itemQty;
    }

    public void setItemQty(Double temQty) {
        this.itemQty = temQty;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemSubCategory() {
        return itemSubCategory;
    }

    public void setItemSubCategory(String itemSubCategory) {
        this.itemSubCategory = itemSubCategory;
    }

    public Double getItemSellingPrice() {
        return itemSellingPrice;
    }

    public void setItemSellingPrice(Double itemSellingPrice) {
        this.itemSellingPrice = itemSellingPrice;
    }

    public Double getItemPurchasePrice() {
        return itemPurchasePrice;
    }

    public void setItemPurchasePrice(Double itemPurchasePrice) {
        this.itemPurchasePrice = itemPurchasePrice;
    }

    public Double getItemReorderQty() {
        return itemReorderQty;
    }

    public void setItemReorderQty(Double itemReorderQty) {
        this.itemReorderQty = itemReorderQty;
    }

    public Double getItemMaxDiscount() {
        return itemMaxDiscount;
    }

    public void setItemMaxDiscount(Double itemMaxDiscount) {
        this.itemMaxDiscount = itemMaxDiscount;
    }

    public String getItemVatCode() {
        return itemVatCode;
    }

    public void setItemVatCode(String itemVatCode) {
        this.itemVatCode = itemVatCode;
    }

    public String getItemUMO() {
        return itemUMO;
    }

    public void setItemUMO(String itemUMO) {
        this.itemUMO = itemUMO;
    }

    public String getItemTrackItem() {
        return itemTrackItem;
    }

    public void setItemTrackItem(String itemTrackItem) {

        this.itemTrackItem = itemTrackItem;
    }

    //aditional get and set
    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }


    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getItemExpiryDate() {
        return itemExpiryDate;
    }

    public void setItemExpiryDate(String itemExpiryDate) {
        this.itemExpiryDate = itemExpiryDate;
    }

    public Integer getItemMaxDiscountType() {
        return itemMaxDiscountType;
    }

    public void setItemMaxDiscountType(Integer itemMaxDiscountType) {
        this.itemMaxDiscountType = itemMaxDiscountType;
    }

    public Double getItemDefaultDiscount() {
        return itemDefaultDiscount;
    }

    public void setItemDefaultDiscount(Double itemDefaultDiscount) {
        this.itemDefaultDiscount = itemDefaultDiscount;
    }

    public Integer getItemDefaultDiscountType() {
        return itemDefaultDiscountType;
    }

    public void setItemDefaultDiscountType(Integer itemDefaultDiscountType) {
        this.itemDefaultDiscountType = itemDefaultDiscountType;
    }

    public Integer getItemLocationId() {
        return itemLocationId;
    }

    public void setItemLocationId(Integer itemLocationId) {
        this.itemLocationId = itemLocationId;
    }

    public Double getItemFreeQty() {
        return itemFreeQty;
    }

    public void setItemFreeQty(Double itemFreeQty) {
        this.itemFreeQty = itemFreeQty;
    }

    public String getItemBatchNo() {
        return itemBatchNo;
    }

    public void setItemBatchNo(String itemBatchNo) {
        this.itemBatchNo = itemBatchNo;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public Double getItemAveCoast() {
        return itemAveCoast;
    }

    public void setItemAveCoast(Double itemAveCoast) {
        this.itemAveCoast = itemAveCoast;
    }

    public String getItemBidText() {
        return itemBidText;
    }

    public void setItemBidText(String itemBidText) {
        this.itemBidText = itemBidText;
    }

    public String getAllowSales() {
        return allowSales;
    }

    public void setAllowSales(String allowSales) {
        this.allowSales = allowSales;
    }

    public int getIndustryType() {
        return industryType;
    }

    public void setIndustryType(int industryType) {
        this.industryType = industryType;
    }

    public String getVatCode1() {
        return vatCode1;
    }

    public void setVatCode1(String vatCode1) {
        this.vatCode1 = vatCode1;
    }

    public String getVatCode2() {
        return vatCode2;
    }

    public void setVatCode2(String vatCode2) {
        this.vatCode2 = vatCode2;
    }

    public String getVatCode3() {
        return vatCode3;
    }

    public void setVatCode3(String vatCode3) {
        this.vatCode3 = vatCode3;
    }

    public String getVatCode4() {
        return vatCode4;
    }

    public void setVatCode4(String vatCode4) {
        this.vatCode4 = vatCode4;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemCategory='" + itemCategory + '\'' +
                ", itemSubCategory='" + itemSubCategory + '\'' +
                ", itemSellingPrice=" + itemSellingPrice +
                '}';
    }
}
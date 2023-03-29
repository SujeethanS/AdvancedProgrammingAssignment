package com.example.customerOrderApp.pojo;

public class OrderDetails implements Cloneable{

    private Integer orderDetailsId;
    private String oderDetailsOrderId = "1" ;
    private String orderDetailsSalesMan = "Admin";
    private String orderDetailsItemNumber;
    private String orderDetailsItemName = "n/l";
    private Double orderDetailsItemQty = 1.0;
    private Double orderDetailsItemPrice = 0.0;
    private Double orderDetailsPurchasePrice = 0.0;
    private Integer orderDetailsSyncStatus = 0;
    private int orderDetailsPatchId = 10001;
    private String orderDetailsItemCategory = "Others";
    private String orderDetailsCompanyId ;
    private Double orderDetailsDiscount = 0.0;
    private Double orderDetailsItemDiscount = 0.0;
    private Double orderDetailsItemMaxDiscount = 0.0;
    private Double orderDetailsRegularPrice = 0.0;
    private String uom = "No";
    private Integer vatCode = 0;
    private Double itemSellingPriceTotal = 0.0;
    private Double orderDetailsItemSellingPrice = 0.0;
    private String orderDetailsProductType = "P";
    private String expiryDate = "n/l";
    private int defaultType = 1;
    private int maxType = 1;
    private String subCategory = "Others";
    private String trackItem = "Y";
    private String salesType = "normal";
    private Double reOrder = 0.0;
    private int locationId = 1;
    private Double orderDetailsWholeQty = 5.0;


    public OrderDetails(Integer orderDetailsId, String oderDetailsOrderId, String orderDetailsSalesMan, String orderDetailsItemNumber, String orderDetailsItemName, Double orderDetailsItemQty, Double orderDetailsItemPrice, Double orderDetailsPurchasePrice, Integer orderDetailsSyncStatus, int orderDetailsPatchId, String orderDetailsItemCategory, String orderDetailsCompanyId, Double orderDetailsDiscount, Double orderDetailsItemDiscount, Double orderDetailsItemMaxDiscount, Double orderDetailsRegularPrice, String uom, Integer vatCode, Double itemSellingPriceTotal, Double orderDetailsItemSellingPrice, Double orderDetailsWholeQty) {
        this.orderDetailsId = orderDetailsId;
        this.oderDetailsOrderId = oderDetailsOrderId;
        this.orderDetailsSalesMan = orderDetailsSalesMan;
        this.orderDetailsItemNumber = orderDetailsItemNumber;
        this.orderDetailsItemName = orderDetailsItemName;
        this.orderDetailsItemQty = orderDetailsItemQty;
        this.orderDetailsItemPrice = orderDetailsItemPrice;
        this.orderDetailsPurchasePrice = orderDetailsPurchasePrice;
        this.orderDetailsSyncStatus = orderDetailsSyncStatus;
        this.orderDetailsPatchId = orderDetailsPatchId;
        this.orderDetailsItemCategory = orderDetailsItemCategory;
        this.orderDetailsCompanyId = orderDetailsCompanyId;
        this.orderDetailsDiscount = orderDetailsDiscount;
        this.orderDetailsItemDiscount = orderDetailsItemDiscount;
        this.orderDetailsItemMaxDiscount = orderDetailsItemMaxDiscount;
        this.orderDetailsRegularPrice = orderDetailsRegularPrice;
        this.uom = uom;
        this.vatCode = vatCode;
        this.itemSellingPriceTotal = itemSellingPriceTotal;
        this.orderDetailsItemSellingPrice = orderDetailsItemSellingPrice;
        this.orderDetailsWholeQty = orderDetailsWholeQty;
    }

    public String getOderDetailsOrderId() {
        return oderDetailsOrderId;
    }

    /*public Double getOrderDetailsWholeQty() {
        return orderDetailsWholeQty;
    }*/

    /*public void setOrderDetailsWholeQty(Double orderDetailsWholeQty) {
        this.orderDetailsWholeQty = orderDetailsWholeQty;
    }*/

    public OrderDetails(Integer orderDetailsId, String oderDetailsOrderId, String orderDetailsSalesMan, String orderDetailsItemNumber, String orderDetailsItemName, Double orderDetailsItemQty, Double orderDetailsItemPrice, Double orderDetailsPurchasePrice, Integer orderDetailsSyncStatus, int orderDetailsPatchId, String orderDetailsItemCategory, String orderDetailsCompanyId, Double orderDetailsDiscount, Double orderDetailsRegularPrice, String uom, Integer vatCode, Double itemSellingPriceTotal, Double orderDetailsWholeQty) {
        this.orderDetailsId = orderDetailsId;
        this.oderDetailsOrderId = oderDetailsOrderId;
        this.orderDetailsSalesMan = orderDetailsSalesMan;
        this.orderDetailsItemNumber = orderDetailsItemNumber;
        this.orderDetailsItemName = orderDetailsItemName;
        this.orderDetailsItemQty = orderDetailsItemQty;
        this.orderDetailsItemPrice = orderDetailsItemPrice;
        this.orderDetailsPurchasePrice = orderDetailsPurchasePrice;
        this.orderDetailsSyncStatus = orderDetailsSyncStatus;
        this.orderDetailsPatchId = orderDetailsPatchId;
        this.orderDetailsItemCategory = orderDetailsItemCategory;
        this.orderDetailsCompanyId = orderDetailsCompanyId;
        this.orderDetailsDiscount = orderDetailsDiscount;
        this.orderDetailsRegularPrice = orderDetailsRegularPrice;
        this.uom = uom;
        this.vatCode = vatCode;
        this.itemSellingPriceTotal = itemSellingPriceTotal;
        this.orderDetailsWholeQty = orderDetailsWholeQty;
    }

    public OrderDetails(Integer orderDetailsId, String rderDetailsOrderId, String orderDetailsSalesMan, String orderDetailsItemNumber, String orderDetailsItemName, Double orderDetailsItemQty, Double orderDetailsItemPrice, Double orderDetailsPurchasePrice, Integer orderDetailsSyncStatus, int orderDetailsPatchId, String orderDetailsItemCategory, String orderDetailsCompanyId, Double orderDetailsDiscount, Double orderDetailsRegularPrice) {
        this.orderDetailsId = orderDetailsId;
        this.oderDetailsOrderId = rderDetailsOrderId;
        this.orderDetailsSalesMan = orderDetailsSalesMan;
        this.orderDetailsItemNumber = orderDetailsItemNumber;
        this.orderDetailsItemName = orderDetailsItemName;
        this.orderDetailsItemQty = orderDetailsItemQty;
        this.orderDetailsItemPrice = orderDetailsItemPrice;
        this.orderDetailsPurchasePrice = orderDetailsPurchasePrice;
        this.orderDetailsSyncStatus = orderDetailsSyncStatus;
        this.orderDetailsPatchId = orderDetailsPatchId;
        this.orderDetailsItemCategory = orderDetailsItemCategory;
        this.orderDetailsCompanyId = orderDetailsCompanyId;
        this.orderDetailsDiscount = orderDetailsDiscount;
        this.orderDetailsRegularPrice = orderDetailsRegularPrice;
    }

    public OrderDetails() {

    }
    public OrderDetails(Integer orderDetailsId, String orderDetailsOrderId, String orderDetailsItemNumber, String orderDetailsItemName, Double orderDetailsItemQty, Double orderDetailsItemPrice, Double orderDetailsPurchasePrice) {

    }

    public Integer getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(Integer orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    public String getRderDetailsOrderId() {
        return oderDetailsOrderId;
    }

    public void setOderDetailsOrderId(String rderDetailsOrderId) {
        this.oderDetailsOrderId = rderDetailsOrderId;
    }

    public String getOrderDetailsSalesMan() {
        return orderDetailsSalesMan;
    }

    public void setOrderDetailsSalesMan(String orderDetailsSalesMan) {
        this.orderDetailsSalesMan = orderDetailsSalesMan;
    }

    public String getOrderDetailsItemNumber() {
        return orderDetailsItemNumber;
    }

    public void setOrderDetailsItemNumber(String orderDetailsItemNumber) {
        this.orderDetailsItemNumber = orderDetailsItemNumber;
    }

    public String getOrderDetailsItemName() {
        return orderDetailsItemName;
    }

    public void setOrderDetailsItemName(String orderDetailsItemName) {
        this.orderDetailsItemName = orderDetailsItemName;
    }

    public Double getOrderDetailsItemQty() {
        return orderDetailsItemQty;
    }

    public void setOrderDetailsItemQty(Double orderDetailsItemQty) {
        this.orderDetailsItemQty = orderDetailsItemQty;
    }

    public Double getOrderDetailsItemPrice() {
        return orderDetailsItemPrice;
    }

    public void setOrderDetailsItemPrice(Double orderDetailsItemPrice) {
        this.orderDetailsItemPrice = orderDetailsItemPrice;
    }

    public Double getOrderDetailsPurchasePrice() {
        return orderDetailsPurchasePrice;
    }

    public void setOrderDetailsPurchasePrice(Double orderDetailsPurchasePrice) {
        this.orderDetailsPurchasePrice = orderDetailsPurchasePrice;
    }

    public Integer getOrderDetailsSyncStatus() {
        return orderDetailsSyncStatus;
    }

    public void setOrderDetailsSyncStatus(Integer orderDetailsSyncStatus) {
        this.orderDetailsSyncStatus = orderDetailsSyncStatus;
    }

    public int getOrderDetailsPatchId() {
        return orderDetailsPatchId;
    }

    public void setOrderDetailsPatchId(int orderDetailsPatchId) {
        this.orderDetailsPatchId = orderDetailsPatchId;
    }

    public String getOrderDetailsItemCategory() {
        return orderDetailsItemCategory;
    }

    public void setOrderDetailsItemCategory(String orderDetailsItemCategory) {
        this.orderDetailsItemCategory = orderDetailsItemCategory;
    }

    public String getOrderDetailsCompanyId() {
        return orderDetailsCompanyId;
    }

    public void setOrderDetailsCompanyId(String orderDetailsCompanyId) {
        this.orderDetailsCompanyId = orderDetailsCompanyId;
    }

    public Double getOrderDetailsDiscount() {
        return orderDetailsDiscount;
    }

    public void setOrderDetailsDiscount(Double orderDetailsDiscount) {
        this.orderDetailsDiscount = orderDetailsDiscount;
    }

    public Double getOrderDetailsRegularPrice() {
        return orderDetailsRegularPrice;
    }

    public void setOrderDetailsRegularPrice(Double orderDetailsRegularPrice) {
        this.orderDetailsRegularPrice = orderDetailsRegularPrice;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public Integer getVatCode() {
        return vatCode;
    }

    public void setVatCode(Integer vatCode) {
        this.vatCode = vatCode;
    }

    public Double getItemSellingPriceTotal() {
        return itemSellingPriceTotal;
    }

    public void setItemSellingPriceTotal(Double itemSellingPriceTotal) {
        this.itemSellingPriceTotal = itemSellingPriceTotal;
    }

    public Double getOrderDetailsItemDiscount() {
        return orderDetailsItemDiscount;
    }

    public void setOrderDetailsItemDiscount(Double orderDetailsItemDiscount) {
        this.orderDetailsItemDiscount = orderDetailsItemDiscount;
    }

    public Double getOrderDetailsItemMaxDiscount() {
        return orderDetailsItemMaxDiscount;
    }

    public void setOrderDetailsItemMaxDiscount(Double orderDetailsItemMaxDiscount) {
        this.orderDetailsItemMaxDiscount = orderDetailsItemMaxDiscount;
    }

    public Double getOrderDetailsItemSellingPrice() {
        return orderDetailsItemSellingPrice;
    }

    public void setOrderDetailsItemSellingPrice(Double orderDetailsItemSellingPrice) {
        this.orderDetailsItemSellingPrice = orderDetailsItemSellingPrice;
    }

    public String getOrderDetailsProductType() {
        return orderDetailsProductType;
    }

    public void setOrderDetailsProductType(String orderDetailsProductType) {
        this.orderDetailsProductType = orderDetailsProductType;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getDefaultType() {
        return defaultType;
    }

    public void setDefaultType(int defaultType) {
        this.defaultType = defaultType;
    }

    public int getMaxType() {
        return maxType;
    }

    public void setMaxType(int maxType) {
        this.maxType = maxType;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getTrackItem() {
        return trackItem;
    }

    public void setTrackItem(String trackItem) {
        this.trackItem = trackItem;
    }

    public Double getReOrder() {
        return reOrder;
    }

    public void setReOrder(Double reOrder) {
        this.reOrder = reOrder;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
        // this line will return you an independent
        // object, the changes made to this object will
        // not be reflected to other object

    }
}

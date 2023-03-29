package com.example.customerOrderApp.pojo;

public class TopTen {

        private String itemsName ;
        private Double qty;
        private Double salesTotal = 0.0;

        public TopTen (){}

        public String getItemsName() {
            return itemsName;
        }

        public void setItemsName(String itemsName) {
            this.itemsName = itemsName;
        }

        public Double getQty() {
            return qty;
        }

        public void setQty(Double qty) {
            this.qty = qty;
        }

        public void setSalesTotal(Double salesTotal) {
            this.salesTotal = salesTotal;
        }


}

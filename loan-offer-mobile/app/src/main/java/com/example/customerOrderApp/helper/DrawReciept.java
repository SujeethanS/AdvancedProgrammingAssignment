package com.example.customerOrderApp.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.util.Log;

import com.example.customerOrderApp.AppInit;
import com.example.customerOrderApp.DrawRicept.ReceiptBuilder;
import com.example.customerOrderApp.pojo.Company;
import com.example.customerOrderApp.pojo.Order;
import com.example.customerOrderApp.pojo.OrderDetails;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DrawReciept {

    int noofItems=0;
    Double dis = 0.00;
    private DecimalFormat df = new DecimalFormat("#,##0.00");
    int fontSizeA = 25*2;
    int fontSizeB = 20*2;
    int fontSizeC = 15*2;
    int fontSizeD = 10*2;
    final int noOfCharLength = 47;
    String userName = "sujee";
     Context context;

    ReceiptBuilder receipt = new ReceiptBuilder(310*2);
    Bitmap bitmap = null;

    public DrawReciept(Context mContext){
        try {
            this.context = mContext;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Bitmap salesPrintReceiptNormalImage(Order order, List<OrderDetails> orderDetails, Company company, String type , String state){

        receipt.setMargin(30, 10);
        receipt.setAlign(Paint.Align.CENTER);
        receipt.setColor(Color.BLACK);
        receipt.setTypeface(AppInit.appContext, "fonts/RobotoMono-Regular.ttf");

        String person = AppSettings.getPrinterUser(context);
        printHeader(type,company,state);
        receipt.setAlign(Paint.Align.LEFT);
        printTop(String.valueOf(order.getOrderId()),type+" #",person);
        printSalesBodyNormal(orderDetails);
        printBottom(order);
        printFooter(company);

        bitmap = receipt.build();
        try {
            saveBarcodeAsImage(receipt.build());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private void printSalesBodyNormal(List<OrderDetails> printOrder){
        int itemNumberSpace;
        int itemNameSpace = 0;
        StringBuffer itemDetails = new StringBuffer();
        int numofSpace;
        //es.print_linefeed();

        itemDetails.append(" ItemName        Qty       Price         Amount" + "\n");

        itemDetails.append("\n");
        receipt.addText(itemDetails.toString(),true);
        //es.print_linefeed();
        itemDetails = new StringBuffer();
        receipt.addText("\n");

        for(OrderDetails order : printOrder){
            noofItems++;
            if(order.getOrderDetailsDiscount() != null) {
                dis = dis + order.getOrderDetailsDiscount();
            }

            String orderItemName = order.getOrderDetailsItemName();
            String fistLetter = orderItemName.substring(0, 1).toUpperCase();
            String otherLetter = orderItemName.substring(1, orderItemName.length()).toLowerCase();
            itemDetails.append(" ").append(fistLetter);
            if (otherLetter.length() >= 16) {
                itemDetails.append(otherLetter.substring(0, 15));// for print item name
                int lowCharLength = 17;

                for (int x = 0; x < lowCharLength - 16; x++) {
                    itemDetails.append(" ");
                }
            } else {
                itemDetails.append(otherLetter);// for print item name//
                int lowCharLength = 17;

                for (int x = 0; x < lowCharLength - orderItemName.length(); x++) {
                    itemDetails.append(" ");
                }
            }

            Double div = (order.getOrderDetailsItemQty()) % 1;
            String displayQty;

            if (!order.getUom().equalsIgnoreCase("no")) {
                int itemQuntityCharLength;
                if (div == 0.0) {
                    long k = ((order.getOrderDetailsItemQty())).longValue();
                    displayQty = Long.toString(k);
                    itemDetails.append(displayQty).append(" ").append(order.getUom());
                    itemQuntityCharLength = 13 - (displayQty.length() + 1 + order.getUom().length()+df.format(order.getOrderDetailsItemPrice()).length());
                } else {
                    itemDetails.append(df.format(order.getOrderDetailsItemQty())).append(" ").append(order.getUom());
                    itemQuntityCharLength = 13 - (df.format(order.getOrderDetailsItemQty()).length() + 1 + order.getUom().length()+String.valueOf(order.getOrderDetailsItemPrice()).length());
                }

                for (int i = 0; i < itemQuntityCharLength; i++) {
                    itemDetails.append(" ");// for printing character in a proper Alignment
                }
            } else {
                if (div == 0.0) {
                    long k = ((order.getOrderDetailsItemQty())).longValue();
                    displayQty = Long.toString(k);
                    itemDetails.append(displayQty);
                } else {
                    itemDetails.append(df.format(order.getOrderDetailsItemQty()));
                }

                if (div == 0.0) {
                    long k = ((order.getOrderDetailsItemQty())).longValue();
                    displayQty = Long.toString(k);
                    int itemQuntityCharLength = 13 - (displayQty.length()+df.format(order.getOrderDetailsItemPrice() - order.getOrderDetailsItemDiscount()).length());
                    for (int i = 0; i < itemQuntityCharLength; i++) {
                        itemDetails.append(" ");// for printing character in a proper Alignment
                    }
                } else {
                    long k = ((order.getOrderDetailsItemQty())).longValue();
                    displayQty = Long.toString(k);
                    int itemQuntityCharLength = 10 - (displayQty.length()+df.format(order.getOrderDetailsItemPrice()- order.getOrderDetailsItemDiscount()).length());
                    for (int i = 0; i < itemQuntityCharLength; i++) {
                        itemDetails.append(" ");// for printing character in a proper Alignment
                    }
                }
            }
            itemDetails.append(" ");// for printing character in a proper Alignment
            itemDetails.append(df.format(order.getOrderDetailsItemPrice() +  order.getOrderDetailsItemDiscount())); // for print 1 item price
            int oneItemPricerCharLength = 15 - df.format((order.getOrderDetailsItemPrice() - order.getOrderDetailsItemDiscount()) * order.getOrderDetailsItemQty()).length();
            for(int l=0; l<oneItemPricerCharLength;l++){
                itemDetails.append(" ");
            }
            if (order.getVatCode() == 1) {
                //itemPriceChar = 15;
                int itemPriceCharLength = 12 - df.format(order.getOrderDetailsItemPrice() * order.getOrderDetailsItemQty() ).length();
                itemDetails.append(df.format((order.getOrderDetailsItemPrice()  )* order.getOrderDetailsItemQty())); // for print item price
            } else {
                int itemPriceCharLength = 12 - df.format(order.getOrderDetailsItemPrice()* order.getOrderDetailsItemQty()).length();
                itemDetails.append(df.format((order.getOrderDetailsItemPrice())* order.getOrderDetailsItemQty())); // for print item price
            }
            itemDetails.append("\n");
            receipt.addText(itemDetails.toString(),true);
            itemDetails = new StringBuffer();
            if (order.getOrderDetailsItemDiscount() > 0.0) {
                itemDetails.append(" ");// for printing character space
                itemDetails.append("Reg "); // for print reqular price label
                int regularPriceLength = 15 - df.format((order.getOrderDetailsItemPrice()+ order.getOrderDetailsItemDiscount()) * order.getOrderDetailsItemQty()).length();
                itemDetails.append(" ");// for printing character space
                Log.e("eeeeeeeeeeee",""+order.getOrderDetailsItemPrice());
                itemDetails.append(df.format((order.getOrderDetailsItemPrice()+ order.getOrderDetailsItemDiscount())  * order.getOrderDetailsItemQty())); // for price reqular price
                for (int i = 0; i < regularPriceLength; i++) {
                    itemDetails.append(" ");// for printing character in a proper Alignment
                }
                itemDetails.append(" ");// for printing character space
                itemDetails.append("Dis"); // for print discount label
                int oneItemDiscountCharLength = 15 - df.format(order.getOrderDetailsItemDiscount() * order.getOrderDetailsItemQty() ).length();
                itemDetails.append(" ");// for printing character space
                itemDetails.append("-"); // for print discount indicate
                itemDetails.append(df.format(order.getOrderDetailsItemDiscount() * order.getOrderDetailsItemQty()));  // for print 1 item discount
                for (int i = 0; i < oneItemDiscountCharLength; i++) {
                    itemDetails.append(" ");// for printing character in a proper Alignment
                }
                itemDetails.append("\n");
                receipt.addText(itemDetails.toString(),true);
            }

        }
        //es.print_line(itemDetails.toString());
    }

    private void printHeader(String header , Company company , String state ){

        receipt.setTextSize(fontSizeB);
        receipt.setTypeface(AppInit.appContext, "fonts/RobotoMono-Bold.ttf");
        String test;
        //es.init_printer();

        //es.select_fontA();
        //es.select_code_tab(ESC_POS_EPSON_ANDROID.CodePage.WPC1252);
        //es.justification_center();
       // es.double_height_width_on();
        test = company.getCompanyName() + "\n" ;
        receipt.setTextSize(fontSizeB);
        receipt.setAlign(Paint.Align.CENTER);
        if (!test.isEmpty()) {
            //es.print_line(test);
            receipt.addText(test+"\n\n",true);
        }
        //es.double_height_width_off();
        //es.print_and_feed_lines((byte) 0.9);
        receipt.setAlign(Paint.Align.CENTER);
        receipt.setTextSize(fontSizeC);
        receipt.setTypeface(AppInit.appContext, "fonts/RobotoMono-Regular.ttf");

        if(header.equalsIgnoreCase("Sales Receipt")){
            //es.print_line(header);
            //receipt.addText(header,true);

            test = company.getCompanyAddress();
            if (test != null) {
                //es.print_line(test);
                receipt.addText(test,true);
            }
            test = company.getCompanyContactNumber();
            if (test != null) {
                //es.print_line(test);
                receipt.addText(test,true);
            }

            if(state.equalsIgnoreCase("reprint")){
                String headerTest = header+"(Re-Print)";
                //es.print_line(headerTest);
                receipt.addText(headerTest,true);
            }else {
                //es.print_line(header);
                receipt.addText(header,true);
            }
            //es.double_height_width_off();

        }else {
            //es.print_line(header);
            receipt.addText(header,true);
            test = ReadableDateFormat.getCurrentDateFormet(new Date(System.currentTimeMillis()));
            receipt.addText(test,true);
            //es.print_line(test);//+"\n");

        }
        //es.double_height_width_off();
        //es.justification_left();

        //es.emphasized_on();
        //es.horizontalLine();
        //es.emphasized_off();
        receipt.setTextSize(fontSizeD);
        //receipt.addText(printType,true);
        receipt.addText("________________________________________________",true);
        receipt.setTypeface(AppInit.appContext, "fonts/RobotoMono-Regular.ttf");
        receipt.addText("\n");
        receipt.setAlign(Paint.Align.LEFT);

    }


    String message = "Thank You Come Again";
    private void printFooter(Company company) {
        //es.justification_center();
        receipt.setAlign(Paint.Align.CENTER);
        if (company.getCompanyMessage() != null && !company.getCompanyMessage().equalsIgnoreCase("n/l")) {
            // if (message.trim() != null && !message.equalsIgnoreCase("N")) {
            //es.print_line(company.getCompanyMessage());
            receipt.addText(company.getCompanyMessage(),true);
            //es.print_line(message);
            //}
            //es.horizontalLine();
            receipt.addText("________________________________________________",true);
            String test;

            /*es.print_and_feed_lines((byte) 0.5);
            es.barcode_height((byte) 100);
            es.select_position_hri((byte) 2);*/
            receipt.addText("\n");

            test = "Designed by Kale Systems (pvt) Ltd";
            //es.print_line(test);
            receipt.addText(test,true);
            test = "www.kalesystems.com \n";
            //es.print_line(test);
            receipt.addText(test,true);

        }else {
            //es.print_line(message);
            receipt.addText(message,true);
            //}
            //es.horizontalLine();
            receipt.addText("________________________________________________",true);
            String test;

            /*es.print_and_feed_lines((byte) 0.5);
            es.barcode_height((byte) 100);
            es.select_position_hri((byte) 2);*/
            receipt.addText("\n");
            test = "Designed by Kale Systems (pvt) Ltd";
            //es.print_line(test);
            receipt.addText(test,true);
            test = "www.kalesystems.com \n";
            //es.print_line(test);
            receipt.addText(test,true);


        }
    }


    private void printTop(String orderNo,String receiptType,String person){
        String test;
        StringBuffer salesPerson = new StringBuffer();
        StringBuffer dateAndTime = new StringBuffer();
        StringBuffer receiptHead = new StringBuffer();

        String id = "Sales Receipt";

        test = ReadableDateFormat.getCurrentDateFormet(new Date(System.currentTimeMillis()));
        dateAndTime.append(" ").append(test.substring(0, 8)); // for get date only
        dateAndTime.append(test.substring(8, 10)); // for get date only
        dateAndTime.append(getSpaceInPrint(27)); // for printing character in a proper Alignment
        dateAndTime.append(test.substring(10, 19));
        //es.justification_left();
        //es.print_line(dateAndTime.toString());
        receipt.setAlign(Paint.Align.LEFT);
        receipt.addText(dateAndTime.toString());
        if(!id.equalsIgnoreCase("Cash Register")) {
            salesPerson.append(" Cashier");
            if (receiptType.equalsIgnoreCase("Sales Receipt #")) {
                int issueCharLength = 47 - (person.length() + 8);
                salesPerson.append(getSpaceInPrint(issueCharLength));
                salesPerson.append(person);
            } else {
                //int issueCharLength = 47 - (user.getUserName().length() + 8);

                int issueCharLength = 47 - (userName.length() + 8);
                salesPerson.append(getSpaceInPrint(issueCharLength));

                //salesPerson.append(user.getUserName);
                salesPerson.append(userName);
            }
            //es.print_line(salesPerson.toString());
            receipt.addText(salesPerson.toString(),true);
        }
        receiptHead.append(" ").append(receiptType);
        int invoiceIdCharLength = 47 - (receiptType.length()+orderNo.length()+1);
        String invoiceIdSpace = String.format("%" + invoiceIdCharLength + "s", "");
        receiptHead.append(invoiceIdSpace);// for printing character in a proper Alignment
        receiptHead.append(orderNo).append("\n");
        //es.print_line_without_linefeed(receiptHead.toString());
        receipt.addText(receiptHead.toString(),true);
        /*if(company.getCompanyIndustry().equalsIgnoreCase("Restaurant")){
            String resType = printOrder.get(0).getResOrderType();
            if(resType.length()>0) {
                String output = resType.substring(0, 1).toUpperCase() + resType.substring(1).toLowerCase();
                es.boldTest();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(" ").append(output);
                String tableID = receipt.getTabelNumber();
                Log.e("table",tableID);
                if(Integer.parseInt(tableID) > 0){
                    stringBuffer.append(" # ").append(tableID);
                }
                es.print_line(stringBuffer.toString());
                es.normal();
            }
        }*/
        //es.print_dotline_without_linefeed();
        receipt.addText("------------------------------------------------"+"\n");
    }

    private void printBottom(Order order){
        try {
            String printType = "Sales Print";
            String test;
            StringBuffer subtotalBuffer = new StringBuffer();
            StringBuffer netTotalBuffer = new StringBuffer();
            StringBuffer receivedBuffer = new StringBuffer();
            StringBuffer balanceBuffer = new StringBuffer();
            StringBuffer noItemsBuffer = new StringBuffer();
            StringBuffer paymentMethod = new StringBuffer();
            StringBuffer issuedMsg = new StringBuffer();
            StringBuffer discountBuffer = new StringBuffer();
            StringBuffer returnBuffer = new StringBuffer();
            StringBuffer totalDisBuffer= new StringBuffer();
            StringBuffer vat = new StringBuffer();
            StringBuffer cgstType;
            StringBuffer sCgstType= new StringBuffer();
            StringBuffer serviceCharge = new StringBuffer();

            //PrintReceipt ord=printOrder.get(0);
            //----------------------------------------------adding subtotal
            //es.print_dotline();
            StringBuffer singleDotLine = new StringBuffer();
            for ( int i = 0; i < 48 ; i++) {
                singleDotLine.append("-");// for printing character in a proper Alignment
            }
            receipt.addText(singleDotLine.toString(),true);
            if(!printType.equalsIgnoreCase("Hold Print")) {

                test = " Subtotal";
                subtotalBuffer.append(test);
                int valueSub = 47 - (df.format(order.getOrderTotal()).length() + 9);
                subtotalBuffer.append(getSpaceInPrint(valueSub));// for printing character in a proper Alignment
                subtotalBuffer.append(df.format(order.getOrderTotal()));
                receipt.addText(subtotalBuffer.toString(),true);
                //es.print_line(subtotalBuffer.toString());
            }/*
            //------------------------------------------------print service charge / delivery charge for resturant company type
            if(company.getCompanyIndustry().equalsIgnoreCase("Restaurant")){
                if(resturantDetails[1].equalsIgnoreCase("Table")){
                    test=" Service Charge";
                    serviceCharge.append(test);
                    int valuecharge = 47- (df.format(Double.parseDouble(resturantDetails[2])).length()+15);
                    serviceCharge.append(getSpaceInPrint(valuecharge));// for printing character in a proper Alignment
                    if((Double.parseDouble(resturantDetails[2]) >0)){
                        serviceCharge.append(df.format(Double.parseDouble(resturantDetails[2])));
                        es.print_line(serviceCharge.toString());
                    }
                }
                else if(resturantDetails[1].equalsIgnoreCase("Delivery")){
                    test=" Delivery Charge";
                    serviceCharge.append(test);
                    int valuecharge = 47- (df.format(Double.parseDouble(resturantDetails[2])).length()+16);
                    serviceCharge.append(getSpaceInPrint(valuecharge));// for printing character in a proper Alignment
                    if((Double.parseDouble(resturantDetails[2]) >0)){
                        serviceCharge.append(df.format(Double.parseDouble(resturantDetails[2])));
                        es.print_line(serviceCharge.toString());
                    }
                }

            }*/
            //-----------------------------------------------adding discount amount

            String orderDisCount = "Included Discount";
            //if(appPreferences.getOrderDiscount().equals("Included Discount")){
            if(AppSettings.getDisType(context).equals("N")){
                if (order.getOrderDiscount() > 0 ) {

                    discountBuffer.append(" Included Discount");

                    int discountCharLength = 47 - (df.format(order.getOrderDiscount()).length()+18);
                    discountBuffer.append(getSpaceInPrint(discountCharLength));// for printing character space
                    discountBuffer.append(df.format(order.getOrderDiscount()));
                    //es.print_line(discountBuffer.toString());
                    receipt.addText(discountBuffer.toString(),true);
                }
            }else {
                if ((order.getOrderDiscount()) > 0 ) {

                    discountBuffer.append(" Order Discount   ");

                    int discountCharLength = 45 - (df.format(order.getOrderDiscount()).length() + 18);
                    discountBuffer.append(getSpaceInPrint(discountCharLength));// for printing character space
                    dis = dis +order.getOrderDiscount();
                    discountBuffer.append("(").append(df.format((order.getOrderDiscount()))).append(")");
                    //es.print_line(discountBuffer.toString());
                    receipt.addText(discountBuffer.toString(),true);
                }
            }
            //-----------------------------------------------adding return amount
            /*if (ord.getRetrurnAmount()!=0.0) {
                returnBuffer.append(" Sales Return");
                int returnAmountCharLength = 47 - (df.format(ord.getRetrurnAmount()).length()+14);
                returnBuffer.append(getSpaceInPrint(returnAmountCharLength));//for line spacing
                returnBuffer.append("-");
                returnBuffer.append(df.format(ord.getRetrurnAmount()));
                es.print_line(returnBuffer.toString());
            }*/
            //-----------------------------------------------adding vat amount

            /*Double ordVat = Double.parseDouble(ord.getVat());

            if (ordVat>0.0) {
                if(accept){
                    vat.append(" GST Total");
                }
                else{
                    vat.append(" Sales Tax");
                }
                //vat.append(" Sales Tax");
                int returnAmountCharLength = 50 - (df.format(ordVat).length()+14);
                vat.append(getSpaceInPrint(returnAmountCharLength));
                vat.append(" ");
                vat.append(df.format(ordVat));
                es.print_line(vat.toString());
            }*/

            //----------------------------------------------adding nettotal
            test=" Net Total";
            netTotalBuffer.append(test);
            int valueNet = 47 - (df.format(order.getOrderTotal()).length()+10);
            netTotalBuffer.append(getSpaceInPrint(valueNet));// for printing character in a proper Alignment
            netTotalBuffer.append(df.format(order.getOrderTotal()));
            //receipt.addText(netTotalBuffer.toString(),true);
            //-----------------------------------------------adding = sign
            int netTotalLineChar = 48 - df.format(order.getOrderTotal()).length();
            netTotalBuffer.append(getSpaceInPrint(netTotalLineChar));// for printing character in a proper Alignment
            for (int i = 0; i < df.format(order.getOrderTotal()).length(); i++) {
                netTotalBuffer.append("=");// for printing character in a proper Alignment
            }
            //es.print_line(netTotalBuffer.toString());
            receipt.addText(netTotalBuffer.toString(),true);

            //----------------------------------------------adding payment type
            //  if(!printType.equalsIgnoreCase("Hold Print")) {
            // if (!printType.equalsIgnoreCase("Packing List")) {
            paymentMethod.append(" Paid By ");
            if (order.getOrderPaymentMethod() == 1001) {
                paymentMethod.append("Cash");
            } else if (order.getOrderPaymentMethod() == 1002) {
                paymentMethod.append("Cheque");
            } else if (order.getOrderPaymentMethod() == 1003) {
                paymentMethod.append("Card");
            } else if (order.getOrderPaymentMethod() == 1004) {
                paymentMethod.append("Credit");
            }

            //es.print_line(paymentMethod.toString());
            receipt.addText(paymentMethod.toString(),true);

            //----------------------------------------------adding received amount
            test = " Received";
            receivedBuffer.append(test);
            int valueReceived = 47 - (df.format(order.getOrderUserPayment()).length() + 9);
            receivedBuffer.append(getSpaceInPrint(valueReceived));// for printing character in a proper Alignment
            receivedBuffer.append(df.format(order.getOrderUserPayment()));
            //es.print_line(receivedBuffer.toString());
            receipt.addText(receivedBuffer.toString(),true);

            //----------------------------------------------adding balance amount
            test = " Balance";
            balanceBuffer.append(test);
            double balance = 0.0;
            if ((order.getOrderUserPayment() > order.getOrderTotal())) {
                balance = order.getOrderUserPayment() - order.getOrderTotal();
            }

            int valueBalance = 47 - (df.format(balance).length() + 8);
            balanceBuffer.append(getSpaceInPrint(valueBalance));// for printing character in a proper Alignment
            balanceBuffer.append(df.format(balance));
            //es.print_line(balanceBuffer.toString());
            receipt.addText(balanceBuffer.toString(),true);
            //   }
            //-----------------------------------------------adding no of items
            //es.print_linefeed();
            receipt.addText("\n");
            test = " No of Item(s) ";
            noItemsBuffer.append(test);
            int spacelegnth= 25 - (15 + String.valueOf(noofItems).length());
            noItemsBuffer.append(getSpaceInPrint(spacelegnth));//space for print
            noItemsBuffer.append(noofItems).append("\n");
            //es.print_line(noItemsBuffer.toString());
            receipt.addText(noItemsBuffer.toString(),true);

            //------------------------------------------------------------adding diffrent vat types

                /*if(vatDetail.size()>0) {
                    for (int i = 0; i < vatDetail.size(); i++) {
                        cgstType= new StringBuffer();
                        cgstType.append(" ").append(vatDetail.get(i).getVatCodeName());
                        int returnAmountCharLength = 46 - vatDetail.get(i).getVatCodeName().length() - (df.format(vatDetail.get(i).getVatAmount()).length());
                        cgstType.append(getSpaceInPrint(returnAmountCharLength));//space for print
                        cgstType.append(df.format(vatDetail.get(i).getVatAmount()));
                        es.print_line(cgstType.toString());
                    }
                }
*/
            String formatDis = df.format(dis);
            if (AppSettings.getDisType(context).equals("Y") && dis != 0.00 && dis != null) {
                //es.justification_center();
                //es.bold();
                receipt.setAlign(Paint.Align.CENTER);
                receipt.setTypeface(AppInit.appContext, "fonts/RobotoMono-Bold.ttf");
                totalDisBuffer.append("You Saved ");
                totalDisBuffer.append(formatDis);
                //es.print_line(totalDisBuffer.toString());
                //es.justification_left();
                //es.normal();
                receipt.addText(totalDisBuffer.toString(),true);
                receipt.setTypeface(AppInit.appContext, "fonts/RobotoMono-Regular.ttf");
                receipt.setAlign(Paint.Align.LEFT);
            }

                /*if (printType.equalsIgnoreCase("Packing List")) {
                    issuedMsg.append("------------");
                    issuedMsg.append("                        ------------");
                    issuedMsg.append("Issued By");
                    issuedMsg.append("                            Received BY");
                    es.print_linefeed();
                    es.print_line(issuedMsg.toString());
                }*/
            // }
            receipt.addText("\n",true);
        }catch (Exception ignored){
            ignored.printStackTrace();
        }
    }
    private  String getSpaceInPrint(Integer legnth) {
        String space=" ";
        if (legnth > 0) {
            space = String.format("%" + legnth + "s", "");
        }
        return  space;
    }

    private void saveBarcodeAsImage(Bitmap bitmap ) throws IOException {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/POS_images");
        myDir.mkdirs();
        Random generator = new Random();
        String fname = "Image.png";
        File file = new File(myDir, fname);
        if (file.getCanonicalPath().equalsIgnoreCase(fname))
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Double unitQtyConvener(Double qty, String uom){
        Double unitQty = qty;
        if(uom.equalsIgnoreCase("kg")){
            unitQty = qty*1000;
        }else if(uom.equalsIgnoreCase("l")){
            unitQty = qty*1000;
        }else if(uom.equalsIgnoreCase("m")){
            unitQty = qty*100;
        }
        return unitQty;
    }

    private String unitConvener(String uom){
        String unit = uom;
        if(uom.equalsIgnoreCase("kg")){
            unit = "g";
        }else if(uom.equalsIgnoreCase("l")){
            unit = "ml";
        }else if(uom.equalsIgnoreCase("m")){
            unit = "cm";
        }
        return unit;
    }
}

package com.util;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InvoiceUtil {

    PDDocument invoice;
    int n;
    Integer total = 0;
    Integer price;
    String CustomerName;
    String CustomerPhone;
    List<String> ProductName = new ArrayList<>();
    List<Integer> ProductPrice = new ArrayList<>();
    List<Integer> ProductQty = new ArrayList<>();
    String InvoiceTitle = "CodeSpeedy Technology Private Limited";
    String SubTitle = "Invoice";

    //Using the constructor to create a PDF with a blank page
    InvoiceUtil() {
        //Create Document
        invoice = new PDDocument();
        //Create Blank Page
        PDPage newPage = new PDPage();
        //Add the blank page
        invoice.addPage(newPage);
    }

    public void getData() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Customer Name: ");
        CustomerName = sc.nextLine();
        System.out.println("Enter the Customer Phone Number: ");
        CustomerPhone = sc.next();
        System.out.println("Enter the Number of Products: ");
        n = sc.nextInt();
        System.out.println();
        for(int i=0; i<n; i++) {
            System.out.println("Enter the Product Name: ");
            ProductName.add(sc.next());
            System.out.println("Enter the Price of the Product: ");
            ProductPrice.add(sc.nextInt());
            System.out.println("Enter the Quantity of the Product: ");
            ProductQty.add(sc.nextInt());
            System.out.println();
            //Calculating the total amount
            total = total + (ProductPrice.get(i)*ProductQty.get(i));
        }
    }

     public void WriteInvoice() {
        //get the page
        PDPage myPage = invoice.getPage(0);
        try {
            //Prepare Content Stream
            PDPageContentStream cs = new PDPageContentStream(invoice, myPage);

            //Writing Single Line text
            //Writing the Invoice title
            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 20);
            cs.newLineAtOffset(140, 750);
            cs.showText(InvoiceTitle);
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 18);
            cs.newLineAtOffset(270, 690);
            cs.showText(SubTitle);
            cs.endText();

            //Writing Multiple Lines
            //writing the customer details
            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 14);
            cs.setLeading(20f);
            cs.newLineAtOffset(60, 610);
            cs.showText("Customer Name: ");
            cs.newLine();
            cs.showText("Phone Number: ");
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 14);
            cs.setLeading(20f);
            cs.newLineAtOffset(170, 610);
            cs.showText(CustomerName);
            cs.newLine();
            cs.showText(CustomerPhone);
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 14);
            cs.newLineAtOffset(80, 540);
            cs.showText("Product Name");
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 14);
            cs.newLineAtOffset(200, 540);
            cs.showText("Unit Price");
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 14);
            cs.newLineAtOffset(310, 540);
            cs.showText("Quantity");
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 14);
            cs.newLineAtOffset(410, 540);
            cs.showText("Price");
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 12);
            cs.setLeading(20f);
            cs.newLineAtOffset(80, 520);
            for(int i =0; i<n; i++) {
                cs.showText(ProductName.get(i));
                cs.newLine();
            }
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 12);
            cs.setLeading(20f);
            cs.newLineAtOffset(200, 520);
            for(int i =0; i<n; i++) {
                cs.showText(ProductPrice.get(i).toString());
                cs.newLine();
            }
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 12);
            cs.setLeading(20f);
            cs.newLineAtOffset(310, 520);
            for(int i =0; i<n; i++) {
                cs.showText(ProductQty.get(i).toString());
                cs.newLine();
            }
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 12);
            cs.setLeading(20f);
            cs.newLineAtOffset(410, 520);
            for(int i =0; i<n; i++) {
                price = ProductPrice.get(i)*ProductQty.get(i);
                cs.showText(price.toString());
                cs.newLine();
            }
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 14);
            cs.newLineAtOffset(310, (500-(20*n)));
            cs.showText("Total: ");
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 14);
            //Calculating where total is to be written using number of products
            cs.newLineAtOffset(410, (500-(20*n)));
            cs.showText(total.toString());
            cs.endText();

            //Close the content stream
            cs.close();
            //Save the PDF
            invoice.save("D:\\ICBT\\Mywork\\AdvancedProgrammingAssignment\\invoices\\invoice.pdf");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        InvoiceUtil i = new InvoiceUtil();
        i.getData();
        i.WriteInvoice();
        System.out.println("Invoice Generated!");
    }

}

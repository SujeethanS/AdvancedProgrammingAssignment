package com.example.customerOrderApp.helper;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;

/**
 * Created by keert on 04/01/2017.
 */

public class ReadableDateFormat {

    public static String dateFormat(Date date){

        DateFormat df = new SimpleDateFormat("dd"); // Monday 01/01/2016, 11:00 am

        return df.format(date);
    }
    public static String getTodayDate(Date date){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.US); // Monday 01/01/2016, 11:00 am

        return df.format(date);
    }
    public static String monthFormat(Date date){

        DateFormat wf = new SimpleDateFormat("MM"); // Monday 01/01/2016, 11:00 am

        return wf.format(date);
    }

    /*public static String getWeekDate7DaysBack(Date date){
        // Date myDate = dateFormat.parse(date);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Monday 01/01/2016, 11:00 am
        Date newDate = new Date(date.getTime() - 604800000L); // 7 * 24 * 60 * 60 * 1000


        return sdf.format(newDate);
    }*/

    /*public static String getWeekDate6DaysBack(Date date){
        // Date myDate = dateFormat.parse(date);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Monday 01/01/2016, 11:00 am
        Date newDate = new Date(date.getTime() - 518400000L); // 6 * 24 * 60 * 60 * 1000


        return sdf.format(newDate);
    }*/

    public static String getWeekDate5DaysBack(Date date){
        // Date myDate = dateFormat.parse(date);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Monday 01/01/2016, 11:00 am
        Date newDate = new Date(date.getTime() - 432000000L); // 5 * 24 * 60 * 60 * 1000


        return sdf.format(newDate);
    }

    public static String getWeekDate4DaysBack(Date date){
        // Date myDate = dateFormat.parse(date);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Monday 01/01/2016, 11:00 am
        Date newDate = new Date(date.getTime() - 345600000L); // 4 * 24 * 60 * 60 * 1000


        return sdf.format(newDate);
    }

    public static String getWeekDate3DaysBack(Date date){
        // Date myDate = dateFormat.parse(date);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Monday 01/01/2016, 11:00 am
        Date newDate = new Date(date.getTime() - 259200000L); // 3 * 24 * 60 * 60 * 1000


        return sdf.format(newDate);
    }

    public static String getWeekDate2DaysBack(Date date){
        // Date myDate = dateFormat.parse(date);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Monday 01/01/2016, 11:00 am
        Date newDate = new Date(date.getTime() - 172800000L); // 2 * 24 * 60 * 60 * 1000


        return sdf.format(newDate);
    }

    public static String getWeekDate(Date date){
        // Date myDate = dateFormat.parse(date);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Monday 01/01/2016, 11:00 am

        return sdf.format(date);
    }

    public static String getCurrentYear(Date date){

        DateFormat df = new SimpleDateFormat("yyyy"); // Monday 01/01/2016, 11:00 am

        return df.format(date);

    }

    public static String getTodyDate(Date date){
        // Date myDate = dateFormat.parse(date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Monday 01/01/2016, 11:00 am

        return sdf.format(date);
    }

    public static String getThiWeekDate(Date date){
        // Date myDate = dateFormat.parse(date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Monday 01/01/2016, 11:00 am
        Date newDate = new Date(date.getTime() - 604800000L); // 7 * 24 * 60 * 60 * 1000

        return sdf.format(newDate);
    }


    public static String getThiMonthDate(Date date){
        // Date myDate = dateFormat.parse(date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM"); // Monday 01/01/2016, 11:00 am
        Date newDate = new Date(date.getTime() - 604800000L); // 7 * 24 * 60 * 60 * 1000



        return sdf.format(newDate);
    }

    public static String getThiMonthDateAcct(Date date){
        // Date myDate = dateFormat.parse(date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Monday 01/01/2016, 11:00 am
        Date newDate = new Date(date.getTime() - 2592000000L); // 7 * 24 * 60 * 60 * 1000



        return sdf.format(newDate);
    }

    public static String getThiMonthDateAcctBefore(Date date){
        // Date myDate = dateFormat.parse(date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Monday 01/01/2016, 11:00 am
        Date newDate = new Date(date.getTime() + 2592000000L); // 7 * 24 * 60 * 60 * 1000



        return sdf.format(newDate);
    }

    public static String getThiYearDateAcct(Date date){
        // Date myDate = dateFormat.parse(date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Monday 01/01/2016, 11:00 am
        Date newDate = new Date(date.getTime() - 31536000000L); // 7 * 24 * 60 * 60 * 1000



        return sdf.format(newDate);
    }

    public static String getMonthStartDate(Date date){
        // Date myDate = dateFormat.parse(date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Monday 01/01/2016, 11:00 am
        Date newDate = new Date(date.getTime() - 864000000); // 7 * 24 * 60 * 60 * 1000

        return sdf.format(newDate);
    }

    public static String getThisMonthLastDate(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
        Date convertedDate = null;
        try {
            convertedDate = dateFormat.parse(String.valueOf(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(convertedDate);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));

        return dateFormat.format(date);
    }

    public static String getTodayTimeForServer(String date){
        DateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.US); // Monday 01/01/2016, 11:00 am
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(date);
    }

    public static String getServerDateFormat(String date){
        if(!date.equalsIgnoreCase("n/l")) {
            String time = ReadableDateFormat.getTodayTimeForServer(String.valueOf(new Date(System.currentTimeMillis())));
            Log.e("DATE", date);
            Log.e("TIME",time);
            return date+"T"+time+"Z";
        }
        else{
            return "n/l";
        }
    }

    public static String getDateFormat2(Date date){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.US); // Monday 01/01/2016, 11:00 am

        return df.format(date);
    }

    public static String getCurrentDateFormet(Date date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US); // Monday 01/01/2016, 11:00 am
        return df.format(date);
    }

    public static String humanReadableFormat(String dateTime){
        //String startTime = "2016-10-11 00:02:19";
       // Log.d("dateTime",dateTime);
        String readable = null;
        StringTokenizer tk = new StringTokenizer(dateTime);
        String date = tk.nextToken();
       // String time = tk.nextToken();

        SimpleDateFormat sdfDateInput = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
        SimpleDateFormat sdfDateOutput = new SimpleDateFormat("dd/mm/yyyy",Locale.US);

        //  SimpleDateFormat sdfTimeInput = new SimpleDateFormat("hh:mm:ss");
        // SimpleDateFormat sdfsTimeOutput = new SimpleDateFormat("hh:mm a");

        Date outputDate;
        try {
            outputDate = sdfDateInput.parse(date);
            //  outputTime = sdfTimeInput.parse(time);
            //System.out.println("Time Display: " + sdfs.format(dt)); // <-- I got result here
            readable = sdfDateInput.format(outputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return readable;
    }

    public static String humanReadableFormatTime(String dateTime){
        //String startTime = "2016-10-11 00:02:19";
        // Log.d("dateTime",dateTime);
        String readable = null;
        StringTokenizer tk = new StringTokenizer(dateTime);
        String date = tk.nextToken();
        // String time = tk.nextToken();

        SimpleDateFormat sdfDateInput = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat sdfDateOutput = new SimpleDateFormat("hh:mm:ss");

        //  SimpleDateFormat sdfTimeInput = new SimpleDateFormat("hh:mm:ss");
        // SimpleDateFormat sdfsTimeOutput = new SimpleDateFormat("hh:mm a");

        Date outputDate;
        try {
            outputDate = sdfDateInput.parse(date);
            //  outputTime = sdfTimeInput.parse(time);
            //System.out.println("Time Display: " + sdfs.format(dt)); // <-- I got result here
            readable = sdfDateInput.format(outputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return readable;
    }

    public static String getTodayTimeForServer(Date date){
        DateFormat df = new SimpleDateFormat("HH:mm:ss",Locale.US); // Monday 01/01/2016, 11:00 am
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(date);
    }


    private static String formatServerTime(String serverTime){
        String removeT = serverTime.replace("T"," ");
        return removeT.replace("Z","");
    }

    public static String UTCToLocalTime(String timeUTC){
        String localTime = "n/l";
        timeUTC = formatServerTime(timeUTC);

        String BASE_FORMAT = "yyyy-MM-dd HH:mm:ss";

        SimpleDateFormat formatUTC = new SimpleDateFormat(BASE_FORMAT,Locale.US);
        formatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat formatLocalTime = new SimpleDateFormat(BASE_FORMAT,Locale.US);
        formatLocalTime.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

        Date javaDate = null;
        try {
            javaDate = formatUTC.parse(timeUTC);
        } catch (ParseException e) {
            e.printStackTrace(); // Shouldn't happen.
        }

        try{
            localTime =  formatLocalTime.format(javaDate);
        }catch (NullPointerException e){
            e.printStackTrace();
        }


        return  localTime;
    }

    public static String tomorrowDate(Date todayDate){

        String day =    ReadableDateFormat.getTodyDate(todayDate);
        String week = ReadableDateFormat.getThiWeekDate(todayDate);
        Calendar c = Calendar.getInstance();
        c.setTime(todayDate);
        c.add(Calendar.DATE, 1);
        String daySample = String.valueOf(c.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dayTO = sdf.format(c.getTime());

        return dayTO;
    }

    public static String getWeekDate7DaysBack(Date date){
        // Date myDate = dateFormat.parse(date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.US); // Monday 01/01/2016, 11:00 am
        Date newDate = new Date(date.getTime() - 604800000L); // 7  24  60  60  1000
        return sdf.format(newDate);
    }

    public static String getWeekDate7DaysBefore(Date date){
        // Date myDate = dateFormat.parse(date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.US); // Monday 01/01/2016, 11:00 am
        Date newDate = new Date(date.getTime() + 604800000L); // 7  24  60  60  1000
        return sdf.format(newDate);
    }

    public static String getWeekDate6DaysBack(Date date){
        // Date myDate = dateFormat.parse(date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.US); // Monday 01/01/2016, 11:00 am
        Date newDate = new Date(date.getTime() - 518400000L); // 6  24  60  60  1000


        return sdf.format(newDate);
    }

    public static Date stringToDate(String date){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.US);

        Date myDate = null;
        try {
            myDate = sdf.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return myDate ;
    }

    public static String humanReadableFormatForPrint(String dateTime){
        //String startTime = "2016-10-11 00:02:19";
        String readable = null;
        StringTokenizer tk = new StringTokenizer(dateTime);
        String date = tk.nextToken();
        String time = tk.nextToken();

        SimpleDateFormat sdfDateInput = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
        SimpleDateFormat sdfDateOutput = new SimpleDateFormat("dd/mm/yyyy",Locale.US);

        //  SimpleDateFormat sdfTimeInput = new SimpleDateFormat("hh:mm:ss");
        // SimpleDateFormat sdfsTimeOutput = new SimpleDateFormat("hh:mm a");

        Date outputDate;
        try {
            outputDate = sdfDateInput.parse(date);
            //outputTime = sdfTimeInput.parse(time);
            //System.out.println("Time Display: " + sdfs.format(dt)); // <-- I got result here
            readable = sdfDateOutput.format(outputDate)+", "+time;
            //readable = sdfDateOutput.format(outputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return readable;
    }

    public static String convertServerDateAndTimeToPrintDateFormat(String dateAndTime){
        String resultDateTemp;
        if (dateAndTime.length()<=10){
            resultDateTemp = dateAndTime;
        }else {
            String [] dateTime = dateAndTime.split("T");
            if(dateTime[1].substring(0,2).equalsIgnoreCase("24")){
                resultDateTemp = dateTime[0] + " " + "00:" + dateTime[1].substring(3);
            } else {
                resultDateTemp = dateTime[0] + " " + removeLastCharFromTime(dateTime[1]);
            }
        }

        return resultDateTemp;
    }

    public static void cursorPosition(EditText editText){
        editText.setSelection(editText.getText().length());
    }

    public static String getNextDate(Date curDate)  {
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return format.format(calendar.getTime());
    }

    public static String[] monthStartAndEndDate(){
        String[]date = new String[2];
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        int year = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int day = 1;
        c1.set(year, month1, day);
        int numOfDaysInMonth = c1.getActualMaximum(Calendar.DAY_OF_MONTH);
        date[0] = sdf.format(c1.getTime());
        c1.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth-1);
        date[1] = sdf.format(c1.getTime());

        return date;
    }

    private static String removeLastCharFromTime(String time){
        String formatedTime="";
        if (time.length()==9){
            formatedTime = time.substring(0,time.length()-1);
        }else {
            formatedTime = time;
        }

        return formatedTime;
    }
}

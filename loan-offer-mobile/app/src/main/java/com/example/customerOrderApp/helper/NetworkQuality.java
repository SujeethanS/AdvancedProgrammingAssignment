package com.example.customerOrderApp.helper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.IOException;

public class NetworkQuality {

    public static int wifiNetwork(Context context) {
        @SuppressLint("WifiManagerPotentialLeak") WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        @SuppressLint("MissingPermission") int linkSpeed = wifiManager.getConnectionInfo().getRssi();
        return linkSpeed;
    }

    public static int mobileNetwork(Context context) {
        // Get GSM Signal Strength (Dbm
        int signalStrength = 0;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = telephonyManager.getNetworkType();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:

                    CellInfoGsm cellInfoGsm = (CellInfoGsm) telephonyManager.getAllCellInfo().get(0);
                    CellSignalStrengthGsm cellSignalStrengthGsm = cellInfoGsm.getCellSignalStrength();
                    signalStrength = cellSignalStrengthGsm.getDbm();
                    break;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) telephonyManager.getAllCellInfo().get(0);
                    CellSignalStrengthWcdma cellSignalStrengthWcdma = cellInfoWcdma.getCellSignalStrength();
                    signalStrength = cellSignalStrengthWcdma.getDbm();
                    break;
                case TelephonyManager.NETWORK_TYPE_LTE:
                    CellInfoLte cellInfoLte = (CellInfoLte) telephonyManager.getAllCellInfo().get(0);
                    CellSignalStrengthLte cellSignalStrengthLte = cellInfoLte.getCellSignalStrength();
                    signalStrength = cellSignalStrengthLte.getDbm();
                    break;
                default:
            }
        }
        return signalStrength;
    }
    private static boolean isWifiConnected(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return ((netInfo != null) && netInfo.isConnected());
    }

    private static boolean isMobileConnected(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return ((netInfo != null) && netInfo.isConnected());
    }

    public static String getNetworkClass(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = mTelephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "2G";
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "3G";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "4G";
            default:
                return "Unknown";
        }
    }

    public static String getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int nt = tm.getNetworkType();

        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
                int linkSpeed = wifiManager.getConnectionInfo().getRssi();
                return "TYPE_WIFI" + linkSpeed;
            }
            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

                //throws null pointer exception here
                @SuppressLint("MissingPermission") CellInfoGsm cellinfogsm = (CellInfoGsm) tm.getAllCellInfo().get(0);
                CellSignalStrengthGsm cellSignalStrengthGsm = cellinfogsm.getCellSignalStrength();
                int dbm = cellSignalStrengthGsm.getLevel();
                return "TYPE_MOBILE " + nt + dbm;
            }
        }
        return "TYPE_NOT_CONNECTED";
    }

    public static int signalStrength(Context context){
        int strength;
        int speed = 0;
        if(isMobileConnected(context)) {
            speed = mobileNetwork(context);
        }
        if(isWifiConnected(context)){
            speed = wifiNetwork(context);
        }
        if(speed > -60){
            strength = 0;//Excellent signal
        }else if ( -60 > speed && speed > -75 ){
            strength = 1;//Very good signal
        }else if ( -75 > speed && speed > -90 ){
            strength = 2;//Good signal
        }else if ( -90 > speed && speed > -100 ){
            strength = 3;//Fair signal
        }else if ( -100 > speed && speed > -110 ){
            strength = 4;//Poor signal
        }else {
            strength = 5;//No signal
        }
        return strength;
    }

    private static int wifiSignalStrength(Context context){
        int strength;
        int speed = wifiNetwork(context);
        if(speed > -60){
            strength = 0;//Excellent signal
        }else if ( -60 > speed && speed > -75 ){
            strength = 1;//Very good signal
        }else if ( -75 > speed && speed > -90 ){
            strength = 2;//Good signal
        }else if ( -90 > speed && speed > -100 ){
            strength = 3;//Fair signal
        }else if ( -100 > speed && speed > -110 ){
            strength = 4;//Poor signal
        }else {
            strength = 5;//No signal
        }
        return strength;
    }

    public static int mobileSignalStrength(Context context){
        int strength;
        int speed = mobileNetwork(context);
        if(speed > -60){
            strength = 0;//Excellent signal
        }else if ( -60 > speed && speed > -75 ){
            strength = 1;//Very good signal
        }else if ( -75 > speed && speed > -90 ){
            strength = 2;//Good signal
        }else if ( -90 > speed && speed > -100 ){
            strength = 3;//Fair signal
        }else if ( -100 > speed && speed > -110 ){
            strength = 4;//Poor signal
        }else {
            strength = 5;//No signal
        }
        return strength;
    }

    private long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return (availableBlocks * blockSize)/(1024*1024);
    }

    private long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return (totalBlocks * blockSize)/(1024*1024);
    }

    private void recordInformations(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi;
            // Version
            pi = pm.getPackageInfo(context.getPackageName(), 0);
            String versionName = pi.versionName;
            //buildNumber = currentVersionNumber(context);
            // Package name
            String packageName = pi.packageName;

            // Device model
            String phoneModel = Build.MODEL;
            // Android version
            String androidVersion = Build.VERSION.RELEASE;

            String board = Build.BOARD;
            String brand = Build.BRAND;
            String device = Build.DEVICE;
            String display = Build.DISPLAY;
            String fingerPrint = Build.FINGERPRINT;
            String host = Build.HOST;
            String id = Build.ID;
            String model = Build.MODEL;
            String product = Build.PRODUCT;
            String manufacturer = Build.MANUFACTURER;
            String tags = Build.TAGS;
            long time = Build.TIME;
            String type = Build.TYPE;
            String user = Build.USER;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo() != null;
    }

    public static boolean isNfcAvailable(Context context){
        NfcManager manager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
        NfcAdapter adapter = manager.getDefaultAdapter();
        return adapter != null && adapter.isEnabled();
    }

    public static boolean isConnectedToThisServer(String host) {
        // https://stackoverflow.com/questions/3905358/how-to-ping-external-ip-from-java-android
        Runtime runtime = Runtime.getRuntime();
        try {
            long a = System.currentTimeMillis() % 1000;
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 " + host);
            int exitValue = ipProcess.waitFor();

            long endTime= a + System.currentTimeMillis() % 1000 ;

            long timeofping = endTime-a;
            Log.e("exitValue",timeofping+" a "+a+" endTime "+endTime);
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static long networkSpeed(String host) {
        Runtime runtime = Runtime.getRuntime();
        long timeofping = 0;
        try {
            long a = System.currentTimeMillis() % 1000;
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 " + host);
            int exitValue = ipProcess.waitFor();

            long endTime= a + System.currentTimeMillis() % 1000 ;

            timeofping = endTime-a;
            Log.e("network",timeofping+" a "+a+" endTime "+endTime+" exitValue "+exitValue);

            return timeofping;
        } catch (Exception e) {
            e.printStackTrace();
            return timeofping;
        }

    }
}

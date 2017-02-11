package com.hiddensound.model;

/**
 * Created by amarques on 2/11/2017.
 */

public class HiddenModel {
    private static String IMEI = null;
    private static String QRMEMO = null;
    private static String USERNAME = null;
    private static String PASS = null;
    private static String TOKERN = null;

    public static String getIMEI() {
        return IMEI;
    }

    public static void setIMEI(String IMEI) {
        HiddenModel.IMEI = IMEI;
    }

    public static String getQRMEMO() {
        return QRMEMO;
    }

    public static void setQRMEMO(String QRMEMO) {
        HiddenModel.QRMEMO = QRMEMO;
    }

    public static String getUSERNAME() {
        return USERNAME;
    }

    public static void setUSERNAME(String USERNAME) {
        HiddenModel.USERNAME = USERNAME;
    }

    public static String getPASS() {
        return PASS;
    }

    public static void setPASS(String PASS) {
        HiddenModel.PASS = PASS;
    }

    public static String getTOKERN() {
        return TOKERN;
    }

    public static void setTOKERN(String TOKERN) {
        HiddenModel.TOKERN = TOKERN;
    }


}

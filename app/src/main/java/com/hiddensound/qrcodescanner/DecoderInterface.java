package com.hiddensound.qrcodescanner;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;

import java.util.Objects;
import java.util.StringTokenizer;

/**
 * Created by amarques on 3/11/2017.
 */

public interface DecoderInterface {
    void setToast(String msg);
    Context getContext();
    Activity getActivity();
    void setAppName(String appName);
    void expandSlideUp();
    Object getSystemService(String Name);
}

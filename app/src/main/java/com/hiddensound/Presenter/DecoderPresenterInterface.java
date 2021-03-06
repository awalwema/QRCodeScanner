package com.hiddensound.Presenter;

import android.graphics.PointF;

import com.hiddensound.qrcodescanner.DecoderActivity;

/**
 * Created by amarques on 2/15/2017.
 */

public interface DecoderPresenterInterface {
    void Approve();
    void Decline();
    void signOut();
    void onQRCodeRead(String text, PointF[] points);
    void checkPermissions(DecoderActivity activity, int value);
}

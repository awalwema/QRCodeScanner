package com.hiddensound.Presenter;

import android.graphics.PointF;

import com.hiddensound.model.HiddenModel;
import com.hiddensound.qrcodescanner.DecoderActivity;

/**
 * Created by amarques on 2/15/2017.
 */

public interface DecoderPresenterInterface {
    void Approve();
    void onQRCodeRead(String text, PointF[] points);
    void checkPermissions(DecoderActivity activity, int value);
}

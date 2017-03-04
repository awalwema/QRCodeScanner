package com.hiddensound.Presenter;

import com.hiddensound.qrcodescanner.MainActivity;

/**
 * Created by amarques on 2/15/2017.
 */

public interface MainPresenterInterface {
    void Approve(boolean approval);
    void checkPermissions(MainActivity activity, int value);
}

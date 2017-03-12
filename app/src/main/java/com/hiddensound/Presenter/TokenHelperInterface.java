package com.hiddensound.Presenter;

import android.content.Context;

import com.hiddensound.model.HiddenModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zane on 3/2/2017.
 */

public interface TokenHelperInterface {
    void tokenStore(HiddenModel tokenInfo);
    HiddenModel tokenRetrieve();

}

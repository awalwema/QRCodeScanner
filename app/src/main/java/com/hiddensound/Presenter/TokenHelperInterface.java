package com.hiddensound.Presenter;

import com.hiddensound.model.HiddenModel;

/**
 * Created by Zane on 3/2/2017.
 */

public interface TokenHelperInterface {
    void tokenStore(HiddenModel tokenInfo);
    HiddenModel tokenRetrieve();
    void deleteTokenInfo();

}

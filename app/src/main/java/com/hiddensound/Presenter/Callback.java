package com.hiddensound.Presenter;

/**
 * this interface will hold UI till http request is done.
 * Created by amarques on 3/1/2017.
 */

public interface Callback<T> {
    void onResponse(T t);
}

package com.hiddensound.Presenter;

import com.hiddensound.model.ModelInterface;

/**
 * Created by amarques on 2/15/2017.
 */

public class MainPresenter implements MainPresenterInterface, ModelInterface.onModelCall{
    private ModelInterface model;

    public MainPresenter(ModelInterface model){
        this.model = model;
    }

    @Override
    public void Approve(boolean approval) {

    }

    @Override
    public void setIMEI() {
        model.setIMEI();
    }

    @Override
    public void setQRMemo() {

    }

    @Override
    public void setUser() {

    }

    @Override
    public void setToken() {

    }
}

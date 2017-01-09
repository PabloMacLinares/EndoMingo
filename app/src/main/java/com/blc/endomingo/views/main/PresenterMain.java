package com.blc.endomingo.views.main;

import android.app.Activity;

import com.blc.endomingo.contracts.ContractMain;

/**
 * Created by Pablo on 30/12/2016.
 */

public class PresenterMain implements ContractMain.Presenter{

    private ContractMain.Model model;
    private ContractMain.View view;

    public PresenterMain(Activity activity){
        this.view = (ContractMain.View) activity;
        model = new ModelMain(activity);
    }


    @Override
    public void onStartSportTracker() {
        view.showSportTracker(model.addRoute());
    }

    @Override
    public void onResume() {
        view.showRoutes(model.getRoutes());
    }
}

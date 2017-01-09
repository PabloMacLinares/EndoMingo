package com.blc.endomingo.views.main;

import android.content.Context;

import com.blc.endomingo.contracts.ContractMain;
import com.blc.endomingo.managers.ManagerRoute;
import com.blc.endomingo.pojo.Route;

import java.util.List;

/**
 * Created by Pablo on 30/12/2016.
 */

public class ModelMain implements ContractMain.Model{

    ManagerRoute mr;

    public ModelMain(Context context){
        mr = new ManagerRoute(context);
    }

    @Override
    public Route addRoute() {
        Route r = new Route();
        r.setId(mr.insert(r));
        return r;
    }

    @Override
    public List<Route> getRoutes() {
        return mr.select(new String[]{"1"}, new String[]{"1"});
    }

}

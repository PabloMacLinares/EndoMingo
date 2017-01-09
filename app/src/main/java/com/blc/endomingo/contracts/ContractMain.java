package com.blc.endomingo.contracts;

import com.blc.endomingo.pojo.Route;

import java.util.List;

/**
 * Created by Pablo on 30/12/2016.
 */

public class ContractMain {
    public interface Model{

        Route addRoute();

        List<Route> getRoutes();
    }

    public interface Presenter{

        void onStartSportTracker();

        void onResume();

    }

    public interface View{

        void showSportTracker(Route route);

        void showRoutes(List<Route> routes);

    }
}

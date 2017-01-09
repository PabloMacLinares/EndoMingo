package com.blc.endomingo.contracts;

/**
 * Created by Pablo on 01/01/2017.
 */

public class ContractDB {

    public static class Route{
        public static final String TABLE = "route";

        public static final String ID = "id";
        public static final String DATE = "date";
        public static final String DISTANCE = "distance";
    }

    public static class RoutePoint{
        public static final String TABLE = "routepoint";

        public static final String ID = "id";
        public static final String ROUTE_ID = "routeId";
        public static final String LONGITUDE = "longitude";
        public static final String LATITUDE = "latitude";
        public static final String ALTITUDE = "altitude";
        public static final String SPEED = "speed";
        public static final String TIME_STAMP = "timeStamp";
    }
}

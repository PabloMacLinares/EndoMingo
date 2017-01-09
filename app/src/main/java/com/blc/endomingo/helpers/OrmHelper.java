package com.blc.endomingo.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.blc.endomingo.pojo.Route;
import com.blc.endomingo.pojo.RoutePoint;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Pablo on 31/12/2016.
 */

public class OrmHelper extends OrmLiteSqliteOpenHelper {

    private static final int version = 1;
    private static final String database = "EndoMingoORM";

    public OrmHelper(Context context){
        super(context, database, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Route.class);
            TableUtils.createTable(connectionSource, RoutePoint.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        if(newVersion != oldVersion){
            try {
                TableUtils.dropTable(connectionSource, Route.class, true);
                TableUtils.dropTable(connectionSource, RoutePoint.class, true);
                onCreate(database, connectionSource);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public RuntimeExceptionDao<Route, Integer> getRouteDao(){
        return getRuntimeExceptionDao(Route.class);
    }

    public RuntimeExceptionDao<RoutePoint, Integer> getRoutePointDao(){
        return getRuntimeExceptionDao(RoutePoint.class);
    }
}

package com.blc.endomingo.managers;

import android.content.Context;

import com.blc.endomingo.contracts.ContractDB;
import com.blc.endomingo.pojo.Route;
import com.blc.endomingo.pojo.RoutePoint;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Pablo on 31/12/2016.
 */

public class ManagerRoute extends ManagerDB<Route> {

    private ManagerRoutePoint mrp;

    public ManagerRoute(Context context) {
        super(context);
        mrp = new ManagerRoutePoint(context);
    }

    @Override
    public long insert(Route object) {
        getHelper().getRouteDao().create(object);
        return object.getId();
    }

    @Override
    public int update(Route object) {
        for (RoutePoint rp : object.getRoutePoints()) {
            if(rp.getId() == -1){
                rp.setRouteId(object.getId());
                mrp.insert(rp);
            }else if(rp.getRouteId() != object.getId()){
                rp.setRouteId(object.getId());
                mrp.update(rp);
            }
        }
        return getHelper().getRouteDao().update(object);
    }

    @Override
    public int delete(Route object) {
        for (RoutePoint rp : object.getRoutePoints()) {
            mrp.delete(rp);
        }
        return getHelper().getRouteDao().delete(object);
    }

    @Override
    public Route selectByID(long id) {
        QueryBuilder<Route, Integer> qb = getHelper().getRouteDao().queryBuilder();
        try {
            qb.setWhere(qb.where().eq(ContractDB.Route.ID, id));
            for (Route r : getHelper().getRouteDao().query(qb.prepare())) {
                r.setRoutePoints(mrp.select(new String[]{ContractDB.RoutePoint.ROUTE_ID}, new String[]{r.getId() + ""}));
                return r;
            }
        } catch (SQLException e) {}
        return null;
    }

    @Override
    public List<Route> select(String[] fields, String[] values) {
        StringBuilder query = new StringBuilder();
        query.append("select * from ").append(ContractDB.Route.TABLE).append(" where ");
        for (int i = 0; i < fields.length; i++) {
            query.append(fields[i]).append(" = ").append(values[i]);
            if(i < fields.length - 1){
                query.append(" and ");
            }
        }
        GenericRawResults<Route> results = getHelper().getRouteDao().queryRaw(
                query.toString(), getHelper().getRouteDao().getRawRowMapper());
        try {
            List<Route> rs = results.getResults();
            for (Route r : rs) {
                r.setRoutePoints(mrp.select(new String[]{ContractDB.RoutePoint.ROUTE_ID}, new String[]{r.getId() + ""}));
            }
            results.close();
            return rs;
        } catch (SQLException | IOException e) {
            return null;
        }
    }
}

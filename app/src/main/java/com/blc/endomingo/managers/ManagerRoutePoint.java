package com.blc.endomingo.managers;

import android.content.Context;

import com.blc.endomingo.contracts.ContractDB;
import com.blc.endomingo.pojo.RoutePoint;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Pablo on 31/12/2016.
 */

public class ManagerRoutePoint extends ManagerDB<RoutePoint> {

    public ManagerRoutePoint(Context context) {
        super(context);
    }

    @Override
    public long insert(RoutePoint object) {
        getHelper().getRoutePointDao().create(object);
        return object.getId();
    }

    @Override
    public int update(RoutePoint object) {
        return getHelper().getRoutePointDao().update(object);
    }

    @Override
    public int delete(RoutePoint object) {
        return getHelper().getRoutePointDao().delete(object);
    }

    @Override
    public RoutePoint selectByID(long id) {
        QueryBuilder<RoutePoint, Integer> qb = getHelper().getRoutePointDao().queryBuilder();
        try {
            qb.setWhere(qb.where().eq(ContractDB.RoutePoint.ID, id));
            for (RoutePoint rp : getHelper().getRoutePointDao().query(qb.prepare())) {
                return rp;
            }
        } catch (SQLException e) {}
        return null;
    }

    @Override
    public List<RoutePoint> select(String[] fields, String[] values) {
        StringBuilder query = new StringBuilder();
        query.append("select * from ").append(ContractDB.RoutePoint.TABLE).append(" where ");
        for (int i = 0; i < fields.length; i++) {
            query.append(fields[i]).append(" = ").append(values[i]);
            if(i < fields.length - 1){
                query.append(" and ");
            }
        }
        GenericRawResults<RoutePoint> results = getHelper().getRoutePointDao().queryRaw(
                query.toString(), getHelper().getRoutePointDao().getRawRowMapper());
        try {
            List<RoutePoint> rps = results.getResults();
            results.close();
            return rps;
        } catch (SQLException | IOException e) {
            return null;
        }
    }
}

package com.blc.endomingo.managers;

import android.content.Context;

import com.blc.endomingo.helpers.OrmHelper;

import java.util.List;

/**
 * Created by Pablo on 31/12/2016.
 */

public abstract class ManagerDB<T> {

    private OrmHelper helper;

    public ManagerDB(Context context){
        helper = new OrmHelper(context);
    }

    public OrmHelper getHelper() {
        return helper;
    }

    public void setHelper(OrmHelper helper) {
        this.helper = helper;
    }

    public abstract long insert(T object);

    public abstract int update(T object);

    public abstract int delete(T object);

    public abstract T selectByID(long id);

    public abstract List<T> select(String[] fields, String[] values);
}

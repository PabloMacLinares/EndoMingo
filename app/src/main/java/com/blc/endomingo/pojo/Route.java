package com.blc.endomingo.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Pablo on 22/12/2016.
 */

@DatabaseTable(tableName = "route")
public class Route implements Parcelable{

    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private Date date;
    @DatabaseField
    private long duration;
    private List<RoutePoint>routePoints;
    @DatabaseField
    private float distance;
    @DatabaseField
    private float calories;

    public Route(long id) {
        this.id = id;
        this.date = new Date();
        this.duration = 0;
        routePoints = new ArrayList<>();
        distance = 0;
        calories = 0;
    }

    public Route(){
        this(-1);
    }

    protected Route(Parcel in) {
        id = in.readLong();
        date = new Date(in.readLong());
        duration = in.readLong();
        routePoints = in.createTypedArrayList(RoutePoint.CREATOR);
        distance = in.readFloat();
        calories = in.readFloat();
    }

    public static final Creator<Route> CREATOR = new Creator<Route>() {
        @Override
        public Route createFromParcel(Parcel in) {
            return new Route(in);
        }

        @Override
        public Route[] newArray(int size) {
            return new Route[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public List<RoutePoint> getRoutePoints() {
        return routePoints;
    }

    public void setRoutePoints(List<RoutePoint> routePoints) {
        this.routePoints = routePoints;
    }

    public boolean addRoutePoint(RoutePoint routePoint){
        return routePoints.add(routePoint);
    }

    public boolean removeRoutePoint(RoutePoint routePoint){
        return routePoints.remove(routePoint);
    }

    public RoutePoint removeRoutePoint(int position){
        return routePoints.remove(position);
    }

    public float getBurnedCalories(float weight){
        float minutes = (duration / 1000f) / 60f;
        setCalories(0.049f * (weight * 2.2f) * minutes);
        return calories;
    }

    public float getAverageSpeed(){
        if(!routePoints.isEmpty()) {
            float avgSpeed = 0;
            for (RoutePoint rp : routePoints) {
                avgSpeed += rp.getSpeed();
            }
            return avgSpeed / routePoints.size();
        }
        return 0;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(date.getTime());
        parcel.writeLong(duration);
        parcel.writeTypedList(routePoints);
        parcel.writeFloat(distance);
        parcel.writeFloat(calories);
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", date=" + date +
                ", duration=" + duration +
                ", routePoints=" + routePoints.size() +
                ", distance=" + distance +
                ", calories=" + calories +
                '}';
    }
}

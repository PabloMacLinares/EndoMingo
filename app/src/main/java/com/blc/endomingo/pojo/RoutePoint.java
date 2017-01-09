package com.blc.endomingo.pojo;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Pablo on 22/12/2016.
 */

@DatabaseTable(tableName = "routepoint")
public class RoutePoint implements Parcelable{

    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private long routeId;
    @DatabaseField
    private double longitude;
    @DatabaseField
    private double latitude;
    @DatabaseField
    private double altitude;
    @DatabaseField
    private float speed;
    @DatabaseField
    private Date timeStamp;

    public RoutePoint(long id, long routeId, double longitude, double latitude, double altitude, float speed, Date timeStamp) {
        this.id = id;
        this.routeId = routeId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
        this.speed = speed;
        this.timeStamp = timeStamp;
    }

    public RoutePoint(Location location, float speed){
        this(-1, -1, location.getLongitude(), location.getLatitude(), location.getAltitude(), speed, new Date());
    }

    public RoutePoint(){
        this(-1, -1, 0, 0, 0, 0, new Date(0));
    }

    protected RoutePoint(Parcel in) {
        id = in.readLong();
        routeId = in.readLong();
        longitude = in.readDouble();
        latitude = in.readDouble();
        altitude = in.readDouble();
        speed = in.readFloat();
        timeStamp = new Date(in.readLong());
    }

    public static final Creator<RoutePoint> CREATOR = new Creator<RoutePoint>() {
        @Override
        public RoutePoint createFromParcel(Parcel in) {
            return new RoutePoint(in);
        }

        @Override
        public RoutePoint[] newArray(int size) {
            return new RoutePoint[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(routeId);
        parcel.writeDouble(longitude);
        parcel.writeDouble(latitude);
        parcel.writeDouble(altitude);
        parcel.writeFloat(speed);
        parcel.writeLong(timeStamp.getTime());
    }

    @Override
    public String toString() {
        return "RoutePoint{" +
                "id=" + id +
                ", routeId=" + routeId +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", altitude=" + altitude +
                ", speed=" + speed +
                ", timeStamp=" + timeStamp +
                '}';
    }
}

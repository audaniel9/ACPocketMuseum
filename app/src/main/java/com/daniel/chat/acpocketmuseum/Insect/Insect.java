package com.daniel.chat.acpocketmuseum.Insect;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

public class Insect implements Parcelable {
    private int id;
    private String name;
    private String location;
    private String price;
    private String times;

    // Constructor
    public Insect(int id, String name, String location, String price, String times) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.price = price;
        this.times = times;
    }

    // Parcelable constructor
    protected Insect(Parcel in) {
        name = in.readString();
        location = in.readString();
        price = in.readString();
        times = in.readString();
    }

    // Parcelable stuff; leave as is
    public static final Creator<Insect> CREATOR = new Creator<Insect>() {
        @Override
        public Insect createFromParcel(Parcel in) {
            return new Insect(in);
        }

        @Override
        public Insect[] newArray(int size) {
            return new Insect[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getPrice() {
        return price;
    }

    public String getTimes() {
        return times;
    }

    public static Comparator<Insect> InsectSortAscending = new Comparator<Insect>() {
        @Override
        public int compare(Insect insect, Insect insect2) {
            return insect.getName().compareTo(insect2.getName());
        }
    };

    public static Comparator<Insect> InsectSortDescending = new Comparator<Insect>() {
        @Override
        public int compare(Insect insect, Insect insect2) {
            return insect2.getName().compareTo(insect.getName());
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(location);
        parcel.writeString(price);
        parcel.writeString(times);
    }
}

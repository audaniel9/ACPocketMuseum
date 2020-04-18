package com.daniel.chat.acpocketmuseum;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Comparator;

public class MuseumSpecimen implements Parcelable {
    private String specimenName;
    private String location;
    private String price;
    private String times;

    // Constructor
    public MuseumSpecimen(String specimenName, String location, String price, String times) {
        this.specimenName = specimenName;
        this.location = location;
        this.price = price;
        this.times = times;
    }

    // Parcelable constructor
    protected MuseumSpecimen(Parcel in) {
        specimenName = in.readString();
        location = in.readString();
        price = in.readString();
        times = in.readString();
    }

    // Parcelable stuff; leave as is
    public static final Creator<MuseumSpecimen> CREATOR = new Creator<MuseumSpecimen>() {
        @Override
        public MuseumSpecimen createFromParcel(Parcel in) {
            return new MuseumSpecimen(in);
        }

        @Override
        public MuseumSpecimen[] newArray(int size) {
            return new MuseumSpecimen[size];
        }
    };

    public String getSpecimenName() {
        return specimenName;
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

    public static Comparator<MuseumSpecimen> MuseumSpecimenSortAscending = new Comparator<MuseumSpecimen>() {
        @Override
        public int compare(MuseumSpecimen specimen, MuseumSpecimen specimen2) {
            return specimen.getSpecimenName().compareTo(specimen2.getSpecimenName());
        }
    };

    public static Comparator<MuseumSpecimen> MuseumSpecimenSortDescending = new Comparator<MuseumSpecimen>() {
        @Override
        public int compare(MuseumSpecimen specimen, MuseumSpecimen specimen2) {
            return specimen2.getSpecimenName().compareTo(specimen.getSpecimenName());
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(specimenName);
        parcel.writeString(location);
        parcel.writeString(price);
        parcel.writeString(times);
    }
}

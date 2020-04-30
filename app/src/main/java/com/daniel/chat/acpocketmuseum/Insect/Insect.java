package com.daniel.chat.acpocketmuseum.Insect;

import android.os.Parcel;

import com.daniel.chat.acpocketmuseum.MuseumSpecimen;

import java.util.Comparator;

public class Insect extends MuseumSpecimen {

    // Constructor
    public Insect(int id, String name, String location, String price, String times) {
        super(id, name, location, price, times);
    }

    // Parcelable constructor
    public Insect(Parcel in) {
        super(in);
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
}

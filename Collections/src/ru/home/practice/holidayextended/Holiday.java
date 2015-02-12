package ru.home.practice.holidayextended;

import java.lang.ref.WeakReference;
import java.util.Date;

/**
 * Created by darshinkin on 30.01.15.
 */
public class Holiday implements Comparable<Holiday> {
    private Date date;
    private String name;
    private String country;

    public Holiday(Date date, String name, String country) {
        this.date = date;
        this.name = name;
        this.country = country;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public int compareTo(Holiday o) {
        return this.date.compareTo(o.getDate());
    }
}

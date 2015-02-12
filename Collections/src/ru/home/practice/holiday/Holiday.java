package ru.home.practice.holiday;

import java.util.Date;

/**
 * Created by darshinkin on 29.01.15.
 */
public class Holiday implements Comparable<Holiday> {
    private Date date;
    private String name;

    public Holiday(Date date, String name) {
        this.date = date;
        this.name = name;
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

    @Override
    public int compareTo(Holiday o) {
        return this.date.compareTo(o.getDate());
    }
}

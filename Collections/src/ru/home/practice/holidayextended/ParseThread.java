package ru.home.practice.holidayextended;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

/**
 * Created by dima on 01.02.15.
 */
public class ParseThread implements Callable<Long> {
    private static Map<String, WeakReference<String>> countries = new WeakHashMap<String, WeakReference<String>>();
    private static Map<Calendar, WeakReference<Calendar>> days = new WeakHashMap<Calendar, WeakReference<Calendar>>();

    private final CountDownLatch latch;
    private final int start;
    private final int end;
    private final Set<Holiday> holidays;
    private final List<String> lines;
    private final ConcurrentMap<Calendar, Integer> holidaysInDay;
    private final ConcurrentMap<Calendar, Integer> holidaysInMonth;

    public ParseThread(CountDownLatch latch, int start, int end, Set<Holiday> holidays, List<String> lines,
                       ConcurrentMap<Calendar, Integer> holidaysInDay, ConcurrentMap<Calendar, Integer> holidaysInMonth) {
        this.latch = latch;
        this.start = start;
        this.end = end;
        this.holidays = holidays;
        this.lines = lines;
        this.holidaysInDay = holidaysInDay;
        this.holidaysInMonth = holidaysInMonth;
    }

    @Override
    public Long call() throws Exception {
        latch.await();
        long startTime = System.nanoTime();
        for (int i = start; i < end; i++) {
            String line = lines.get(i);
            int space = line.indexOf(" ");
            String dateStr = line.substring(0, space);
            Calendar date = getDate(dateStr);
            int bracket = line.indexOf("(");
            String name = line.substring(space, bracket);
            String country = line.substring(bracket, line.length());

            String countryR = getCounty(country);
            Calendar dateR = getDate(date);
            Holiday holiday = new Holiday(dateR.getTime(), name, countryR);
            holidays.add(holiday);

            Integer value = holidaysInDay.putIfAbsent(dateR, 1);
            if (value != null) {
                while (!holidaysInDay.replace(dateR, value, value+1)) {
                    value = holidaysInDay.get(dateR);
                }
            }
            dateR.set(Calendar.DAY_OF_MONTH, 1);
            Integer valueMonth = holidaysInMonth.putIfAbsent(dateR, 1);
            if (valueMonth != null) {
                while (!holidaysInMonth.replace(dateR, valueMonth, valueMonth+1)) {
                    valueMonth = holidaysInMonth.get(dateR);
                }
            }
        }
        return System.nanoTime() - startTime;
    }

    private static String getCounty(String country) {
        WeakReference<String> countryRef = countries.get(country);
        if (countryRef == null || countryRef.get() == null ) {
            countryRef = new WeakReference<String>(country);
            countries.put(country, countryRef);
        }
        return countryRef.get();
    }

    private static Calendar getDate(Calendar date) {
        WeakReference<Calendar> dayRef = days.get(date);
        if (dayRef == null || dayRef.get() == null ) {
            dayRef = new WeakReference<Calendar>(date);
            days.put(date, dayRef);
        }
        return dayRef.get();
    }

    private static Calendar getDate(String dateStr) {
        String[] words = dateStr.split("/");
        Calendar calendar = new GregorianCalendar(Integer.parseInt(words[0]), Integer.parseInt(words[1])-1, Integer.parseInt(words[2]));
        return calendar;
    }
}

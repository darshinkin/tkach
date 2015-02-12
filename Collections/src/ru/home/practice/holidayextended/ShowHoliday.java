package ru.home.practice.holidayextended;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by darshinkin on 30.01.15.
 */
public class ShowHoliday {
    private static Map<String, WeakReference<String>> countries = new WeakHashMap<String, WeakReference<String>>();
    private static Map<Date, WeakReference<Date>> days = new WeakHashMap<Date, WeakReference<Date>>();
    static final DateFormat FORMAT = new SimpleDateFormat("YYYY/MM/dd");

    static Holiday fakeHolidayToday;
    static Holiday fakeHolidayTomorrow;
    static Holiday fakeHolidayAfterTomorrow;
    static Holiday fakeHolidayNextFiveDays;

    public static void main(String[] args) throws Exception {
        File file = new File("/home/dima/java/Tkach/Collections/holidays.txt");
        if (!file.exists()) {
            throw new Exception("It is not file!");
        }
        List<String> lines = FileUtils.readLines(file, "cp1251");

//        commingHolidays(lines);
        statisticOfHolidays(lines);
    }

    private static void statisticOfHolidays(List<String> lines) throws ExecutionException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Set<Holiday> holidays = new ConcurrentSkipListSet<Holiday>();
        ConcurrentMap<Calendar, Integer> holidaysInDay = new ConcurrentHashMap<Calendar, Integer>(1000);
        ConcurrentMap<Calendar, Integer> holidaysInMonth = new ConcurrentHashMap<Calendar, Integer>(1000);
        int size = lines.size();
        Future<Long> f1 = executor.submit(new ParseThread(latch, 0, size / 2, holidays, lines, holidaysInDay, holidaysInMonth));
        Future<Long> f2 = executor.submit(new ParseThread(latch, size / 2, size, holidays, lines, holidaysInDay, holidaysInMonth));
        latch.countDown();
        f1.get();
        f2.get();
        System.out.println("Thread 1: " + f1.get()/100);
        System.out.println("Thread 2: " + f2.get()/100);
        /*for (Map.Entry<Calendar, Integer> entry : holidaysInDay.entrySet()) {
            System.out.println(FORMAT.format(entry.getKey())+"   "+entry.getValue());
        }*/

        for (Map.Entry<Calendar, Integer> entry : holidaysInMonth.entrySet()) {
            System.out.println(FORMAT.format(entry.getKey().getTime())+"   "+entry.getValue());
        }
//        printHolidays(holidays);
    }

    private static void commingHolidays(List<String> lines) {
        NavigableSet<Holiday> holidays = new TreeSet<Holiday>();
        for (String line : lines) {
            int space = line.indexOf(" ");
            String dateStr = line.substring(0, space);
            Date date = getDate(dateStr);
            int bracket = line.indexOf("(");
            String name = line.substring(space, bracket);
            String country = line.substring(bracket, line.length());

            String countryR = getCounty(country);
            Date dateR = getDate(date);
            Holiday holiday = new Holiday(dateR, name, countryR);
            holidays.add(holiday);
        }

        initializeValues();
        System.out.println("Сегодня:");
        printHolidaysToday(holidays);
        System.out.println();
        System.out.println("Завтра:");
        printHolidaysTomorrow(holidays);
        System.out.println();
        System.out.println("Скоро:");
        printHolidaysNextFiveDays(holidays);
    }

    private static String getCounty(String country) {
        WeakReference<String> countryRef = countries.get(country);
        if (countryRef == null || countryRef.get() == null ) {
            countryRef = new WeakReference<String>(country);
            countries.put(country, countryRef);
        }
        return countryRef.get();
    }

    private static Date getDate(Date date) {
        WeakReference<Date> dayRef = days.get(date);
        if (dayRef == null || dayRef.get() == null ) {
            dayRef = new WeakReference<Date>(date);
            days.put(date, dayRef);
        }
        return dayRef.get();
    }

    private static void initializeValues() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Calendar c = new GregorianCalendar(year, month, day);
        Date dayR = getDate(c.getTime());
        fakeHolidayToday = new Holiday(dayR, "", null);
        c.add(Calendar.DAY_OF_MONTH, 1);
        dayR = getDate(c.getTime());
        fakeHolidayTomorrow = new Holiday(dayR, "", null);
        c.add(Calendar.DAY_OF_MONTH, 1);
        dayR = getDate(c.getTime());
        fakeHolidayAfterTomorrow = new Holiday(dayR, "", null);
        c.add(Calendar.DAY_OF_MONTH, 4);
        dayR = getDate(c.getTime());
        fakeHolidayNextFiveDays = new Holiday(dayR, "", null);
    }

    private static void printHolidaysToday(NavigableSet<Holiday> holidays) {
        Set<Holiday> holidaysToday = holidays.subSet(fakeHolidayToday, true, fakeHolidayTomorrow, true);
        printHolidays(holidaysToday);
    }

    private static void printHolidaysTomorrow(NavigableSet<Holiday> holidays) {
        Set<Holiday> holidaysTomorrow = holidays.subSet(fakeHolidayTomorrow, true, fakeHolidayAfterTomorrow, true);
        printHolidays(holidaysTomorrow);
    }

    private static void printHolidaysNextFiveDays(NavigableSet<Holiday> holidays) {
        Set<Holiday> holidaysNextFiveDays = holidays.subSet(fakeHolidayAfterTomorrow, true, fakeHolidayNextFiveDays, true);
        printHolidays(holidaysNextFiveDays);
    }

    private static Date getDate(String dateStr) {
        Calendar calendar = GregorianCalendar.getInstance();
        String[] words = dateStr.split("/");
        calendar.set(Integer.parseInt(words[0]), Integer.parseInt(words[1])-1, Integer.parseInt(words[2]));
        return calendar.getTime();
    }

    private static void printHolidays(Set<Holiday> holidays) {
        StringBuilder sb;
        for (Holiday holiday : holidays) {
            sb = new StringBuilder();
            System.out.println(sb.append(FORMAT.format(holiday.getDate())).
                    append("   ").append(holiday.getName()).append("   ").append(holiday.getCountry()));
        }
    }
}

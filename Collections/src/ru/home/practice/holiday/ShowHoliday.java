package ru.home.practice.holiday;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by darshinkin on 29.01.15.
 */
public class ShowHoliday {
    static final DateFormat FORMAT = new SimpleDateFormat("YYYY/MM/DD");

    static Holiday fakeHolidayToday;
    static Holiday fakeHolidayTomorrow;
    static Holiday fakeHolidayNextFiveDays;

    public static void main(String[] args) throws Exception {
        File file = new File("/home/darshinkin/Java/Tkach/Collections/holidays.txt");
        if (!file.exists()) {
            throw new Exception("It is not file!");
        }
        NavigableSet<Holiday> holidays = new TreeSet<Holiday>();
        List<String> lines = FileUtils.readLines(file, "cp1251");
        for (String line : lines) {
            int space = line.indexOf(" ");
            String dateStr = line.substring(0, space);
            Date date = getDate(dateStr);
            String name = line.substring(space, line.length());
            Holiday holiday = new Holiday(date, name);
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

    private static void initializeValues() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Calendar c = new GregorianCalendar(year, month, day);
        fakeHolidayToday = new Holiday(c.getTime(), "");
        c.add(Calendar.DAY_OF_MONTH, 1);
        fakeHolidayTomorrow = new Holiday(c.getTime(), "");
        c.add(Calendar.DAY_OF_MONTH, 4);
        fakeHolidayNextFiveDays = new Holiday(c.getTime(), "");
    }

    private static void printHolidaysToday(NavigableSet<Holiday> holidays) {
        NavigableSet<Holiday> holidaysToday = holidays.subSet(fakeHolidayToday, true, fakeHolidayToday, true);
        printHolidays(holidaysToday);
    }

    private static void printHolidaysTomorrow(NavigableSet<Holiday> holidays) {
        NavigableSet<Holiday> holidaysTomorrow = holidays.subSet(fakeHolidayTomorrow, true, fakeHolidayTomorrow, true);
        printHolidays(holidaysTomorrow);
    }

    private static void printHolidaysNextFiveDays(NavigableSet<Holiday> holidays) {
        NavigableSet<Holiday> holidaysNextFiveDays = holidays.subSet(fakeHolidayTomorrow, false, fakeHolidayNextFiveDays, true);
        printHolidays(holidaysNextFiveDays);
    }

    private static void printHolidays(Set<Holiday> holidays) {
        //        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
        StringBuilder sb;
        for (Holiday holiday : holidays) {
            sb = new StringBuilder();
            System.out.println(sb.append(FORMAT.format(holiday.getDate())).
                    append("   ").append(holiday.getName()));
//            System.out.println(df.format(holiday.getDate()) +"  "+holiday.getName());
        }
    }

    private static Date getDate(String dateStr) {
        Calendar calendar = GregorianCalendar.getInstance();
        String[] words = dateStr.split("/");
        calendar.set(Integer.parseInt(words[0]), Integer.parseInt(words[1])-1, Integer.parseInt(words[2]));
        return calendar.getTime();
    }
}

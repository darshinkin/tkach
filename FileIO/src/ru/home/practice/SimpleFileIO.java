package ru.home.practice;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.Format;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by darshinkin on 29.01.15.
 */
public class SimpleFileIO {
    static final Format FORMAT = new MessageFormat("{0} Missing parameter!");

    public static void main(String[] args) {
        String fileNameFrom = null;
        String fileNameTo = null;
        Order order = null;
        if (args[0] != null) {
            fileNameFrom = args[0];
        } else {
            log(1);
        }
        if (args[1] != null) {
            fileNameTo = args[1];
        } else {
            log(2);
        }
        if (args[2] != null) {
            order = Order.getOrder(args[2]);
            if (order == null) {
                log(3);
            }
        } else {
            log(3);
        }
        File fileFrom = new File(fileNameFrom);
        File fileTo = new File(fileNameTo);
//        copyWithNative(fileFrom, fileTo, order);
        copyWithUtils(fileFrom, fileTo, order);
    }

    static void log(Integer num){
        System.out.println(FORMAT.format(num));
    }

    private static void copyWithNative(File fileFrom, File fileTo, Order order) {
        Reader reader = null;
        Writer writer = null;
        try {
            reader = new BufferedReader(new FileReader(fileFrom));
            writer = new PrintWriter(new FileWriter(fileTo));
            List<String> list = new LinkedList<String>();
            String line;
            while ((line = ((BufferedReader) reader).readLine()) != null) {
                list.add(line);
            }
            Collections.sort(list, new ComparatorString(order));
            for (String str : list) {
                ((PrintWriter) writer).println(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static void copyWithUtils(File fileFrom, File fileTo, Order order) {
        List<String> lines = new ArrayList<String>();
        try {
            if (fileFrom.exists()) {
                lines = FileUtils.readLines(fileFrom);
                Collections.sort(lines, new ComparatorString(order));
            }

            if (!fileTo.exists()) {
                fileTo.createNewFile();
            }
            OutputStream os = new FileOutputStream(fileTo);
            IOUtils.writeLines(lines, null, os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ComparatorString implements Comparator<String> {
        private Order order;

        public ComparatorString(Order order) {
            this.order = order;
        }

        @Override
        public int compare(String o1, String o2) {
            if (o1.length() < o2.length()) {
                return order.equals(Order.ASC) ? -1 : 1;
            } else if (o2.length() < o1.length()) {
                return order.equals(Order.ASC) ? 1 : -1;
            }
            return 0;
        }
    }

    private enum Order {
        ASC, DESC;

        static public Order getOrder(String str) {
            for(Order c : Order.values()) {
                if (str.equalsIgnoreCase(c.name())) {
                    return c;
                }
            }
            return null;
        }
    }
}

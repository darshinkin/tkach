package ru.home.practice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by darshinkin on 02.02.15.
 */
public class Test {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DataObject obj = new DataObject();
        obj.setMyData("sldkkkkkklllllldddddddddddddddddd7878888888888888888888888888888888888888888888888888888");
        File file = new File("store.bin");

        FileOutputStream fo = new FileOutputStream(file);
        ObjectOutputStream so = new ObjectOutputStream(fo);
        so.writeObject(obj);
        so.flush();
        so.close();

        ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
        DataObject objNew = (DataObject) is.readObject();
        is.close();
        System.out.println(objNew.toString()+";  myData:"+obj.getMyData());
    }
}

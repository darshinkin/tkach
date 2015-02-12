package ru.home.practice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by darshinkin on 02.02.15.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "dataObj")
public class DataObject extends NonSerializable implements Serializable {
    @XmlElement
    private int i = 5;
    String s = "aaa";
    transient String[] def = new String[2];
    CustomObject customObject = new CustomObject();

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(getMyData());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        setMyData((String) in.readObject());
    }
}

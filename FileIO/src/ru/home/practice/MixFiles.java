package ru.home.practice;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by darshinkin on 29.01.15.
 */
public class MixFiles {
    public static void main(String[] args) throws Exception {
        File fileFrom = new File("/home/darshinkin/Java/Tkach/Photos/");
        File dirTo = new File("mixPhotos");
        if (dirTo.exists()) {
            FileUtils.deleteDirectory(dirTo);
        }
        boolean isMkDir = dirTo.mkdir();
        if (!isMkDir) {
            throw new Exception("File for photos was not create");
        }
        File[] list = fileFrom.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.isFile()) {
                    return true;
                }
                return false;
            }
        });
        createNewListOfFiles(list, dirTo);
    }

    private static void createNewListOfFiles(File[] list, File dirTo) {
        Set<Integer> numbersForFiles = new HashSet<Integer>();
        int size = list.length;
        for (File file : list) {
            String nameFile = getNameFile(size, numbersForFiles);
            File destFile = new File(dirTo, nameFile+".JPG");
            try {
                FileUtils.copyFile(file, destFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getNameFile(int size, Set<Integer> numbersForFiles) {
        Integer numberFile;
        do {
            numberFile = new Random().nextInt(size);
        } while (!numbersForFiles.add(numberFile));
        return numberFile.toString();
    }
}

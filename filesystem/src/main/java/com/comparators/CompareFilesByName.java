package com.comparators;

import com.saaweel.*;
import java.util.Comparator;

public class CompareFilesByName implements Comparator<File> {
    @Override
    public int compare(File file1, File file2) {
        return file1.getName().compareTo(file2.getName());
    }
}
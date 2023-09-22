package com.comparators;

import com.saaweel.*;
import java.util.Comparator;

public class CompareFilesBySize implements Comparator<File> {
    @Override
    public int compare(File file1, File file2) {
        return file1.getSize() > file2.getSize() ? 1 : -1;
    }
}
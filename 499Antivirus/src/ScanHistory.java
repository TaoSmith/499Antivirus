package com.abc.accounts;

import java.util.*;

public class ScanHistory {
    private String filename;
    private Date date;
    private boolean infected;

    public ScanHistory(String filename, boolean infected) {
        this.filename = filename;
        this.infected = infected;
        date = new Date();
    }

    public String getFilename() {
        return filename;
    }

    public Date getDate() {
        return date;
    }

    public boolean isInfected() {
        return infected;
    }

    @Override
    public String toString() {
        return "[filename=" + filename + ", date=" + date + ", infected=" + infected + "]";
    }
}

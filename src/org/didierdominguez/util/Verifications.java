package org.didierdominguez.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Verifications {
    private static Verifications instance;

    Random rand = new Random();
    private ArrayList<String> arrayListSerialsNo = new ArrayList<>();
    String serialNo;

    private Verifications() {
    }

    public static Verifications getInstance() {
        if (instance == null) {
            instance = new Verifications();
        }
        return instance;
    }

    public void clear() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    public boolean isNumeric(String valor) {
        try {
            Double.parseDouble(valor);
            return true;
        } catch(NumberFormatException nfe) {
            return false;
        }
    }

    public String getDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        return formatter.format(date);
    }

    public String getExpirationDate() {
        Date date = new Date();

        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.plusYears(10);
        Date expirationDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        return formatter.format(expirationDate);
    }

    public String serial() {
        serialNo = "";
        do {
            for (int i = 1; i <= 16; i++) {
                int n = rand.nextInt(10);
                serialNo += Integer.toString(n);
                if (i % 4 == 0) {
                    serialNo += " ";
                }
            }
            serialNo = serialNo.trim();
            return serialNo;
        } while (serialVerification());
    }

    private Boolean serialVerification() {
        for (String serials : arrayListSerialsNo) {
            if (serials.equalsIgnoreCase(serialNo)) {
                return true;
            }
        }
        return false;
    }
}

package utility;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class TimeUtility {

    private static final ArrayList<String> listMonth = new ArrayList<String>(
            Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
    );

    private static final ArrayList<Integer> listDayMonth = new ArrayList<Integer>(
            Arrays.asList(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    );

    private static final ArrayList<String> listQuarter = new ArrayList<String>(
            Arrays.asList("01", "02", "03", "04")
    );

    public static ArrayList<String> getListMonth() {
        return listMonth;
    }

    public static ArrayList<Integer> getListDayMonth(int year) {
        if (Year.of(year).isLeap()) {
            listDayMonth.set(1, 29);
        }
        return listDayMonth;
    }

    public static ArrayList<String> getListQuarter() {
        return listQuarter;
    }

    public static ArrayList<String> getListYear()
    {
        ArrayList<String> listYear = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int year = currentYear; year >= 2000 ; year--) {
            listYear.add(String.valueOf(year));
        }

        return listYear;
    }

    public static int getMonthFromDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String month = sdf.format(date);
        return Integer.parseInt(month);
    }

    public static int getQuarterFromDate(Date date) {
        int month = getMonthFromDate(date);
        int quarter = (month / 3);
        int r = month % 3;
        if(r == 0) return quarter;
        return quarter+1;
    }

    public static int getYearFromDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
        String year = sdf.format(date);
        return Integer.parseInt(year);
    }

    public static double convertToDouble(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);
        double decimalTime = hours + (minutes / 60.0) + (seconds / 3600.0);
        DecimalFormat df = new DecimalFormat("#.#");
        return Double.parseDouble(df.format(decimalTime));
    }
}

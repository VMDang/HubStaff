package utility;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;

public class TimeUtility {

    private static final ArrayList<String> listMonth = new ArrayList<String>(
            Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
    );

    private static final ArrayList<Integer> listDayMonth = new ArrayList<Integer>(
            Arrays.asList(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
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

    public static ArrayList<String> getListYear()
    {
        ArrayList<String> listYear = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int year = currentYear; year >= 2000 ; year--) {
            listYear.add(String.valueOf(year));
        }

        return listYear;
    }
}

package iomango.com.forestdirect.mvp.common.utilities;

import java.util.Calendar;

/**
 * Created by Clelia López on 3/12/17
 */

public class Date {

    /**
     * Attributes
     */
    private int year;
    private int month;
    private int day;


    public Date() {
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
    }

    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String toString() {
        String monthString = "";

        switch (month) {
            case 0: monthString = "January"; break;
            case 1: monthString = "February"; break;
            case 2: monthString = "March"; break;
            case 3: monthString = "April"; break;
            case 4: monthString = "May"; break;
            case 5: monthString = "June"; break;
            case 6: monthString = "Jule"; break;
            case 7: monthString = "August"; break;
            case 8: monthString = "September"; break;
            case 9: monthString = "October"; break;
            case 10: monthString = "November"; break;
            case 11: monthString = "December"; break;
        }

        return day + " " + monthString + ", " + year;
    }

    @SuppressWarnings("RedundantIfStatement")
    public boolean isValid() {
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);

        if (year > currentYear)
            return true;
        else if (year == currentYear) {
            if (month > currentMonth)
                return true;
            else if (month == currentMonth) {
                if (day >= currentDay)
                    return true;
                else
                    return false;
            }
        }

        return false;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}



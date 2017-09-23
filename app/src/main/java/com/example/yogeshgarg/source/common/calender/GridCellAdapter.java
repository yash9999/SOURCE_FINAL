package com.example.yogeshgarg.source.common.calender;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


import com.example.yogeshgarg.source.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Om on 16-Dec-16.
 */
// Inner Class
public class GridCellAdapter extends BaseAdapter implements View.OnClickListener {
    private static final String tag = "GridCellAdapter";
    private final Context _context;

    List<String> list;
    private static final int DAY_OFFSET = 1;
    private final String[] weekdaysNameArray = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private final String[] monthNameArray = {"January", "February", "March",
            "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};
    private final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private int daysInMonth;

    private int currentWeekDay;
    int day;
    int month;
    int year;

    int selected_day;
    int selected_month;
    int selected_year;

    private final HashMap<String, Integer> eventsPerMonthMap;
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    private String selectedDate;

    //days setting
    public static final String NEXT_PREVIOUS_MONTH_DAYS = "inactive";
    public static final String CURRENT_MONTH_DAYS = "current";
    // public static final String SELECTED_DAY="selected_day";

    // Days in Current Month
    public GridCellAdapter(Context context, int day, int month, int year) {

        this._context = context;
        this.list = new ArrayList<String>();
        this.day = day;
        this.month = month;
        this.year = year;

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 0);

        selected_day = calendar.get(Calendar.DAY_OF_MONTH);
        selected_month = month;
        selected_year = year;

        setSelectedDate(calendar.get(Calendar.DAY_OF_MONTH) + "-" + monthNameArray[month - 1] + "-" + year);
        setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));

        // Print Month
        printMonth(day, month, year);

        // Find Number of Events
        eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    public void updateCalender(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
        list = new ArrayList<String>();
        printMonth(day, month, year);
        notifyDataSetChanged();
    }

    public String getMonthAsString(int i) {
        return monthNameArray[i];
    }

    private String getWeekDayAsString(int i) {
        return weekdaysNameArray[i];
    }

    private int getNumberOfDaysOfMonth(int i) {
        return daysOfMonth[i];
    }

    public String getItem(int position) {
        return list.get(position);
    }

    /**
     * Prints Month
     *
     * @param mm
     * @param yy
     */

    private void printMonth(int day, int mm, int yy) {
        //Log.d(tag, "==> printMonth: mm: " + mm + " " + "yy: " + yy);
        int trailingSpaces = 0;
        int daysInPrevMonth = 0;
        int prevMonth = 0;
        int prevYear = 0;
        int nextMonth = 0;
        int nextYear = 0;

        int currentMonth = mm - 1;
        String currentMonthName = getMonthAsString(currentMonth);
        daysInMonth = getNumberOfDaysOfMonth(currentMonth);

        GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);

        if (currentMonth == 11) {
            prevMonth = currentMonth - 1;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 0;
            prevYear = yy;
            nextYear = yy + 1;
        } else if (currentMonth == 0) {
            prevMonth = 11;
            prevYear = yy - 1;
            nextYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 1;
        } else {
            prevMonth = currentMonth - 1;
            nextMonth = currentMonth + 1;
            nextYear = yy;
            prevYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
        }

        int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
        trailingSpaces = currentWeekDay;


        if (cal.isLeapYear(cal.get(Calendar.YEAR)))
            if (mm == 2)
                ++daysInMonth;
            else if (mm == 3)
                ++daysInPrevMonth;

        // Trailing Month days
       /* for (int i = 0; i < trailingSpaces; i++) {
            list.add(String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i)
                    + "-" + NEXT_PREVIOUS_MONTH_DAYS
                    + "-" + getMonthAsString(prevMonth)
                    + "-" + prevYear);
        }
*/
        //       Current Month Days
        for (int i = 1; i <= daysInMonth; i++) {
            list.add(String.valueOf(i) + "-" + CURRENT_MONTH_DAYS + "-" +
                    getMonthAsString(currentMonth) + "-" + yy);
        }

        // Leading Month days
        for (int i = 0; i < list.size() % 7; i++) {
            list.add(String.valueOf(i + 1) + "-" + CURRENT_MONTH_DAYS + "-"
                    + getMonthAsString(nextMonth) + "-" + nextYear);
        }
    }


    private HashMap<String, Integer> findNumberOfEventsPerMonth(int year, int month) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        return map;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;


        if (convertView == null) {
            holder = new Holder();
            LayoutInflater inflater = (LayoutInflater) _context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.screen_gridcell, parent, false);

            holder.gridcell = (Button) convertView.findViewById(R.id.calendar_day_gridcell);
            holder.num_events_per_day = (TextView) convertView.findViewById(R.id.num_events_per_day);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        // Get a reference to the Day gridcell
        holder.gridcell.setOnClickListener(this);

        // ACCOUNT FOR SPACING
        String[] day_color = list.get(position).split("-");
        String theday = day_color[0];
        String themonth = day_color[2];
        String theyear = day_color[3];

        if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)) {
            if (eventsPerMonthMap.containsKey(theday)) {

                Integer numEvents = (Integer) eventsPerMonthMap.get(theday);
                holder.num_events_per_day.setText(numEvents.toString());
            }
        }

        // Set the Day GridCell
        holder.gridcell.setText(theday);
        holder.gridcell.setTag(theday + "-" + themonth + "-" + theyear);
        try {
            //|| !checkOutdatedDate(Integer.parseInt(theday) + "/" + (month) + "/" + theyear)
            if (day_color[1].equals(NEXT_PREVIOUS_MONTH_DAYS)) {
                holder.gridcell.setTextColor(ContextCompat.getColor(_context, R.color.color_black));
                holder.gridcell.setBackground(ContextCompat.getDrawable(_context, R.drawable.calendar_disable_cell));
                holder.gridcell.setEnabled(false);
            } else {
                if (day_color[1].equals(CURRENT_MONTH_DAYS)) {
                    if (((String) holder.gridcell.getTag()).equals(getSelectedDate())) {
                        holder.gridcell.setTextColor(ContextCompat.getColor(_context, R.color.color_bg));//selected date
                        holder.gridcell.setBackground(ContextCompat.getDrawable(_context, R.drawable.calendar_green_cell));
                    } else {
                        holder.gridcell.setTextColor(ContextCompat.getColor(_context, R.color.color_black));
                        holder.gridcell.setBackground(ContextCompat.getDrawable(_context, R.drawable.calendar_cell));

                    }
                }
                holder.gridcell.setEnabled(true);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    public boolean checkOutdatedDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date strDate = null;
        Date newDate = null;
        Date lastSelectableDate = null;

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 0);

        Calendar calendarLastSelectableDate = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);

      /*  Date tomorrow = calendar.getTime();

        String tomorrowDate = sdf.format(tomorrow);*/
        try {
            strDate = sdf.parse(date);
            newDate = sdf.parse(sdf.format(calendar.getTime()));
            lastSelectableDate = sdf.parse(sdf.format(calendarLastSelectableDate.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }


        if (/*newDate.after(strDate) && */lastSelectableDate.after(strDate)) {
            return true;
        } else
            return false;
    }

    public class Holder {
        Button gridcell;
        TextView num_events_per_day;
    }

    @Override
    public void onClick(View view) {
        String date_month_year = (String) view.getTag();
        // Toast.makeText(_context, date_month_year, Toast.LENGTH_SHORT).show();
        selectedDate = date_month_year;

        String[] day_color = date_month_year.split("-");
        selected_day = Integer.parseInt(day_color[0].trim());
        selected_month = month;
        selected_year = year;

        notifyDataSetChanged();
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    private void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public void setCurrentWeekDay(int currentWeekDay) {
        this.currentWeekDay = currentWeekDay;
    }

    public int getCurrentWeekDay() {
        return currentWeekDay;
    }

    public String getSelectedDateInFormat() {

        String selectedDate = selected_year + "-" + selected_month + "-" + selected_day;
        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat DesiredFormat = new SimpleDateFormat("dd-MM-yyyy");
        // 'a' for AM/PM

        Date date = null;
        String formattedDate = "";
        try {
            date = sourceFormat.parse(selectedDate);
            formattedDate = DesiredFormat.format(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

// Now formattedDate have current date/time
        //Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();
        return formattedDate;


    }
}


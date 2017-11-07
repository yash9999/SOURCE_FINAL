package com.example.yogeshgarg.source.common;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by yogeshgarg on 20/09/17.
 */

public class XaxisValueFormatter implements IAxisValueFormatter {

    protected String[] mMonths = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int intValue = ((int) value);

        switch (intValue) {
            case 0:
                return mMonths[0];
            case 1:
                return mMonths[1];
            case 2:
                return mMonths[2];
            case 3:
                return mMonths[3];
            case 4:
                return mMonths[4];
            case 5:
                return mMonths[5];
            case 6:
                return mMonths[6];
            case 7:
                return mMonths[7];
            case 8:
                return mMonths[8];
            case 9:
                return mMonths[9];
            case 10:
                return mMonths[10];
            case 11:
                return mMonths[11];
        }
        return "";
    }
}

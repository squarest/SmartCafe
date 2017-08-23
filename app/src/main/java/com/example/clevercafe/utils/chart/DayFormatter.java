package com.example.clevercafe.utils.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Chudofom on 23.08.17.
 */

public class DayFormatter implements IAxisValueFormatter {

    private SimpleDateFormat mFormat;

    public DayFormatter() {
        mFormat = new SimpleDateFormat("dd MMM", new Locale("ru"));
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        return mFormat.format(value);
    }
}
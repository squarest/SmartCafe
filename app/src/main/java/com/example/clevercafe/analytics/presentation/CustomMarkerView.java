package com.example.clevercafe.analytics.presentation;

import android.content.Context;
import android.widget.TextView;

import com.example.clevercafe.R;
import com.example.clevercafe.utils.chart.DayFormatter;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

/**
 * Created by Chudofom on 12.09.17.
 */

public class CustomMarkerView extends MarkerView {
    private TextView valueTextView;
    private TextView dateTextView;

    public CustomMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        valueTextView = findViewById(R.id.value);
        dateTextView = findViewById(R.id.date);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        LargeValueFormatter valueFormatter = new LargeValueFormatter();
        DayFormatter dayFormatter = new DayFormatter();

        valueTextView.setText(valueFormatter.getFormattedValue(e.getY(), new XAxis()));
        dateTextView.setText(dayFormatter.getFormattedValue(e.getX(), new XAxis()));
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}

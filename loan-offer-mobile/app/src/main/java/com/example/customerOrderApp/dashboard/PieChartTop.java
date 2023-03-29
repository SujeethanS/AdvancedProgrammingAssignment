package com.example.customerOrderApp.dashboard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.SeekBar;

import com.example.customerOrderApp.pojo.TopTen;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class PieChartTop {


    private static Typeface tf = null;
    private static List<TopTen> topTen = new ArrayList<>();
    private static List<TopTen> bottomTen = new ArrayList<>();

    private static final String[] parties = new String[]{
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };

    public static void showTop(PieChart chart, SeekBar seekBarXT, SeekBar seekBarYT, Typeface typeface, Context context) {

        tf = typeface;

        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setCenterTextTypeface(Typeface.createFromAsset(context.getAssets(), "OpenSans-Light.ttf"));
        chart.setCenterText(generateCenterSpannableText());

        chart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener

        seekBarXT.setProgress(4);
        seekBarYT.setProgress(100);

        chart.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);

        chart.setEntryLabelColor(Color.BLACK);
        chart.setEntryLabelTypeface(tf);
        chart.setEntryLabelTextSize(12f);
    }

    private static SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("Top Three   \ndeveloped by Kale System");
        s.setSpan(new RelativeSizeSpan(1.5f), 0, 9, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 10, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.BLUE), 10, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.65f), 10, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 19, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 15, s.length(), 0);
        return s;
    }

    public static void setData(int count, float range, PieChart chartP, List<TopTen> productListBottom) {
        topTen = productListBottom;
        ArrayList<PieEntry> entries = new ArrayList<>();
        if(productListBottom.size() > 0 ){
            if(productListBottom.size() > 3) {
                for (int i = productListBottom.size() - 1; i > productListBottom.size() - 4; i--) {
                    if(productListBottom.get(i).getItemsName().length() >10) {
                        entries.add(new PieEntry(Float.parseFloat(productListBottom.get(i).getQty().toString()), productListBottom.get(i).getItemsName().substring(0, 10)+"..."));
                    }else {
                        entries.add(new PieEntry(Float.parseFloat(productListBottom.get(i).getQty().toString()), productListBottom.get(i).getItemsName()));
                    }                }
            }else {
                for (int i = 0; i < productListBottom.size() ; i++) {
                    if(productListBottom.get(i).getItemsName().length() >10) {
                        entries.add(new PieEntry(Float.parseFloat(productListBottom.get(i).getQty().toString()), productListBottom.get(i).getItemsName().substring(0, 10)+"..."));
                    }else {
                        entries.add(new PieEntry(Float.parseFloat(productListBottom.get(i).getQty().toString()), productListBottom.get(i).getItemsName()));
                    }                }
            }


        PieDataSet dataSet = new PieDataSet(entries, "Top Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : com.example.customerOrderApp.dashboard.ColorTemplate.GREEN_COLORS)
            colors.add(c);

        for (int c : com.example.customerOrderApp.dashboard.ColorTemplate.BLUE_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);

        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(tf);
        data.getDataSetLabels();
        data.setValueTextColor(Color.WHITE);
        chartP.setData(data);

        chartP.highlightValues(null);

        chartP.invalidate();
    }
}

}

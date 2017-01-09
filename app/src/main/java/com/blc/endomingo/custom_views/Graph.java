package com.blc.endomingo.custom_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pablo on 30/12/2016.
 */

public class Graph extends View{
    private Paint paintColorStyle;
    private int backgroundColor = Color.rgb(200, 200, 200);
    int pointRadius = 10;
    int lineWidth = 10;
    private int pointColor = Color.BLUE;
    private int lineColor = Color.GREEN;
    private int fillColor = Color.rgb(178, 255, 102);
    private List<Double> points;
    private double minheight;
    private double maxheight;
    private int heightLine = -100;

    public Graph(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintColorStyle = new Paint();

        points = new ArrayList<>();
    }

    private void refresh(){
        invalidate();
        requestLayout();
    }

    public List<Double> getPoints() {
        return points;
    }

    public void setPoints(List<Double> points){
        this.points = points;
        minheight = points.get(0);
        maxheight = -1;
        for (double p : points) {
            if(p < minheight){
                minheight = p;
            }
            if(p > maxheight){
                maxheight = p;
            }
        }
        refresh();
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        refresh();
    }

    public int getPointRadius() {
        return pointRadius;
    }

    public void setPointRadius(int pointRadius) {
        this.pointRadius = pointRadius;
        refresh();
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        refresh();
    }

    public int getPointColor() {
        return pointColor;
    }

    public void setPointColor(int pointColor) {
        this.pointColor = pointColor;
        refresh();
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
        refresh();
    }

    public int getFillColor() {
        return fillColor;
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
        refresh();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.setBackgroundColor(backgroundColor);
        int width = this.getMeasuredWidth();
        int height = this.getMeasuredHeight();
        if(!points.isEmpty()){
            Path fillPath = new Path();
            fillPath.moveTo(width, height);
            fillPath.lineTo(width, height);
            fillPath.lineTo(0, height);
            for (int i = 0; i < points.size(); i++) {
                fillPath.lineTo(
                        ((float)width / ((float)points.size() - 1)) * i,
                        (float) switchRange(points.get(i), maxheight, minheight, 0, height)
                );
                if(i < points.size() - 1){
                    drawLine(
                            canvas,
                            ((float)width / ((float)points.size() - 1)) * i,
                            (float) switchRange(points.get(i), maxheight, minheight, 0, height),
                            ((float)width / ((float)points.size() - 1)) * (i + 1),
                            (float) switchRange(points.get(i + 1), maxheight, minheight, 0, height)
                    );
                }
                /*drawPoint(
                        canvas,
                        ((float)width / ((float)points.size() - 1)) * i,
                        (float) switchRange(points.get(i), maxheight, minheight, 0, height)
                );*/
            }
            fillPath.close();
            paintColorStyle.setStyle(Paint.Style.FILL);
            paintColorStyle.setAntiAlias(true);
            paintColorStyle.setColor(fillColor);
            paintColorStyle.setStrokeWidth(0);
            canvas.drawPath(fillPath, paintColorStyle);
            drawheightLine(canvas, width, height, heightLine);
        }
    }

    private double switchRange(double value, double oldMin, double oldMax, double newMin, double newMax){
        return ((value - oldMin) / (oldMax - oldMin)) * (newMax - newMin) + newMin;
    }

    private void drawPoint(Canvas canvas, float px, float py){
        paintColorStyle.setStyle(Paint.Style.FILL);
        paintColorStyle.setAntiAlias(true);
        paintColorStyle.setColor(pointColor);
        canvas.drawCircle(px, py, pointRadius, paintColorStyle);
    }

    private void drawLine(Canvas canvas, float px1, float py1, float px2, float py2){
        paintColorStyle.setStyle(Paint.Style.FILL);
        paintColorStyle.setAntiAlias(true);
        paintColorStyle.setColor(lineColor);
        paintColorStyle.setStrokeWidth(lineWidth);
        canvas.drawLine(px1, py1, px2, py2, paintColorStyle);
    }

    private void drawheightLine(Canvas canvas, float width, float height, float py){
        paintColorStyle.setStyle(Paint.Style.FILL);
        paintColorStyle.setAntiAlias(true);
        paintColorStyle.setColor(Color.RED);
        paintColorStyle.setStrokeWidth(lineWidth);
        paintColorStyle.setTextSize(30);
        canvas.drawLine(0, py, width, py, paintColorStyle);
        //TODO ajustar escala de py
        double p = switchRange(py, 0, height, maxheight, minheight);
        String h = String.format("%.2f m", p);
        if(py > 40) {
            canvas.drawText(h, 0, py - 15, paintColorStyle);
        } else {
            canvas.drawText(h, 0, py + 30, paintColorStyle);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        heightLine = (int) event.getY();
        refresh();
        return true;
    }
}

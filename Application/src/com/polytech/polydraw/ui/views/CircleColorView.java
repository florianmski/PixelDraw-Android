package com.polytech.polydraw.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.View;

public class CircleColorView extends View
{
	private int color;
	private Paint circlePaint;
	private Paint circleOutlinePaint;
	
	public CircleColorView(Context context, String colorString)
	{
		super(context);
		circlePaint = new Paint();
		circleOutlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		circleOutlinePaint.setStyle(Style.STROKE);
		circleOutlinePaint.setColor(Color.parseColor("#88000000"));
		setColor(colorString);
	}

	@Override
	public void draw(Canvas canvas) 
	{
		float cx = getWidth()/2;
		float cy = getHeight()/2;
//		Log.e("test","cx : "  + cx);
//		Log.e("test","cy : "  + cy);
		canvas.drawCircle(cx, cy, cx/2, circlePaint);
		canvas.drawCircle(cx, cy, cx/2, circleOutlinePaint);
	}

	public void setColor(String colorString)
	{
		this.color = Color.parseColor(colorString);
		circlePaint.setColor(color);
		invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	{
		int min = Math.min(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(min, min);
	}	
	
	
}

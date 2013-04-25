package com.polytech.polydraw.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class CircleColorView extends View
{
	private int color;
	private Paint circlePaint;
	private Paint circleOutlinePaint;

	public CircleColorView(Context context, Integer color8888) 
	{
		super(context);
		init(color8888);
	}

	public CircleColorView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		init(0xff51574a);
	}

	public CircleColorView(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		init(0xff51574a);
	}
	
	public void init(Integer color8888)
	{
		circlePaint = new Paint();
		circleOutlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		circleOutlinePaint.setStyle(Style.STROKE);
		circleOutlinePaint.setColor(0xff000000);
		setColor(color8888);
	}

	@Override
	public void draw(Canvas canvas) 
	{
		super.draw(canvas);
		
		float cx = getWidth()/2;
		float cy = getHeight()/2;
		canvas.drawCircle(cx, cy, cx/2, circlePaint);
		canvas.drawCircle(cx, cy, cx/2, circleOutlinePaint);
	}

	public void setColor(Integer color8888)
	{
		this.color = color8888;
		circlePaint.setColor(color);
		invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	{
		int min = Math.min(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(min, min);
	}
	
}

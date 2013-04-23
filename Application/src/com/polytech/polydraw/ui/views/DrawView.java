package com.polytech.polydraw.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View
{
	private final static int GRID_SIZE = 16;
	
	private String currentColor = "#51574a";

	// matrix containing the case colors
	private String[][] matrix;
	// paint used to draw "pixels"
	private Paint casePaint;
	// paint used to draw the grid
	private Paint gridPaint;

	public DrawView(Context context) 
	{
		super(context);
		init();
	}

	public DrawView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		init();
	}

	public DrawView(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);
		init();
	}

	private void init()
	{
		// let's start by filling the matrix with white
		matrix = new String[GRID_SIZE][GRID_SIZE];
		for(int i = 0; i < GRID_SIZE; i++)
		{
			for(int j = 0; j < GRID_SIZE; j++)
				matrix[i][j] = "#FFFFFF";
		}

		casePaint = new Paint();

		gridPaint = new Paint();
		gridPaint.setColor(Color.parseColor("#88000000"));
	}
	
	public void setColor(String colorString)
	{
		this.currentColor = colorString;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);

		// surface size in pixels
		int sizeInpixels = getMeasuredWidth();
		// determinate the size of a case
		int caseSize = (int) ((float)sizeInpixels / GRID_SIZE);

		// draw each case one by one using the right color
		for(int i = 0; i < GRID_SIZE; i++)
		{
			for(int j = 0; j < GRID_SIZE; j++)
			{
				casePaint.setColor(Color.parseColor(matrix[i][j]));
				canvas.drawRect(i*caseSize, j*caseSize, (i+1)*caseSize, (j+1)*caseSize, casePaint);
			}
		}

		// draw the grid on top of the cases
		for(int i = 0; i < GRID_SIZE; i++)
		{
			float end = i*caseSize;
			// line
			canvas.drawLine(0, end, sizeInpixels, end, gridPaint);
			// column
			canvas.drawLine(end, 0, end, sizeInpixels, gridPaint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		int sizeInpixels = getMeasuredWidth();
		int caseSize = (int) ((float)sizeInpixels / GRID_SIZE);

		int x = (int) (event.getX()/caseSize);
		int y = (int) (event.getY()/caseSize);
		
		// we're out, we don't handle this, return
		if((x <= GRID_SIZE-1 && y <= GRID_SIZE-1) && (x >= 0 && y >= 0))
		{
			String caseColor = matrix[x][y];
			// redraw only if player use another color on the case
			if(caseColor.equals(currentColor))
				return true;
			
			// locate the square which correspond to the point the user has touched
			// I do think we can think of a better algorithm but let's start with this one
			matrix[x][y] = currentColor;

			// redraw
			invalidate();
			Log.e("test", "redraw");
		}

		// we have consumed the event, return true
		return true;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	{
		// we want the surface to be a square which not goes beyond the screen
		int min = Math.min(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(min, min);
	}
}

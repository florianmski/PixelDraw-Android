package com.polytech.polydraw.ui.views;

import java.util.ArrayList;
import java.util.List;

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
	
	private int currentColor = 0xff51574a;

	// matrix containing the case colors
	private List<Integer> picture;
	// paint used to draw "pixels"
	private Paint casePaint;
	// paint used to draw the grid
	private Paint gridPaint;
	// define if the player is currently the drawer or not
	private boolean drawer = true;
	
	private OnNewDrawingDataListener listener;

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
		picture = new ArrayList<Integer>(GRID_SIZE*GRID_SIZE);
		for(int i=0; i<GRID_SIZE*GRID_SIZE;i++){
			picture.add(0xffffffff); //Init with white color
		}

		casePaint = new Paint();

		gridPaint = new Paint();
		gridPaint.setColor(0xff000000);
		
	}
	
	public void setColor(int colorString)
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
		for(int i=0; i<GRID_SIZE*GRID_SIZE;i++){
			int x = i/GRID_SIZE;
			int y = i%GRID_SIZE;
			casePaint.setColor(picture.get(i));
			canvas.drawRect(x*caseSize, y*caseSize, (x+1)*caseSize, (y+1)*caseSize, casePaint);
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
		// if player isn't currently the drawer, do nothing when player touch the view
		if(!drawer)
			return false;
		
		int sizeInpixels = getMeasuredWidth();
		int caseSize = (int) ((float)sizeInpixels / GRID_SIZE);

		int x = (int) (event.getX()/caseSize);
		int y = (int) (event.getY()/caseSize);
		
		// we're out, we don't handle this, return
		if((x <= GRID_SIZE-1 && y <= GRID_SIZE-1) && (x >= 0 && y >= 0))
		{
			int pictureIndex = x*GRID_SIZE+y;
			int caseColor = picture.get(pictureIndex);
			// redraw only if player use another color on the case
			if(caseColor == currentColor)
				return true;
			
			// locate the square which correspond to the point the user has touched
			// I do think we can think of a better algorithm but let's start with this one
			picture.set(pictureIndex, currentColor);

			// redraw
			invalidate();
			
			// send new data
			if(listener != null && drawer)
				listener.onNewDrawingData(picture);
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
	
	public void update(List<Integer> picture)
	{
		// if player is the drawer, no need to update, return
		if(drawer)
			return;
		
		this.picture = picture;
		invalidate();
	}
	
	public void setDrawer(boolean drawer)
	{
		this.drawer = drawer;
	}
	
	public List<Integer> getDrawing()
	{
		return picture;
	}
	
	public void setOnNewDrawingDataListener(OnNewDrawingDataListener listener)
	{
		this.listener = listener;
	}
	
	public interface OnNewDrawingDataListener
	{
		public void onNewDrawingData(List<Integer> picture);
	}
}

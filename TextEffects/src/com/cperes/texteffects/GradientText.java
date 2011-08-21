package com.cperes.texteffects;

import com.cperes.texteffects.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class GradientText extends View {

	private TextPaint mainTextPaint;
	private TextPaint secondaryTextPaint;
	private int topColor;
	private int bottomColor;
	private float mainTextSize;
	private float secondaryTextSize;
	private int mainTextStyle;
	private int secondaryTextStyle;
	private String mainText;
	private String secondaryText;
	private Boolean isMirror = false;

	public GradientText(Context context) {
		super(context);
		AttributeSet attrs = null;
		init(attrs);
	}
	public GradientText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
		Log.w("MIRROR", "INIT");
	}
	public GradientText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
		Log.w("MIRROR", "INIT");
	}
	
	public void setMainText(String mainText) {
		this.mainText = mainText;
	}
	public void setSecondaryText(String secondaryText) {
		this.secondaryText = secondaryText;
	}
	public void setMainTextSize(float mainTextSize) {
		this.mainTextSize = mainTextSize;
	}
	public void setSecondaryTextSize(float secondaryTextSize) {
		this.secondaryTextSize = secondaryTextSize;
	}
	public void setMainTextStyle(int mainTextStyle) {
		this.mainTextStyle = mainTextStyle;
	}
	public void setSecondaryTextStyle(int secondaryTextStyle) {
		this.secondaryTextStyle = secondaryTextStyle;
	}
	public void setTopColor(int topColor) {
		this.topColor = topColor;
	}
	public void setBottomColor(int bottomColor) {
		this.bottomColor = bottomColor;
	}
	public void setIsMirror(Boolean isMirror) {
		this.isMirror = isMirror;
	}
	
	public void init(AttributeSet attrs) {
		
		if(attrs!=null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs,R.styleable.TextEffects);
			if(a.getString(R.styleable.TextEffects_te_main_text)!=null) {
				mainText = a.getString(R.styleable.TextEffects_te_main_text);
				if(a.getString(R.styleable.TextEffects_te_secondary_text)!=null) {
					secondaryText = a.getString(R.styleable.TextEffects_te_secondary_text);
					secondaryTextSize = a.getDimension(R.styleable.TextEffects_te_secondary_textsize, getResources().getDimension(R.dimen.secondaryTextSize));
					secondaryTextStyle = a.getInt(R.styleable.TextEffects_te_secondary_style, 0);
				}
				mainTextSize = a.getDimension(R.styleable.TextEffects_te_main_textsize, getResources().getDimension(R.dimen.mainTextSize));
				mainTextStyle = a.getInt(R.styleable.TextEffects_te_main_style, 1);
				if(isMirror) {
					topColor= a.getColor(R.styleable.TextEffects_te_colorbackground, 0xFFFFFF);
				}
				else {
					topColor = a.getColor(R.styleable.TextEffects_te_colortop, 0xFF000000);
				}
				bottomColor = a.getColor(R.styleable.TextEffects_te_colorbottom, 0xFF000000);
			}
			else {
				return;
			}
		}
		int[]colors = new int[]{topColor, bottomColor};
		float[]percentages = (isMirror)? new float[]{0.4f, 1}: new float[]{0.5f, 1};
		int gradientSize = (isMirror)? (int)(mainTextSize*1.8): (int)mainTextSize;
		Shader textShader=new LinearGradient(0, 0, 0, gradientSize, colors, percentages, TileMode.CLAMP);
		mainTextPaint = new TextPaint();
		if(mainTextStyle == 1) {
			mainTextPaint.setStyle(TextPaint.Style.FILL_AND_STROKE);
			mainTextPaint.setFakeBoldText(true);
			mainTextPaint.setStrokeWidth(2);
		}
		mainTextPaint.setAntiAlias(true);
		mainTextPaint.setTextSize(mainTextSize);
		mainTextPaint.setShader(textShader);
		
		if(secondaryText!=null) {
			secondaryTextPaint = new TextPaint();
			secondaryTextPaint.setAntiAlias(true);
			if(secondaryTextStyle == 1) {
				secondaryTextPaint.setStyle(TextPaint.Style.FILL_AND_STROKE);
				secondaryTextPaint.setFakeBoldText(true);
				secondaryTextPaint.setStrokeWidth(2);
			}
			secondaryTextPaint.setTextSize(secondaryTextSize);
			secondaryTextPaint.setShader(textShader);
		}
        
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		int width = (secondaryText!=null)? (int)(mainTextPaint.measureText(mainText)+secondaryTextPaint.measureText(secondaryText)): (int)(mainTextPaint.measureText(mainText));
	    setMeasuredDimension( width , (int) mainTextPaint.getFontSpacing());
	}
	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		canvas.drawText(mainText, 0, getMeasuredHeight()-2, mainTextPaint);
		if((secondaryText!=null)) canvas.drawText(secondaryText, mainTextPaint.measureText(mainText), getMeasuredHeight(), secondaryTextPaint);
//		canvas.translate(0, getMeasuredHeight()/2);
//		canvas.save();
	}

}

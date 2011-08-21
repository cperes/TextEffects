package com.cperes.texteffects;

import com.cperes.texteffects.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MirrorText extends RelativeLayout {

	private Matrix matrixMirrorY;
    private GradientText mirrorText ;
	private ImageView mirrorImage;
	private Bitmap textBitmap;
	private Context context;
	private View view;
	private int topColor;
	private int bottomColor;
	private float mainTextSize;
	private float secondaryTextSize;
	private int mainTextStyle;
	private int secondaryTextStyle;
	private String mainText;
	private String secondaryText;
    
	public MirrorText(Context context) {
		super(context);
		this.context = context;
		AttributeSet attrs = null;
		init(attrs);
	}
	public MirrorText(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		
		init(attrs);
	}
	public MirrorText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init(attrs);
	}
	
	public void setMainText(String mainText) {
		this.mainText = mainText;
	}
	public void setSecondaryText(String secondaryText) {
		this.secondaryText = secondaryText;
	}
	public void setMainTextSize(int mainTextSize) {
		this.mainTextSize = mainTextSize;
	}
	public void setSecondaryTextSize(int secondaryTextSize) {
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
	
	private void init(AttributeSet attrs) {
		if(attrs!=null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs,R.styleable.TextEffects);
			mainText = a.getString(R.styleable.TextEffects_te_main_text);
			secondaryText = a.getString(R.styleable.TextEffects_te_secondary_text);
			mainTextSize = a.getDimension(R.styleable.TextEffects_te_main_textsize, getResources().getDimension(R.dimen.mainTextSize));
			secondaryTextSize = a.getDimension(R.styleable.TextEffects_te_secondary_textsize, getResources().getDimension(R.dimen.secondaryTextSize));
			mainTextStyle = a.getInt(R.styleable.TextEffects_te_main_style, 1);
			secondaryTextStyle = a.getInt(R.styleable.TextEffects_te_secondary_style, 0);
			topColor = a.getColor(R.styleable.TextEffects_te_colortop, 0xFF000000);
			bottomColor = a.getColor(R.styleable.TextEffects_te_colorbottom, 0xFF000000);
		}
		
		LayoutInflater inflater = (LayoutInflater)   context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		inflater.inflate(R.layout.mirrortext, this);
		
		GradientText gradientText = (GradientText) findViewById(R.id.gradient_text);
		gradientText.init(attrs);
        mirrorText = (GradientText) findViewById(R.id.mirror_text);
        mirrorText.setIsMirror(true);
        mirrorText.init(attrs);
        mirrorText.setDrawingCacheEnabled(true); 
        mirrorImage = (ImageView) findViewById(R.id.mirror_image);
        
        mirrorText.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        Log.w("MIRROR", "MEASURES "+mirrorText.getMeasuredWidth()+" // "+ mirrorText.getMeasuredHeight());
        mirrorText.layout(0, -mirrorText.getMeasuredHeight()/2, mirrorText.getMeasuredWidth(), mirrorText.getMeasuredHeight()/2); 
        
        mirrorText.buildDrawingCache();
        Log.w("MIRROR", "drawingcache "+ mirrorText.getDrawingCache());
        textBitmap = Bitmap.createBitmap(mirrorText.getDrawingCache());  
        
        initMirrorMatrix();
        drawMatrix();
    }
    
    private void initMirrorMatrix()
    {
     float[] mirrorY = 
     {  1, 0, 0, 
      0, -1, 0,  
      0, 0, 1    
     };
     matrixMirrorY = new Matrix();
     matrixMirrorY.setValues(mirrorY);
    }
    
    private void drawMatrix()
    {
      Matrix matrix = new Matrix();
      matrix.postConcat(matrixMirrorY); 
      
      Bitmap mirrorBitmap = Bitmap.createBitmap(textBitmap, 0, 0, textBitmap.getWidth(), textBitmap.getHeight(), matrix, true);
      mirrorImage.setImageBitmap(mirrorBitmap);
      Log.w("MIRROR", "mirrorImage "+ mirrorImage.getWidth() + " // "+mirrorImage.getHeight());
     }
}
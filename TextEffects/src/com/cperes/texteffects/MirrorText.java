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
	
	
	
	private void init(AttributeSet attrs) {
		if(attrs!=null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs,R.styleable.TextEffects);
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
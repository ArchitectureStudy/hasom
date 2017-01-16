package com.hasom.mvc.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.squareup.picasso.Transformation;

public class RoundedConrnerTransformation implements Transformation {

	@Override
	public String key() {
		// TODO Auto-generated method stub
		return "square()";
	}

	@Override
	public Bitmap transform(Bitmap bitmap) {
		Bitmap output = null;
		try {
			output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		    final Canvas canvas = new Canvas(output);
		 
		    final int color = Color.RED;
		    final Paint paint = new Paint();
		    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		    final RectF rectF = new RectF(rect);
		 
		    paint.setAntiAlias(true);
		    canvas.drawARGB(0, 0, 0, 0);
		    paint.setColor(color);
		    canvas.drawOval(rectF, paint);
		 
		    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		    canvas.drawBitmap(bitmap, rect, rect, paint);


			bitmap.recycle();

		} catch (Exception ex) {
			// 예외가 발생하면, 리턴값은 null
			ex.printStackTrace();
			output = null;
		} catch (OutOfMemoryError err) {
			// 메모리 에러가 발생하면, 리턴값은 null
			err.printStackTrace();
			output = null;
		}

		return output;
	}

}

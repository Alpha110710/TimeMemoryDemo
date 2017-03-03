package com.time.memory.gui;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.time.memory.R;

/**
 * ==============================
 *
 * @author Qiu
 * @version 1.0 ==============================
 * @Package com.time.memory.gui
 * @Description:自定义提示Toast
 * @date 2016-7-5 上午10:58:47
 */
public class MyToast extends Toast {

	public MyToast(Context context) {
		super(context);
	}

	/**
	 * 生成一个只有文本的自定义Toast
	 *
	 * @param context  // 上下文对象
	 * @param text     // 要显示的文字
	 * @param duration // Toast显示时间
	 * @return
	 */
	public static MyToast makeTextToast(Context context, CharSequence text, int duration) {
		MyToast result = new MyToast(context);
		LayoutInflater inflate = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflate.inflate(R.layout.view_toast, null);
		TextView tv = (TextView) v.findViewById(android.R.id.message);
		tv.setText(text);
		result.setView(v);
		// setGravity方法用于设置位置，此处为垂直居中
		result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		result.setDuration(duration);
		return result;
	}
}
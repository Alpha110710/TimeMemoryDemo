package com.time.memory.view.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.time.memory.R;
import com.time.memory.entity.PhotoInfo;
import com.time.memory.entity.WriterMemory;
import com.time.memory.gui.WriterEditText;
import com.time.memory.gui.sixGridImage.SixGridImageViewAdapter;
import com.time.memory.gui.sixGridImage.SixGridImageWriterView;
import com.time.memory.util.CLog;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * ==============================
 *
 * @author Qiu
 * @version V1.0
 * @Description:写记忆
 * @date 2016-10-7下午3:09:16
 * ==============================
 */
public class WriterMHolder extends BaseHolder<WriterMemory> implements TextWatcher {
	private static final String TAG = "WriterMHolder";
	@Bind(R.id.writer_grid)
	SixGridImageWriterView writer_grid;

	@Bind(R.id.writer_add_iv)
	ImageView writer_add_iv;//添加
	@Bind(R.id.writer_num_tv)
	TextView writer_num_tv;//数量指示
	@Bind(R.id.writer_date_tv)
	TextView writer_date_tv;//时间
	@Bind(R.id.writer_loc_tv)
	TextView writer_loc_tv;//地址

	@Bind(R.id.writer_et)
	WriterEditText writer_et;

	WriterMemory mEntity;
	private int mPosition;
	private int size = 1;

	public WriterMHolder(View view) {
		super(view);
	}

	@Override
	public void init() {
		writer_et.addTextChangedListener(this);

		SixGridImageViewAdapter<PhotoInfo> mAdapter = new SixGridImageViewAdapter<PhotoInfo>() {
			@Override
			protected void onDisplayImage(Context context, final ImageView imageView, PhotoInfo entity) {
				//显示
				if (size == 1) {
					Picasso.with(context).load("file://" + entity.getPhotoPath()).config(Bitmap.Config.RGB_565).resize(500, 400).centerCrop().into(imageView);
				} else {
					Picasso.with(context).load("file://" + entity.getPhotoPath()).config(Bitmap.Config.RGB_565).resize(300, 300).centerCrop().into(imageView);
				}
			}

			@Override
			protected void onDeleteClick(int position) {
				CLog.e(TAG, "onDeleteClick:" + position + "  mPosition:" + mPosition);
				//删除图片
				mHolderCallBack.onClick(mPosition, position, 1);
			}

			@Override
			protected void onItemImageClick(int position) {
				CLog.e(TAG, "onItemImageClick:" + position + "  mPosition:" + mPosition);
				//点击图片
				mHolderCallBack.onClick(mPosition, position, 2);
			}

			@Override
			protected void onAddClick(int position) {
				//添加
				CLog.e(TAG, "onAddClick:" + position + "  mPosition:" + mPosition);
				mHolderCallBack.onClick(mPosition, position, 3);
			}
		};
		writer_grid.setAdapter(mAdapter);

	}

	@Override
	public void setData(WriterMemory entity, int position) {
		super.setData(entity);
		this.mPosition = position;
		this.mEntity = entity;
		this.size = entity.getPictureEntits().size();
		//设置数据
		writer_grid.setImagesData(mEntity.getPictureEntits());
		//设置显示头
//		writer_add_iv.setVisibility(entity.isFirst() ? View.GONE : View.VISIBLE);
		//文字
		writer_et.setText(mEntity.getDesc());
		//数量指示
//		writer_num_tv.setText(mEntity.getNum());
		//时间显示
		writer_date_tv.setText(mEntity.getDate());
		//地址
		writer_loc_tv.setText(mEntity.getAddress());

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	@Override
	public void afterTextChanged(Editable s) {
		CLog.e(TAG, "s.toString().toLowerCase():" + s.toString().trim());
		//文字变化
		mEntity.setDesc(s.toString().trim());
	}

	@OnClick({R.id.writer_date_tv, R.id.writer_loc_tv, R.id.writer_add_iv})
	public void onClick(View view) {
		if (view.getId() == R.id.writer_date_tv) {
			//日期
			mHolderCallBack.onClick(mPosition, -1, 4);
		} else if (view.getId() == R.id.writer_loc_tv) {
			//地址
			mHolderCallBack.onClick(mPosition, -1, 5);
		} else if (view.getId() == R.id.writer_add_iv) {
			//头部追加
			mHolderCallBack.onClick(mPosition, -1, 6);
		}
	}

}
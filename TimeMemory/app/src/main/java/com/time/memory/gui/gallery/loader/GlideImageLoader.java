/*
 * Copyright (C) 2014 pengjianbo(pengjianbosoft@gmail.com), Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.time.memory.gui.gallery.loader;

import android.app.Activity;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.time.memory.MainApplication;
import com.time.memory.gui.gallery.widget.GFImageView;


/**
 * Desction:
 * Date:15/12/1 下午10:28
 */
public class GlideImageLoader implements com.time.memory.gui.gallery.ImageLoader {

	//resize(width, height).centerCrop().error(defaultDrawable).placeholder(defaultDrawable)
	@Override
	public void displayImage(Activity activity, String path, final GFImageView imageView) {
		Picasso.with(MainApplication.getContext()).load("file://" + path).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(imageView);
	}

	@Override
	public void clearMemoryCache() {
	}
}

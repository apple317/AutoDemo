package com.base.image;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.base.utils.FileUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;


/**
 * AsyncHttpClient async网络申请实现类
 * 如果有新网络tcp请求，就要重新实现一个网络交互类
 *
 * @author applehsp
 */
public class ImageLoadImpl implements BaseImages {

    private Glide mImageLoader = null;
    Context mContext;
    //单例模式实现
    private static ImageLoadImpl instance;

    public static ImageLoadImpl getInstance(Context context) {
        if (instance == null)
            instance = new ImageLoadImpl(context);
        return instance;
    }

    private ImageLoadImpl(Context context) {
        mContext = context;
        mImageLoader = Glide.get(context);
    }

    @Override
    public void displayImage(String uri, ImageView imageView) {
        displayImage(uri, imageView, null);
    }

    @Override
    public void displayImage(String uri, ImageView imageView, AppImageOptions options) {
        displayImage(uri, imageView, options, null);
    }


    @Override
    public void displayImage(String uri, ImageView imageView, AppImageOptions options, final BaseImageLoadingListener listener) {
        if (uri == null || uri.trim().equals("") || imageView == null) {
            return;
        }
        RequestOptions displayImageOptions = null;
        if (options != null && options.imageResOnLoading != 0) {
            displayImageOptions = new RequestOptions()
                    .centerCrop()
                    .placeholder(options.imageResForEmptyUri == 0 ? options.imageResOnLoading : options.imageResOnFail)
                    .error(options.imageResOnFail == 0 ? options.imageResOnLoading : options.imageResOnLoading)
                    .diskCacheStrategy(DiskCacheStrategy.NONE);
        }
        if (listener == null) {
            mImageLoader.with(mContext)
                    .load(uri)
                    .apply(displayImageOptions)
                    .into(imageView);
        } else {
            Glide.with(mContext)
                    .load(uri)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            listener.onLoadingFailed("", null, 0);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            listener.onLoadingComplete("", null, resource);
                            return false;
                        }
                    }).into(imageView);
        }
    }

    @Override
    public void loadImage(String uri, AppImageOptions options, final BaseImageLoadingListener listener) {
        RequestOptions displayImageOptions = null;
        if (options != null && options.imageResOnLoading != 0) {
            displayImageOptions = new RequestOptions()
                    .centerCrop()
                    .placeholder(options.imageResForEmptyUri == 0 ? options.imageResOnLoading : options.imageResOnFail)
                    .error(options.imageResOnFail == 0 ? options.imageResOnLoading : options.imageResOnLoading)
                    .diskCacheStrategy(DiskCacheStrategy.NONE);
        }
        if (listener == null) {
            mImageLoader.with(mContext)
                    .load(uri)
                    .apply(displayImageOptions);
        } else {
            Glide.with(mContext)
                    .load(uri)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            listener.onLoadingFailed("", null, 0);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            listener.onLoadingComplete("", null, resource);
                            return false;
                        }
                    });
        }
    }

    @Override
    public void loadImage(String uri, BaseImageLoadingListener listener) {
        loadImage(uri, null, listener);
    }

    @Override
    public Bitmap loadImage(String uri) {
        return null;
    }


    private BitmapFactory.Options getBitmapOption(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

    @Override
    public void initImagesHelper() {

    }

    @Override
    public long getImageSize() {
        return new FileUtils().getFileSize(Glide.getPhotoCacheDir(mContext));
    }

    @Override
    public void clearImages() {
        mImageLoader.clearDiskCache();
    }
}

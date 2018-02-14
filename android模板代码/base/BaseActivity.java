package com.jack.fasthelp.ui.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.jack.fasthelp.R;

import rx.subscriptions.CompositeSubscription;

/**
 * Jack
 * luodijack@163.com
 * https://github.com/LuodiJackShen
 * 2018.02.14 下午4:55.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected final CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
	//xml must write it: android:fitsSystemWindows="true"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Integer color = getResources().getColor(R.color.base_color);
            if (color != null) {
                getWindow().setStatusBarColor(color);
            } else {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        initView();
    }

    @LayoutRes
    protected abstract int getContentView();

    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.clear();
    }
}

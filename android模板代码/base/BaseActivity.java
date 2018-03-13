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
		Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.base_red));
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup systemContent = (ViewGroup) findViewById(android.R.id.content);
            View statusBarView = new View(this);
            ViewGroup.LayoutParams lp =
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            getStatusBarHeight()
                    );
            statusBarView.setBackgroundColor(getResources().getColor(R.color.base_red));
            systemContent.getChildAt(0).setFitsSystemWindows(true);
            systemContent.addView(statusBarView, 0, lp);
        }

        initView();
    }

	 private int getStatusBarHeight() {
        int height = 0;
        //获取status_bar_height资源的ID
        int resourceId = getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            height = getResources().getDimensionPixelSize(resourceId);
        }
        return height;
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

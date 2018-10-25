package com.adasplus.proadas.common.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.adasplus.proadas.App;
import com.adasplus.proadas.common.presenter.BasePresenter;
import com.adasplus.proadas.common.util.net.RequestManager;
import com.adasplus.proadas.common.view.BaseView;
import com.adasplus.proadas.common.view.custom.LoadingDialog;
import com.adasplus.proadas.common.view.custom.ProgressLoading;


/**
 * Author:fengyin
 * Date: 16-3-18    09:31
 * Email:594601408@qq.com
 * LastUpdateTime: 16-3-18
 * LastUpdateBy:594601408@qq.com
 */
public abstract class BaseActivity<T extends BasePresenter> extends FragmentActivity implements BaseView, View.OnClickListener {
    protected T mBasePresenter;
    protected ProgressLoading mProgressDialog;
    protected Toolbar mToolBar;
    protected FragmentManager mFragmentManager;
    public boolean mCreate = false;
    public LoadingDialog mLoadingDialog;
    public boolean isShowDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShowDialog = true;
        mLoadingDialog = new LoadingDialog(this);
        mFragmentManager = getSupportFragmentManager();
        int layoutId = getLayoutId();
        setContentView(layoutId);
        initView();
        mBasePresenter = createPresenter();
        mCreate = true;
    }

    protected abstract T createPresenter();

    @Override
    protected void onResume() {
        super.onResume();
        if (mCreate) {
            initData();
            mCreate = false;
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBasePresenter != null) {
            mBasePresenter.onDestroy();
        }
        RequestManager.getInstance(App.getContext()).cancleAll();
        isShowDialog = false;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();



    protected abstract void initData() ;



    public void showToast(String msg) {

        Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toast(String msg) {
        showToast(msg);
    }

    @Override
    public void showProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SwipeRefreshLayout loadiView = getLoadiView();
                if(loadiView!=null)
                    loadiView.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SwipeRefreshLayout loadiView = getLoadiView();
                if(loadiView!=null)
                    loadiView.setRefreshing(false);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    public void showProcessDialog(final String text) {

         runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressLoading(BaseActivity.this);
                    mProgressDialog.setMessage(text);
                }
                mProgressDialog.show();
            }
        });
    }

    /**
     * 隐藏加载对话框
     */
    public void dismissProcessDialog() {

            runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }
            }
        });
    }


    @Override
    public void showProgress(String msg) {
        toast(msg);
    }

    @Override
    public void toastNetworkError(String msg) {
        toast(msg);
    }

    public abstract SwipeRefreshLayout getLoadiView();


    public boolean onTouchEvent(MotionEvent event) {
        if(null != this.getCurrentFocus()){

            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super .onTouchEvent(event);
    }
}

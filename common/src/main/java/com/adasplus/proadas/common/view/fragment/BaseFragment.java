package com.adasplus.proadas.common.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.adasplus.proadas.App;
import com.adasplus.proadas.common.presenter.BasePresenter;
import com.adasplus.proadas.common.util.net.AdasNet;
import com.adasplus.proadas.common.util.net.RequestManager;
import com.adasplus.proadas.common.view.BaseView;


/**
 * Created by fengyin on 16-4-11.
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements View.OnClickListener, BaseView {
    protected Activity mActivity;
    protected View mRootView;
    protected T mBasePresenter;

    private boolean mVisible;
    private boolean mCreated = false;
    private ProgressDialog mProgressDialog;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            mVisible = true;
            onVisible();
        } else {
            mVisible = false;
            onInVisible();
        }
    }



    protected void onVisible() {
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInVisible() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mCreated = true;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        mRootView = inflater.inflate(layoutId, container, false);
        initView(mRootView);
        mBasePresenter = createPresenter();
        return mRootView;
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    protected abstract T createPresenter();


    protected abstract void initData();

    @Override
    public void onResume() {
        super.onResume();
        if (mCreated) {
            initData();
            mCreated = false;
        }
//        if (mBasePresenter != null) {
//            mBasePresenter.onResume();
//        }
        mVisible = getUserVisibleHint();
        setUserVisibleHint(mVisible);
    }

    @Override
    public void onPause() {
        super.onPause();
        mCreated=true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBasePresenter != null) {
            mBasePresenter.onDestroy();
        }
       RequestManager.getInstance(App.getContext()).cancleAll();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideProgress();
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
    }

    /**
     * Show message in logcat in info level.
     *
     * @param msg
     */


    public void showToast(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {

    }
    @Override
    public void showProgress(String msg) {
        showProcessDialog(msg);
    }
    @Override
    public void showProgress() {
        mActivity.runOnUiThread(new Runnable() {
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
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SwipeRefreshLayout loadiView = getLoadiView();
                if(loadiView!=null)
                    loadiView.setRefreshing(false);
            }
        });
    }




    public void showProcessDialog(final String text) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog(getActivity());
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

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void toast(String msg) {
        showToast(msg);
    }

    public abstract SwipeRefreshLayout getLoadiView();
}

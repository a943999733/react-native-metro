package com.beng.react.ui;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.beng.react.UpdateProgressListener;
import com.beng.react.util.FileUtils;
import com.facebook.react.ReactFragment;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.ReactContext;
import com.beng.react.LoadScriptListener;
import com.beng.react.modle.RnBundle;
import com.beng.react.util.ScriptLoadUtil;
import com.demo.R;

import androidx.fragment.app.FragmentActivity;

import com.demo.demo.MainApplication;

import java.io.File;

public class RNBundleView extends FrameLayout {

    private static final String TAG = "RNBundleView";

    private RnBundle rnBundle;
    protected ReactFragment fragment;

    public RNBundleView(@NonNull Context context) {
        this(context, null);
    }

    public RNBundleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RNBundleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_layout_rn_bundle_view, this, true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RNBundleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setRnBundle(final RnBundle rnBundle) {
        this.rnBundle = rnBundle;
        if (rnBundle != null) {
            final ReactInstanceManager manager = MainApplication.instance.getReactNativeHost().getReactInstanceManager();
            if (!manager.hasStartedCreatingInitialContext()
                    || ScriptLoadUtil.getCatalystInstance(MainApplication.instance.getReactNativeHost()) == null) {
                manager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
                    @Override
                    public void onReactContextInitialized(ReactContext context) {
                        loadRnBundle();
                    }
                });
            } else {
                loadRnBundle();
            }

        }
    }

    public boolean onBackPressed() {
        if (fragment != null && fragment.isVisible()) {
            return fragment.onBackPressed();
        }
        return false;
    }

    public FragmentActivity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof FragmentActivity) {
                return (FragmentActivity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }

        return null;
    }

    protected void loadRnBundle() {
        loadScript(new LoadScriptListener() {
            @Override
            public void onLoadComplete(boolean success, String scriptPath) {
                if (success) {
                    fragment = new ReactFragment.Builder()
                            .setComponentName(rnBundle.mainComponentName)
                            .build();

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.bundle_container, fragment)
                            .commit();
                }

            }
        });
    }

    protected void loadScript(final LoadScriptListener loadListener) {
        /** all buz module is loaded when in debug mode*/
        if (ScriptLoadUtil.MULTI_DEBUG) {//当设置成debug模式时，所有需要的业务代码已经都加载好了
            loadListener.onLoadComplete(true, null);
            return;
        }
        int pathType = rnBundle.scriptType;
        String scriptPath = rnBundle.scriptUrl;
        final CatalystInstance instance = ScriptLoadUtil.getCatalystInstance(MainApplication.instance.getReactNativeHost());
        if (pathType == RnBundle.ASSET) {
            ScriptLoadUtil.loadScriptFromAsset(MainApplication.instance, instance, scriptPath, false);
            loadListener.onLoadComplete(true, null);
        } else if (pathType == RnBundle.FILE) {
            File scriptFile = new File(MainApplication.instance.getFilesDir()
                    + File.separator +/*ScriptLoadUtil.REACT_DIR+File.separator+*/scriptPath);
            scriptPath = scriptFile.getAbsolutePath();
            ScriptLoadUtil.loadScriptFromFile(scriptPath, instance, scriptPath, false);
            loadListener.onLoadComplete(true, scriptPath);
        } else if (pathType == RnBundle.NETWORK) {
            //由于downloadRNBundle里面的md5参数由组件名代替了，实际开发中需要用到md5校验的需要自己修改
            FileUtils.downloadRNBundle(MainApplication.instance, scriptPath, rnBundle.mainComponentName, new UpdateProgressListener() {
                @Override
                public void updateProgressChange(final int precent) {
                    Log.i(TAG, "server change=" + precent);
                }

                @Override
                public void complete(boolean success) {
                    Log.i(TAG, "server complete success=" + success);

                    if (!success) {
                        loadListener.onLoadComplete(false, null);
                        return;
                    }
                    String info = FileUtils.getCurrentPackageMd5(MainApplication.instance);
                    String bundlePath = FileUtils.getPackageFolderPath(MainApplication.instance, info);
                    String jsBundleFilePath = FileUtils.appendPathComponent(bundlePath, rnBundle.scriptPath);
                    File bundleFile = new File(jsBundleFilePath);
                    if (bundleFile != null && bundleFile.exists()) {
                        ScriptLoadUtil.loadScriptFromFile(jsBundleFilePath, instance, jsBundleFilePath, false);
                    } else {
                        success = false;
                    }
                    loadListener.onLoadComplete(success, jsBundleFilePath);
                }
            });
        }
    }
}

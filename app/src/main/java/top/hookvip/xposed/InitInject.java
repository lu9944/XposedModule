package top.hookvip.xposed;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import java.lang.reflect.Method;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class InitInject extends XC_MethodHook implements IXposedHookLoadPackage, IXposedHookZygoteInit {

    public static String ModulePath;
    public static XC_LoadPackage.LoadPackageParam loadParam;

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {

        ModulePath = startupParam.modulePath;

    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        loadParam = lpparam;
        XposedBridge.hookMethod(getAtInject(), this);

    }

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        super.afterHookedMethod(param);
    }

    private Method getAtInject() {
        try {
            if (loadParam.appInfo.name != null) {

                Class<?> clz = loadParam.classLoader.loadClass(loadParam.appInfo.name);

                try {
                    return clz.getDeclaredMethod("attachBaseContext", Context.class);
                } catch (Throwable i) {
                    try {
                        return clz.getDeclaredMethod("onCreate");
                    } catch (Throwable e) {
                        try {
                            return clz.getSuperclass().getDeclaredMethod("attachBaseContext", Context.class);
                        } catch (Throwable m) {
                            return clz.getSuperclass().getDeclaredMethod("onCreate");
                        }
                    }
                }

            }
        } catch (Throwable o) {
            XposedBridge.log("[error]" + Log.getStackTraceString(o));
        }
        try {
            return ContextWrapper.class.getDeclaredMethod("attachBaseContext", Context.class);
        } catch (Throwable u) {
            XposedBridge.log("[error]" + Log.getStackTraceString(u));
            return null;
        }
    }
}
package top.hookvip.xposed;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class InitInject implements IXposedHookLoadPackage, IXposedHookZygoteInit {
    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {



    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {



    }
}
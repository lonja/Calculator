package com.lonja.calculator.ui.splashscreen;

import rx.Observable;

public interface SplashScreenContract {

    interface View extends com.lonja.calculator.ui.common.View {

        void showErrorPermissionCheck();

    }

    interface ViewModel extends com.lonja.calculator.ui.common.ViewModel<View> {

        Observable<Boolean> checkContactsPesmission();

        void checkAccount();
    }
}

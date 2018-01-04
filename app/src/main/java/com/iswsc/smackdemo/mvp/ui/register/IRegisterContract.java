package com.iswsc.smackdemo.mvp.ui.register;

import com.iswsc.smackdemo.mvp.base.BasePresenter;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/12/31 17:22.
 */

public interface IRegisterContract {

        interface View {

                void showLoading();

                void hideLoading();

                void registerError(String message);

                void toSetting();

                void toFinish();
        }

        interface Presenter extends BasePresenter{

                void toRegister(String account, String password);

                void toSetting();
        }

}

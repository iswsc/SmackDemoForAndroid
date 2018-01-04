package com.iswsc.smackdemo.mvp.ui.login;

import com.iswsc.smackdemo.mvp.base.BasePresenter;
import com.iswsc.smackdemo.mvp.base.BaseView;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/12/31 17:22.
 */

public interface ILoginContract{

        interface View {

                void initAccount(String account,String password);

                void showLoading();

                void hideLoading();

                void loginError(String message);

                void toRegister();

                void toSetting();

                void toMainUI();
        }

        interface Presenter extends BasePresenter{

                void toLogin(String account,String password);

                void toLoginSuccess();

                void initAccount();

                void toRegister();

                void toSetting();
        }

}

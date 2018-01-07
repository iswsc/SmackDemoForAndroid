package com.iswsc.smackdemo.mvp.ui.setting;

import com.iswsc.smackdemo.mvp.base.BasePresenter;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/12/31 17:22.
 */

public interface ISettingContract {

        interface View {

                void showSetting(String host, String port,String serviceName,String resource);
        }

        interface Presenter extends BasePresenter{

                void toSaveSetting(String host, String port,String serviceName,String resource);

        }

}

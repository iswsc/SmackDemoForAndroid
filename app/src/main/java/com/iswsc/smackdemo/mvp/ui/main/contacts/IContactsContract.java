package com.iswsc.smackdemo.mvp.ui.main.contacts;

import android.os.Bundle;

import com.iswsc.smackdemo.mvp.base.BasePresenter;
import com.iswsc.smackdemo.vo.ContactVo;

import java.util.ArrayList;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/12/31 17:22.
 */

public interface IContactsContract {

        interface View {

                void toUpdateList(ArrayList<ContactVo> mContactList);

                void toChattingUI(Bundle bundle);
        }

        interface Presenter extends BasePresenter{

                void toChattingUI(ContactVo vo);

        }

}

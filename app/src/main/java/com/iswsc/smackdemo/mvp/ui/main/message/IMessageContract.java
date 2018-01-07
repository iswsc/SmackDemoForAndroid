package com.iswsc.smackdemo.mvp.ui.main.message;

import android.os.Bundle;

import com.iswsc.smackdemo.mvp.base.BasePresenter;
import com.iswsc.smackdemo.vo.ChatMessageVo;
import com.iswsc.smackdemo.vo.ContactVo;

import java.util.ArrayList;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2017/12/31 17:22.
 */

public interface IMessageContract {

        interface View {

                void toUpdateMessage(ChatMessageVo vo);

                void toChattingUI(Bundle bundle,int position);
        }

        interface Presenter extends BasePresenter{

                void toChattingUI(ChatMessageVo vo,int position);

        }

}

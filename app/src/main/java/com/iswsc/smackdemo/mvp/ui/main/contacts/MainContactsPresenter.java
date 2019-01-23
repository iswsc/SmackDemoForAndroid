package com.iswsc.smackdemo.mvp.ui.main.contacts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.iswsc.smackdemo.app.MyApp;
import com.iswsc.smackdemo.service.XmppService;
import com.iswsc.smackdemo.util.JacenUtils;
import com.iswsc.smackdemo.xmpp.XmppAction;
import com.iswsc.smackdemo.vo.ContactVo;

import java.util.ArrayList;

/**
 * @version 1.0
 * @email jacen@iswsc.com
 * Created by Jacen on 2018/1/8 0:37.
 */

public class MainContactsPresenter implements IContactsContract.Presenter {

    private IContactsContract.View mContactsView;

    private MyContactBroadcastReveiver myContactBroadcastReveiver;

    public MainContactsPresenter(IContactsContract.View mContactsView) {
        this.mContactsView = mContactsView;
    }

    @Override
    public void init() {
        myContactBroadcastReveiver = new MyContactBroadcastReveiver(mContactsView);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(XmppAction.ACTION_USER_CONTACT);
        JacenUtils.registerLocalBroadcastReceiver(MyApp.mContext, myContactBroadcastReveiver, intentFilter);
        JacenUtils.intentService(MyApp.mContext, XmppService.class, XmppAction.ACTION_USER_CONTACT, null);

    }

    @Override
    public void destroy() {
        JacenUtils.unRegisterLocalBroadcastReceiver(MyApp.mContext, myContactBroadcastReveiver);
        mContactsView = null;
    }

    @Override
    public void toChattingUI(ContactVo vo) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("vo", vo);
        bundle.putString("chatJid", vo.getJid());
        bundle.putString("nickName", vo.getNickName());
        mContactsView.toChattingUI(bundle);
    }


    class MyContactBroadcastReveiver extends BroadcastReceiver {

        private IContactsContract.View mContactsView;

        public MyContactBroadcastReveiver(IContactsContract.View mContactsView) {
            this.mContactsView = mContactsView;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            mContactsView.toUpdateList((ArrayList<ContactVo>) intent.getSerializableExtra("contactVoList"));
            ;
        }
    }
}

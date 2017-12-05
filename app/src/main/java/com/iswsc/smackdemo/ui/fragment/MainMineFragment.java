package com.iswsc.smackdemo.ui.fragment;

import android.view.View;

import com.iswsc.smackdemo.R;
import com.iswsc.smackdemo.ui.base.BaseFragment;
import com.iswsc.smackdemo.util.XmppUtils;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;

import java.io.IOException;

/**
 * Created by Jacen on 2017/8/22 13:39.
 * jacen@iswsc.com
 */

public class MainMineFragment extends BaseFragment {

    @Override
    protected void setContentView() {
        setContentView(R.layout.include_recyclerview);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        try {
            setTitle(getString(R.string.mine) + XmppUtils.getInstance().getConnection().getUser());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        }
        setBackViewGone();

        try {
            VCardManager vCardManager = VCardManager.getInstanceFor(XmppUtils.getInstance().getConnection());
            VCard vCard = vCardManager.loadVCard();
            vCard.getFirstName();
            showLogI(vCard.getLastName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
    }
}

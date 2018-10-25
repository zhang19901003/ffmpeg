package com.adasplus.proadas.business.main.model.fragment.about;


import com.adasplus.proadas.App;
import com.adasplus.proadas.common.Constant;
import com.adasplus.proadas.common.util.Utils;
import com.adasplus.proadas.socket.tcp.TcpClient;
import com.adasplus.proadas.common.entry.DevicesInfo;
import com.adasplus.proadas.common.model.BaseModel;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public class AboutModel implements IAboutModel{

    @Override
    public void loadResourse(final  BaseModel.Result result) {
        String version = Utils.getVersion(App.getContext());
        version = App.getContext().getString(com.adasplus.proadas.common.R.string.app_name)+" "+version;
        result.success(version);
    }

    @Override
    public void checkout(Result result) {

    }

    @Override
    public void introduction(Result result) {

    }
}

package com.adasplus.proadas.business.main.model.fragment.sim;


import com.adasplus.proadas.App;
import com.adasplus.proadas.common.Constant;
import com.adasplus.proadas.socket.tcp.TcpClient;
import com.adasplus.proadas.common.entry.SimInfo;
import com.adasplus.proadas.common.model.BaseModel;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhangyapeng on 18-4-10.
 */

public class SimModel implements BaseModel {

    @Override
    public void loadResourse(final Result result) {

//        try {
//            TcpClient.getInstance(new Result<byte[]>() {
//                @Override
//                public void success(byte[] resultCallBack) {
//                    SimInfo simInfo = SimInfo.toObject(new String(resultCallBack));
//                    if(simInfo==null){
//                        result.failure(App.getContext().getResources().getString(com.adasplus.proadas.common.R.string.quest_failue));
//                    }else {
//                        result.success(simInfo);
//                    }
//                }
//                @Override
//                public void failure(String s) {
//                    result.failure(s);
//                }
//            }).sendSocketMsg(Constant.MSG_REQUEST_SIM_INFO.getBytes("UTF-8"));
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            result.failure(App.getContext().getResources().getString(com.adasplus.proadas.common.R.string.quest_failue));
//        }
    }
}

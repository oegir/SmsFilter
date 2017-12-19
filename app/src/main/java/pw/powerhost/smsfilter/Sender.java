package pw.powerhost.smsfilter;

import android.content.Context;

/**
 * Created by oegir on 17.12.17.
 */

public class Sender {
    private String  mAdderss;
    private Context mContext;

    Sender(String adderss, Context context) {
        mAdderss = adderss;
        mContext = context;
    }

    public String getmAdderss() {
        return mAdderss;
    }

    public Context getmContext() {
        return mContext;
    }
}

package pw.powerhost.smsfilter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by oegir on 17.12.17.
 */

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Object[] pdus = (Object[]) bundle.get("pdus");

        if (pdus.length == 0) {
            return;
        }
        Sms sms = Sms.fromPdus(pdus, context);
        SmsDatabase db = null;

        try {
//            db = SmsDatabase.open(context);
        } finally {
            if (db != null) {
//                db.close();
            }
        }
    }
}

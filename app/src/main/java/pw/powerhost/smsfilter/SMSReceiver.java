package pw.powerhost.smsfilter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by oegir on 17.12.17.
 */

public class SMSReceiver extends BroadcastReceiver {
    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Object[] pdus = (Object[]) bundle.get("pdus");

        if (pdus.length == 0) {
            return;
        }
        Sms sms = Sms.fromPdus(pdus, context);
        SmsDatabase sms_db = new SmsDatabase(context);

        if (sms_db.isSpam(sms)) {
            abortBroadcast();
            sms_db.storeMessage(sms);
        }
    }
}

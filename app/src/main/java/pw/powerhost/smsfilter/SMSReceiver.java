package pw.powerhost.smsfilter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

/**
 * Created by oegir on 17.12.17.
 */

public class SMSReceiver extends BroadcastReceiver {
    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        Resources resources = context.getResources();

        if (!Settings.getInstance(context).getBoolean(resources.getResourceEntryName(R.id.action_active_filter), false)) {
            return;
        }

        Bundle bundle = intent.getExtras();

        try {
            Object[] pdus = (Object[]) bundle.get("pdus");

            if (pdus.length == 0) {
                return;
            }
            Sms sms = Sms.fromPdus(pdus, context);

            if (sms.isSpam()) {
                abortBroadcast();

                if (Settings.getInstance(context).getBoolean(resources.getResourceEntryName(R.id.action_store_sms), false)) {
                    sms.store();
                }
            }
        } catch (Exception e) {
            return;
        }
    }
}

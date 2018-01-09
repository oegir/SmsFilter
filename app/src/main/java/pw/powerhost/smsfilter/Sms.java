package pw.powerhost.smsfilter;

import android.content.Context;
import android.telephony.SmsMessage;

/**
 * Created by Alexey on 17.12.17.
 */

public class Sms {
    private String mBody = "";

    private  Sender mSender;
    private long mTimestamp;

    /**
     * Parse sms message
     *
     * @param pdus
     * @param context
     * @return parsed sms
     */
    public static Sms fromPdus(Object[] pdus, Context context) {
        Sms result = new Sms();

        for (int i = 0; i < pdus.length; i++) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            result.mBody += smsMessage.getMessageBody();
        }
        SmsMessage first = SmsMessage.createFromPdu((byte[]) pdus[0]);
        result.mSender = new Sender(first.getDisplayOriginatingAddress(), context);
        result.mTimestamp = first.getTimestampMillis();

        return result;
    }

    /**
     * Check if message is spam
     *
     * @return true if spam
     */
    public boolean isSpam() {
        return mSender.isBlocked();
    }
}

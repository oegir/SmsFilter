package pw.powerhost.smsfilter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.SmsMessage;

import pw.powerhost.smsfilter.data.SmsContract.SmsEntry;
import pw.powerhost.smsfilter.data.SmsDbHelper;

/**
 * Created by Alexey on 17.12.17.
 *
 */

class Sms {
    private String mBody = "";
    private  Sender mSender;
    private long mTimestamp;
    private long mId = -1;
    private SQLiteOpenHelper mDbHelper;

    /**
     * Constructor
     *
     * @param context application context
     */
    Sms(Context context) {
        mDbHelper = new SmsDbHelper(context);
    }

    /**
     * Parse sms message
     *
     * @param pdus sms package
     * @param context application context
     * @return parsed sms
     */
    static Sms fromPdus(Object[] pdus, Context context) {
        Sms result = new Sms(context);

        for (int i = 0; i < pdus.length; i++) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            result.mBody += smsMessage.getMessageBody();
        }
        SmsMessage first = SmsMessage.createFromPdu((byte[]) pdus[0]);
        result.mSender = new Sender(context);
        result.mSender.findOne(first.getDisplayOriginatingAddress());
        result.mTimestamp = first.getTimestampMillis();

        return result;
    }

    /**
     * Check if message is spam
     *
     * @return true if spam
     */
    boolean isSpam() {
        return mSender.isBlocked();
    }

    /**
     * Store sms in data base
     */
    void store() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SmsEntry.COLUMN_DATE, Long.toString(mTimestamp));
        values.put(SmsEntry.COLUMN_MESSAGE, mBody);
        values.put(SmsEntry.COLUMN_SENDER_ID, mSender.getId());

        if (mId < 0) {
            // TODO: Сохранение sms
        } else {
            // TODO: Обновление sms
        }
    }
}

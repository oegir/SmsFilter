package pw.powerhost.smsfilter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.SmsMessage;

import pw.powerhost.smsfilter.data.SmsContract.SmsEntry;
import pw.powerhost.smsfilter.data.SmsDbHelper;

/**
 * Created by Alexey on 17.12.17.
 *
 */

class Sms {
    private static SmsDbHelper mDbHelper;
    private Context mContext;
    private String mBody = "";
    private  Sender mSender;
    private long mTimestamp;

    /**
     * Constructor
     *
     * @param context application context
     */
    Sms(Context context) {
        mContext = context;
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
     * get db-cursor for retrieve data about blocked sms
     *
     * @return
     */
    static Cursor getSendersCursor(Context context) {
        SQLiteDatabase db = getDbHelper(context).getReadableDatabase();
        String[] columns = {SmsEntry._ID, SmsEntry.COLUMN_SENDER_ID, SmsEntry.COLUMN_DATE, SmsEntry.COLUMN_MESSAGE};

        return db.query(SmsEntry.TABLE_NAME, columns, null, null, null, null, null);
    }

    /**
     * Getter for mDbHelper
     *
     * @param context application context
     * @return helper for db queries
     */
    static SmsDbHelper getDbHelper(Context context) {

        if (mDbHelper == null) {
            mDbHelper = new SmsDbHelper(context);
        }
        return mDbHelper;
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
        SQLiteDatabase db = getDbHelper(mContext).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SmsEntry.COLUMN_DATE, Long.toString(mTimestamp));
        values.put(SmsEntry.COLUMN_MESSAGE, mBody);
        values.put(SmsEntry.COLUMN_SENDER_ID, mSender.getId());
        db.insert(SmsEntry.TABLE_NAME, null, values);
    }
}

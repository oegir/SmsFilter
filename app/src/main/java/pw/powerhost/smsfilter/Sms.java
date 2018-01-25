package pw.powerhost.smsfilter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.SmsMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

import pw.powerhost.smsfilter.data.SmsContract.SendersEntry;
import pw.powerhost.smsfilter.data.SmsContract.SmsEntry;
import pw.powerhost.smsfilter.data.SmsDbHelper;

/**
 * Created by Alexey on 17.12.17.
 *
 */

class Sms {
    public static final String FIELD_DATE = "nameDate";

    private static SmsDbHelper mDbHelper;
    private Context mContext;
    private long mId;
    private String mBody = "";
    private  Sender mSender;
    private String mDate;

    String getBody() {
        return mBody;
    }

    Sender getSender() {
        return mSender;
    }

    String getDate() {
        return mDate;
    }

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

        Date date = new Date(first.getTimestampMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM HH:mm");
        result.mDate = dateFormat.format(date);

        return result;
    }

    /**
     * get db-cursor for retrieve data about blocked sms
     *
     * @return
     */
    static Cursor getSmsCursor(Context context) {
        SQLiteDatabase db = getDbHelper(context).getReadableDatabase();
        String query = "SELECT " +
                SmsEntry.TABLE_NAME + "." + SmsEntry._ID + "," +
                SmsEntry.COLUMN_DATE + " || \" \" || " + SendersEntry.COLUMN_NAME + " AS " + FIELD_DATE + "," +
                SmsEntry.COLUMN_MESSAGE +
            " FROM " +
                SmsEntry.TABLE_NAME + " LEFT JOIN " + SendersEntry.TABLE_NAME + " ON " + SmsEntry.TABLE_NAME + "." + SmsEntry.COLUMN_SENDER_ID + " = " + SendersEntry.TABLE_NAME + "." + SendersEntry._ID;

        return db.rawQuery(query, null);
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
     * Delete a message
     */
    void delete() {
        SQLiteDatabase db = getDbHelper(mContext).getWritableDatabase();
        String[] whereArgs = {String.valueOf(mId)};
        db.delete(SmsEntry.TABLE_NAME, SmsEntry._ID + " = ?", whereArgs);
    }

    /**
     * Find Sms by id
     * @param id
     */
    void findOne(long id) {
        String[] projection = {SmsEntry.COLUMN_SENDER_ID, SmsEntry.COLUMN_DATE, SmsEntry.COLUMN_MESSAGE};
        String selection =  SmsEntry._ID + " = ?";
        String [] selectionArgs = {String.valueOf(id)};

        SQLiteDatabase db = getDbHelper(mContext).getReadableDatabase();
        Cursor cursor = db.query(SmsEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        try {
            cursor.moveToFirst();

            mId = id;
            mDate = cursor.getString(cursor.getColumnIndex(SmsEntry.COLUMN_DATE));
            mSender = new Sender(mContext);
            mSender.findOne(cursor.getLong(cursor.getColumnIndex(SmsEntry.COLUMN_SENDER_ID)));
            mBody = cursor.getString(cursor.getColumnIndex(SmsEntry.COLUMN_MESSAGE));
        } finally {
            cursor.close();
        }
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
        values.put(SmsEntry.COLUMN_DATE, mDate);
        values.put(SmsEntry.COLUMN_MESSAGE, mBody);
        values.put(SmsEntry.COLUMN_SENDER_ID, mSender.getId());
        db.insert(SmsEntry.TABLE_NAME, null, values);
    }
}

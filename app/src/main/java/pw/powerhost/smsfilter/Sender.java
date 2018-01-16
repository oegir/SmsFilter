package pw.powerhost.smsfilter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import pw.powerhost.smsfilter.data.SmsContract.SendersEntry;
import pw.powerhost.smsfilter.data.SmsDbHelper;

/**
 * Created by Alexey on 17.12.17.
 *
 */

public class Sender {
    private static SmsDbHelper mDbHelper;
    private Context mContext;
    private long mId = -1;
    private String mName;
    private String mIdentity;

    /**
     * 2-params constructor
     * @param context application context
     */
    Sender(Context context) {
        mContext = context;
    }

    /**
     * 3-params constructor
     *
     * @param name human readable string
     * @param identity phone number or etc.
     * @param context application context
     */
    Sender(String name, String identity, Context context) {
        mIdentity = identity;
        mName = name;
        mContext = context;
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
     * get db-cursor for retrieve data about blocked senders
     *
     * @return
     */
    static Cursor getSendersCursor(Context context) {
        SQLiteDatabase db = getDbHelper(context).getReadableDatabase();
        String[] columns = {SendersEntry._ID, SendersEntry.COLUMN_NAME, SendersEntry.COLUMN_IDENTITY};

        return db.query(SendersEntry.TABLE_NAME, columns, null, null, null, null, null);
    }

    /**
     * Getter for id
     * @return sender id in data base
     */
    long getId() {
        return mId;
    }

    /**
     * Check if sender store in data base for blocking
     *
     * @return blocking sign
     */
    boolean isBlocked() {
        SQLiteDatabase db = getDbHelper(mContext).getReadableDatabase();

        String[] columns = {SendersEntry._ID};
        String selection = SendersEntry.COLUMN_IDENTITY + " = ?";
        String[] selectionArgs = {mIdentity};

        Cursor cursor = db.query(SendersEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        boolean result = cursor.getCount() > 0;
        cursor.close();

        return result;
    }

    /**
     * Find sender by phone number
     *
     * @param identity search parameter
     */
    void findOne(String identity) {
        SQLiteDatabase db = getDbHelper(mContext).getReadableDatabase();
        String[] projection = {SendersEntry._ID, SendersEntry.COLUMN_NAME};
        String selection = SendersEntry.COLUMN_IDENTITY + " = ?";
        String[] selectionArgs = {identity};
        Cursor cursor = db.query(SendersEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        if ((cursor != null) && cursor.moveToFirst()) {
            mId = cursor.getLong(cursor.getColumnIndex(SendersEntry._ID));
            mName = cursor.getString(cursor.getColumnIndex(SendersEntry.COLUMN_NAME));
            mIdentity = identity;
        }
    }

    /**
     * Store user in data base
     */
    void store() {
        SQLiteDatabase db = getDbHelper(mContext).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SendersEntry.COLUMN_IDENTITY, mIdentity);
        values.put(SendersEntry.COLUMN_NAME, mName);

        if (mId < 0) {
            mId = db.insert(SendersEntry.TABLE_NAME, null, values);
        } else {
            db.update(SendersEntry.TABLE_NAME, values, SendersEntry._ID + " = ?", new String[]{Long.toString(mId)});
        }
    }
}

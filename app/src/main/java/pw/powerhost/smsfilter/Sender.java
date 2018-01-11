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
    private long mId = -1;
    private String mName;
    private String mIdentity;
    private SmsDbHelper mDbHelper;

    /**
     * 2-params constructor
     * @param context application context
     */
    Sender(Context context) {
        mDbHelper = new SmsDbHelper(context);
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
        mDbHelper = new SmsDbHelper(context);
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
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

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
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // TODO: Доделать загрузку полей класса из базы
    }

    /**
     * Store user in data base
     */
    void store() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
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

package pw.powerhost.smsfilter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import pw.powerhost.smsfilter.data.SmsContract.SendersEntry;
import pw.powerhost.smsfilter.data.SmsDbHelper;

/**
 * Created by Alexey on 17.12.17.
 */

public class Sender {
    private long mId;
    private String mName;
    private String mIdentity;
    private SmsDbHelper mDbHelper;

    /**
     * Constructor
     *
     * @param identity
     * @param context
     */
    Sender(String identity, Context context) {
        mIdentity = identity;
        mDbHelper = new SmsDbHelper(context);
    }

    /**
     * Constructor
     *
     * @param name
     * @param identity
     * @param context
     */
    Sender(String name, String identity, Context context) {
        mIdentity = identity;
        mName = name;
        mDbHelper = new SmsDbHelper(context);
    }

    /**
     * Get address identity
     * @return
     */
    public String getAdderss() {
        return mIdentity;
    }

    /**
     * Check if sender store in data base for blocking
     *
     * @return
     */
    public boolean isBlocked() {
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
     * Store user in data base
     */
    public void store() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SendersEntry.COLUMN_IDENTITY, mIdentity);
        values.put(SendersEntry.COLUMN_NAME, mName);
        mId = db.insert(SendersEntry.TABLE_NAME, null, values);
    }
}

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

    long getId() {
        return mId;
    }

    String getIdentity() {
        return mIdentity;
    }

    String getName() {
        return mName;
    }

    public void setIdentity(String identity) {
        this.mIdentity = identity;
    }

    public void setName(String name) {
        this.mName = name;
    }

    /**
     * 2-params constructor
     * @param context application context
     */
    Sender(Context context) {
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
     * Delete sender from database
     */
    void delete() {
        SQLiteDatabase db = getDbHelper(mContext).getWritableDatabase();
        String[] whereArgs = {String.valueOf(mId)};
        db.delete(SendersEntry.TABLE_NAME, SendersEntry._ID + " = ?", whereArgs);
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

        mIdentity = identity;
        if ((cursor != null) && cursor.moveToFirst()) {
            mId = cursor.getLong(cursor.getColumnIndex(SendersEntry._ID));
            mName = cursor.getString(cursor.getColumnIndex(SendersEntry.COLUMN_NAME));
        } else {
            mId = -1;
            mName = mIdentity;
        }
    }

    /**
     * Find sender by record id
     *
     * @param id search parameter
     */
    void findOne(long id) {
        SQLiteDatabase db = getDbHelper(mContext).getReadableDatabase();
        String[] projection = {SendersEntry.COLUMN_IDENTITY, SendersEntry.COLUMN_NAME};
        String selection = SendersEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(SendersEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        try {
            cursor.moveToFirst();
            mId = id;
            mIdentity = cursor.getString(cursor.getColumnIndex(SendersEntry.COLUMN_IDENTITY));
            mName = cursor.getString(cursor.getColumnIndex(SendersEntry.COLUMN_NAME));
        } catch (Exception e){
            mId = -1;
        } finally {
            cursor.close();
        }
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

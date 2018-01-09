package pw.powerhost.smsfilter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import pw.powerhost.smsfilter.data.SmsContract.SendersEntry;
import pw.powerhost.smsfilter.data.SmsDbHelper;

/**
 * Created by Alexey on 19.12.2017.
 */

public class SmsDatabase {
    private SmsDbHelper mDbHelper;

    /**
     * Constructor
     *
     * @param context
     */
    public SmsDatabase(Context context) {
        mDbHelper = new SmsDbHelper(context);
    }

    /**
     * get db-cursor for retrieve data about blocked senders
     * @return
     */
    public Cursor getSendersCursor() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] columns = {SendersEntry._ID, SendersEntry.COLUMN_NAME, SendersEntry.COLUMN_IDENTITY};

        return db.query(SendersEntry.TABLE_NAME, columns, null, null, null, null, null);
    }

    /**
     * Store information to data base about blocked sms
     *
     * @param sms
     */
    public void save(Sms sms) {

    }

    /**
     * Save message in database
     *
     * @param sms
     */
    public void storeMessage(Sms sms) {
//        if (!Preferences.get(mContext).storeSms()) return;
//        int maxCount = Preferences.get()
    }
}

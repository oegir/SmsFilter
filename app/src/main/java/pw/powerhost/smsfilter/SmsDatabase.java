package pw.powerhost.smsfilter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import pw.powerhost.smsfilter.data.SmsContract;
import pw.powerhost.smsfilter.data.SmsContract.SendersEntry;
import pw.powerhost.smsfilter.data.SmsDbHelper;

/**
 * Created by Alexey on 19.12.2017.
 */

public class SmsDatabase {
    private SmsDbHelper mDbHelper;

    public SmsDatabase(Context context) {
        mDbHelper = new SmsDbHelper(context);
    }

    /**
     * Check if message is spam
     *
     * @param sms message
     * @return true if spam
     */
    public boolean isSpam(Sms sms) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] columns = {SmsContract.SendersEntry._ID};
        String selection = SendersEntry.COLUMN_IDENTITY + " = ?";
        String[] selectionArgs = {sms.getSender().getAdderss()};

        Cursor cursor = db.query(SendersEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        boolean result = cursor.getCount() > 0;
        cursor.close();

        return result;
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

    public void save(Sms sms) {

    }
}

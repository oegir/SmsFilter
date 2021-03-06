package pw.powerhost.smsfilter.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import pw.powerhost.smsfilter.data.SmsContract.SendersEntry;
import pw.powerhost.smsfilter.data.SmsContract.SmsEntry;

/**
 * Created by Alexey on 05.01.2018.
 */

public class SmsDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smsfilter.db";
    private static final int DATABASE_VERSION = 1;

    public SmsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_SMS_TABLE = "CREATE TABLE " + SmsEntry.TABLE_NAME + " (" +
                SmsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SmsEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                SmsEntry.COLUMN_SENDER_ID + " INTEGER NOT NULL, " +
                SmsEntry.COLUMN_MESSAGE + " TEXT," +
                "FOREIGN KEY(`" + SmsEntry.COLUMN_SENDER_ID + "`) REFERENCES " + SendersEntry.TABLE_NAME + " ( `" + SendersEntry._ID + "` ) ON DELETE CASCADE" +
                ")";
        db.execSQL(SQL_CREATE_SMS_TABLE);

        String SQL_CREATE_SENDERS_TABLE = "CREATE TABLE " + SendersEntry.TABLE_NAME + " (" +
                SendersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SendersEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                SendersEntry.COLUMN_IDENTITY + " TEXT NOT NULL" +
                ")";
        db.execSQL(SQL_CREATE_SENDERS_TABLE);
    }

    @SuppressLint("NewApi")
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_key = ON");
        }
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}

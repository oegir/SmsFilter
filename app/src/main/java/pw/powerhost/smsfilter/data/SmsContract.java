package pw.powerhost.smsfilter.data;

import android.provider.BaseColumns;

/**
 * Created by Alexey on 05.01.2018.
 */

public final class SmsContract {
    public SmsContract() {
    }

    public static final class SmsEntry implements BaseColumns {
        public static final String TABLE_NAME = "sms";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_SENDER_ID = "sender_id";
        public static final String COLUMN_MESSAGE = "message";
    }

    public static final class SendersEntry implements BaseColumns {
        public static final String TABLE_NAME = "senders";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_IDENTITY = "identity";
    }
}

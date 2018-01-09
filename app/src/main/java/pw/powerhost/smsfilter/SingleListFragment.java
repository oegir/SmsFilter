package pw.powerhost.smsfilter;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

import pw.powerhost.smsfilter.data.SmsContract.SendersEntry;

/**
 * Created by Alexey on 09.01.2018.
 */

public class SingleListFragment extends ListFragment {
    Cursor mCursor;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] from = new String[]{SendersEntry.COLUMN_NAME, SendersEntry.COLUMN_IDENTITY};
        int[] to = new int[]{android.R.id.text1, android.R.id.text2};
        Activity activity = getActivity();
        mCursor = (new SmsDatabase(activity)).getSendersCursor();

        ListAdapter adapter = new SimpleCursorAdapter(activity, android.R.layout.simple_expandable_list_item_2, mCursor, from, to, 0);
        setListAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO: Сделать обновление ListView
        int a = 0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCursor.close();
    }
}

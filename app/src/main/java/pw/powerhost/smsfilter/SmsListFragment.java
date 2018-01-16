package pw.powerhost.smsfilter;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.SimpleCursorAdapter;

import pw.powerhost.smsfilter.data.SmsContract.SmsEntry;

/**
 * Created by Alexey on 09.01.2018.
 */

public class SmsListFragment extends ListFragment {
    Cursor mCursor;
    SimpleCursorAdapter mAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] from = new String[]{SmsEntry.COLUMN_DATE, SmsEntry.COLUMN_MESSAGE};
        int[] to = new int[]{android.R.id.text1, android.R.id.text2};

        mCursor = Sms.getSendersCursor(getContext());
        mAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_expandable_list_item_2, mCursor, from, to, 0);
        setListAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh senders list
        Cursor newCursor = Sender.getSendersCursor(getContext());
        mAdapter.changeCursor(newCursor);
        mCursor.close();
        mCursor = newCursor;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCursor.close();
    }
}

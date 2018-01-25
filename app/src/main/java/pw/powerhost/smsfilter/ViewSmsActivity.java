package pw.powerhost.smsfilter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class ViewSmsActivity extends AppCompatActivity {
    private Sms mSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sms);
        // Get Sms data
        Intent intent = getIntent();
        long id = intent.getLongExtra("id", -1);
        // Get sms data
        if (id < 0) {
            finish();;
            return;
        }
        mSms = new Sms(ViewSmsActivity.this);
        try {
            mSms.findOne(id);
        } catch (Exception e) {
            finish();;
            return;
        }
        ((TextView)findViewById(R.id.date_textView)).setText(mSms.getDate());
        ((TextView)findViewById(R.id.from_textView)).setText(mSms.getSender().getName());
        ((TextView)findViewById(R.id.body_textView)).setText(mSms.getBody());
    }

    /**
     * Handler for clicking on "Delete" button
     *
     * @param view
     */
    public void deleteButtonOnClick(View view) {
        mSms.delete();
        finish();
    }

    /**
     * Handler for clicking on "Close" button
     *
     * @param view
     */
    public void closeButtonOnClick(View view) {
        finish();
    }
}

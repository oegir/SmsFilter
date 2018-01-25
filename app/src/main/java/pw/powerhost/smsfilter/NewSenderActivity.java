package pw.powerhost.smsfilter;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Iterator;
import java.util.List;

public class NewSenderActivity extends AppCompatActivity {
    private Sender mSender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sender);
        // Get Sender data
        Intent intent = getIntent();
        mSender = new Sender(NewSenderActivity.this);

        long id = intent.getLongExtra("id", -1);
        if (id < 0) {
            return;
        }
        mSender.findOne(id);
        ((EditText)findViewById(R.id.editText_name)).setText(mSender.getName());
        ((EditText)findViewById(R.id.editText_identity)).setText(mSender.getIdentity());
        // Change buttons labels
        ((Button)findViewById(R.id.add_button)).setText(R.string.button_save);
        ((Button)findViewById(R.id.cancel_button)).setText(R.string.button_delete);
    }

    /**
     * Handler for clicking on "Add/Save" button
     *
     * @param view
     */
    public void addButtonOnClick(View view) {
        // Insert new sender in data base
        String name = ((EditText) this.findViewById(R.id.editText_name)).getText().toString();
        mSender.setName(name);

        String identity = ((EditText) this.findViewById(R.id.editText_identity)).getText().toString();
        mSender.setIdentity(identity );

        mSender.store();
        finish();
    }

    /**
     * Handler for clicking on "Cancel/Delete" button
     * @param view
     */
    public void cancelButtonOnClick(View view) {

        if (mSender.getId() > 0) {
            mSender.delete();
        }
        finish();
    }
}

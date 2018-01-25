package pw.powerhost.smsfilter;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.Iterator;
import java.util.List;

public class NewSenderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sender);
    }

    /**
     * Handler for clicking on "Add" button
     *
     * @param view
     */
    public void addButtonOnClick(View view) {
        // Insert new sender in data base
        String name = ((EditText) this.findViewById(R.id.editText_name)).getText().toString();
        String identity = ((EditText) this.findViewById(R.id.editText_identity)).getText().toString();
        Sender sender = new Sender(name, identity, this);
        sender.store();
        finish();
    }
}

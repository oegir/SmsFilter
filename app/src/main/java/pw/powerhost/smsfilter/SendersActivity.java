package pw.powerhost.smsfilter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class SendersActivity extends AppCompatActivity {

    /* TODO: Вынести получение настроек в общий класс*/
    /**
     * Settings file name
     */
    static final String APP_PREFERENCES = "appsettings";
    private SharedPreferences mSettings;

    /* TODO: Вынести создание и обработку мению в отдельный класс */

    /**
     * Get string presentation of menu item identifier
     *
     * @param item
     * @return android:id attribute value
     */
    private String getMenuItemId(MenuItem item) {
        return getResources().getResourceName(item.getItemId()).split("\\/")[1];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senders);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // Load application preferences
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    /* TODO: Вынести создание и обработку мению в отдельный класс */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Restore menu items state from settings
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            // Check only checkable items
            if (item.isCheckable()) {
                Boolean isChecked = mSettings.getBoolean(getMenuItemId(item), false);
                item.setChecked(isChecked);
            }
        }
        return true;
    }

}

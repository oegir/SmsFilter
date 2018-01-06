package pw.powerhost.smsfilter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    /* TODO: Вынести получение настроек в общий класс*/
    /**
     * Settings file name
     */
    static final String APP_PREFERENCES = "appsettings";
    private SharedPreferences mSettings;

    /* TODO: Вынести создание и обработку мению в отдельный класс */
    /**
     * Get string presentation of menu item identifier
     * @param item
     * @return android:id attribute value
     */
    private String getMenuItemId(MenuItem item) {
        return getResources().getResourceName(item.getItemId()).split("\\/")[1];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Load application preferences
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    /* TODO: Вынести создание и обработку мению в отдельный класс */
    @Override
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

    /* TODO: Вынести создание и обработку мению в отдельный класс */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change state for checkable items
        if (item.isCheckable()) {
            // Change menu item state
            item.setChecked(!item.isChecked());
            // Save menu item new state
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putBoolean(getMenuItemId(item), item.isChecked());
            editor.apply();
            return true;
        }
        // Process item commands
        switch (item.getItemId()) {
            case R.id.action_blocked_view:
                startActivity(new Intent(MainActivity.this, SendersActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

package pw.powerhost.smsfilter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    /**
     * Settings file name
     */
    static final String APP_PREFERENCES = "appsettings";
    /**
     * Stored settings param names
     */
    static final String APP_PREFERENCES_ACTIVE_FILTER = "active_filter";
    static final String APP_PREFERENCES_STORE_SMS = "store_sms";

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}

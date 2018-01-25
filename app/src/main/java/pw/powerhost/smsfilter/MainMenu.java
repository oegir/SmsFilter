package pw.powerhost.smsfilter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Alexey on 08.01.2018.
 */

public class MainMenu {

    private Activity mActivity;

    /**
     * Constructor
     *
     * @param activity
     */
    public MainMenu(Activity activity) {
        mActivity = activity;
    }

    /**
     * Reverse checked item state
     *
     * @param item
     * @return
     */
    private boolean reverseChecked(MenuItem item) {
        Resources resources = mActivity.getResources();
        item.setChecked(!item.isChecked());
        // Save menu item new state
        Settings.getInstance(mActivity).putBoolean(resources.getResourceEntryName(item.getItemId()), item.isChecked());

        return true;
    }

    /**
     * Process menu item selection
     *
     * @param item
     */
    public boolean processItem(MenuItem item) {
        // Change state for checkable items
        if (item.isCheckable()) {
            return reverseChecked(item);
        }
        // Process item commands
        switch (item.getItemId()) {
            // Open Senders view
            case R.id.action_blocked_view:
                mActivity.startActivity(new Intent(mActivity, SendersActivity.class));
                return true;
            // Open Main view
            case R.id.action_main_view:
                mActivity.startActivity(new Intent(mActivity, MainActivity.class));
                return true;

            default:
                Activity parent = mActivity.getParent();
                if (parent != null) {
                    return parent.onOptionsItemSelected(item);
                }
                return false;
        }
    }

    /**
     * Make some items checked or invisible
     *
     * @param menu
     */
    public void setItems(Menu menu) {
        Resources resources = mActivity.getResources();

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            // Check only checkable items
            if (item.isCheckable()) {
                Boolean isChecked = Settings.getInstance(mActivity).getBoolean(resources.getResourceEntryName(item.getItemId()), false);
                item.setChecked(isChecked);
            }
        }
        // Set current menu item invisible
        switch (mActivity.getLocalClassName()) {
            case "MainActivity":
                menu.findItem(R.id.action_main_view).setVisible(false);
                break;

            case "SendersActivity":
                menu.findItem(R.id.action_blocked_view).setVisible(false);
                break;
        }
    }
}

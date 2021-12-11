package ca.calvinli.recipebook;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.app.ActionBar;
import com.google.firebase.auth.FirebaseAuth;

// This class is used by all activities that uses the same functions more than once.
public class App {

    public static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void hideActionBar(ActionBar actionBar)
    {
        if(actionBar != null)
        {
            actionBar.hide();
        }
    }

    public static void newUserInstance(FirebaseAuth fAuth)
    {
        // Check if there still is a logged in user.
        if (fAuth.getCurrentUser() != null)
        {
            // Log out their account
            fAuth.signOut();
        }
    }

}

package ca.calvinli.recipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainRecipeMenuActivity extends AppCompatActivity {

    // DEBUG TAG
    final String ACTIVITY_NAME = "MainRecipeMenuActivity";

    // Firebase Connectivity
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    // Layout Objects
    Button logOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recipe_menu);

        logOutButton = findViewById(R.id.log_out);

        hideActionBar();
        setOnClickLogOutButton();
    }

    private void setOnClickLogOutButton()
    {
        logOutButton.setOnClickListener(view -> {
            Log.i(ACTIVITY_NAME, "User " + fAuth.getUid() + " has logged out.");
            fAuth.signOut();
            finish();
        });
    }

    private void hideActionBar()
    {
        if(getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
    }
}
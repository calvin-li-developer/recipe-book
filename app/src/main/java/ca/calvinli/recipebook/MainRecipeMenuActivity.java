package ca.calvinli.recipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    Button addRecipeButton;
    Button viewRecipeButton;
    Button accountInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recipe_menu);

        logOutButton = findViewById(R.id.log_out);
        addRecipeButton = findViewById(R.id.add_recipe);
        viewRecipeButton = findViewById(R.id.view_recipe);
        accountInfoButton = findViewById(R.id.account_info);

        hideActionBar();
        setOnClickLogOutButton();
        setOnClickAddRecipeButton();
        setOnClickViewRecipeButton();
        setOnClickAccountInfoButton();
    }

    private void setOnClickLogOutButton()
    {
        logOutButton.setOnClickListener(view -> {
            Log.i(ACTIVITY_NAME, "User " + fAuth.getUid() + " has logged out.");
            fAuth.signOut();
            finish();
        });
    }

    private void setOnClickAddRecipeButton()
    {
        addRecipeButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AddRecipeActivity.class)));
    }

    private void setOnClickViewRecipeButton()
    {
        viewRecipeButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ViewRecipeActivity.class)));
    }

    private void setOnClickAccountInfoButton()
    {
        accountInfoButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), UserAccountActivity.class)));
    }

    private void hideActionBar()
    {
        if(getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
    }
}
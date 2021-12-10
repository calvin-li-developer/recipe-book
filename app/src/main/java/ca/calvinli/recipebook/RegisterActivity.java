package ca.calvinli.recipebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    // DEBUG TAG
    final String ACTIVITY_NAME = "RegisterActivity";

    // Firebase Connectivity
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    // Layout Objects
    EditText rFullName, rEmail, rPassword;
    Button rRegisterBtn;
    TextView rLoginBtn;
    ProgressBar progressBar;
    ConstraintLayout registerPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rFullName = findViewById(R.id.registerFullName);
        rEmail = findViewById(R.id.registerEmail);
        rPassword = findViewById(R.id.registerPwd);
        rRegisterBtn = findViewById(R.id.registerBtn);
        rLoginBtn = findViewById(R.id.registerLoginBtn);
        progressBar = findViewById(R.id.progressBarRegister);
        registerPage = findViewById(R.id.register_page);

        hideActionBar();
        endUserSession();

        setOnClickLayoutPage();
        setOnClickLoginButton();
        setOnClickRegisterBtn();
    }

    private void endUserSession()
    {
        // Check if there still is a logged in user.
        if (fAuth.getCurrentUser() != null)
        {
            // Log out their account
            fAuth.signOut();
        }
    }
    private void setOnClickLayoutPage()
    {
        registerPage.setOnClickListener(view -> hideKeyboard(this));
    }
    private void setOnClickLoginButton()
    {
        rLoginBtn.setOnClickListener(view -> finish());
    }

    private void setOnClickRegisterBtn()
    {
        rRegisterBtn.setOnClickListener(view -> {
            String fullName = rFullName.getText().toString().trim();
            String email = rEmail.getText().toString().trim();
            String password = rPassword.getText().toString();

            if(TextUtils.isEmpty(fullName))
            {
                rFullName.setError("Full Name is Required.");
                return;
            }

            if(TextUtils.isEmpty(email))
            {
                rEmail.setError("Email is Required.");
                return;
            }

            if(TextUtils.isEmpty(password))
            {
                rPassword.setError("Password is Required.");
                return;
            }

            if(password.length() < 6)
            {
                rPassword.setError("Password must be 6 or more characters.");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            // Register the user in Firebase Auth + Add user metadata to Realtime Database
            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful())
                {
                    String userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                    String userCreatedMessage = "User " + userID + " Created";
                    Toast.makeText(getApplicationContext(), userCreatedMessage,Toast.LENGTH_SHORT).show();

                    // User Class for user metadata

                    Users user = new Users(fullName, email);
                    DatabaseReference userIDDatabaseReference = database.getReference("users/" + userID);
                    userIDDatabaseReference.setValue(user);

                    startActivity(new Intent(getApplicationContext(), MainRecipeMenuActivity.class));
                }
                else{
                    String errorMessage = "Error: " + Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            });
            hideKeyboard(this);
        });

    }

    public void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void hideActionBar()
    {
        if(getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
    }
}
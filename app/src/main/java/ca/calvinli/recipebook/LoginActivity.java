package ca.calvinli.recipebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    // DEBUG TAG
    final String ACTIVITY_NAME = "LoginActivity";

    // Firebase Connectivity
    FirebaseAuth fAuth = FirebaseAuth.getInstance();

    // Layout Objects
    EditText lEmail, lPassword;
    Button lLoginBtn;
    TextView lRegisterBtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lEmail = findViewById(R.id.loginEmail);
        lPassword = findViewById(R.id.loginPwd);
        lRegisterBtn = findViewById(R.id.loginRegisterBtn);
        lLoginBtn = findViewById(R.id.loginBtn);
        progressBar = findViewById(R.id.progressBarLogin);

        //TODO remove this later
        lEmail.setText("lixx4090@mylaurier.ca");
        lPassword.setText("123456");

        hideActionBar();
        newUserInstance();

        setOnClickRegisterButton();
        setOnClickLoginButton();
    }

    private void setOnClickRegisterButton()
    {
        lRegisterBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.i(ACTIVITY_NAME, "onResume()");
        newUserInstance();
    }

    private void newUserInstance()
    {
        // Check if there still is a logged in user.
        if (fAuth.getCurrentUser() != null)
        {
            // Log out their account
            fAuth.signOut();
        }
    }

    private void setOnClickLoginButton()
    {
        lLoginBtn.setOnClickListener(view -> {
            String email = lEmail.getText().toString().trim();
            String password = lPassword.getText().toString().trim();

            if(TextUtils.isEmpty(email))
            {
                lEmail.setError("Email is Required.");
                return;
            }
            if(TextUtils.isEmpty(password))
            {
                lPassword.setError("Password is Required.");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            // Authenticate the user
            fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful())
                {
                    String loginMessage = "User " + fAuth.getUid() + " has logged in.";
                    Log.i(ACTIVITY_NAME, loginMessage);
                    Toast.makeText(getApplicationContext(), loginMessage,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainRecipeMenuActivity.class));
                }
                else{
                    String errorMessage = "Error: " + Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            });
            hideKeyboard(LoginActivity.this);
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
package ca.calvinli.recipebook;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
    ConstraintLayout loginPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lEmail = findViewById(R.id.loginEmail);
        lPassword = findViewById(R.id.loginPwd);
        lRegisterBtn = findViewById(R.id.loginRegisterBtn);
        lLoginBtn = findViewById(R.id.loginBtn);
        progressBar = findViewById(R.id.progressBarLogin);
        loginPage = findViewById(R.id.login_page);

        //TODO remove this later
        lEmail.setText("calvin.li@mylaurier.ca");
        lPassword.setText("123456");

        App.hideActionBar(getSupportActionBar());
        App.newUserInstance(fAuth);

        setOnClickLayoutPage();
        setOnClickRegisterButton();
        setOnClickLoginButton();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.i(ACTIVITY_NAME, "onResume()");
        App.newUserInstance(fAuth);
    }

    private void setOnClickLayoutPage()
    {
        loginPage.setOnClickListener(view -> App.hideKeyboard(this));
    }

    private void setOnClickRegisterButton()
    {
        lRegisterBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));
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
                    startActivity(new Intent(getApplicationContext(),MainRecipeMenuActivity.class));
                }
                else{
                    String errorMessage = "Error: " + Objects.requireNonNull(task.getException()).getMessage();
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            });
            App.hideKeyboard(this);
        });
    }
}
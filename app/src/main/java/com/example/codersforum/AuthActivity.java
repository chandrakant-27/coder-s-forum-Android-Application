package com.example.codersforum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

//public class AuthActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_authentication);
//
//    }
//}

        import android.content.Intent;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
import android.widget.Button;
import android.widget.EditText;
        import android.widget.ProgressBar;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;

//        import com.facebook.AccessToken;
//        import com.facebook.CallbackManager;
//        import com.facebook.FacebookCallback;
//        import com.facebook.FacebookException;
//        import com.facebook.login.LoginManager;
//        import com.facebook.login.LoginResult;
//        import com.google.android.gms.auth.api.Auth;
//        import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//        import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//        import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//        import com.google.android.gms.common.api.ApiException;
//        import com.google.android.material.button.MaterialButton;
//        import com.google.firebase.auth.AuthCredential;
//        import com.google.firebase.auth.AuthResult;
//        import com.google.firebase.auth.FacebookAuthProvider;
//        import com.google.firebase.auth.FirebaseAuth;
//        import com.google.firebase.auth.FirebaseUser;
//        import com.google.firebase.auth.GoogleAuthProvider;
//        import com.google.firebase.auth.OAuthProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularimageview.CircularImageView;
     import com.example.codersforum.fragments.ProgressFragement;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;
        import java.util.regex.Pattern;

public class AuthActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 101;

    private CircularImageView mSignInWithGoogle;
    private CircularImageView mSignInWithFacebook;
    private CircularImageView mSignInWithGithub;
    private MaterialButton mLoginButton;
    private EditText mEmail;
    private EditText mPassword;
    private Button mForgotPasswordButton;
    private Button mSignUpButton;
    private TextView mProgressTv;
    private ProgressBar mAuthProgress;
    private MaterialButton mLoginPhone;
    private RelativeLayout mContainer;
    //    private CallbackManager mCallbackManager;
//    private OAuthProvider.Builder mGitAuthProvider;
    private static final int RC_SIGN_UP = 701;
    private ProgressFragement mProgressFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        getWindow().setStatusBarColor(Color.BLACK);
        mAuth = FirebaseAuth.getInstance();

        initViews();

        mForgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AuthActivity.this,"Feature under development.",Toast.LENGTH_SHORT).show();
                return;
            }
        });
        mSignInWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AuthActivity.this,"Feature under development.",Toast.LENGTH_SHORT).show();
                return;
            }
        });
        mSignInWithFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AuthActivity.this,"Feature under development.",Toast.LENGTH_SHORT).show();
                return;
            }
        });
        mSignInWithGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AuthActivity.this,"Feature under development.",Toast.LENGTH_SHORT).show();
                return;
            }
        });
        mLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){

                String email = mEmail.getText().toString().trim();
                if(!isValidEmail(email) || email.length()<6)
                {
                    Toast.makeText(AuthActivity.this,"Please enter valid email or username",Toast.LENGTH_SHORT).show();
                    return;
                }
                String password = mPassword.getText().toString();
                if(password.length()<6)
                {
                    Toast.makeText(AuthActivity.this,"Password must be greater than 6 letters",Toast.LENGTH_SHORT).show();
                    return;
                }

                inflateProgressFragment();

                signInWithEmailPassword(email, password);
            }
        });
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AuthActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initViews() {
        mSignInWithGoogle = findViewById(R.id.signin_google);
        mSignInWithFacebook = findViewById(R.id.signin_facebook);
        mSignInWithGithub = findViewById(R.id.signin_github);
        mLoginButton = findViewById(R.id.login_button);
        mEmail = findViewById(R.id.username_email_et);
        mPassword = findViewById(R.id.password_et);
        mForgotPasswordButton = findViewById(R.id.forgot_password_button);
        mSignUpButton = findViewById(R.id.signup_button);
        mProgressTv = findViewById(R.id.progress_tv);
        mAuthProgress = findViewById(R.id.auth_progress);
        mLoginPhone = findViewById(R.id.login_with_phone);
        mContainer = findViewById(R.id.signup_contianer);
    }
    private void inflateProgressFragment()
    {
        mAuthProgress.setVisibility(View.VISIBLE);
        mProgressTv.setVisibility(View.VISIBLE);
        mContainer.setVisibility(View.VISIBLE);
        if(mProgressFragment == null)
        {
            mProgressFragment = new ProgressFragement();
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.signup_contianer,mProgressFragment)
                .commit();
    }
    public static boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    private void hideProgressFragment()
    {
        mAuthProgress.setVisibility(View.GONE);
        mProgressTv.setVisibility(View.GONE);
        mContainer.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction()
                .remove(mProgressFragment)
                .commit();
    }
//


 private void signInWithEmailPassword(String email, String password)
 {
      mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("USER_EP", "signInWithEmail:success: " + mAuth.getCurrentUser().isEmailVerified());
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(AuthActivity.this, "Login Successs ", Toast.LENGTH_SHORT).show();
                            hideProgressFragment();
                            Intent intent = new Intent(AuthActivity.this,HomeActivity.class);
                           startActivity(intent);
                              finishAfterTransition();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("USER_EP_FAIL", "signInWithEmail:failure", task.getException());
                            Toast.makeText(AuthActivity.this, "Authentication failed."+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();

                            hideProgressFragment();
                        }

                    }
                });
    }
//
}


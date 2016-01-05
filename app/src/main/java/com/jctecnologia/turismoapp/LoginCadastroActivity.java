package com.jctecnologia.turismoapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
        import com.facebook.FacebookCallback;
        import com.facebook.FacebookException;
        import com.facebook.FacebookSdk;
        import com.facebook.appevents.AppEventsLogger;
        import com.facebook.login.LoginManager;
        import com.facebook.login.LoginResult;
        import com.google.android.gms.auth.api.Auth;
        import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
        import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
        import com.google.android.gms.auth.api.signin.GoogleSignInResult;
        import com.google.android.gms.common.ConnectionResult;
        import com.google.android.gms.common.GoogleApiAvailability;
        import com.google.android.gms.common.api.GoogleApiClient;
        import com.google.android.gms.drive.Drive;
        import com.google.android.gms.plus.Plus;

public class LoginCadastroActivity  extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = "Login";
    private static final int RC_SIGN_IN = 0;
    Button btnCadastrarPessoa;
    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //INTEGRAÇÃO COM FACEBOOK
        FacebookSdk.sdkInitialize(getApplicationContext());

        // INTEGRAÇÃO COM GOOGLE
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        setContentView(R.layout.activity_login_cadastro);

        findViewById(R.id.sign_in_button).setOnClickListener(this);

        //Registar o usuário caso ainda não esteja cadastrado
        if(!false) {

            btnCadastrarPessoa = (Button) findViewById(R.id.email_sign_in_button);

            btnCadastrarPessoa.setOnClickListener(this);

        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        callbackManager = CallbackManager.Factory.create();

        if(mGoogleApiClient.isConnected()){
            Intent intent = new Intent();
            intent.setClass(LoginCadastroActivity.this,MainActivity.class);
            startActivity(intent);
        }


        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Intent intent = new Intent();
                        intent.setClass(LoginCadastroActivity.this,MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

        if(requestCode == 1001){
            Intent intent = new Intent();
            intent.setClass(LoginCadastroActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //FACEBOOK
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //FACEBOOK
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);

        //GOOGLE
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    //METODOS INTEGRACAO GOOGLE
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (mResolvingError) {
            // Already attempting to resolve an error.
            return;
        } else if (result.hasResolution()) {
            try {
                mResolvingError = true;
                result.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                // There was an error with the resolution intent. Try again.
                mGoogleApiClient.connect();
            }
        } else {
            // Show dialog using GoogleApiAvailability.getErrorDialog()
            showErrorDialog(result.getErrorCode());
            mResolvingError = true;
        }
    }

    // The rest of this code is all about building the error dialog

    /* Creates a dialog for an error message */
    private void showErrorDialog(int errorCode) {
        // Create a fragment for the error dialog
        ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
        // Pass the error that should be displayed
        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "errordialog");
    }

    /* Called from ErrorDialogFragment when the dialog is dismissed. */
    public void onDialogDismissed() {
        mResolvingError = false;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(LoginCadastroActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    //INTEGRACAO GOOLE
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.email_sign_in_button:
                //ENVIA PARA A ACTIVITY DE CADASTRO
                Intent intent = new Intent();
                intent.setClass(LoginCadastroActivity.this, CadastroPessoaActivity.class);
                startActivity(intent);
                break;
            // ...
        }
    }

    private void signIn() {
        //GOOGLE
        if (!mResolvingError) {  // more about this later
            mGoogleApiClient.connect();
        }
        //Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        //startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            //updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }

    /* A fragment to display an error dialog */
    public static class ErrorDialogFragment extends DialogFragment {
        public ErrorDialogFragment() { }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get the error code and retrieve the appropriate dialog
            int errorCode = this.getArguments().getInt(DIALOG_ERROR);
            return GoogleApiAvailability.getInstance().getErrorDialog(
                    this.getActivity(), errorCode, REQUEST_RESOLVE_ERROR);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            ((LoginCadastroActivity) getActivity()).onDialogDismissed();
        }
    }
}

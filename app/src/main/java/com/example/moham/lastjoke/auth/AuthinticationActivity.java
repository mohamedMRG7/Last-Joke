package com.example.moham.lastjoke.auth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moham.lastjoke.Database.DbUtilies;
import com.example.moham.lastjoke.Database.FirebaseDbUtilies;
import com.example.moham.lastjoke.MainActivity;
import com.example.moham.lastjoke.R;
import com.example.moham.lastjoke.user.UserJokes;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.ConnectionBuddyConfiguration;
import com.zplesac.connectionbuddy.interfaces.ConnectivityChangeListener;
import com.zplesac.connectionbuddy.models.ConnectivityEvent;
import com.zplesac.connectionbuddy.models.ConnectivityState;

import java.io.Serializable;
import java.util.Arrays;

public class AuthinticationActivity extends AppCompatActivity implements Serializable, ConnectivityChangeListener {

    private FirebaseAuth.AuthStateListener authStateListener;
    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth firebaseAuth;
    FirebaseDbUtilies firebaseDbUtilies;
    DbUtilies dbUtilies;
    FirebaseUser firebaseuser;
    public static final String AUTHKEY="authkey";
    private boolean isConnected=false;
    private boolean trytoOut=false;
    TextView connectiontxt;
    ImageView connectionIamge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authintication);
        firebaseAuth=FirebaseAuth.getInstance();

        sign_In();
        firebaseDbUtilies =new FirebaseDbUtilies(this);
        ConnectionBuddyConfiguration networkInspectorConfiguration = new ConnectionBuddyConfiguration.Builder(this).build();
        ConnectionBuddy.getInstance().init(networkInspectorConfiguration);
        connectiontxt=findViewById(R.id.tv_noconnection);
        connectionIamge=findViewById(R.id.img_connection_logo);
        Log.d("ONCreat","onCreate");
    }

    public void sign_In()
    {
        authStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();

                if(user !=null)
                {

                    UserJokes userJokes=new UserJokes(user.getDisplayName(),user.getEmail(),user.getUid());
                    firebaseDbUtilies.readUserinfo();
                    Intent intent=new Intent(AuthinticationActivity.this,MainActivity.class);
                    intent.putExtra("activity","authactivity");
                    intent.putExtra(AUTHKEY,userJokes);
                    startActivity(intent);
                    finish();
                }else
                {

                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setTheme(R.style.AppTheme)
                                    .setLogo(R.drawable.logo)
                                    .setAvailableProviders(
                                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }


    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);
        Log.d("ONCreat","onPuase");
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);


        Log.d("ONCreat","onResum");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

          Log.d("ONCreat","onActivityResult");
        if (requestCode==1)
            if (resultCode==RESULT_OK)
            {
                Log.e("inside","inside");
                firebaseuser=firebaseAuth.getCurrentUser();

                UserJokes userJokes=new UserJokes(firebaseuser.getDisplayName(),firebaseuser.getEmail(),firebaseuser.getUid());
                firebaseDbUtilies.addUserInfo(userJokes);
                firebaseDbUtilies.readUserinfo();
                Intent intent=new Intent(AuthinticationActivity.this,MainActivity.class);
                intent.putExtra(AUTHKEY,userJokes);
                intent.putExtra("activity","authactivity");
                startActivity(intent);
                finish();

                //i will send the userjoke with data but will make it seriazable first and modify constractor
            }else if (resultCode==RESULT_CANCELED)  if (isConnected==true)finish();




    }

    @Override
    protected void onStart() {
        super.onStart();
        ConnectionBuddy.getInstance().registerForConnectivityEvents(this, this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("ONCreat","onRestart");

    }


    @Override
    protected void onStop() {
        super.onStop();
        ConnectionBuddy.getInstance().unregisterFromConnectivityEvents(this);
        firebaseDbUtilies.removeuserlisner();

    }

    @Override
    public void onConnectionChange(ConnectivityEvent event) {
       if(event.getState().getValue()== ConnectivityState.CONNECTED)
       {
            connectiontxt.setVisibility(View.INVISIBLE);
            connectionIamge.setVisibility(View.INVISIBLE);
            isConnected=true;
       }else if (event.getState().getValue()==ConnectivityState.DISCONNECTED)
       {
           connectiontxt.setVisibility(View.VISIBLE);
           connectionIamge.setVisibility(View.VISIBLE);
           isConnected=false;
       }
    }
}

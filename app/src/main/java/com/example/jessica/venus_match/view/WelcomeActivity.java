package com.example.jessica.venus_match.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.example.jessica.venus_match.R;
import com.example.jessica.venus_match.sessions.SessionManager;


public class WelcomeActivity extends AppCompatActivity {

    //protected ArrayList<User> mockUsers = new ArrayList<>();
    //final static int REGISTER_RESULT = 10;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        session = new SessionManager(getApplicationContext());
        if(session.checkLoginStatusAtLogin())
        {
            finish();
        }

        TextView loginLink = (TextView) findViewById(R.id.loginLink);
        String loginString = String.valueOf(loginLink.getText());
        loginLink.setMovementMethod(LinkMovementMethod.getInstance());
        loginLink.setText(loginString,TextView.BufferType.SPANNABLE);

        Spannable spannable = (Spannable) loginLink.getText();

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                //intent.putExtra("users",mockUsers);
                startActivity(intent);
                finish();
            }
        };
        spannable.setSpan(clickableSpan,24,29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public void register(View view)
    {
        Intent intent = new Intent(WelcomeActivity.this,RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}

package com.error.grrravity.mynews.controllers.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.error.grrravity.mynews.R;
import com.error.grrravity.mynews.utils.Helper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.spinner_help)
    Spinner mSubject;
    @BindView(R.id.help_activity_message)
    EditText mMessage;
    @BindView(R.id.help_activity_submit_button)
    Button mSubmit;

    //DATA
    public static final String MAIL = "julienne.prof@gmail.com";
    public static final String MAIL_SUBJECT = "MyNews";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);

        this.configureToolbar();
        this.configureListener();
    }

    private void configureToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void configureListener() {
        mSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mSubmit) {
            String subject = mSubject.getSelectedItem().toString();
            String mailcontent = mMessage.getText().toString();
            int validation = Helper.validateHelpInfo(subject, mailcontent, this);
            switch (validation) {
                case 0:
                    mSubject.requestFocus();
                    break;
                case 1:
                    mMessage.setError("A message is required");
                    mMessage.requestFocus();
                    break;
                case 2:
                    Intent sendMail = new Intent(Intent.ACTION_SENDTO);

                    sendMail.setData(Uri.parse("mailto:"));
                    sendMail.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{MAIL});
                    sendMail.putExtra(android.content.Intent.EXTRA_SUBJECT,
                            MAIL_SUBJECT + " - " + subject);
                    sendMail.putExtra(android.content.Intent.EXTRA_TEXT, mailcontent);

                    startActivity(Intent.createChooser(sendMail, "Send mail"));
            }

        }
    }


}

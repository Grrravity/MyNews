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
import android.widget.Toast;

import com.error.grrravity.mynews.R;
import com.error.grrravity.mynews.utils.ErrorListener;
import com.error.grrravity.mynews.utils.HelpHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener, ErrorListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.spinner_help)
    Spinner mSubject;
    @BindView(R.id.help_activity_message)
    EditText mMessage;
    @BindView(R.id.help_activity_submit_button)
    Button mSubmit;

    //DATA
    public final String MAIL = "julienne.prof@gmail.com";
    public final String MAIL_SUBJECT = "MyNews";

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
            if (!HelpHelper.validateHelpInfo(subject, mailcontent, this)) {
                if (HelpHelper.getSubjectFocus()) {
                    mSubject.requestFocus();
                }
                if (HelpHelper.getMessageFocus()) {
                    mMessage.setError(getString(R.string.missing_message));
                    mMessage.requestFocus();
                }
            } else{
                    Intent sendMail = new Intent(Intent.ACTION_SENDTO);

                    sendMail.setData(Uri.parse(this.getResources().getString(R.string.mailto)));
                    sendMail.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{MAIL});
                    sendMail.putExtra(android.content.Intent.EXTRA_SUBJECT,
                            MAIL_SUBJECT +getString(R.string.mail_object_separator)+ subject);
                    sendMail.putExtra(android.content.Intent.EXTRA_TEXT, mailcontent);

                    startActivity(Intent.createChooser(sendMail, getString((R.string.send_mail))));
            }

        }
    }


    @Override
    public void onShowErrorString(String error) {
        Toast.makeText(this,
                error,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowErrorFromResources(int error) {
        Toast.makeText(this,
                getString(error),
                Toast.LENGTH_SHORT).show();
    }
}

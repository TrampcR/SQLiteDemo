package com.trampcr.sphomework;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by zxm on 2016/8/13.
 */
public class HomeActivity extends AppCompatActivity {

    public static final String WHERE_CLAUSE = "username = ?";
    public static final String IS_BACK = "isBack";
    private Button mBtnBack;
    private Boolean mIsBack = false;
    private TextView mTvUsername;
    private Intent mIntent;
    private String mAction;
    private String mUsername;
    private SharedPreferences mSp;
    private Button mBtnToDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mBtnBack = (Button) findViewById(R.id.btn_back);
        mTvUsername = (TextView) findViewById(R.id.tv_username);
        mBtnToDownload = (Button) findViewById(R.id.btn_to_download);

        mSp = getSharedPreferences(LoginActivity.SP_NAME, MODE_PRIVATE);

        mIntent = getIntent();
        mAction = mIntent.getAction();

        if (mAction.equals(RegisterActivity.INTENT_ACTION_REGISTER_TO_HOME)) {
            mUsername = mIntent.getStringExtra(RegisterActivity.TV_USERNAME);
            mTvUsername.setText(mUsername);
        } else if (mAction.equals(LoginActivity.INTENT_ACTION_LOGIN_TO_HOME)) {
            mUsername = mIntent.getStringExtra(RegisterActivity.TV_USERNAME);
            mTvUsername.setText(mUsername);
        } else if (mAction.equals(LoginActivity.INTENT_ACTION_DIRECT_TO_HOME)){
            mUsername = mIntent.getStringExtra(RegisterActivity.TV_USERNAME);
            mTvUsername.setText(mUsername);
        }

        SharedPreferences.Editor editor = mSp.edit();
        editor.putBoolean(IS_BACK, mIsBack);
        editor.apply();

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsBack = true;
                SharedPreferences.Editor editor = mSp.edit();
                editor.putBoolean(IS_BACK, mIsBack);
                editor.apply();
                MyOpenHelper myOpenHelper = new MyOpenHelper(getApplicationContext());
                SQLiteDatabase db = myOpenHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(LoginActivity.LOGIN_STATE, "");

                //判断是从登陆界面进来的还是从注册界面进来的
                if (mAction.equals(RegisterActivity.INTENT_ACTION_REGISTER_TO_HOME)) {
                    db.update(RegisterActivity.TABLE_NAME, values, WHERE_CLAUSE, new String[]{mUsername});
                } else if (mAction.equals(LoginActivity.INTENT_ACTION_LOGIN_TO_HOME)) {
                    db.update(RegisterActivity.TABLE_NAME, values, WHERE_CLAUSE, new String[]{mUsername});
                } else if (mAction.equals(LoginActivity.INTENT_ACTION_DIRECT_TO_HOME)) {
                    db.update(RegisterActivity.TABLE_NAME, values, WHERE_CLAUSE, new String[]{mUsername});
                }
                finish();
            }
        });

        mBtnToDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2Download = new Intent(HomeActivity.this, DownloadActivity.class);
                startActivity(intent2Download);
            }
        });
    }
}
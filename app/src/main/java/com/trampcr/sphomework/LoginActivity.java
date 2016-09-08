package com.trampcr.sphomework;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by zxm on 2016/8/10.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String LOGIN_STATE = "login_state";
    public static final String LOGIN_YES = "登录中";
    public static final String CB_IS_CHECKED = "cbIsChecked";
    public static final String SP_NAME = "shared_pre";
    public static final String SP_USERNAME = "username";
    public static final String SP_PASSWORD = "password";
    public static final String INTENT_ACTION_LOGIN_TO_HOME = "com.trampcr.intent.action.LOGIN_TO_HOME";
    public static final String INTENT_ACTION_DIRECT_TO_HOME = "com.trampcr.intent.action.DIRECT_TO_HOME";
    private EditText mEtUsername;
    private EditText mEtPassword;
    private CheckBox mCbRememberPassword;
    private Button mBtnLogin;
    private Button mBtnRegister;
    private MyOpenHelper mMyOpenHelper;
    private SQLiteDatabase db;
    public static String mUsername;
    private String mPassword;
    public static SharedPreferences mSp;
    private boolean mCbIsChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        mSp = getSharedPreferences(SP_NAME, MODE_PRIVATE);

        boolean isBack = mSp.getBoolean(HomeActivity.IS_BACK, true);

        boolean isCheck = mSp.getBoolean(CB_IS_CHECKED, false);

        if (!isBack){
            Intent intent = new Intent(INTENT_ACTION_DIRECT_TO_HOME);
            MyOpenHelper myOpenHelper = new MyOpenHelper(getApplicationContext());
            SQLiteDatabase db = myOpenHelper.getReadableDatabase();
            Cursor cursor = db.query(RegisterActivity.TABLE_NAME, null, "login_state = ?", new String[]{LoginActivity.LOGIN_YES}, null, null, null);
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex(RegisterActivity.USERNAME));
                intent.putExtra(RegisterActivity.TV_USERNAME, username);
            }
            startActivity(intent);
            finish();
        }

        if (isCheck) {
            mCbRememberPassword.setChecked(true);
            mEtUsername.setText(mSp.getString(SP_USERNAME, null));
            mEtPassword.setText(mSp.getString(SP_PASSWORD, null));
        }

        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
    }

    private void initView() {
        mEtUsername = (EditText) findViewById(R.id.et_username);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mCbRememberPassword = (CheckBox) findViewById(R.id.cb_remenber_password);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:

                mUsername = mEtUsername.getText().toString();
                mPassword = mEtPassword.getText().toString();
                String username;
                String password;
                boolean flag = false;

                mMyOpenHelper = new MyOpenHelper(getApplicationContext());
                db = mMyOpenHelper.getReadableDatabase();
                Cursor cursor = db.query(RegisterActivity.TABLE_NAME, null, null, null, null, null, null);

                while (cursor.moveToNext()) {

                    //从第一条开始，查询数据库中的用户名和密码
                    username = cursor.getString(cursor.getColumnIndex(RegisterActivity.USERNAME));
                    password = cursor.getString(cursor.getColumnIndex(RegisterActivity.PASSWORD));

                    //比较输入的用户名、密码和数据库中的用户名、密码，如果相等就跳转到主界面
                    if (mUsername.equals(username) && mPassword.equals(password)) {

                        flag = true;

                        mCbIsChecked = mCbRememberPassword.isChecked();
                        SharedPreferences.Editor editor = mSp.edit();
                        editor.putBoolean(CB_IS_CHECKED, mCbIsChecked);
                        editor.putString(SP_USERNAME, mUsername);
                        editor.putString(SP_PASSWORD, mPassword);
                        editor.apply();

                        ContentValues values = new ContentValues();
                        values.put(LOGIN_STATE, LOGIN_YES);
                        db.update(RegisterActivity.TABLE_NAME, values, HomeActivity.WHERE_CLAUSE, new String[]{LoginActivity.mUsername});

                        Intent intent2home = new Intent(INTENT_ACTION_LOGIN_TO_HOME);
                        intent2home.putExtra(RegisterActivity.TV_USERNAME, username);
                        startActivity(intent2home);
                        finish();
                    }
                }
                if (!flag){
                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
                break;
            case R.id.btn_register:
                Intent intent2register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent2register);
                finish();
                break;
        }
    }
}

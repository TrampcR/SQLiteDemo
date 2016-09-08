package com.trampcr.sphomework;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by zxm on 2016/8/13.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TABLE_NAME = "user";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String INTENT_ACTION_REGISTER_TO_HOME = "com.trampcr.intent.action.REGISTER_TO_HOME";
    public static final String TV_USERNAME = "tv_username";
    private EditText mEtUsernameReg;
    private EditText mEtPasswordReg;
    private Button mBtnLoginReg;
    private Button mBtnRegisterReg;
    private MyOpenHelper mMyOpenHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        mBtnLoginReg.setOnClickListener(this);
        mBtnRegisterReg.setOnClickListener(this);
    }

    private void initView() {
        mEtUsernameReg = (EditText) findViewById(R.id.et_username_reg);
        mEtPasswordReg = (EditText) findViewById(R.id.et_password_reg);
        mBtnLoginReg = (Button) findViewById(R.id.btn_login_reg);
        mBtnRegisterReg = (Button) findViewById(R.id.btn_register_reg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register_reg:
                Intent intent_home = new Intent(INTENT_ACTION_REGISTER_TO_HOME);
                String username = mEtUsernameReg.getText().toString();
                String password = mEtPasswordReg.getText().toString();
                mMyOpenHelper = new MyOpenHelper(getApplicationContext());
                db = mMyOpenHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                if (username.equals("") || password.equals("")){
                    Toast.makeText(RegisterActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    values.put(USERNAME, username);
                    values.put(PASSWORD, password);
                    values.put(LoginActivity.LOGIN_STATE, LoginActivity.LOGIN_YES);
                    db.insert(TABLE_NAME, null, values);
                    intent_home.putExtra(TV_USERNAME, username);
                    startActivity(intent_home);
                    finish();
                }
                break;
            case R.id.btn_login_reg:
                Intent intent_login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent_login);
                finish();
                break;
        }
    }
}

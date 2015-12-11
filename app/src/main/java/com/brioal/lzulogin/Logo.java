package com.brioal.lzulogin;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class Logo extends Activity implements OnClickListener {


    private EditText userName;
    private EditText passWord;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    Button mEmailSignInButton;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        preferences = getSharedPreferences("brioal", MODE_PRIVATE);
        String username = preferences.getString("username", null);
        String password = preferences.getString("password", null);
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    Toast.makeText(Logo.this, "登陆成功 2秒后关闭 ", Toast.LENGTH_SHORT).show();
                }
            }
        };

        userName = (EditText) findViewById(R.id.username);
        passWord = (EditText) findViewById(R.id.password);
        if (username != null && password != null) {
            userName.setText(username);
            passWord.setText(password);

        }
        mEmailSignInButton.setOnClickListener(this);
    }

    public void logoIn(final String username, final String password) {
//        Toast.makeText(Logo.this, "正在登陆请稍后", Toast.LENGTH_SHORT).show();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String click_url = "http://10.10.1.254/cgi-bin/srun_portal";
//                String logo_parma = "action=login&username=huangj2013@lzu.edu.cn&ac_id=12&type=1&wbaredirect=http://www.nuomi.com/?cid&mac\n=&nas_ip=&password=199501&is_ldap=1";
                String parma = "action=login&username=" + username + "@lzu.edu.cn&ac_id=12&type=1&wbaredirect=http://www.nuomi.com/?cid&mac\n" +
                        "=&nas_ip=&password=" + password + "&is_ldap=1";


                Info from_logo = GetLzu.Post(new Info(click_url, null, parma)); // �˴��ѽ��е�½����
                String data = from_logo.getData();
                System.out.println(data);
                if (data.contains("连接成功")) {

                    handler.sendEmptyMessage(0x123);
                    try {
                        Thread.sleep(2000);
                        System.exit(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(runnable).start();
    }


    @Override
    public void onClick(View v) {
        if (userName.getText().toString() != null && passWord.getText().toString() != null) {
            editor = preferences.edit();
            editor.putString("username", userName.getText().toString());
            editor.putString("password", passWord.getText().toString());
            editor.commit();
            logoIn(userName.getText().toString(), passWord.getText().toString());
        }

    }
}


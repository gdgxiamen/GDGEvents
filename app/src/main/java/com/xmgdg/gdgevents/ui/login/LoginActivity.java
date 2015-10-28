package com.xmgdg.gdgevents.ui.login;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.otto.Subscribe;
import com.xmgdg.gdgevents.R;
import com.xmgdg.gdgevents.otto.FragmentResume;
import com.xmgdg.gdgevents.ui.BaseActivity;

public class LoginActivity extends BaseActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void findView() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
    }

    @Override
    protected void initView() {
        LoginActivityFragment loginActivityFragment = new LoginActivityFragment();
        loginActivityFragment.setSignUp(new LoginActivityFragment.SignUp() {
            @Override
            public void signUp() {
                RegisterFragment registerFragment = new RegisterFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment, registerFragment).commit();
            }
        });
        getFragmentManager().beginTransaction().add(R.id.fragment, loginActivityFragment).commit();
    }

    @Override
    protected void setViewEvent() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onFragmentChange(FragmentResume fragmentResume) {
        if (fragmentResume.FramgentName.compareTo(RegisterFragment.class.getSimpleName()) == 0) {
            if (fragmentResume.resumeOrPause) {
                toolbar.setTitle("注册");
            }
        }
        if (fragmentResume.FramgentName.compareTo(LoginActivityFragment.class.getSimpleName()) == 0) {
            if (fragmentResume.resumeOrPause) {
                toolbar.setTitle("登入");
            }
        }
    }
}

package com.xmgdg.gdgevents.ui.login;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.xmgdg.gdgevents.R;
import com.xmgdg.gdgevents.ui.BaseActivity;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    @Override
    protected void findView() {

    }

    @Override
    protected void initView() {
        LoginActivityFragment loginActivityFragment = new LoginActivityFragment();
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
}

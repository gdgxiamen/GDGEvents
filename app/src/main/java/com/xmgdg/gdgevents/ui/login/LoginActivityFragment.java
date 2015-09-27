package com.xmgdg.gdgevents.ui.login;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.xmgdg.gdgevents.R;
import com.xmgdg.gdgevents.Tools.Tool;
import com.xmgdg.gdgevents.ui.BaseFragment;

/**
 * 登陆界面
 */
public class LoginActivityFragment extends BaseFragment {

    TextInputLayout userNameInputLayout, passwordInputLayout;
    Button signInButton, signUpButton;

    public LoginActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    protected void findViews(View view) {
        userNameInputLayout = (TextInputLayout) view.findViewById(R.id.user_name_input_layout);
        passwordInputLayout = (TextInputLayout) view.findViewById(R.id.password_input_layout);
        signInButton = (Button) view.findViewById(R.id.btn_sign_in);
        signUpButton = (Button) view.findViewById(R.id.btn_sign_up);
    }

    @Override
    protected void initViewsAndSetEvent() {

        userNameInputLayout.setError("请输入电子邮箱地址");
        userNameInputLayout.setErrorEnabled(false);

        final EditText username = userNameInputLayout.getEditText();

        assert username != null;
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                    userNameInputLayout.setErrorEnabled(false);
                } else {
                    userNameInputLayout.setErrorEnabled(true);
                }
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(name).matches())
                    Tool.SignUpInLeanCloud(name, passwordInputLayout.getEditText().getText().toString(), null, name, getActivity());
            }
        });
    }
}

package com.example.brochat;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends Activity {
	protected EditText mUsername;
	protected EditText mPassword;
	protected EditText mEmail;
	protected Button mSignUpButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_sign_up);
		
		mUsername = (EditText)findViewById(R.id.usernameField1);
		mPassword = (EditText) findViewById(R.id.passwordField1);
		mEmail = (EditText) findViewById(R.id.emailField);
		mSignUpButton =(Button)findViewById(R.id.signUpButton);
		mSignUpButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = mUsername.getText().toString().trim();
				String password = mPassword.getText().toString().trim();
				String email = mEmail.getText().toString().trim();
				
				if (username.isEmpty() || password.isEmpty() || email.isEmpty()){
					AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
					builder.setMessage(R.string.signup_error_message)
							.setTitle("Oops!")
							.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog=builder.create();
					dialog.show();
				}else{
					//Make a  new User!
					setProgressBarIndeterminateVisibility(true);
					ParseUser user = new ParseUser();
					user.setUsername(username);
					user.setPassword(password);
					user.setEmail(email);
					user.signUpInBackground(new SignUpCallback() {
						@Override
						public void done(ParseException e) {
							setProgressBarIndeterminateVisibility(false);
								if(e == null){
									//Success!
									Intent intent=new Intent(SignUpActivity.this, MainActivity.class);
									intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
									startActivity(intent);
								}
								else{
									AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
									builder.setMessage(e.getMessage())
											.setTitle("Oops!")
											.setPositiveButton(android.R.string.ok, null);
									AlertDialog dialog=builder.create();
									dialog.show();
								}
						}
					});
				}
				
			}
		});
	}

	
}

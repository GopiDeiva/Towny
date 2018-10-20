package hatchure.towny;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static hatchure.towny.Helpers.Utils.GetSharedPreferencesEditor;
import static hatchure.towny.Helpers.Utils.PhoneNumber;

public class OTPRequest extends Activity implements TextView.OnEditorActionListener {
    EditText editText_one, editText_two, editText_three, editText_four;
    String otpReceived, phoneNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otprequest);
        Intent intent = getIntent();
        phoneNo = (String) intent.getSerializableExtra(getString(R.string.PhoneNumber));
        otpReceived= (String) intent.getSerializableExtra("otp");
        TextView setOtpInfo =findViewById(R.id.get_otp_info);
        setOtpInfo.setText("Please type the OTP sent to "+phoneNo);

        editText_one = findViewById(R.id.number1);
        editText_one.setOnEditorActionListener(this);
        editText_two = findViewById(R.id.number2);
        editText_two.setOnEditorActionListener(this);
        editText_three = findViewById(R.id.number3);
        editText_three.setOnEditorActionListener(this);
        editText_four = findViewById(R.id.number4);
        editText_four.setOnEditorActionListener(this);

        final Button verify = findViewById(R.id.verify);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifyOTP(String.format("%s%s%s%s", editText_one.getText().toString().trim(),
                        editText_two.getText().toString().trim(),
                        editText_three.getText().toString().trim(),
                        editText_four.getText().toString().trim()), otpReceived);
            }
        });

        editText_one.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    editText_two.requestFocus();
                }
            }
        });
        editText_two.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    editText_three.requestFocus();
                }
                if (s.length() == 0) {
                    editText_one.requestFocus();
                }
            }
        });
        editText_three.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    editText_four.requestFocus();
                }
                if (s.length() == 0) {
                    editText_two.requestFocus();
                }
            }
        });
        editText_four.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    editText_three.requestFocus();
                }
            }
        });
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        VerifyOTP(String.format("%s%s%s%s", editText_one.getText().toString().trim(),
                            editText_two.getText().toString().trim(),
                            editText_three.getText().toString().trim(),
                            editText_four.getText().toString().trim()), otpReceived);
        return false;
    }

    private  void  VerifyOTP(String otp, String otpReceived){
        if(otp.length()==4) {
            if (otp.equals(otpReceived)) {
                SharedPreferences.Editor editor = GetSharedPreferencesEditor(this);
                editor.putString(PhoneNumber, phoneNo);
                editor.commit();
                Intent moveToHomeScreen = new Intent(getApplicationContext(), Home.class);
                startActivity(moveToHomeScreen);
            } else {
                Toast.makeText(getApplicationContext(), "OTP doesn't match", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Enter a valid OTP", Toast.LENGTH_LONG).show();
        }
    }
}

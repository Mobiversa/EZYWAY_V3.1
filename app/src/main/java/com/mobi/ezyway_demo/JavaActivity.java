package com.mobi.ezyway_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.mobi.ezyway.PaymentActivity;
import com.mobi.ezyway.service.network.BankListModel;
import com.mobi.ezyway.service.network.BoostResponse;
import com.mobi.ezyway.service.network.BoostStatus;
import com.mobi.ezyway.service.network.PaymentResponse;
import com.mobi.ezyway.service.network.PaymentResult;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class JavaActivity extends AppCompatActivity {
    
        EditText edit_ip;
        EditText edit_passport;
        EditText edit_country;
        EditText edit_amount;
        EditText edit_city;
        EditText edit_contactName;
        EditText edit_postalCode;
        EditText edit_latitude;
        EditText edit_longitude;
        EditText edit_mobileNo;
        EditText edit_expectedDate;
        EditText edit_orderId;
        EditText edit_nameOnCard;
        EditText edit_street;
        EditText edit_invoiceId;
        EditText edit_state;
        EditText edit_cardNumber;
        EditText edit_cardCVV;
        EditText edit_cardExpirymonth;
        EditText edit_cardExpiryYear;
        EditText edit_email;
        EditText edit_orderDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_ip = findViewById(R.id.edit_ip);
        edit_passport = findViewById(R.id.edit_passport);
        edit_country = findViewById(R.id.edit_country);
        edit_amount = findViewById(R.id.edit_amount);
        edit_city = findViewById(R.id.edit_city);
        edit_contactName = findViewById(R.id.edit_contactName);
        edit_postalCode = findViewById(R.id.edit_postalCode);
        edit_latitude = findViewById(R.id.edit_latitude);
        edit_longitude = findViewById(R.id.edit_longitude);
        edit_mobileNo = findViewById(R.id.edit_mobileNo);
        edit_expectedDate = findViewById(R.id.edit_expectedDate);
        edit_orderId = findViewById(R.id.edit_orderId);
        edit_nameOnCard = findViewById(R.id.edit_nameOnCard);
        edit_street = findViewById(R.id.edit_street);
        edit_invoiceId = findViewById(R.id.edit_invoiceId);
        edit_state = findViewById(R.id.edit_state);
        edit_cardNumber = findViewById(R.id.edit_cardNumber);
        edit_cardCVV = findViewById(R.id.edit_cardCVV);
        edit_cardExpirymonth = findViewById(R.id.edit_cardExpirymonth);
        edit_cardExpiryYear = findViewById(R.id.edit_cardExpiryYear);
        edit_email = findViewById(R.id.edit_email);
        edit_orderDesc = findViewById(R.id.edit_orderDesc);

//        edit_ip.setText(getLocalIpAddress());
        edit_passport.setText("N1234567");
        edit_country.setText("Malaysia");
        edit_amount.setText("1.00");
        edit_city.setText("Kuala Lumpur");
        edit_contactName.setText("Mobiversa");
        edit_postalCode.setText("50790");
        edit_latitude.setText("3.1414518");
        edit_longitude.setText("101.6578041");
        edit_mobileNo.setText("01112201234");
        edit_expectedDate.setText("25-Jul-2018");
        edit_orderId.setText("0192822828");
        edit_nameOnCard.setText("Mobiversa");
        edit_street.setText("Damansara Heights");
        edit_invoiceId.setText("Testing");
        edit_state.setText("KL");
        edit_cardNumber.setText("4918914107195005");
        edit_cardCVV.setText("123");
        edit_cardExpirymonth.setText("12");
        edit_cardExpiryYear.setText("20");
        edit_email.setText("karthik@mobiversa.com");
        edit_orderDesc.setText("Android Description");

        PaymentActivity.Companion.getInstance(getApplicationContext(), paymentResponse,true);
        PaymentActivity pay = new PaymentActivity();


    }

    //Payment Connection Interface
    private PaymentResponse paymentResponse = new PaymentResponse() {
        @Override
        public void getBoostData(@NotNull BoostResponse boostResponse) {

        }

        @Override
        public void checkBoostStatus(@NotNull BoostStatus boostStatus) {

        }

        @Override
        public void getBankList(@NotNull BankListModel bankListModel) {

        }

        @Override
        public void setSuccess(@NotNull PaymentResult paymentResult) {

        }

        @Override
        public void setFailure(@NotNull String s) {

        }
    };
}
package com.example.tstocks;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdView = findViewById(R.id.adViewbanner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        final EditText buy = (EditText)findViewById(R.id.buy);
        buy.setText("40");

        final EditText buyNum = (EditText)findViewById(R.id.buyNum);
        buyNum.setText("1000");

        Spinner fee = (Spinner)findViewById(R.id.buyFee);
        final ArrayAdapter<CharSequence> nAdapter = ArrayAdapter.createFromResource(
                this, R.array.fees, android.R.layout.simple_spinner_item);
        nAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        fee.setAdapter(nAdapter);
        fee.setSelection(2);
        fee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(
                        MainActivity.this,
                        nAdapter.getItem(position),
                        Toast.LENGTH_LONG);
                toast.show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setBuyPrice(buy, buyNum);
        setBFee();

        buy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setBuyPrice(buy, buyNum);
            }
        });
    }
    private void setBuyPrice(TextView buy, TextView buyNum) {
        TextView showText = (TextView)findViewById(R.id.buyPrice);
        EditText fieldBuy = (EditText)findViewById(R.id.buy);
        EditText fieldBuyNum = (EditText)findViewById(R.id.buyNum);
        double buyP = Double.parseDouble(fieldBuy.getText().toString());
        double buyN = Double.parseDouble(fieldBuyNum.getText().toString());
        double buyPrice = buyP * buyN;
        showText.setText(buyPrice+"");
    }
    private void setBFee() {
        TextView showText = (TextView)findViewById(R.id.bFee);
        TextView buyPrice = (TextView)findViewById(R.id.buyPrice);
        Spinner buyFee = (Spinner)findViewById(R.id.buyFee);
        double buyP =  Double.parseDouble(buyPrice.getText().toString());
        double buyF = Double.parseDouble(buyFee.getSelectedItem().toString());
        double bFee = buyP * 0.001425 * buyF * 0.1;
        if(bFee < 20) {
            bFee = 20;
        }
        showText.setText(bFee+"");
    }
    public void ShowAd(View v) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }
}

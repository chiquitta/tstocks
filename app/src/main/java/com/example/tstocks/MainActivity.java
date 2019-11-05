package com.example.tstocks;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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

import java.text.DecimalFormat;

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
        final EditText sell = (EditText)findViewById(R.id.sell);
        sell.setText("42");

        final EditText buyNum = (EditText)findViewById(R.id.buyNum);
        buyNum.setText("1000");

        final EditText sellNum = (EditText)findViewById(R.id.sellNum);
        sellNum.setText("1000");

        String[] plants = new String[]{
                "10",
                "9.5",
                "6",
                "0",
        };

        Spinner fee = (Spinner)findViewById(R.id.buyFee);
//        final ArrayAdapter<CharSequence> nAdapter = ArrayAdapter.createFromResource(
//                this, R.array.fees, android.R.layout.);
        final ArrayAdapter<String> nAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, plants);
//        nAdapter.setDropDownViewResource(
//                android.R.layout.spinner_item);
        nAdapter.setDropDownViewResource(R.layout.spinner_item);
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

        Spinner sFee = (Spinner)findViewById(R.id.sellFee);
//        final ArrayAdapter<CharSequence> nAdapter = ArrayAdapter.createFromResource(
//                this, R.array.fees, android.R.layout.);
        final ArrayAdapter<String> nsAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, plants);
//        nAdapter.setDropDownViewResource(
//                android.R.layout.spinner_item);
        nsAdapter.setDropDownViewResource(R.layout.spinner_item);
        sFee.setAdapter(nAdapter);
        sFee.setSelection(2);
        sFee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        setBuyTotal();
        setSellPrice(sell, sellNum);
        setSFee();
        setSellTotal();

        sell.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!sell.getText().toString().matches("")) {
                    setSellPrice(sell, sellNum);
                    setSFee();
                    setSellTotal();
                } else {
                    sell.setText("0");
                }
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
//        double price = Math.round(buyPrice);
//        double price = Double.parseDouble(buyPrice+"");
        DecimalFormat formatter = new DecimalFormat("###,###");
//        String formatted = formatter.format(price);
        showText.setText(Math.round(buyPrice)+"");
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
        showText.setText(Math.round(bFee)+"");
    }
    private  void setBuyTotal() {
        TextView showText = (TextView)findViewById(R.id.buyTotal);
        TextView buyPrice = (TextView)findViewById(R.id.buyPrice);
        TextView bFee = (TextView)findViewById(R.id.bFee);
        double buyP =  Double.parseDouble(buyPrice.getText().toString());
        double buyF =  Double.parseDouble(bFee.getText().toString());
        double buyTotal = buyP + buyF;
        showText.setText(Math.round(buyTotal)+"");
    }
    private void setSellPrice(TextView sell, TextView sellNum) {
        TextView showText = (TextView)findViewById(R.id.sellPrice);
        EditText fieldBuy = (EditText)findViewById(R.id.sell);
        EditText fieldBuyNum = (EditText)findViewById(R.id.sellNum);
        double sellP = Double.parseDouble(fieldBuy.getText().toString());
        double sellN = Double.parseDouble(fieldBuyNum.getText().toString());
        double sellPrice = sellP * sellN;
//        double price = Math.round(buyPrice);
//        double price = Double.parseDouble(buyPrice+"");
        DecimalFormat formatter = new DecimalFormat("###,###");
//        String formatted = formatter.format(price);
        showText.setText(Math.round(sellPrice)+"");
    }
    private void setSFee() {
        TextView showText = (TextView)findViewById(R.id.sFee);
        TextView buyPrice = (TextView)findViewById(R.id.sellPrice);
        Spinner buyFee = (Spinner)findViewById(R.id.sellFee);
        double sellP =  Double.parseDouble(buyPrice.getText().toString());
        double sellF = Double.parseDouble(buyFee.getSelectedItem().toString());
        double sFee = sellP * 0.001425 * sellF * 0.1;
        if(sFee < 20) {
            sFee = 20;
        }
        showText.setText(Math.round(sFee)+"");
    }
    private  void setSellTotal() {
        TextView showText = (TextView)findViewById(R.id.sellTotal);
        TextView buyPrice = (TextView)findViewById(R.id.sellPrice);
        TextView bFee = (TextView)findViewById(R.id.sFee);
        double sellP =  Double.parseDouble(buyPrice.getText().toString());
        double sellF =  Double.parseDouble(bFee.getText().toString());
        double sellTotal = sellP + sellF;
        showText.setText(Math.round(sellTotal)+"");
    }
    public void ShowAd(View v) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }
}

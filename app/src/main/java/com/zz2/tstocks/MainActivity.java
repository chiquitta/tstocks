package com.zz2.tstocks;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
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

        mAdView = findViewById(R.id.adViewBanner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
//        MobileAds.initialize(this,
//                "ca-app-pub-3940256099942544~3347511713");
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());

//        TextView titleText = (TextView)findViewById(R.id.titleText);
//        titleText.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/chogokubosogothic_5.ttf"));

        final EditText buy = (EditText)findViewById(R.id.buy);
        buy.setText("0");
        final EditText sell = (EditText)findViewById(R.id.sell);
        sell.setText("0");

        final EditText buyNum = (EditText)findViewById(R.id.buyNum);
        buyNum.setText("1000");
        final EditText sellNum = (EditText)findViewById(R.id.sellNum);
        sellNum.setText("1000");

        final TextView buyFeeF = (TextView)findViewById(R.id.buyFeeF);
        buyFeeF.setText("0");
        final String[] listItems = getResources().getStringArray(R.array.fees);

        buyFeeF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                mBuilder.setTitle("券商下單折扣");

                mBuilder.setSingleChoiceItems(listItems, 8, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        buyFeeF.setText(listItems[which]+"");
                        setBuyPrice(buy, buyNum);
                        setBFee();
                        setBuyTotal();
                        setProfit();
                        setProfitPercentage();
                        dialog.dismiss();
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        final TextView sellFeeF = (TextView)findViewById(R.id.sellFeeF);
        sellFeeF.setText("0");
//        final String[] listItems = getResources().getStringArray(R.array.fees);

        sellFeeF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                mBuilder.setTitle("券商下單折扣");

                mBuilder.setSingleChoiceItems(listItems, 8, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sellFeeF.setText(listItems[which]+"");
                        setSellPrice(buy, buyNum);
                        setSFee();
                        setSellTotal();
                        setProfit();
                        setProfitPercentage();
                        dialog.dismiss();
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        setBuyPrice(buy, buyNum);
        setBFee();
        setBuyTotal();
        setSellPrice(sell, sellNum);
        setSFee();
        setSellTotal();
        setProfit();
        setProfitPercentage();

        buy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!buy.getText().toString().matches("")) {
                    setBuyPrice(buy, buyNum);
                    setBFee();
                    setBuyTotal();
                    setProfit();
                    setProfitPercentage();
                } else {
                    buy.setText("0");
                }
            }
        });

        buyNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!buyNum.getText().toString().matches("")) {
                    setBuyPrice(buy, buyNum);
                    setBFee();
                    setBuyTotal();
                    setProfit();
                    setProfitPercentage();
                } else {
                    buyNum.setText("0");
                }
            }
        });

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
                    setProfit();
                    setProfitPercentage();
                } else {
                    sell.setText("0");
                }
            }
        });

        sellNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!sellNum.getText().toString().matches("")) {
                    setSellPrice(sell, sellNum);
                    setSFee();
                    setSellTotal();
                    setProfit();
                    setProfitPercentage();
                } else {
                    sellNum.setText("0");
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
        DecimalFormat formatter = new DecimalFormat("###,###");
        double price = Double.parseDouble(buyPrice+"");
        price = Math.round(price);
        String formatted = formatter.format(price);
        showText.setText(formatted);
    }
    private void setBFee() {
        TextView showText = (TextView)findViewById(R.id.bFee);
        TextView buyPrice = (TextView)findViewById(R.id.buyPrice);
//        Spinner buyFee = (Spinner)findViewById(R.id.buyFee);
        TextView buyFee = (TextView)findViewById(R.id.buyFeeF);
        double buyP =  Double.parseDouble(buyPrice.getText().toString().replace(",", ""));
        double buyF =  Double.parseDouble(buyFee.getText().toString());
//        double buyF = Double.parseDouble(buyFee.getSelectedItem().toString());
        double bFee = buyP * 0.001425 * buyF * 0.1;
        if(bFee < 20) {
            bFee = 20;
        }
        DecimalFormat formatter = new DecimalFormat("###,###");
        double price = Double.parseDouble(bFee+"");
        price = Math.round(price);
        String formatted = formatter.format(price);
        showText.setText(formatted);
    }
    private  void setBuyTotal() {
        TextView showText = (TextView)findViewById(R.id.buyTotal);
        TextView buyPrice = (TextView)findViewById(R.id.buyPrice);
        TextView bFee = (TextView)findViewById(R.id.bFee);
        double buyP =  Double.parseDouble(buyPrice.getText().toString().replace(",", ""));
        double buyF =  Double.parseDouble(bFee.getText().toString().replace(",", ""));
        double buyTotal = buyP + buyF;
        DecimalFormat formatter = new DecimalFormat("###,###");
        double price = Double.parseDouble(buyTotal+"");
        price = Math.round(price);
        String formatted = formatter.format(price);
        showText.setText(formatted);
    }
    private void setSellPrice(TextView sell, TextView sellNum) {
        TextView showText = (TextView)findViewById(R.id.sellPrice);
        EditText fieldBuy = (EditText)findViewById(R.id.sell);
        EditText fieldBuyNum = (EditText)findViewById(R.id.sellNum);
        double sellP = Double.parseDouble(fieldBuy.getText().toString().replace(",", ""));
        double sellN = Double.parseDouble(fieldBuyNum.getText().toString());
        double sellPrice = sellP * sellN;
        DecimalFormat formatter = new DecimalFormat("###,###");
        double price = Double.parseDouble(sellPrice+"");
        price = Math.round(price);
        String formatted = formatter.format(price);
        showText.setText(formatted);
    }
    private void setSFee() {
        TextView showText = (TextView)findViewById(R.id.sFee);
        TextView showTax = (TextView)findViewById(R.id.tax);
        TextView sellPrice = (TextView)findViewById(R.id.sellPrice);
//        Spinner sellFee = (Spinner)findViewById(R.id.sellFee);
        TextView sellFee = (TextView)findViewById(R.id.sellFeeF);
        double sellP =  Double.parseDouble(sellPrice.getText().toString().replace(",", ""));
        double sellF = Double.parseDouble(sellFee.getText().toString());
        double sFee = sellP * 0.001425 * sellF * 0.1;
        if(sFee < 20) {
            sFee = 20;
        }

        DecimalFormat formatter = new DecimalFormat("###,###");
        double sprice = Double.parseDouble(sFee+"");
        sprice = Math.round(sprice);
        String sformatted = formatter.format(sprice);
        showText.setText(sformatted);

        double tax = sellP * 0.003;

        double price = Double.parseDouble(tax+"");
        price = Math.round(price);
        String formatted = formatter.format(price);
        showTax.setText(formatted+"");
    }
    private  void setSellTotal() {
        TextView showText = (TextView)findViewById(R.id.sellTotal);
        TextView sellPrice = (TextView)findViewById(R.id.sellPrice);
        TextView sFee = (TextView)findViewById(R.id.sFee);
        TextView sTax = (TextView)findViewById(R.id.tax);
        double sellP =  Double.parseDouble(sellPrice.getText().toString().replace(",", ""));
        double sellF =  Double.parseDouble(sFee.getText().toString().toString().replace(",", ""));
        double sellT =  Double.parseDouble(sTax.getText().toString().toString().replace(",", ""));
        double sellTotal = sellP - sellF - sellT;
        DecimalFormat formatter = new DecimalFormat("###,###");
        double price = Double.parseDouble(sellTotal+"");
        price = Math.round(price);
        String formatted = formatter.format(price);
        showText.setText(formatted);
    }
    private  void setProfit() {
        TextView showText = (TextView)findViewById(R.id.profit);
        TextView sellTotal = (TextView)findViewById(R.id.sellTotal);
        TextView buyTotal = (TextView)findViewById(R.id.buyTotal);
        double stotal =  Double.parseDouble(sellTotal.getText().toString().replace(",", ""));
        double btotal =  Double.parseDouble(buyTotal.getText().toString().replace(",", ""));
        double profit = stotal - btotal;
        DecimalFormat formatter = new DecimalFormat("###,###");
        double price = Double.parseDouble(profit+"");
        price = Math.round(price);
        String formatted = formatter.format(price);
        showText.setText(formatted);
        if(price > 0) {
            showText.setTextColor(Color.parseColor("#dc3545"));
        } else {
            showText.setTextColor(Color.parseColor("#28a745"));
        }
    }
    private  void setProfitPercentage() {
        TextView showText = (TextView)findViewById(R.id.profitPercentage);
        TextView profit = (TextView)findViewById(R.id.profit);
        TextView buyTotal = (TextView)findViewById(R.id.buyTotal);
        double dprofit =  Double.parseDouble(profit.getText().toString().replace(",", ""));
        double btotal =  Double.parseDouble(buyTotal.getText().toString().replace(",", ""));
        double profitpercentage = dprofit / btotal * 100;
        DecimalFormat formatter = new DecimalFormat("###,###.##");
        double price = Double.parseDouble(profitpercentage+"");
//        price = Math.round(price);
        String formatted = formatter.format(price);
        showText.setText(formatted+"%");
        if(price > 0) {
            showText.setTextColor(Color.parseColor("#dc3545"));
        } else {
            showText.setTextColor(Color.parseColor("#28a745"));
        }
    }
    public void ShowAd(View v) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
}

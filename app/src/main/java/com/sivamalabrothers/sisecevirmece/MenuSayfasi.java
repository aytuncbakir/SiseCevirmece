package com.sivamalabrothers.sisecevirmece;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class MenuSayfasi extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    RelativeLayout arkaplan;
    LinearLayout lay1,lay2,lay3,lay4;
    TextView tv_baslik,tsise,tdogruluk,tcesaret,tdogces;


    Boolean sesDurumu;
    SharedPreferences preferences, ayarlar;


    private AdView adView, adView1;
    LinearLayout reklam_layout,reklam_layout1;
    private static final String REKLAM_ID = "ca-app-pub-3183404528711365/5531317805";
    private static final String REKLAM_ID1 = "ca-app-pub-3183404528711365/1400501102";

    private static final int  BILGI_ALERT_DIALOG_ID = 1;

    private InterstitialAd interstitial;
    private static final String REKLAM_ID2 = "ca-app-pub-3183404528711365/2872100804";

    private Typeface font;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Make to run your application only in portrait mode
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // Make to run your application only in LANDSCAPE mode
        //setContentView(R.layout.disable_android_orientation_change);
        setContentView(R.layout.menu_sayfasi);

        /*
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        */

        // geri butonu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ilklendirmeleriYap();

        lay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MenuSayfasi.this);
                    Intent krn = new Intent(getApplicationContext(), Tahta.class);
                    krn.putExtra("menu", "sise");
                    startActivity(krn,options.toBundle());
                }else {

                    Intent krn = new Intent(getApplicationContext(), Tahta.class);
                    krn.putExtra("menu", "sise");
                    startActivity(krn);
                }
            }
        });

        lay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MenuSayfasi.this);
                    Intent krn = new Intent(getApplicationContext(), Tahta.class);
                    krn.putExtra("menu", "dogruluk");
                    startActivity(krn,options.toBundle());
                }else {
                    Intent krn = new Intent(getApplicationContext(), Tahta.class);
                    krn.putExtra("menu", "dogruluk");
                    startActivity(krn);
                }
            }
        });

        lay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MenuSayfasi.this);
                    Intent krn = new Intent(getApplicationContext(), Tahta.class);
                    krn.putExtra("menu", "cesaret");
                    startActivity(krn,options.toBundle());
                }else {
                    Intent krn = new Intent(getApplicationContext(), Tahta.class);
                    krn.putExtra("menu", "cesaret");
                    startActivity(krn);
                }
            }
        });

        lay4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Build.VERSION.SDK_INT>=21 ){
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MenuSayfasi.this);
                    Intent krn = new Intent(getApplicationContext(), Tahta.class);
                    krn.putExtra("menu", "dogces");
                    startActivity(krn,options.toBundle());
                }else {
                    Intent krn = new Intent(getApplicationContext(), Tahta.class);
                    krn.putExtra("menu", "dogces");
                    startActivity(krn);
                }
            }
        });



        reklam_yukle1();
        reklam_yukle();
        animasyonUygula();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.ayarlar){
            Intent ayarlar = new Intent(getApplicationContext(),Ayarlar.class);
            startActivity(ayarlar);

            return  true;
        }else if(id == R.id.nasil){
            showDialog(BILGI_ALERT_DIALOG_ID);
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(final int id) {

        switch (id){


            case BILGI_ALERT_DIALOG_ID:

                final Dialog kaydetDialog = new Dialog(this);

                kaydetDialog.setTitle(Html.fromHtml(getResources().getString(R.string.html_title)));
                kaydetDialog.setContentView(R.layout.bilgilendirme);

                final EditText bilgilendirme = kaydetDialog.findViewById(R.id.bilgilendirme);
                bilgilendirme.setText(Html.fromHtml(getResources().getString(R.string.html)));

                return kaydetDialog;
            default:
                return super.onCreateDialog(id);

        }

    }


    private void reklam_yukle1(){
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(REKLAM_ID2);

        AdRequest adRequest = new AdRequest.Builder().build();

        interstitial.loadAd(adRequest);

        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (interstitial.isLoaded()) {
                    interstitial.show();
                }
            }
        });
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void ilklendirmeleriYap(){

        ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        arkaplan = findViewById(R.id.klay);
        lay1 = findViewById(R.id.lay1);
        lay2 = findViewById(R.id.lay2);
        lay3 = findViewById(R.id.lay3);
        lay4 = findViewById(R.id.lay4);

        font = Typeface.createFromAsset(getAssets(),"fonts/Candy_Shop_Black.ttf");

        tv_baslik = findViewById(R.id.tv_baslik);

        tv_baslik.setGravity(Gravity.CENTER);
        tv_baslik.setTypeface(font,Typeface.BOLD);
        tv_baslik.setText("Doğruluk ve Cesaret");

        tsise = findViewById(R.id.tsise);
        tsise.setTypeface(font,Typeface.BOLD);
        tsise.setText("Şişe Çevir");

        tdogruluk = findViewById(R.id.tdogruluk);
        tdogruluk.setTypeface(font,Typeface.BOLD);
        tdogruluk.setText("Doğruluk");

        tcesaret = findViewById(R.id.tcesaret);
        tcesaret.setTypeface(font,Typeface.BOLD);
        tcesaret.setText("Cesaret");

        tdogces = findViewById(R.id.tdogces);
        tdogces.setTypeface(font,Typeface.BOLD);
        tdogces.setText("Doğruluk   Cesaret");

        ayarlari_yukle();

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void ayarlari_yukle() {

        sesDurumu = ayarlar.getBoolean("ses",false);
        ayarlar.registerOnSharedPreferenceChangeListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        ayarlari_yukle();
    }


    private void animasyonUygula(){
        if(Build.VERSION.SDK_INT >=21){
            Slide enterTransition = new Slide();
            enterTransition.setDuration(300);
            enterTransition.setSlideEdge(Gravity.RIGHT);
            getWindow().setEnterTransition(enterTransition);
        }
    }
    // geri butonuna basıldığında çalışır
    @Override
    public boolean onSupportNavigateUp(){
        if(Build.VERSION.SDK_INT >= 21)
            finishAfterTransition();
        else
            finish();
        return true;
    }

    private void reklam_yukle(){

        reklam_layout = findViewById(R.id.reklam_layout);
        reklam_layout1 = findViewById(R.id.reklam_layout1);

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(REKLAM_ID);
        reklam_layout.addView(adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);

        if(getScreenHeight()>1000) {
            adView1 = new AdView(this);
            adView1.setAdSize(AdSize.BANNER);
            adView1.setAdUnitId(REKLAM_ID1);
            reklam_layout1.addView(adView1);
            AdRequest adRequest1 = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
            adView1.loadAd(adRequest1);
        }

    }



    private static int getScreenWidth(){
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    private static int getScreenHeight(){
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}

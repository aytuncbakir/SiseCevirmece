package com.sivamalabrothers.sisecevirmece;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Random;



public class Tahta extends AppCompatActivity implements View.OnClickListener, Animation.AnimationListener {
    ImageView sise;
    private int currentRotation;

    private Bundle gelenVeri;
    private ArrayList<String> datalar;
    private ArrayList<String> dogruluk;
    private ArrayList<String> cesaret;
    private String menu = "";
    private String okunacakDosya = "";
    EditText soru;
    private int cıkanSoru = 0;
    private static int reklamFrekans = 1;
    RotateAnimation anim = null;
    TextView txtsapsik;
    Context context = this;
    private MediaPlayer ses;

    private static final int  CUSTOM_DIALOG_ID = 1;

    private AdView adView;
    LinearLayout reklam_layout;
    LinearLayout reklam_layout1;
    private static final String REKLAM_ID = "ca-app-pub-3183404528711365/8037582715";
    private static final String REKLAM_ID1 = "ca-app-pub-3183404528711365/8363675219";

    private Typeface font;

    SharedPreferences preferences, ayarlar;
    Boolean sesDurumu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tahta);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        ilklendirmeleriYap();
        animasyonUygula();
        reklam_yukle();
        reklam_yukle1();


    }


    private void ilklendirmeleriYap(){

        sise = findViewById(R.id.sise);
        sise.setOnClickListener(this);

        font = Typeface.createFromAsset(getAssets(),"fonts/Candy_Shop_Black.ttf");

        soru = findViewById(R.id.soru);
        soru.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        soru.setText("Çevirmek için şişeyi tıklayınız.");

        gelenVeri = getIntent().getExtras();
        if(gelenVeri != null) {
            menu = gelenVeri.getString("menu");
        }

        ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sesDurumu = ayarlar.getBoolean("ses",false);

        dosyaIslemleri();
    }

    private void dosyaIslemleri(){
        assert menu != null;
        switch (menu){
            case "sise":
                break;
            case "dogruluk":
                okunacakDosya = "dogruluk.txt";
                break;
            case "cesaret":
                okunacakDosya = "cesaret.txt";
                break;
            case "dogces":
                DosyaOku dosyaOku = new DosyaOku(this);
                dogruluk = dosyaOku.dosyadanyukle("dogruluk.txt");
                cesaret = dosyaOku.dosyadanyukle("cesaret.txt");
                break;
        }

        if(!menu.equals("") && !okunacakDosya.equals("") && !menu.equals("dogces")) {
            DosyaOku dosyaOku = new DosyaOku(this);
            datalar = dosyaOku.dosyadanyukle(okunacakDosya);
        }
    }

    private void soruyuGoster(){

        switch (menu){
            case "sise":
                soru.setText("Doğruluk mu Cesaret mi?");
                break;
            case "dogruluk":
                if(datalar.size() == 1){
                    cıkanSoru = generateRandomNumber(datalar.size());
                    soru.setText(datalar.get(cıkanSoru));
                     datalar.remove(cıkanSoru);
                    dosyaIslemleri();

                }else{
                    cıkanSoru = generateRandomNumber(datalar.size());
                    soru.setText(datalar.get(cıkanSoru));
                    datalar.remove(cıkanSoru);
                }

                break;
            case "cesaret":
                if(datalar.size() == 1){
                    cıkanSoru = generateRandomNumber(datalar.size());
                    soru.setText(datalar.get(cıkanSoru));
                    datalar.remove(cıkanSoru);
                    dosyaIslemleri();

                }else{
                    cıkanSoru = generateRandomNumber(datalar.size());
                    soru.setText(datalar.get(cıkanSoru));
                    datalar.remove(cıkanSoru);
                }
                break;
            case "dogces":

                int hangisi = generateRandomNumber( 100);
                if(hangisi % 2 == 0) {

                    if(dogruluk.size() == 1){
                        cıkanSoru = generateRandomNumber(dogruluk.size());
                        soru.setText(dogruluk.get(cıkanSoru));
                        dogruluk.remove(cıkanSoru);
                        dosyaIslemleri();

                    }else{
                        cıkanSoru = generateRandomNumber(dogruluk.size());
                        soru.setText(dogruluk.get(cıkanSoru));
                        dogruluk.remove(cıkanSoru);
                    }
                }else{

                    if(cesaret.size() == 1){
                        cıkanSoru = generateRandomNumber(cesaret.size());
                        soru.setText(cesaret.get(cıkanSoru));
                        cesaret.remove(cıkanSoru);
                        dosyaIslemleri();

                    }else{
                        cıkanSoru = generateRandomNumber(cesaret.size());
                        soru.setText(cesaret.get(cıkanSoru));
                        cesaret.remove(cıkanSoru);
                    }
                }

                break;
        }
    }

    private void turn() {
        soru.setText("");
        int random = generateRandomNumber();
        anim = new RotateAnimation(currentRotation, (currentRotation + random ) ,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        currentRotation = (currentRotation +  random);
        anim.setAnimationListener(this);
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(3000);
        anim.setFillEnabled(true);
        //anim.setRepeatCount(repeatRandomNumber());
        anim.setRepeatMode(MODE_APPEND);

        anim.setFillAfter(true);
        sise.startAnimation(anim);


        if(reklamFrekans % 7 == 0 && reklamFrekans != 0)
        {


        }

        if(reklamFrekans % 7 == 0 && reklamFrekans != 0 ){
            showDialog(CUSTOM_DIALOG_ID);
        }

        reklamFrekans++;

    }


    private void animasyonUygula(){
        if(Build.VERSION.SDK_INT >=21){
            Slide enterTransition = new Slide();
            enterTransition.setDuration(300);
            enterTransition.setSlideEdge(Gravity.BOTTOM);
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

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(REKLAM_ID);

        reklam_layout.addView(adView);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);
    }

    private void reklam_yukle1(){

        reklam_layout1 = findViewById(R.id.reklam_layout1);

        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(REKLAM_ID1);

        reklam_layout1.addView(adView);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);
    }

    @Override
    public void onClick(View v) {

        turn();
    }

    private int generateRandomNumber() {

        Random rand = new Random();
        int randomNum = rand.nextInt((3600) + 1080);
        while(randomNum > 3200 || randomNum < 1900 )
            randomNum = rand.nextInt((3600) + 1080);
        //Toast.makeText( getApplicationContext(), randomNum+"", Toast.LENGTH_SHORT).show();
        return randomNum;
    }

    private int generateRandomNumber(int size) {

        Random rand = new Random();
        int randomNum = rand.nextInt(size);
        //Toast.makeText( getApplicationContext(), randomNum+"", Toast.LENGTH_SHORT).show();
        return randomNum;
    }

    @Override
    public void onAnimationStart(Animation animation) {

         sesAc(R.raw.bottle);
    }

    @Override
    public void onAnimationEnd(Animation animation) {

        if(animation.equals(anim)){
            soruyuGoster();
            sesKapat();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {}



    @Override
    protected Dialog onCreateDialog(final int id) {

        switch (id){


            case CUSTOM_DIALOG_ID:

                final Dialog kaydetDialog1 = new Dialog(this);
                kaydetDialog1.setCancelable(false);
                kaydetDialog1.setCanceledOnTouchOutside(false);


                kaydetDialog1.setContentView(R.layout.sapsik);
                TextView txttitle = kaydetDialog1.findViewById(R.id.txttitle);
                ImageView imgsapsik = kaydetDialog1.findViewById(R.id.imgsapsik);
                txtsapsik = kaydetDialog1.findViewById(R.id.txtsapsik);
                Button tmmsapsik = kaydetDialog1.findViewById(R.id.tmmsapsik);

                int gorevBelirle = 0;
                gorevBelirle = generateRandomNumber(9);

                if(gorevBelirle == 0)
                    txtsapsik.setText(Html.fromHtml(getResources().getString(R.string.sarki)));
                else if(gorevBelirle == 1)
                    txtsapsik.setText(Html.fromHtml(getResources().getString(R.string.turku)));
                else if(gorevBelirle == 2)
                    txtsapsik.setText(Html.fromHtml(getResources().getString(R.string.uzunhavaoku)));
                else if(gorevBelirle == 3)
                    txtsapsik.setText(Html.fromHtml(getResources().getString(R.string.fikraanlat)));
                else if(gorevBelirle == 4)
                    txtsapsik.setText(Html.fromHtml(getResources().getString(R.string.manioku)));
                else if(gorevBelirle == 5)
                    txtsapsik.setText(Html.fromHtml(getResources().getString(R.string.tekerlemeoku)));
                else if(gorevBelirle == 6)
                    txtsapsik.setText(Html.fromHtml(getResources().getString(R.string.gazeloku)));
                else if(gorevBelirle == 7)
                    txtsapsik.setText(Html.fromHtml(getResources().getString(R.string.siiroku)));
                else if(gorevBelirle == 8)
                    txtsapsik.setText(Html.fromHtml(getResources().getString(R.string.taklidyap)));



                txttitle.setText(Html.fromHtml(getResources().getString(R.string.html_title1)));

                tmmsapsik.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       /*
                       Intent intent = new Intent(context, Tahta.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish(); // Call once you redirect to another activity
                        intent.putExtra("menu", menu);
                        startActivity(intent);
                        */
                        removeDialog(1);

                    }
                });
                return kaydetDialog1;
            default:
                return super.onCreateDialog(id);
        }

    }

    public void sesKapat(){

        if(ses.isPlaying())
            ses.stop();

    }

    public void sesAc(int sesId){

        ses = MediaPlayer.create(getApplicationContext(),sesId);
        if(sesDurumu)
            ses.start();

    }
}


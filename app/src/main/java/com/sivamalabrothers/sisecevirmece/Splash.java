package com.sivamalabrothers.sisecevirmece;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.Random;


public class Splash extends AppCompatActivity implements Animation.AnimationListener {

    ImageView sise;
    int currentRotation;

    private TextView tv;
    private TextView tv1;
    private Animation mImgAnim;
    private Animation mTextAnim;

    private AdView adView;
    LinearLayout reklam_layout;
    private Typeface font;

    private static final String REKLAM_ID = "ca-app-pub-3183404528711365/6297604566";

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
        setContentView(R.layout.splash);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();


        initViews();
        setTexts();
        //reklam_yukle();
        //spanText();
        //spanText1();
        turn();

        alphaAnimation(tv1);
    }

    private void initViews(){
        sise = findViewById(R.id.sise);
        tv = findViewById(R.id.tv);
        tv1 = findViewById(R.id.tv1);


        font = Typeface.createFromAsset(getAssets(),"fonts/Candy_Shop_Black.ttf");

    }

    private void setTexts(){
        tv.setTextSize(18);
        tv.setTypeface(font,Typeface.BOLD);
        tv1.setTextSize(18);
        tv1.setTypeface(font,Typeface.BOLD);
    }

    public int generateRandomNumber() {

        Random rand = new Random();
        int randomNum = rand.nextInt((3600) + 1080);
        while(randomNum < 1000)
            randomNum = rand.nextInt((3600) + 1080);
        //Toast.makeText( getApplicationContext(), randomNum+"", Toast.LENGTH_SHORT).show();

        return randomNum;
    }

    public int repeatRandomNumber() {

        Random rand = new Random();
        int randomNum = rand.nextInt((10) + 1);

        return randomNum;
    }


    public void turn()
    {
        int random = generateRandomNumber();
        RotateAnimation anim = new RotateAnimation(0, 3000 ,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);

        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(4000);
        anim.setFillEnabled(true);
        //anim.setRepeatCount(repeatRandomNumber());
        anim.setRepeatMode(MODE_APPEND);
        anim.setFillAfter(true);
        sise.startAnimation(anim);
    }

    private void scaleAnimation(View view){

        mTextAnim = AnimationUtils.loadAnimation(this, R.anim.splash_anim_text);
        mTextAnim.setAnimationListener(this);
        tv.startAnimation(mTextAnim);

    }

    private void alphaAnimation(View view){

        mImgAnim = AnimationUtils.loadAnimation(this, R.anim.splash_anim_image);
        mImgAnim.setAnimationListener(this);
        tv1.startAnimation(mImgAnim);

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

    @Override
    public void onAnimationStart(Animation animation) {
        if(animation.equals(mImgAnim))
            scaleAnimation(tv1);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(animation.equals(mTextAnim)) {

            if(Build.VERSION.SDK_INT>=21 ){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Splash.this);
                Intent intent = new Intent(Splash.this, MenuSayfasi.class);
                startActivity(intent,options.toBundle());
            }else {
                Intent intent = new Intent(Splash.this, MenuSayfasi.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {}

    private void spanText(){


        tv = findViewById(R.id.tv);


        final SpannableStringBuilder sb = new SpannableStringBuilder("Ş İ Ş E");

        // Span to set text color to some RGB value
        final ForegroundColorSpan fcsRed = new ForegroundColorSpan(Color.rgb(205, 105, 201));
        final ForegroundColorSpan fcsGreen = new ForegroundColorSpan(Color.rgb(139, 71, 137));
        final ForegroundColorSpan fcsBlue = new ForegroundColorSpan(Color.rgb(0, 77, 64));
        final ForegroundColorSpan fsRed = new ForegroundColorSpan(Color.rgb(255, 95, 0));
        final ForegroundColorSpan fsGreen = new ForegroundColorSpan(Color.rgb(255, 64, 129));
        final ForegroundColorSpan fsBlue = new ForegroundColorSpan(Color.rgb(97, 0, 0));
        final ForegroundColorSpan Red = new ForegroundColorSpan(Color.rgb(87, 0, 149));
        final ForegroundColorSpan Green = new ForegroundColorSpan(Color.rgb(255, 185, 15));


        // Span to make text bold
        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);

        // Set the text color for first 4 characters
        sb.setSpan(fcsRed, 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // make them also bold
        sb.setSpan(bss, 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        // Set the text color for first 4 characters
        sb.setSpan(Green, 2, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // make them also bold
        sb.setSpan(bss, 2, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        // Set the text color for first 4 characters
        sb.setSpan(fsGreen, 4, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // make them also bold
        sb.setSpan(bss, 4, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        // Set the text color for first 4 characters
        sb.setSpan(fsRed, 6, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // make them also bold
        sb.setSpan(bss, 6, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);



        tv.setTextSize(24);
        tv.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        tv.setText(sb);
        //yourTextView.setText(sb);

    }


    private void spanText1(){


        tv1 = findViewById(R.id.tv1);

        final SpannableStringBuilder sb = new SpannableStringBuilder("Ç E V İ R M E C E");

        // Span to set text color to some RGB value
        final ForegroundColorSpan fcsRed = new ForegroundColorSpan(Color.rgb(205, 105, 201));
        final ForegroundColorSpan fcsGreen = new ForegroundColorSpan(Color.rgb(139, 71, 137));
        final ForegroundColorSpan fcsBlue = new ForegroundColorSpan(Color.rgb(0, 77, 64));
        final ForegroundColorSpan fsRed = new ForegroundColorSpan(Color.rgb(255, 95, 0));
        final ForegroundColorSpan fsGreen = new ForegroundColorSpan(Color.rgb(255, 64, 129));
        final ForegroundColorSpan fsBlue = new ForegroundColorSpan(Color.rgb(97, 0, 0));
        final ForegroundColorSpan Red = new ForegroundColorSpan(Color.rgb(87, 0, 149));
        final ForegroundColorSpan Green = new ForegroundColorSpan(Color.rgb(255, 185, 15));
        final ForegroundColorSpan Blue = new ForegroundColorSpan(Color.rgb(0, 77, 64));

        // Span to make text bold
        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);

        // Set the text color for first 4 characters
        sb.setSpan(fcsRed, 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // make them also bold
        sb.setSpan(bss, 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        // Set the text color for first 4 characters
        sb.setSpan(fcsGreen, 2, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // make them also bold
        sb.setSpan(bss, 2, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        // Set the text color for first 4 characters
        sb.setSpan(fcsBlue, 4, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // make them also bold
        sb.setSpan(bss, 4, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        // Set the text color for first 4 characters
        sb.setSpan(fsRed, 6, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // make them also bold
        sb.setSpan(bss, 6, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        // Set the text color for first 4 characters
        sb.setSpan(fsGreen, 8, 10, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // make them also bold
        sb.setSpan(bss, 8, 10, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        // Set the text color for first 4 characters
        sb.setSpan(fsBlue, 10, 12, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // make them also bold
        sb.setSpan(bss, 10, 12, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        // Set the text color for first 4 characters
        sb.setSpan(Red, 12, 14, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // make them also bold
        sb.setSpan(bss, 12, 14, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        // Set the text color for first 4 characters
        sb.setSpan(Green, 14, 16, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // make them also bold
        sb.setSpan(bss, 14, 16, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        // Set the text color for first 4 characters
        sb.setSpan(Blue, 16, 17, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // make them also bold
        sb.setSpan(bss, 16, 17, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        tv1.setTextSize(24);
        tv1.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
        tv1.setText(sb);
        //yourTextView.setText(sb);

    }



}


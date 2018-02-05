package com.example.moham.lastjoke;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.moham.lastjoke.auth.AuthinticationActivity;

import java.util.concurrent.TimeUnit;

public class Splachscreen extends AppCompatActivity {

    private TextView textView;
    private ImageView logo;
    private int couter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splach_screen);

        final String [] words={"Have","Fun","Its","LAST JOKE"};
        textView=findViewById(R.id.tv_logotext);
        logo=findViewById(R.id.logo);
        Handler handler=new Handler();
        final int dalytime= (int) TimeUnit.SECONDS.toMillis(2);
        final int dalytime1= (int) TimeUnit.SECONDS.toMillis(1);



     Thread thread=   new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                for(couter=0;couter<5;couter++)
                {

                    runOnUiThread(new Runnable() {
                        public void run() {
                                    if (couter<3) {
                                        YoYo.with(Techniques.Tada)
                                                .duration(1000)
                                                .playOn(textView);
                                        textView.setText(words[couter]);
                                    }else if (couter ==3)
                                        {
                                            textView.animate().alpha(0);
                                            logo.animate().alpha(1).setDuration(600);
                                            YoYo.with(Techniques.ZoomIn)
                                                    .duration(1200)
                                                    .playOn(logo);
                                            YoYo.with(Techniques.Tada)
                                                    .duration(1200)
                                                    .playOn(logo);
                                        }else if (couter ==4)
                                    {
                                        startActivity(new Intent(Splachscreen.this,AuthinticationActivity.class));
                                    }


                        }
                    });

                    try {
                        Thread.sleep(1300);

                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
thread.start();

    }
}

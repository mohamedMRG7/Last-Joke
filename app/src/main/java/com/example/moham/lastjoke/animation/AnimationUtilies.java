package com.example.moham.lastjoke.animation;

import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by moham on 2/4/2018.
 */

public class AnimationUtilies {



    public static void shake(View view)
    {
        YoYo.with(Techniques.Tada)
                .duration(1000)
                .playOn(view);
    }
    public static void zomein(View view)
    {
        YoYo.with(Techniques.Bounce)
                .duration(1000)
                .playOn(view);
    }
}

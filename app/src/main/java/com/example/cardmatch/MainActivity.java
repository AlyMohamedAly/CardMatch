package com.example.cardmatch;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageButton Cards[] = new ImageButton[8];
    final int BlurMe = Color.argb(255,50, 141, 168);
    int[] Locations = {0,0,0,0,0,0,0,0};
    boolean Pressed = false;

    public void PressMe(){ Pressed = (Pressed ? false : true); }

    public static int[] getRandomNumbers(){
        Random rand = new Random();
        int[] ar = {0,1,2,3,4,5,6,7};
        for (int i = 7; i > 0; i--)
        {
            int index = rand.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        Log.d("msg", "getRandomNumbers: Done");
        return ar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cards[0] = findViewById(R.id.imageButton1);
        Cards[1] = findViewById(R.id.imageButton2);
        Cards[2] = findViewById(R.id.imageButton3);
        Cards[3] = findViewById(R.id.imageButton4);
        Cards[4] = findViewById(R.id.imageButton5);
        Cards[5] = findViewById(R.id.imageButton6);
        Cards[6] = findViewById(R.id.imageButton7);
        Cards[7] = findViewById(R.id.imageButton8);

        int[] RandomArray = getRandomNumbers();
        int[] ImageIds = {R.drawable.apple, R.drawable.coconut, R.drawable.orange, R.drawable.milk};
        for (int i = 0; i < 8; i++){
            Cards[RandomArray[i]].setImageResource(ImageIds[i/2]);
            Locations[RandomArray[i]] = i/2;
            Cards[RandomArray[i]].setColorFilter(BlurMe);
            Cards[RandomArray[i]].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageButton TempPhoto = (ImageButton) v;
                    TempPhoto.clearColorFilter();
                }
            });
        }

    }
}

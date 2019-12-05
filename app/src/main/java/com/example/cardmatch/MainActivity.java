package com.example.cardmatch;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    long startTime = 0;

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            timerTextView.setText(String.format("%d:%02d", minutes, seconds));

            timerHandler.postDelayed(this, 500);
        }
    };

    ImageButton Cards[] = new ImageButton[8];
    final int BlurMe = Color.argb(255,50, 141, 168);
    HashMap<Integer,Integer> Location = new HashMap<Integer, Integer>();
    ImageButton PressedCard = null;
    boolean Pressed = false;

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

        timerTextView = findViewById(R.id.timerTextView);


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
            Location.put(Cards[RandomArray[i]].getId(), i/2);
            Cards[RandomArray[i]].setColorFilter(BlurMe);
            Cards[RandomArray[i]].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (ImageButton MyCards: Cards) { MyCards.setClickable(false); }

                    ImageButton TempPhoto = (ImageButton) v;

                    if (TempPhoto.getColorFilter() != null){
                        if (Pressed){
                            TempPhoto.clearColorFilter();
                            if (Location.get(TempPhoto.getId()) == Location.get(PressedCard.getId())){
                                Log.d("msg", "onClick: Here");
                            }else{
                                startTime = System.currentTimeMillis();
                                TempPhoto.clearColorFilter();
                                TempPhoto.setColorFilter(BlurMe);
                                PressedCard.setColorFilter(BlurMe);
                            }
                            Pressed = false;
                            PressedCard = null;
                        }else{
                            TempPhoto.clearColorFilter();
                            PressedCard = TempPhoto;
                            Pressed = true;
                        }
                    }
                    for (ImageButton MyCards: Cards) { MyCards.setClickable(true); }
                }
            });
            startTime = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable, 0);
        }
    }
}

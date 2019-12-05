package com.example.cardmatch;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

    public void openDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(getText(R.string.congratulations));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                getText(R.string.again),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                getText(R.string.Exit),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.setContentView(R.layout.dialog);
        alert11.show();
    }

    TextView timerTextView;
    long startTime = 0;
    long startTime2 = 0;
    int TimeTaken = 0;
    boolean TimerSleeping= true;
    int CardsDone = 0;
    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            Log.d("msg", "run1111: Hereeeeeee");
            Log.d("msg", "run222: " + startTime);
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            timerTextView.setText(String.format("%d:%02d", minutes, seconds));

            if (!TimerSleeping) {
                long millis2 = System.currentTimeMillis() - startTime2;
                int seconds2 = (int) (millis2 / 1000);
                seconds2 = seconds2 % 60;
                TimeTaken = seconds2;

                if (TimeTaken >= 1) {
                    TimerSleeping = true;
                    TimeTaken = 0;

                    for (ImageButton MyCards: Cards) { MyCards.setClickable(true); }
                    PressedCard.setColorFilter(BlurMe);
                    TempPhoto.setColorFilter(BlurMe);
                }
            }
            timerHandler.postDelayed(this, 500);
        }
    };

    ImageButton Cards[] = new ImageButton[8];
    final int BlurMe = Color.argb(255,50, 141, 168);
    HashMap<Integer,Integer> Location = new HashMap<Integer, Integer>();
    ImageButton PressedCard = null;
    ImageButton TempPhoto = null;
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

                    TempPhoto = (ImageButton) v;

                    if (TempPhoto.getColorFilter() != null){
                        if (Pressed){
                            TempPhoto.clearColorFilter();
                            if (Location.get(TempPhoto.getId()) == Location.get(PressedCard.getId())){
                                CardsDone++;
                                if (CardsDone == 4){
                                    timerHandler.removeCallbacks(timerRunnable);
                                    openDialog();
                                }
                            }else{
                                TempPhoto.clearColorFilter();
                                startTime2 = System.currentTimeMillis();
                                TimerSleeping = false;
                            }
                            Pressed = false;
                            if(TimerSleeping)
                                PressedCard = null;
                        }else{
                            TempPhoto.clearColorFilter();
                            PressedCard = TempPhoto;
                            Pressed = true;
                        }
                    }
                    if (TimerSleeping)
                        for (ImageButton MyCards: Cards) { MyCards.setClickable(true); }
                }
            });
            startTime = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable, 0);
        }
    }
}

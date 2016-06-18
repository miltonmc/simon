package br.com.inovant.genius;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Milton on 11/06/2016.
 */
public class RulesHandler {



    public static final String SCORE = "SCORE";
    public static final String TAG = "RulesHandler";
    public static final int TRANSACTION_TIME = 500;
    public static final int DURATION = 1000;
    private static Random rand = new Random();

    private ArrayList<LightButton> buttons = new ArrayList<LightButton>(4);
    private int score = 0;
    private String sequence = "";
    private String input = "";
    final Activity activity;

    public RulesHandler(final Activity activity) {
        this.activity = activity;
    }

    public void addButton(final LightButton button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compareSequence(button.getIndex());
            }
        });
        buttons.add(button);
        button.setIndex(buttons.indexOf(button));
    }

    public void compareSequence(int lastPressed){
        if (lastPressed == Integer.valueOf(sequence.charAt(input.length())+"")){
            input = input + lastPressed;
            if (input.length() == sequence.length()) {
                score = sequence.length();
                generateNextSequence();
                blink();
            }
        } else {
            showToast("You loose!\nYour score was: " + score);
            Intent resultIntent = new Intent();
            resultIntent.putExtra(SCORE,score);
            activity.setResult(Activity.RESULT_OK, resultIntent);
            activity.finish();
        }
    }

    public void generateNextSequence(){
        sequence = sequence + rand.nextInt(4);
        input = "";
    }

    private void freeze(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Log.e(TAG, "InterruptedException", e);
        }
    }

    public void blink(){
        new Thread() {
            @Override
            public void run() {
                input = "";
                for (final LightButton button: buttons) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            button.enableTouch(false);
                        }
                    });
                }

                for (final char charIndex: sequence.toCharArray()) {

                    freeze(TRANSACTION_TIME);

                    final int index = Integer.valueOf(charIndex+"");
                    final LightButton button =  buttons.get(index);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            button.enableLight(true);
                        }
                    });

                    freeze(DURATION);

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            button.enableLight(false);
                        }
                    });
                }

                for (final LightButton button: buttons) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("Now is your turn!");
                            button.enableTouch(true).enableClick(true);
                        }
                    });
                }
            }
        }.start(); //start
    }

    private void showToast(final String msg){
        final Toast toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 500);
    }
}
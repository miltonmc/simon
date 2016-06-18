package br.com.inovant.genius;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends WearableActivity {

    public static final String PREFS_NAME = "br.com.inovant.genius";
    public static final int SCORE = 1;
    private int highScore;
    private TextView mScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        // Restore preferences
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        highScore = settings.getInt("highScore", 0);

        mScore = (TextView) findViewById(R.id.score);
        mScore.setText(highScore + "");

        Button mStart = (Button) findViewById(R.id.start);
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main.this, Game.class);
                startActivityForResult(intent, SCORE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the request went well (OK) and the request was PICK_CONTACT_REQUEST
        if (resultCode == Activity.RESULT_OK && requestCode == SCORE) {
            int score = data.getIntExtra(RulesHandler.SCORE, 0);
            if(score > highScore){
                highScore = score;

                // We need an Editor object to make preference changes.
                // All objects are from android.context.Context
                final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("highScore", highScore);

                // Commit the edits!
                editor.commit();

                mScore.setText(highScore + "");
                showToast("New high score!");
            }
        }
    }

    private void showToast(final String msg){
        final Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 2000);
    }
}

package br.com.inovant.genius;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;

public class Game extends WearableActivity {

    private LightButton mYellow;
    private LightButton mBlue;
    private LightButton mGreen;
    private LightButton mRed;
    private RulesHandler rulesHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setAmbientEnabled();

        mYellow = (LightButton) findViewById(R.id.buttonYellow);
        mYellow.setLightColor(R.color.yellow_light).setDarkColor(R.color.yellow_dark);//.setId(YELLOW);

        mBlue = (LightButton) findViewById(R.id.buttonBlue);
        mBlue.setLightColor(R.color.blue_light).setDarkColor(R.color.blue_dark);//.setId(BLUE);

        mGreen = (LightButton) findViewById(R.id.buttonGreen);
        mGreen.setLightColor(R.color.green_light).setDarkColor(R.color.green_dark);//.setId(GREEN);

        mRed = (LightButton) findViewById(R.id.buttonRed);
        mRed.setLightColor(R.color.red_light).setDarkColor(R.color.red_dark);//.setId(RED);

        rulesHandler = new RulesHandler(this);
        rulesHandler.addButton(mYellow);
        rulesHandler.addButton(mBlue);
        rulesHandler.addButton(mGreen);
        rulesHandler.addButton(mRed);

        rulesHandler.generateNextSequence();

    }

    @Override
    protected void onResume() {
        super.onResume();
        rulesHandler.blink();
    }
}

package br.com.inovant.genius;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by Milton on 04/06/2016.
 */
public class LightButton extends Button {

//    public static final int YELLOW = 0;
//    public static final int BLUE = 0;
//    public static final int GREEN = 0;
//    public static final int RED = 0;

    private Integer lightColor;
    private Integer darkColor;
    private Integer index;
    private OnTouchListener onTouchListener;
    private OnClickListener onClickListener;

    public LightButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        LightButton.this.setBackgroundColor(getResources().getColor(lightColor));
                        break;
                    case MotionEvent.ACTION_UP:
                        LightButton.this.setBackgroundColor(getResources().getColor(darkColor));
                        break;
                }
                return false;
            }
        };
        enableTouch(true);
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        super.setOnClickListener(onClickListener);
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer id) {
        this.index = id;
    }

    public LightButton setLightColor(int lightColor) {
        this.lightColor = lightColor;
        return this;
    }

    public LightButton setDarkColor(int darkColor) {
        this.darkColor = darkColor;
        return this;
    }

    public LightButton enableTouch(final boolean enable){
        if (enable){
            setOnTouchListener(onTouchListener);
        } else {
            setOnTouchListener(null);
        }
        return this;
    }

    public LightButton enableClick(final boolean enable){
        if (enable){
            setOnClickListener(onClickListener);
        } else {
            setOnClickListener(null);
        }
        return this;
    }

    public LightButton enableLight(final boolean enable){
        if (enable){
            setBackgroundColor(getResources().getColor(lightColor));
        } else {
            setBackgroundColor(getResources().getColor(darkColor));
        }
        return this;
    }
}

package ir.mp.musicpad;

import android.content.Context;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by apple on 2/10/2017 AD.
 */

public class SoundButton extends ImageButton {
    public static final String TAG = "SOUND_BUTTON";

    private SoundPool mSoundPool;
    private int mSoundId;

    public SoundButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initButton();
    }
    public SoundButton(Context context ){
        super(context);
        initButton();
    }

    public void setSoundPool(SoundPool soundPool) {
        mSoundPool = soundPool;
    }

    public SoundPool getSoundPool() {
        return mSoundPool;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            Log.e(TAG, "onTouchEvent: "+String.valueOf(mSoundId));
            mSoundPool.play(mSoundId , 1, 1, 0,0,1);

            //// TODO: 2/11/17 :  it should be a better way to do that. search for performance.
            setBackgroundResource(R.drawable.sound_button_pressed);
            return true;
        }
        else if (event.getAction() == MotionEvent.ACTION_UP){
            setBackgroundResource(R.drawable.sound_button_default);
            return true;
        }

        return super.onTouchEvent(event);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        ViewGroup.LayoutParams layoutParams = getLayoutParams();
//        int width = MeasureSpec.getSize(widthMeasureSpec);
//        int height = MeasureSpec.getSize(heightMeasureSpec);
//        int dimension = Math.min(width , height);
//        setMeasuredDimension(dimension, dimension);
//    }

    public void setSoundId(int soundId) {
        mSoundId = soundId;
    }

    public int getSoundId() {
        return mSoundId;
    }

    private void initButton(){
        setBackgroundResource(R.drawable.sound_button_default);
    }
}


package ir.mp.musicpad;

import android.content.Context;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by apple on 2/10/2017 AD.
 */

public class SoundButton extends View {

    private SoundPool mSoundPool;

    public SoundButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public SoundButton(Context context ){
        super(context);
    }

    public void setSoundPool(SoundPool soundPool) {
        mSoundPool = soundPool;
    }

    public SoundPool getSoundPool() {
        return mSoundPool;
    }
}


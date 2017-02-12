package ir.mp.musicpad;

import android.app.DownloadManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MAIN_ACTIVITY";
    private DownloadManager downloadManager;

    private static String saved_sound_name = "cartoon001.wav";


//    private SparseArray<SoundPool> mSoundPools;
    private SparseArray<SoundButton> mButtonSparseArray;
//    private int[] mSoundIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSoundButtons();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mButtonSparseArray == null){
            initSoundButtons();
        }
        for (int i = 0; i < 12; i++) {
           SoundButton soundButton = mButtonSparseArray.get(i);
            if (soundButton.getSoundPool() == null){
                SoundPool soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
                int mSoundID = soundPool
                        .load(getFile(saved_sound_name).getPath(), 1);
                soundButton.setSoundId(mSoundID);
                soundButton.setSoundPool(soundPool);
            }
        }


    }

    //    static String downloadTestAddress = "http://dl.nex1music.ir/1395/11/17/Ali%20Sotoodeh%20-%20Hesse%20Tanhaei%20[128].mp3";




    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    private long downloadData (Uri uri) {

        long downloadReference;

        // Create request for android download manager
        downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        //Setting title of request
        request.setTitle("Data Download");

        //Setting description of request
        request.setDescription("Android Data download using DownloadManager.");

        //Set the local destination for the downloaded file to a path within the application's external files directory

        request.setDestinationInExternalFilesDir(MainActivity.this, Environment.DIRECTORY_DOWNLOADS,saved_sound_name);

        //Enqueue download and save into referenceId
        downloadReference = downloadManager.enqueue(request);


        return downloadReference;
    }
    public File getFile(String dbFilePath) {

        // Create full path
        String picturePath = Environment.DIRECTORY_DOWNLOADS.concat(File.separator).concat(
                dbFilePath);
        Log.e(TAG, "getFile: file_path " + picturePath );
        // Create file
        File mFile = getExternalFilesDir(picturePath);

        return mFile;
    }

    private void initSoundButtons(){
        if (mButtonSparseArray == null){
            mButtonSparseArray = new SparseArray<>(12);
        }
        for (int i = 0; i < 12; i++) {
            int buttonId = getResources().getIdentifier("sound_button_"+ String.valueOf(i+1), "id", getApplicationContext().getPackageName());
            // TODO: 2/11/17 : add buttons manually to onCreate(). its not good for performance.
            SoundButton soundButton = (SoundButton)findViewById(buttonId);
            mButtonSparseArray.put(i ,soundButton);
        }

        final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.buttons_frame_layout);
        ViewTreeObserver treeObserver = frameLayout.getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ButtonPlacer.ViewContainer viewContainer = new ButtonPlacer.ViewContainer();
                    frameLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    viewContainer.margin = ButtonPlacer.ViewContainer.getPixelFromDp(getResources() , 2);
                    viewContainer.buttons = mButtonSparseArray;
                    viewContainer.mFrameLayout = frameLayout;
                    viewContainer.height = frameLayout.getMeasuredHeight();
                    viewContainer.width = frameLayout.getMeasuredWidth();
                    ButtonPlacer.place(viewContainer , 4 , 3);
                }


            }
        });

    }

}
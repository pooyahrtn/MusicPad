package ir.mp.musicpad;

import android.app.DownloadManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MAIN_ACTIVITY";
    private DownloadManager downloadManager;


    private List<SoundPool> mSoundPools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSoundPools = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
            mSoundPools.add(soundPool);
        }

        if (isExternalStorageReadable()&& isExternalStorageWritable()){
            Log.e(TAG, "onCreate: accessable" );
            long id = downloadData(Uri.parse(downloadTestAddress));
            Log.e(TAG, "onCreate: id = " + String.valueOf(id) );
        }


    }



    static String downloadTestAddress = "http://dl.nex1music.ir/1395/11/17/Ali%20Sotoodeh%20-%20Hesse%20Tanhaei%20[128].mp3";


    public void save_sound(){
        DownloadManager.Request req = new DownloadManager.Request(Uri.parse("http://dl.nex1music.ir/1395/11/17/Ali%20Sotoodeh%20-%20Hesse%20Tanhaei%20[128].mp3"));
        req.setTitle("file tit").setDescription("Downloading ....") // download the package at the /sdcard/download path.
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "k_one"+ ".mp3");

    }

    public File getMusicStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }
        return file;
    }

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

        request.setDestinationInExternalFilesDir(MainActivity.this, Environment.DIRECTORY_DOWNLOADS,"AndroidTutorialPoint.mp3");

        //Enqueue download and save into referenceId
        downloadReference = downloadManager.enqueue(request);


        return downloadReference;
    }

}
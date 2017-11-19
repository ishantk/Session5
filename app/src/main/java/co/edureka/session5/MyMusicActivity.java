package co.edureka.session5;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MyMusicActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnPlay, btnStop;
    String songToPlay;

    MediaPlayer mediaPlayer;

    ConstraintLayout constraintLayout;

    ImageView imageView;


    // Check for the Internet
    boolean isInternetConnected(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo!=null && networkInfo.isConnected(); // -> true if we have internet connected

    }

    // Java Thread
    class MyThread extends Thread{

        @Override
        public void run() {

            // Do the background task here...

        }
    }

    // Android Thread
    class MyTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {

            // Do the background task here...

            return null;
        }
    }

    void initViews(){
        btnPlay = (Button)findViewById(R.id.buttonPlay);
        btnStop = (Button)findViewById(R.id.buttonStop);

        constraintLayout = (ConstraintLayout)findViewById(R.id.constraintLayout);

        imageView = (ImageView)findViewById(R.id.imageView);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.alpha_anim);
        Animation animation1 = AnimationUtils.loadAnimation(this,R.anim.rotate_anim);

        //constraintLayout.startAnimation(animation);
        btnPlay.startAnimation(animation);
        btnStop.startAnimation(animation1);


        AnimationDrawable drawable = (AnimationDrawable)imageView.getBackground();
        drawable.start();

        btnPlay.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        songToPlay = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Avicii_The_Nights.mp3";

        mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_music);
        initViews();

        Toast.makeText(this,"Internet Connected State: "+isInternetConnected(),Toast.LENGTH_LONG).show();

        MyThread th = new MyThread();
        th.start();

        MyTask task = new MyTask();
        task.execute();
    }

    void playMusic(){

        try{

            // From Local Storage
            //mediaPlayer.setDataSource(songToPlay);

            // From URL | Streaming
            Uri uri = Uri.parse("https://www.somemusicweb.com/somesong.mp3");
            mediaPlayer.setDataSource(this,uri);


            mediaPlayer.prepare();
            mediaPlayer.start();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    void pauseMusic(){

        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }

    }

    void stopMusic(){

        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
        }

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonPlay){
            playMusic();
        }else {
            stopMusic();
        }

    }
}

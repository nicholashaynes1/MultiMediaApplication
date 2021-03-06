package ctec.multimediaapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.media.MediaPlayer;
import android.content.Intent;
import android.view.View;
import android.graphics.Color;

public class AudioActivity extends Activity implements Runnable
{
    private RelativeLayout backGround;
    private Button playButton;
    private Button pauseButton;
    private Button stopButton;
    private Button videoButton;
    private SeekBar soundSeekBar;
    private MediaPlayer soundPlayer;
    private Thread soundThread;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        backGround = (RelativeLayout) findViewById(R.id.appBackground);
        videoButton = (Button) findViewById(R.id.videoButton);
        playButton = (Button) findViewById(R.id.playButton);
        pauseButton = (Button) findViewById(R.id.pauseButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        soundSeekBar = (SeekBar) findViewById(R.id.soundSeekBar);
        soundPlayer = MediaPlayer.create(this.getBaseContext(), R.raw.stokedandbroke);


        setupListeners();

        soundThread = new Thread(this);
        soundThread.start();
    }


    private void setupListeners()
    {
        playButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                soundPlayer.start();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                soundPlayer.pause();
            }


        });

        stopButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View CurrentView)
            {
                soundPlayer.stop();
                soundPlayer = MediaPlayer.create(getBaseContext(), R.raw.stokedandbroke);
            }
        });


        videoButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View currentView)
            {
                Intent myIntent = new Intent(currentView.getContext(), VideoActivity.class);
                startActivityForResult(myIntent, 0);

            }
        });


        soundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {


            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                if (fromUser)
                {
                    soundPlayer.seekTo(progress);
                } else
                {
                    changeColor();
                }
            }

        });


    }

        public void changeColor()
        {
            int redColor;
            int blueColor;
            int greenColor;

            redColor = (int) (Math.random() * 256);
            greenColor = (int) (Math.random() * 256);
            blueColor = (int) (Math.random() *  256);

            backGround.setBackgroundColor(Color.rgb(redColor, greenColor, blueColor));
        }












        public void run()
        {
            int currentPosition = 0;
            int soundTotal = soundPlayer.getDuration();
            soundSeekBar.setMax(soundTotal);

            while (soundPlayer != null && currentPosition < soundTotal)
            {
                try
                {
                    Thread.sleep(300);
                    currentPosition = soundPlayer.getCurrentPosition();
                }
                catch(InterruptedException soundException)
                {
                    return;
                }
                catch(Exception otherException)
                {
                    return;
                }
                soundSeekBar.setProgress(currentPosition);
            }
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu)
        {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_audio, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item)
        {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings)
            {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }


}

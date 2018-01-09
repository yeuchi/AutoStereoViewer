/*
 * Module:      Main activity of stereo viewer
 *
 * Description: 1. load 1st image pair - Andy
 *              2. start accelerometer to detect motion.
 *              3. start speechHelper to detect voice command when motion.
 *
 * accelerometer article
 * - Article: http://www.vogella.com/tutorials/AndroidSensor/article.html
 */
package com.ctyeung.autostereoviewer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.TextView;
import android.widget.Toast;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.ctyeung.autostereoviewer.data.ImageAssets;
import com.ctyeung.autostereoviewer.data.ImageFragment;
import com.ctyeung.autostereoviewer.utility.DistortImage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener
{
    private TextView textView;
    private int index=-1;
    protected SpeechRecognitionHelper speechHelper;
    private SensorManager sensorManager;
    private long lastUpdate;
    private int imageLength=0;
    private DistortImage distortImage;
    private boolean LEFT=true;
    private boolean RIGHT=false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // load images
        imageLength = getShortLenght();
        goLoad();

        // motion sensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();

        // speech recognition
        //initSpeechHelper();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }
    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = event.timestamp;
        if (accelationSquareRoot >= 1.3) //
        {
            if (actualTime - lastUpdate < 2000) {
                return;
            }
            lastUpdate = actualTime;
            goLoad();
        }
    }

    private void goLoad()
    {
        // load next image-pair
        index = (index<3)? index+1:0;
        loadImage(LEFT);
        loadImage(RIGHT);

        String name = ImageAssets.getNames().get(index);
        Toast.makeText(this, name, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private void initSpeechHelper()
    {
        Activity activity = (Activity) this;
        speechHelper = new SpeechRecognitionHelper();
        speechHelper.run(activity);
    }

    private int getShortLenght()
    {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);

        //float density  = getResources().getDisplayMetrics().density;
        int h = Math.round(outMetrics.heightPixels);// / density;
        int w  = Math.round(outMetrics.widthPixels);// / density;
        int shorter = (w<h)?w:h;
        int longer = (w<h)?h:w;

        int len = longer/2;
        return (len<shorter)?len:shorter;
    }

    private void loadImage(boolean isLeft)
    {
        ImageFragment headFragment = new ImageFragment();
        headFragment.setIndex(index);
        headFragment.setDirection(isLeft);
        headFragment.setLength(imageLength);

        int frameId = (isLeft)?
                        R.id.left_container:
                        R.id.right_container;

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(frameId, headFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        //int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // if it�s speech recognition results
        // and process finished ok
        if (requestCode == 1234 && resultCode == RESULT_OK) {

            // receiving a result in string array
            // there can be some strings because sometimes speech recognizing inaccurate
            // more relevant results in the beginning of the list
            ArrayList matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            CharSequence chars ="";

            // in �matches� array we holding a results... let�s show the most relevant
            if (matches.size() > 0) {
         //       if(null==textView)
         //           textView = (TextView)findViewById(R.id.tv_dictation);
         //       else
                    chars = textView.getText();

                String str = matches.get(0).toString();
                String first = str.substring(0,1).toUpperCase();
                str = first + str.substring(1, str.length());

                textView.setText(chars + " " + str + ". ");
                Toast.makeText(this, (CharSequence) matches.get(0), Toast.LENGTH_LONG).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}

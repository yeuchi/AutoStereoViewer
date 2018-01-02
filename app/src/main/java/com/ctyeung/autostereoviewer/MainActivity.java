package com.ctyeung.autostereoviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.ctyeung.autostereoviewer.data.ImageAssets;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private ImageView imageViewLeft;
    private ImageView imageViewRight;
    private TextView textView;
    private int index=0;
    protected SpeechRecognitionHelper speechHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // load images
        int len = getShortLenght();
        loadImages(len);

        // speech recognition
        initSpeechHelper();
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

    private void loadImages(int len)
    {
        imageViewLeft = (ImageView) findViewById(R.id.imageView_left);
        imageViewLeft.setImageResource(ImageAssets.getLefts().get(index));
        imageViewLeft.getLayoutParams().height = len;
        imageViewLeft.getLayoutParams().width = len;

        imageViewRight = (ImageView) findViewById(R.id.imageView_right);
        imageViewRight.setImageResource(ImageAssets.getRights().get(index));
        imageViewRight.getLayoutParams().height = len;
        imageViewRight.getLayoutParams().width = len;
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
                if(null==textView)
                    textView = (TextView)findViewById(R.id.tv_dictation);
                else
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

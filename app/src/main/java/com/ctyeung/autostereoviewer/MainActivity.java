package com.ctyeung.autostereoviewer;

import android.os.Bundle;
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

import com.ctyeung.autostereoviewer.data.ImageAssets;

public class MainActivity extends AppCompatActivity
{
    private ImageView imageViewLeft;
    private ImageView imageViewRight;
    private int index=0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int len = getShortLenght();
        loadImages(len);
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
}

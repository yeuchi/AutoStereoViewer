package com.ctyeung.autostereoviewer.data;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ctyeung.autostereoviewer.R;

/**
 * Created by ctyeung on 1/8/18.
 */

public class ImageFragment extends Fragment
{
    private ImageView imageView;
    private TextView textView;
    private View rootView;
    private int index = 0;
    private boolean isLeft = true;
    private int imageLength=0;


    public void setIndex(int index)
    {
        this.index = index;
    }

    public void setDirection(boolean isLeft)
    {
        this.isLeft = isLeft;
    }

    public void setLength(int len)
    {
        this.imageLength = len;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle saveInstanceState)
    {
        // load image
        rootView = inflater.inflate(R.layout.fragment_image, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.imageView);

        int resId = (isLeft)?
                ImageAssets.getLefts().get(index):
                ImageAssets.getRights().get(index);

        // size image to screen size
        imageView.setImageResource(resId);
        imageView.getLayoutParams().height = imageLength;
        imageView.getLayoutParams().width = imageLength;

        // apply barrel distortion
        // Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
        // Bitmap barrel = DistortImage.barrel(bitmap);
        // imageView.setImageBitmap(barrel);

        // load image name
        String name = ImageAssets.getNames().get(index);
        textView = (TextView) rootView.findViewById(R.id.txtName);
        textView.setText(name);

        return rootView;
    }
}

package com.ctyeung.autostereoviewer.data;

import java.util.ArrayList;
import java.util.List;
import com.ctyeung.autostereoviewer.R;
/**
 * Created by ctyeung on 1/1/18.
 */

public class ImageAssets
{
    final public static int NUM_IMAGES = 4;

    private static final List<Integer> lefts = new ArrayList<Integer>()
    {{
        add(R.drawable.andy_left_500);
        add(R.drawable.balloon_left_500);
        add(R.drawable.edgerton_left_500);
        add(R.drawable.strobel_left_500);
    }};

    private static final List<Integer> rights = new ArrayList<Integer>()
    {{
        add(R.drawable.andy_right_500);
        add(R.drawable.balloon_right_500);
        add(R.drawable.edgerton_right_500);
        add(R.drawable.strobel_right_500);
    }};

    private static final List<String> names = new ArrayList<String>()
    {{
        add("Professor Andrew Davidhazy");
        add("Balloon - flash");
        add("Papa Flash - Dr. Edgerton");
        add("The Sheek - Dr. Stroble");
    }};

    private static final List<Integer> all = new ArrayList<Integer>()
    {{
        addAll(lefts);
        addAll(rights);
    }};

    public static List<Integer> getLefts()
    {
        return lefts;
    }

    public static List<Integer> getRights()
    {
        return rights;
    }

    public static List<Integer> getAll()
    {
        return all;
    }

    public static List<String> getNames() {return names;}

    public static int count()
    {
        return lefts.size();
    }
}

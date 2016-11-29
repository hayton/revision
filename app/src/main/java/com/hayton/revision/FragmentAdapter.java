package com.hayton.revision;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;


/**
 * Created by hayton on 29/10/2016.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    public static ArrayList<String> imageUrl;
    public static ArrayList<Integer> defaultImage;

    public FragmentAdapter(FragmentManager fm, ArrayList<String> imageUrl, ArrayList<Integer> defaultImage){
        super(fm);
        this.imageUrl = imageUrl;
        this.defaultImage = defaultImage;
    }

    @Override
    public Fragment getItem(int position) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getCount() {
        return cseParams.init_num;
    }

    public static class MyFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState){

            View v = inflater.inflate(R.layout.img_fragment,null);
            NetworkImageView imageView = (NetworkImageView) v.findViewById(R.id.networkImage);

            if (imageUrl.size()!=0) {
                ImageLoader loader = VolleySingleton.getInstance(this.getContext()).getImageLoader();
                imageView.setImageUrl(imageUrl.get(getArguments().getInt("position")), loader);
            }

            imageView.setDefaultImageResId(defaultImage.get(getArguments().getInt("position")));


            return v;
        }

    }
}

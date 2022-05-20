package com.example.miwok;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private final int mBgColorResourceId;

    public WordAdapter(Context context, ArrayList<Word> words, int colorResourceId){
        super(context, 0, words);
        mBgColorResourceId = colorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        Word currWord = getItem(position);

        RelativeLayout listItemRelativeLayout = v.findViewById(R.id.list_item_relative_layout);
        ImageView image = v.findViewById(R.id.image);
        TextView defaultTextView = v.findViewById(R.id.default_text_view);
        TextView miwokTextView = v.findViewById(R.id.miwok_text_view);

        if(currWord.hasImage()) image.setImageResource(currWord.getImageResourceId());
        else image.setVisibility(View.GONE);
        listItemRelativeLayout.setBackgroundResource(mBgColorResourceId);
        defaultTextView.setText(currWord.getDefaultTranslation());
        miwokTextView.setText(currWord.getMiwokTranslation());

        return v;
    }
}

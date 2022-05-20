package com.example.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ColorsFragment extends Fragment {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener;

    public ColorsFragment(){
        //Empty Constructor
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer(){
        if(mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list, container, false);

        ListView listView = rootView.findViewById(R.id.list);

        ArrayList<Word> words = new ArrayList<>();

        words.add(new Word("red","weṭeṭṭi",R.drawable.color_red,R.raw.color_red));
        words.add(new Word("green","chokokki",R.drawable.color_green,R.raw.color_green));
        words.add(new Word("brown","ṭakaakki",R.drawable.color_brown,R.raw.color_brown));
        words.add(new Word("gray","ṭopoppi",R.drawable.color_gray,R.raw.color_gray));
        words.add(new Word("black","kululli",R.drawable.color_black,R.raw.color_black));
        words.add(new Word("white","kelelli",R.drawable.color_white,R.raw.color_white));
        words.add(new Word("dusty yellow","ṭopiisә",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        words.add(new Word("mustard yellow","chiwiiṭә",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));

        WordAdapter wordsAdapter = new WordAdapter(getActivity(), words, R.color.category_colors);

        listView.setAdapter(wordsAdapter);

        mAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        mAudioFocusChangeListener = focus -> {
            if(focus == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focus == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                //Temporary Loss of Audio Focus
                //Pause Playing
                mMediaPlayer.pause();
            }else if(focus == AudioManager.AUDIOFOCUS_GAIN){
                //Got AudioFocus back
                //Resume Playing
                mMediaPlayer.start();
            }else if(focus == AudioManager.AUDIOFOCUS_LOSS){
                //Permanent Loss of Audio Focus
                //Release Media Player
                releaseMediaPlayer();
            }
        };

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Word currWord = wordsAdapter.getItem(i);
            releaseMediaPlayer();
            int result = mAudioManager.requestAudioFocus(mAudioFocusChangeListener, AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                mMediaPlayer = MediaPlayer.create(getActivity(), currWord.getAudioResourceId());
                mMediaPlayer.setOnCompletionListener(mediaPlayer -> releaseMediaPlayer());
                mMediaPlayer.start();
            }
        });

        return rootView;
    }
}

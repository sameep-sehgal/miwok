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

public class NumbersFragment extends Fragment {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener;

    public NumbersFragment(){
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

        words.add(new Word("one","lutti",R.drawable.number_one,R.raw.number_one));
        words.add(new Word("two","otiiko",R.drawable.number_two,R.raw.number_two));
        words.add(new Word("three","tolookosu",R.drawable.number_three,R.raw.number_three));
        words.add(new Word("four","oyyisa",R.drawable.number_four,R.raw.number_four));
        words.add(new Word("five","massokka",R.drawable.number_five,R.raw.number_five));
        words.add(new Word("six","temmokka",R.drawable.number_six,R.raw.number_six));
        words.add(new Word("seven","kenekaku",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("eight","kawinta",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("nine","wo' e",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("ten","na' aacha",R.drawable.number_ten,R.raw.number_ten));


        WordAdapter wordsAdapter = new WordAdapter(getActivity(), words,R.color.category_numbers);

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

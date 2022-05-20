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

public class PhrasesFragment extends Fragment {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener;

    public PhrasesFragment(){
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

        words.add(new Word("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?","tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is..." ,"oyaaset...",R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?","michәksәs?",R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I’m feeling good.","kuchi achit",R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I’m coming.","hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        words.add(new Word("I’m coming.","әәnәm",R.raw.phrase_im_coming));
        words.add(new Word("Let’s go.","yoowutis",R.raw.phrase_lets_go));
        words.add(new Word("Come here.","әnni'nem",R.raw.phrase_come_here));


        WordAdapter wordsAdapter = new WordAdapter(getActivity(), words,R.color.category_phrases);

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

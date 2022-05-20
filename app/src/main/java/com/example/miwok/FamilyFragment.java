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

public class FamilyFragment extends Fragment {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener;

    public FamilyFragment(){
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

        words.add(new Word("father","әpә",R.drawable.family_father,R.raw.family_father));
        words.add(new Word("mother","әṭa",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word("son","angsi",R.drawable.family_son,R.raw.family_son));
        words.add(new Word("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Word("older sister","teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Word("youger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Word("grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Word("grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));


        WordAdapter wordsAdapter = new WordAdapter(getActivity(), words, R.color.category_family);

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

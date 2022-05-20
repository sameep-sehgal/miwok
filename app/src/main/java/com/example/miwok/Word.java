package com.example.miwok;

import androidx.annotation.NonNull;

public class Word {
    private final String mDefaultTranslation;
    private final String mMiwokTranslation;
    private int mImageResourceId = NO_IMAGE;
    private final int mAudioResourceId;

    private static final int NO_IMAGE = -1;

    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
    }

    public Word(String defaultTranslation, String miwokTranslation,int imageResourceId,int audioResourceId){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }

    public String getMiwokTranslation(){
        return mMiwokTranslation;
    }

    public int getImageResourceId(){
        return mImageResourceId;
    }

    public int getAudioResourceId(){
        return mAudioResourceId;
    }

    public boolean hasImage(){
        return mImageResourceId != NO_IMAGE;
    }

    @NonNull
    public String toString(){
        return mMiwokTranslation + "\n" + mDefaultTranslation;
    }
}

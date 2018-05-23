package com.skill.voice_vedio;

import static com.skill.voice_vedio.ConstantApp.VIDEO_PROFILES;

public class LocalEngineConfig {
    public int mUid;
    public String mChannel;

    public int mVideoProfile;
    public String mEncryptionKey;
    public String encryptionMode;

    public void reset() {
        mChannel = null;
    }

    LocalEngineConfig() {

        mVideoProfile = VIDEO_PROFILES[2];
        mEncryptionKey = "";
        encryptionMode="AES-128-XTS";
        mChannel = "1000";
        mUid = 200;



    }
}
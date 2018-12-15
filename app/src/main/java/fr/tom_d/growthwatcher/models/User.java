package fr.tom_d.growthwatcher.models;

import android.support.annotation.Nullable;

public class User {

    private String mUid;
    @Nullable
    private String mUsername;

    public User() { }

    public User(String uid, String username) {
        mUid = uid;
        mUsername = username;
    }

    // --- GETTERS ---
    public String getUid() { return mUid; }
    public String getUsername() { return mUsername; }

    // --- SETTERS ---
    public void setUsername(String username) { this.mUsername = username; }
    public void setUid(String uid) { this.mUid = uid; }
}
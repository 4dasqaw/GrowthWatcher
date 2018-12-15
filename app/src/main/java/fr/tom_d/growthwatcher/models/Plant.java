package fr.tom_d.growthwatcher.models;


import android.support.annotation.Nullable;
import java.util.ArrayList;

public class Plant {

    public String mNom;
    @Nullable
    public String mDesc;
    @Nullable public float mTemp, mHumi, mLumi;
    @Nullable public boolean mIsOk;
    protected ArrayList<Plant> mListe;

    public Plant(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Plant(String nom, String desc, float temp, float humi, float lumi, boolean isOk){
        this.mNom = nom;
        this.mDesc = desc;
        this.mTemp = temp;
        this.mHumi = humi;
        this.mLumi = lumi;
        this.mIsOk = isOk;
    }

    /*public Plant(Context context, String nom, String id){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mNom = nom;
        mPlantId = id;

        Resources res = context.getResources();
        final String[] noms = res.getStringArray(R.array.noms);
        final int[] ids = res.getIntArray(R.array.id);

        mListe = new ArrayList<Plant>();
        for (int i=0; i<noms.length; ++i){
            mListe.add(new Plant(context,noms[i],ids[i]));
        }
    }*/

    //GETTERS
    public String getNom() { return mNom; }
    public String getDesc() { return mDesc;}
    public float getTemp() { return mTemp; }
    public float getHumi() {return mHumi; }
    public float getLumi() {return mLumi;}
    public boolean getIsOk() { return mIsOk;}

    //SETTER
    public void setNom(String nom){mNom = nom;}
    public void setDesc(String desc) { mDesc = desc;}
    public void setTemp(float temp) { mTemp = temp; }
    public void setHumi(float humi) {mHumi = humi; }
    public void setLumi(float lumi) {mLumi = lumi;}
    public void setOk(boolean ok) { mIsOk = ok;}

}

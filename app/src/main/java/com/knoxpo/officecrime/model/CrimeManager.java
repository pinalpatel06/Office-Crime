package com.knoxpo.officecrime.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Tejas Sherdiwala on 11/24/2016.
 * &copy; Knoxpo
 */

public class CrimeManager {

    private static CrimeManager sCrimeManger;

    public static CrimeManager getInstance(Context context) {
        if (sCrimeManger == null) {
            sCrimeManger = new CrimeManager(context.getApplicationContext());
        }
        return sCrimeManger;
    }

    private Context mAppContext;
    private ArrayList<Crime> mCrimes;

    private CrimeManager(Context appContext) {
        mAppContext = appContext;
        mCrimes = new ArrayList<>();
    }

    public void addCrime(Crime crime) {
        mCrimes.add(crime);
    }

    public Crime getCrime(UUID id) {
        for (int i = 0; i < mCrimes.size(); i++) {
            Crime crime = mCrimes.get(i);
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }

    public int getCount() {
        return mCrimes.size();
    }

    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    public void removeCrime(UUID uuid) {
        Crime crime = getCrime(uuid);
        mCrimes.remove(crime);
    }
}
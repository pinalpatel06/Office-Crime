package com.knoxpo.officecrime.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Tejas Sherdiwala on 11/23/2016.
 * &copy; Knoxpo
 */

public class Crime implements Parcelable {

    private UUID mId;

    private String mTitle;
    private boolean mCrimeStatus;
    private Date mDateOfCrime;
    private String mCriminals;

    public Crime(){
        this(null,false,null,null);
    }

    public Crime(String title, boolean crimeStatus, Date dateOfCrime, String criminals) {
        mId = UUID.randomUUID();
        mTitle = title;
        mCrimeStatus = crimeStatus;
        mDateOfCrime = dateOfCrime;
        mCriminals = criminals;
    }

    protected Crime(Parcel in) {
        mTitle = in.readString();
        long time = in.readLong();
        if(time == -1){
            mDateOfCrime = null;
        }else{
            mDateOfCrime = new Date(time);
        }
        mCrimeStatus = in.readByte() != 0;
        mCriminals = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeLong(mDateOfCrime == null? -1 : mDateOfCrime.getTime());
        dest.writeByte((byte) (mCrimeStatus ? 1 : 0));
        dest.writeString(mCriminals);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Crime> CREATOR = new Creator<Crime>() {
        @Override
        public Crime createFromParcel(Parcel in) {
            return new Crime(in);
        }

        @Override
        public Crime[] newArray(int size) {
            return new Crime[size];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public boolean isCrimeStatus() {
        return mCrimeStatus;
    }

    public void setCrimeStatus(boolean crimeStatus) {
        mCrimeStatus = crimeStatus;
    }

    public Date getDateOfCrime() {
        return mDateOfCrime;
    }

    public void setDateOfCrime(Date dateOfCrime) {
        mDateOfCrime = dateOfCrime;
    }

    public String getCriminals() {
        return mCriminals;
    }

    public void setCriminals(String criminals) {
        mCriminals = criminals;
    }

    public UUID getId() {
        return mId;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof Crime){
            Crime crime = (Crime) obj;

            boolean result = crime.getTitle() == mTitle
                    && crime.isCrimeStatus() == mCrimeStatus;

            if(result){
                if(mDateOfCrime == null && crime.getDateOfCrime()==null)
                    return false;
                else if(mDateOfCrime != null && crime.getDateOfCrime() == null)
                    return false;
                else if(mDateOfCrime == null && crime.getDateOfCrime() !=null)
                    return false;
                else if(mDateOfCrime != null && crime.getDateOfCrime() !=null)
                    return mDateOfCrime.equals(crime.getDateOfCrime());
            }
            return result;
            }
        return false;
    }

    public boolean isNull(){
        boolean result = (mTitle.compareTo("")==0);
        return result;
    }
}

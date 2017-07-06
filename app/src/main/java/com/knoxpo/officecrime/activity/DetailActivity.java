package com.knoxpo.officecrime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.knoxpo.officecrime.R;
import com.knoxpo.officecrime.fragment.DetailFragment;
import com.knoxpo.officecrime.model.Crime;
import com.knoxpo.officecrime.model.CrimeManager;

import java.util.UUID;

/**
 * Created by Tejas Sherdiwala on 11/23/2016.
 * &copy; Knoxpo
 */

public class DetailActivity extends ToolbarFragmentActivity {

    private static final String
            TAG = DetailActivity.class.getSimpleName();

    public static final String
            EXTRA_CRIME_DATA = TAG + ".EXTRA_CRIME_DATA";

    private Crime mCrime;

/*
    @Override
    public Fragment getContentFragment() {
        mCrime = getIntent().getParcelableExtra(EXTRA_CRIME_DATA);
        return DetailFragment.newInstance(mCrime);
    }
*/

    @Override
    public Fragment getContentFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_DATA);
        return DetailFragment.newInstance(crimeId);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    /*@Override
    public void onBackPressed() {
        DetailFragment detailFragment = (DetailFragment)getSupportFragmentManager().findFragmentById(getContainerId());
        Intent intent = new Intent(getIntent());
        if(detailFragment.getResult().isNull()){
            setResult(RESULT_CANCELED);
        }else {
            intent.putExtra(EXTRA_CRIME_DATA, detailFragment.getResult());
            setResult(RESULT_OK, intent);
        }
        super.onBackPressed();
    }*/

    @Override
    public void onBackPressed() {
        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(getContainerId());
        Intent intent = new Intent();
        intent.putExtras(getIntent());

        Crime crime = detailFragment.getResult();

        if(crime.getTitle().isEmpty()){
            setResult(RESULT_CANCELED);
        }else{
            CrimeManager.getInstance(this).addCrime(crime);
            intent.putExtra(EXTRA_CRIME_DATA,crime.getId());
        }


        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}

package com.knoxpo.officecrime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.knoxpo.officecrime.fragment.MainFragment;

public class MainActivity extends ToolbarFragmentActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public Fragment getContentFragment() {
        return new MainFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d(TAG, "on Create");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "Result");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "New intent");
    }
}

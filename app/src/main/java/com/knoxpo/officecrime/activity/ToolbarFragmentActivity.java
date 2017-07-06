package com.knoxpo.officecrime.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.knoxpo.officecrime.R;

/**
 * Created by Tejas Sherdiwala on 11/23/2016.
 * &copy; Knoxpo
 */

public abstract class ToolbarFragmentActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    public abstract Fragment getContentFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(getContainerId());

        if(fragment==null){
            fragmentManager.beginTransaction().replace(getContainerId(),getContentFragment()).commit();
        }
        init();
        setSupportActionBar(mToolbar);
    }
    private void init(){
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
    }
    protected int getContainerId(){
        return R.id.fragment_container_toolbar;
    }
    protected int getLayoutId(){
        return R.layout.activity_toolbar_fragment;
    }
}

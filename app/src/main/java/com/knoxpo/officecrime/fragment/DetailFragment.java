package com.knoxpo.officecrime.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.knoxpo.officecrime.R;
import com.knoxpo.officecrime.model.Crime;
import com.knoxpo.officecrime.model.CrimeManager;

import java.util.Calendar;
import java.util.UUID;

/**
 * Created by Tejas Sherdiwala on 11/23/2016.
 * &copy; Knoxpo
 */

public class DetailFragment extends Fragment implements View.OnClickListener {
    private static final String
            TAG = DetailFragment.class.getSimpleName(),
            ARGUMENTS = TAG + ".ARGUMENTS",
            ARGS_CRIME_ID = TAG + ".ARGS_CRIME_ID",
            STATE_LIST = TAG + ".STATE_LIST";

    private static final int
            REQUEST_DATE = 0;
    public static final String
            EXTRA_YEAR = TAG + ".EXTRA_YEAR",
            EXTRA_MONTH = TAG + ".EXTRA_MONTH",
            EXTRA_DAY = TAG + ".EXTRA_DAY";

    public static DetailFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARGS_CRIME_ID, crimeId);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static DetailFragment newInstance(Crime crime) {
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENTS, crime);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private EditText mTitleET;
    private CheckBox mCrimeStatusCB;
    private Button mDateOfCrimeBtn, mCriminalBtn;

    private Crime mCrime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        //mCrime = args.getParcelable(ARGUMENTS);
        UUID crimeId = (UUID) args.getSerializable(ARGS_CRIME_ID);
        mCrime = CrimeManager.getInstance(getActivity()).getCrime(crimeId);
        if(mCrime == null){
            mCrime = new Crime();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        init(rootView);

        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_LIST)) {
            mCrime = savedInstanceState.getParcelable(STATE_LIST);
        }

        updateUI();
        mDateOfCrimeBtn.setOnClickListener(this);
        return rootView;
    }

   /* @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(STATE_LIST,mCrime);
        super.onSaveInstanceState(outState);
    }*/

    private void init(View view) {
        mTitleET = (EditText) view.findViewById(R.id.et_title);
        mCrimeStatusCB = (CheckBox) view.findViewById(R.id.cb_status);
        mDateOfCrimeBtn = (Button) view.findViewById(R.id.btn_date);
        mCriminalBtn = (Button) view.findViewById(R.id.btn_criminal);
    }


    private void updateUI() {
        mTitleET.setText(mCrime.getTitle());
        mCrimeStatusCB.setChecked(mCrime.isCrimeStatus());
        mDateOfCrimeBtn.setText(
                mCrime.getDateOfCrime() == null
                ?getString(R.string.btn_date)
                :mCrime.getDateOfCrime().toString()
        );
    }

    public Crime getResult() {
        mCrime.setTitle(mTitleET.getText().toString());
        mCrime.setCrimeStatus(mCrimeStatusCB.isChecked());
        return mCrime;
    }

    @Override
    public void onClick(View view) {
        DatePickerFragment datePicker = new DatePickerFragment().newInstance(mCrime.getDateOfCrime());
        datePicker.setTargetFragment(this, REQUEST_DATE);
        datePicker.show(getFragmentManager(), TAG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_DATE && resultCode == Activity.RESULT_OK) {
            int year = data.getIntExtra(EXTRA_YEAR, 0);
            int month = data.getIntExtra(EXTRA_MONTH, 0);
            int day = data.getIntExtra(EXTRA_DAY, 0);

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            mCrime.setDateOfCrime(calendar.getTime());
            updateUI();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

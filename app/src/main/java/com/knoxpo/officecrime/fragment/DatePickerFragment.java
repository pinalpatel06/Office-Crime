package com.knoxpo.officecrime.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Tejas Sherdiwala on 11/23/2016.
 * &copy; Knoxpo
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private final static String
            TAG = DatePickerFragment.class.getSimpleName(),
            ARGS_VAR = TAG + ".ARGS_VAR";
    public static DatePickerFragment newInstance(Date date) {

        Bundle args = new Bundle();
        DatePickerFragment fragment = new DatePickerFragment();
        if(date!=null){
            args.putLong(ARGS_VAR,date.getTime());
            fragment.setArguments(args);
        }
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        Calendar calendar = Calendar.getInstance();
        if(bundle!=null && bundle.containsKey(ARGS_VAR)){
            long date = bundle.getLong(ARGS_VAR);
            calendar.setTimeInMillis(date);
        }
        int year = calendar.get(Calendar.YEAR);
        int monnth = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),this,year,monnth,day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Intent intent = new Intent();
        intent.putExtra(DetailFragment.EXTRA_YEAR,year);
        intent.putExtra(DetailFragment.EXTRA_MONTH,month);
        intent.putExtra(DetailFragment.EXTRA_DAY,day);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);

    }
}

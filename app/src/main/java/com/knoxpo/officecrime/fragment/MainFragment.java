package com.knoxpo.officecrime.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.knoxpo.officecrime.R;
import com.knoxpo.officecrime.activity.DetailActivity;
import com.knoxpo.officecrime.model.Crime;
import com.knoxpo.officecrime.model.CrimeManager;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Tejas Sherdiwala on 11/23/2016.
 * &copy; Knoxpo
 */

public class MainFragment extends Fragment {
    private static final String
            TAG = MainFragment.class.getSimpleName(),
            STATE_LIST = TAG + ".STATE_LIST";

    private static final int
            REQUEST_DATA = 0,
            UPDATE_DATA = 1;

    public static final String
            EXTRA_POSITION = TAG + ".EXTRA_POSITION";

    private ArrayList<Crime> mCrimeList;
    private RecyclerView mItemRV;
    private CrimeAdapter mCrimeAdapter;
    private TextView mDataTV;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            startActivityForResult(intent, REQUEST_DATA);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        init(rootView);

        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_LIST)) {
            mCrimeList = savedInstanceState.getParcelableArrayList(STATE_LIST);
        }

        mItemRV.setAdapter(mCrimeAdapter);
        mItemRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STATE_LIST, mCrimeList);
        super.onSaveInstanceState(outState);
    }

    private void init(View view) {
        mItemRV = (RecyclerView) view.findViewById(R.id.rv_items);
        mDataTV = (TextView) view.findViewById(R.id.tv_no_data);
        mCrimeAdapter = new CrimeAdapter();
        //mCrimeList = new ArrayList<>();
        mCrimeList = CrimeManager.getInstance(getActivity()).getCrimes();
    }

    private void updateUI() {
        mDataTV.setVisibility(mCrimeList.isEmpty() ? View.VISIBLE : View.GONE);
        mItemRV.setVisibility(mCrimeList.isEmpty() ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Crime crime;

        if (requestCode == REQUEST_DATA && resultCode == Activity.RESULT_OK) {
            /*crime = data.getParcelableExtra(DetailActivity.EXTRA_CRIME_DATA);
            if (crime.getTitle().isEmpty()) {
                return;
            }*/
            UUID crimeId = (UUID) data.getSerializableExtra(DetailActivity.EXTRA_CRIME_DATA);
            crime = CrimeManager.getInstance(getActivity()).getCrime(crimeId);
            if(crime.getTitle()==null || crime.getTitle().trim().isEmpty()){
                CrimeManager.getInstance(getActivity()).removeCrime(crimeId);
                return;
            }
            //mCrimeList.add(crime);
            updateUI();
            mCrimeAdapter.notifyItemInserted(mCrimeList.indexOf(crime));
            // mCrimeAdapter.notifyDataSetChanged();
        } else if (requestCode == UPDATE_DATA && resultCode == Activity.RESULT_OK) {
            crime = data.getParcelableExtra(DetailActivity.EXTRA_CRIME_DATA);
            int position = data.getIntExtra(EXTRA_POSITION, -1);

            Crime existingCrime = mCrimeList.get(position);

            if (position != -1 && !crime.equals(existingCrime)) {
                mCrimeList.set(position, crime);
                mCrimeAdapter.notifyItemChanged(position);
            }
        } else {
            Log.d(TAG, "Same Crime data");
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeViewHolder> {
        private LayoutInflater mInflater;

        public CrimeAdapter() {
            mInflater = LayoutInflater.from(getActivity());
        }

        @Override
        public CrimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_crime_list, parent, false);
            return new CrimeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeViewHolder holder, int position) {
            holder.bindCrimeData(mCrimeList.get(position));
        }

        @Override
        public int getItemCount() {
            return mCrimeList.size();
        }
    }

    private class CrimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTV;
        private TextView mCrimeDateTV;
        private CheckBox mCrimeStatusCB;
        private Crime mCrime;

        public CrimeViewHolder(View itemView) {
            super(itemView);
            mTitleTV = (TextView) itemView.findViewById(R.id.tv_crime_title);
            mCrimeDateTV = (TextView) itemView.findViewById(R.id.tv_crime_date);
            mCrimeStatusCB = (CheckBox) itemView.findViewById(R.id.cb_crime_status);
        }

        public void bindCrimeData(Crime crime) {
            mTitleTV.setText(crime.getTitle());
            if (crime.getDateOfCrime() != null)
                mCrimeDateTV.setText(crime.getDateOfCrime().toString());
            mCrimeStatusCB.setChecked(crime.isCrimeStatus());
            itemView.setOnClickListener(this);
            mCrime = crime;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            //intent.putExtra(DetailActivity.EXTRA_CRIME_DATA, mCrime);
            intent.putExtra(DetailActivity.EXTRA_CRIME_DATA, mCrime.getId());
            intent.putExtra(EXTRA_POSITION, getAdapterPosition());
            startActivityForResult(intent, UPDATE_DATA);

        }
    }
}

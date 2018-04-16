package com.adiaz.deportesmadrid.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adiaz.deportesmadrid.R;
import com.adiaz.deportesmadrid.activities.CompetitionDetailsActivity;
import com.adiaz.deportesmadrid.adapters.CalendarAdapter;
import com.adiaz.deportesmadrid.adapters.ClassificationAdapter;
import com.adiaz.deportesmadrid.callbacks.ClassificationCallback;
import com.adiaz.deportesmadrid.retrofit.classification.ClassificationRetrofitEntity;
import com.adiaz.deportesmadrid.retrofit.matches.MatchRetrofitEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabClassification extends Fragment {

    @BindView(R.id.rv_classification)
    RecyclerView rvClassification;

    ClassificationCallback mClassificationCallback;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mClassificationCallback = (ClassificationCallback)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ClassificationCallback");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classification, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<ClassificationRetrofitEntity> classificationList = mClassificationCallback.queryClassificationList();
        String team = mClassificationCallback.underlineTeam();
        if (classificationList !=null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
            ClassificationAdapter classificationAdapter = new ClassificationAdapter(this.getContext(), classificationList, team);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvClassification.getContext(), layoutManager.getOrientation());
            rvClassification.setHasFixedSize(true);
            rvClassification.setLayoutManager(layoutManager);
            rvClassification.setAdapter(classificationAdapter);
            rvClassification.addItemDecoration(dividerItemDecoration);
            classificationAdapter.notifyDataSetChanged();
        }
    }
}
package com.example.apoorv.conferenceattendancetracker.ui.attendeeList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.apoorv.conferenceattendancetracker.R;
import com.example.apoorv.conferenceattendancetracker.data.model.Attendee;
import com.example.apoorv.conferenceattendancetracker.utils.mvp.BaseView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttendeeDisplayFragment extends BaseView implements AttendeeContract.View {

    @BindView(R.id.attendee_recycler_view)
    RecyclerView attendeeDisplayRecyclerView;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.tvPlaceholder)
    TextView tvPlaceholder;

    private RecyclerView.LayoutManager layoutManager;
    private AttendeeDisplayAdapter attendeeDisplayAdapter;
    private List<Attendee> attendeeList;
    private AttendeeContract.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AttendeePresenter(this);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendee_display, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attendeeList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getContext());
        attendeeDisplayRecyclerView.setLayoutManager(layoutManager);
        attendeeDisplayAdapter = new AttendeeDisplayAdapter(attendeeList, getContext());
        attendeeDisplayAdapter.notifyDataSetChanged();
        getAttendeeList();
        attendeeDisplayAdapter.notifyDataSetChanged();
    }

    private void getAttendeeList() {
        attendeeDisplayAdapter.notifyDataSetChanged();
        presenter.getAttendeeList(getContext().getApplicationContext());
        attendeeDisplayAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAttendeeList(List<Attendee> attendeeList) {
        this.attendeeList = attendeeList;
        attendeeDisplayAdapter.setNewList(attendeeList);
        attendeeDisplayRecyclerView.setAdapter(attendeeDisplayAdapter);
    }

    @Override
    public void shouldShowPlaceholderText() {
        if (attendeeList.isEmpty()) {
            tvPlaceholder.setVisibility(View.VISIBLE);
        } else {
            tvPlaceholder.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        presenter.onViewActive(this);
        super.onResume();
    }

    @Override
    public void onDestroy() {
        presenter.onViewInactive();
        super.onDestroy();
    }
}

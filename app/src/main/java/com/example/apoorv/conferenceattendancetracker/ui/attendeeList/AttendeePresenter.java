package com.example.apoorv.conferenceattendancetracker.ui.attendeeList;

import android.content.Context;

import com.example.apoorv.conferenceattendancetracker.R;
import com.example.apoorv.conferenceattendancetracker.data.interactor.AttendeeDataInteractor;
import com.example.apoorv.conferenceattendancetracker.data.model.Attendee;
import com.example.apoorv.conferenceattendancetracker.utils.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.List;


public class AttendeePresenter extends BasePresenter<AttendeeContract.View> implements AttendeeContract.Presenter {

    private AttendeeDataInteractor interactor;
    private List<Attendee> attendeeList = new ArrayList<>();

    public AttendeePresenter(AttendeeContract.View view) {
        this.view = view;
    }

    @Override
    public void getAttendeeList(final Context context) {
        if (view == null) {
            return;
        }
        interactor = new AttendeeDataInteractor(this, attendeeList);
        view.setProgressBar(true);
        interactor.retrieveUpdatedList(attendeeList);
        view.showAttendeeList(attendeeList);
    }

    @Override
    public void updateAttendeeList(boolean b, List<Attendee> attendeeList) {
        if (b == true) {
            view.showAttendeeList(attendeeList);
            view.shouldShowPlaceholderText();
            view.setProgressBar(false);
        } else {
            view.shouldShowPlaceholderText();
            view.showToastMessage(view.getContext().getResources().getString(R.string.server_error_message));
        }
    }

    public void clearAttendeeList() {
        attendeeList.clear();
    }
}

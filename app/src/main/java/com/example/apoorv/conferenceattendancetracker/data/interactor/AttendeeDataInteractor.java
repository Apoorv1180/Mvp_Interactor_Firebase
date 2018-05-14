package com.example.apoorv.conferenceattendancetracker.data.interactor;


import com.example.apoorv.conferenceattendancetracker.data.model.Attendee;
import com.example.apoorv.conferenceattendancetracker.ui.attendeeList.AttendeePresenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.example.apoorv.conferenceattendancetracker.utils.Properties.ATTENDEE_DATABASE;

public class AttendeeDataInteractor {

    private static final String TAG = AttendeeDataInteractor.class.getSimpleName();
    public List<Attendee> attendeeList;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private AttendeePresenter presenter;

    public AttendeeDataInteractor(AttendeePresenter presenter, List<Attendee> attendeeList) {
        this.presenter = presenter;
        this.attendeeList = attendeeList;
        initialiseFirebase();
    }

    private void initialiseFirebase() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(ATTENDEE_DATABASE);
        mFirebaseDatabase.getReference().keepSynced(true);
    }

    public void retrieveUpdatedList(final List<Attendee> attendeeList) {
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                presenter.clearAttendeeList();

                for (DataSnapshot attendee : dataSnapshot.getChildren()) {

                    attendeeList.add(new Attendee(attendee.getValue(Attendee.class).getId(),
                            attendee.getValue(Attendee.class).getName(),
                            attendee.getValue(Attendee.class).getCompany(),
                            attendee.getValue(Attendee.class).getType(),
                            attendee.getValue(Attendee.class).getCheckedIn()));
                }
                presenter.updateAttendeeList(true, attendeeList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                presenter.updateAttendeeList(false, attendeeList);
            }
        });
    }

}



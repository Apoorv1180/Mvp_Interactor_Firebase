package com.example.apoorv.conferenceattendancetracker.data.interactor;

import com.example.apoorv.conferenceattendancetracker.data.model.Attendee;
import com.example.apoorv.conferenceattendancetracker.ui.qrscanner.QRPresenter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.apoorv.conferenceattendancetracker.utils.Properties.ATTENDEE_DATABASE;
import static com.example.apoorv.conferenceattendancetracker.utils.Properties.BASEURL;

public class QRDataInteractor {
    private static final String TAG = QRDataInteractor.class.getSimpleName();
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private QRPresenter presenter;

    public QRDataInteractor(QRPresenter presenter) {
        this.presenter = presenter;
        initialiseFirebase();
    }

    private void initialiseFirebase() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(ATTENDEE_DATABASE);
        mFirebaseDatabase.getReference().keepSynced(true);
    }

    public boolean updateMyData(String updatedId, Attendee updatedData) {
        try {
            mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(BASEURL + ATTENDEE_DATABASE + "/" + updatedId);
            mDatabaseReference.setValue(updatedData);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

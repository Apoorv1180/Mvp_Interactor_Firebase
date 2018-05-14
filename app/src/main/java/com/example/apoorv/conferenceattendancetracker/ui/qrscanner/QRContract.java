package com.example.apoorv.conferenceattendancetracker.ui.qrscanner;

import com.example.apoorv.conferenceattendancetracker.data.model.Attendee;
import com.example.apoorv.conferenceattendancetracker.utils.mvp.IBasePresenter;
import com.example.apoorv.conferenceattendancetracker.utils.mvp.IBaseView;
import com.google.zxing.integration.android.IntentResult;


interface QRContract {

    interface View extends IBaseView {
        void shouldFinishActivity();
    }

    interface Presenter extends IBasePresenter<View> {
        void getQRScanner();

        void makeMyUpdatedObject(IntentResult result);

        void updateMyStatus(Attendee updatedData, String updatedId);

        void doErrorHandling(Exception e);
    }
}
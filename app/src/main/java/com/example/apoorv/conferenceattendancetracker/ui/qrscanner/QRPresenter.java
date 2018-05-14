package com.example.apoorv.conferenceattendancetracker.ui.qrscanner;

import android.app.Activity;

import com.example.apoorv.conferenceattendancetracker.R;
import com.example.apoorv.conferenceattendancetracker.data.interactor.QRDataInteractor;
import com.example.apoorv.conferenceattendancetracker.data.model.Attendee;
import com.example.apoorv.conferenceattendancetracker.utils.mvp.BasePresenter;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class QRPresenter extends BasePresenter<QRContract.View> implements QRContract.Presenter {
    private IntentIntegrator qrScan;
    private Attendee updatedData;
    private String updatedId;
    private JSONObject obj = null;
    private QRDataInteractor interactor;

    public QRPresenter(QRContract.View view) {
        this.view = view;
        interactor = new QRDataInteractor(this);
    }

    @Override
    public void getQRScanner() {
        qrScan = new IntentIntegrator((Activity) view.getContext());
        qrScan.initiateScan();
    }

    @Override
    public void makeMyUpdatedObject(IntentResult result) {
        try {
            obj = new JSONObject(result.getContents());
            updatedData = new Attendee(obj.getString("id"), obj.getString("name"), obj.getString("company"), obj.getString("type"), obj.getBoolean("checkedIn"));
            updatedId = obj.getString("id");
            updateMyStatus(updatedData, updatedId);
        } catch (JSONException e) {
            e.printStackTrace();
            doErrorHandling(e);
        }
    }

    @Override
    public void updateMyStatus(Attendee updatedData, String updatedId) {
        boolean value = interactor.updateMyData(updatedId, updatedData);
        if (value == true) {
            view.showToastMessage(updatedData.getName() + " Checked In!!");
            view.shouldFinishActivity();
        } else
            view.shouldFinishActivity();
    }

    @Override
    public void doErrorHandling(Exception e) {
        e.printStackTrace();
        view.showToastMessage(view.getContext().getResources().getString(R.string.scan_error));
        view.shouldFinishActivity();
    }
}

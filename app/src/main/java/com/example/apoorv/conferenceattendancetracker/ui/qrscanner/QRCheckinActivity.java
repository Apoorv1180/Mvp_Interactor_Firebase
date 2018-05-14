package com.example.apoorv.conferenceattendancetracker.ui.qrscanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.apoorv.conferenceattendancetracker.R;
import com.example.apoorv.conferenceattendancetracker.utils.mvp.BaseViewActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QRCheckinActivity extends BaseViewActivity implements QRContract.View {

    /*
        Variables
        qrScan -- refers to IntentIntegrator required to resolve the scan of QR Code
        mDatabaseReference -- refers to the reference to the api end point where CRUD operations takes place
        updatedData -- refers to the scanned object of Attendee needed to be updated
        obj -- JSONObject returned as result of QR CODE Scan
     */

   QRPresenter qrPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcheckin);
        qrPresenter = new QRPresenter(this);
        qrPresenter.getQRScanner();
    }

    /*
    @funtion onActivity result called as a result of QR Scan
    @result --  contains the result after scan
    @result is then handled for null and not null conditions so as to update the Attendee object or show an error message
    and hence return to the main activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                finish();
            } else {
                qrPresenter.makeMyUpdatedObject(result);
            }
        } else {
            finish();
        }
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void shouldFinishActivity() {
        finish();
    }

    @Override
    public void onResume() {
        qrPresenter.onViewActive(this);
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        qrPresenter.onViewInactive();
        super.onDestroy();
    }
}

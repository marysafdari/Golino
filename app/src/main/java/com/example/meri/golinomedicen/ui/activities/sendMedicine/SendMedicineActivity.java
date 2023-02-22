package com.example.meri.golinomedicen.ui.activities.sendMedicine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.zarinpal.ewallets.purchase.PaymentRequest;
import com.zarinpal.ewallets.purchase.ZarinPal;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.meri.golinomedicen.R;
import com.example.meri.golinomedicen.application.AppController;
import com.example.meri.golinomedicen.ui.activities.MyActivity;
import com.example.meri.golinomedicen.ui.activities.history.HistoryActivity;
import com.example.meri.golinomedicen.utility.ActivityUtils;

public class SendMedicineActivity extends MyActivity implements NavigationView
        .OnNavigationItemSelectedListener {

    //region Variables
    private static final String TAG = SendMedicineActivity.class.getSimpleName();

    private SendMedicinePresenter presenter;

    //region widgets
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView nav_view;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    //endregion

    //endregion

    //region LifeCycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_medicine);

        ButterKnife.bind(this);

        initialize();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    private void initialize() {
        setSupportActionBar(toolbar);
        nav_view.setNavigationItemSelectedListener(this);

        Uri data = getIntent().getData();
        ZarinPal.getPurchase(this).verificationPayment(data, (isPaymentSuccess, refID, paymentRequest) -> {
            Log.e(TAG, "onCallbackResultVerificationPayment: " + isPaymentSuccess + " ," + refID);
            if (isPaymentSuccess)
                Toast.makeText(this, "پرداخت با موفقیت انجام شد" + refID, Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "پرداخت انجام نشد", Toast.LENGTH_SHORT).show();

        });

        setupMVP();
    }

    private void setupMVP() {

        SendMedicineFragment fragment = (SendMedicineFragment) getSupportFragmentManager().findFragmentById
                (R.id.content_frame);

        if (fragment == null) {
            fragment = SendMedicineFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.content_frame);
        }


        presenter = (SendMedicinePresenter) getLastCustomNonConfigurationInstance();
        if (presenter == null)
            presenter = new SendMedicinePresenter(fragment, AppController.getInstance().getDataModel());
        presenter.attachView(fragment);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.END)) drawer.closeDrawers();
        else super.onBackPressed();
    }

    //endregion

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_navigation:
                drawer.openDrawer(Gravity.END);
                break;
            case R.id.btn_send_drug:
                Toast.makeText(this, "ارسال نسخه", Toast.LENGTH_SHORT).show();
                break;


        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        Log.e(TAG, "onNavigationItemSelected: " + item.getItemId());

        drawer.closeDrawers();
        item.setChecked(true);

        switch (item.getItemId()) {
            case R.id.order_drug:
                Toast.makeText(this, "سفارش دارو", Toast.LENGTH_SHORT).show();
                break;
            case R.id.order_flower:
                Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://Golbishe.com"));
                startActivity(browser);
                break;
            case R.id.history_order:
                startActivity(new Intent(this, HistoryActivity.class));
                break;
            case R.id.increase_credit:
                myPayment();
                break;
        }


        return true;
    }

    private void myPayment() {

        ZarinPal p = ZarinPal.getPurchase(this);
        PaymentRequest pp = ZarinPal.getPaymentRequest();

        pp.setMerchantID("06628f6a-0c8e-11e8-a3f6-005056a205be");
        pp.setAmount(100);//  unit : Toman
        pp.setDescription("پرداخت تست");
        pp.setCallbackURL("return://zarinpalpayment");

        Log.e(TAG, "myPayment: ");

        p.startPayment(pp, (status, authority, paymentGatewayUri, intent) -> {
            Log.e(TAG, "onCallbackResultPaymentRequest: " + status + ", authority= " + authority + ",uri= "
                    + paymentGatewayUri + " ,intent= " + intent);
            if (status == 100) startActivity(intent);
            else Toast.makeText(this, "خطا در پرداخت", Toast.LENGTH_SHORT).show();
        });


    }


}

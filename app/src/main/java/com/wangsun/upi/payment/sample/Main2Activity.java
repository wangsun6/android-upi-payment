package com.wangsun.upi.payment.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wangsun.upi.payment.UpiPayment;
import com.wangsun.upi.payment.model.PaymentDetail;
import com.wangsun.upi.payment.model.TransactionDetails;

import org.jetbrains.annotations.NotNull;

public class Main2Activity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        findViewById(R.id.id_pay_using_upi_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startUpiPayment();
            }
        });
    }


    private void startUpiPayment() {
        PaymentDetail payment = new PaymentDetail("wangsunhakhun@oksbi","Wangsun Hakhun",
                "","", "description","2.00");

        new UpiPayment(this)
                .setPaymentDetail(payment)
                .setUpiApps(UpiPayment.getUPI_APPS())
                .setCallBackListener(new UpiPayment.OnUpiPaymentListener() {
                    @Override
                    public void onSubmitted(@NotNull TransactionDetails data) {
                        Toast.makeText(Main2Activity.this, "transaction pending: " + data, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(@NotNull String message) {
                        Toast.makeText(Main2Activity.this, "transaction failed: " + message, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(@NotNull TransactionDetails data) {
                        Toast.makeText(Main2Activity.this, "transaction success: " + data.toString(), Toast.LENGTH_LONG).show();
                    }
                }).pay();
    }
}

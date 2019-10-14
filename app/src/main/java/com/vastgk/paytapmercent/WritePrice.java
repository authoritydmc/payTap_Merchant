package com.vastgk.paytapmercent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class WritePrice extends AppCompatActivity {
    public static String TOTAL_AMOUNT_TO_WRITE="amount_total_write";
    private String VENDOR_NAME="PAYTAP ";
    private String VENDORD_ID="VEN4578";
private TextView dataTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_price);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dataTxtView=findViewById(R.id.write_nfc_data_txtView);
        String data_to_written= String.format("Vendor Name:%s| VENDOR ID=%s|AMOUNT=%s|CUST_ID=%s", VENDOR_NAME, VENDORD_ID, String.valueOf(getIntent().getDoubleExtra(TOTAL_AMOUNT_TO_WRITE,0.0)),"124578");

        dataTxtView.setText(data_to_written);

        Toast.makeText(this, "Touch the NFC tag to complete the writing", Toast.LENGTH_SHORT).show();
    }
}

package com.vastgk.paytapmercent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class WritePrice extends AppCompatActivity {
    public static String TOTAL_AMOUNT_TO_WRITE="amount_total_write";
    private String VENDOR_NAME="PAYTAP ";
    private String VENDORD_ID="VEN4578";
private TextView dataTxtView;
    private String CUSTOMER_ID="cust565447"; //TODO users mobile number

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_price);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dataTxtView=findViewById(R.id.write_nfc_data_txtView);
        Map map=new LinkedHashMap();
        map.put("VendorName",VENDOR_NAME);
        map.put("VendorId",VENDORD_ID);
        map.put("Amount",getIntent().getStringExtra(TOTAL_AMOUNT_TO_WRITE));
        map.put("Cust_ID",CUSTOMER_ID);
        JSONObject datas=new JSONObject(map);
        dataTxtView.setText(datas.toString());



        Toast.makeText(this, "Touch the NFC tag to complete the writing", Toast.LENGTH_SHORT).show();
    }
}

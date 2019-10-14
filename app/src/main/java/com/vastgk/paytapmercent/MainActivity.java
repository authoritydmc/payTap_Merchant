package com.vastgk.paytapmercent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Random;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;



////======================================== rohan kumar =============================================================

/* this project is brought to by Nikmaa TEAM*/

//this is added BY RAj dubey

public class MainActivity extends AppCompatActivity {
//<<<<<<< master
    private static final int MY_REQUEST_CODE_CAMERA = 9;
    private static  double TOTAL_PRICE = 0.0;
    //added TOTAL_PRICE variable @beast
    HashMap<String, Double> ItemMap_Price = new HashMap<>();
    HashMap<String, Integer> ItemMap_Quan = new HashMap<>();
    private final int CODE_SCAN = 7;
//=====
//     private static final int MY_REQUEST_CODE_CAMERA =9 ;
//     private double TOTAL_PRICE=0.0;
//     HashMap<String,Double> ItemMap_Price=new HashMap<>();
//     HashMap<String,Integer> ItemMap_Quan=new HashMap<>();
// private final int CODE_SCAN=7;
// >>>>>>> master

    private static final String TAG = "RAAJ";
    Random random = new Random();
    ListView itemView;
    TextView DetailsTxtView;
    Button scanCode, writePrice;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemView = findViewById(R.id.lstViewItems);
        DetailsTxtView = findViewById(R.id.txtView_DetailsItem);
//        scanCode = findViewById(R.id.Btn_ScanCode);
//        writePrice = findViewById(R.id.Btn_writePrice);
        bottomNavigationView = findViewById(R.id.bottomnavigation);

        //bottom Navigation ----------RK-------------

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Intent intent;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.activity_main_bottomnavigation_bank:
                        intent = new Intent(MainActivity.this,ActivityBank.class);
                        break;
                    case R.id.activity_main_bottomnavigation_scanner:
                        scan();
                        break;
                    case R.id.activity_main_bottomnavigation_nfc:
                        write();
                        Toast.makeText(MainActivity.this, "Yet To Be Implemented", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

    }

    private void write() {
        Toast.makeText(MainActivity.this, "To be Implemented ", Toast.LENGTH_SHORT).show();
    }

    private void scan() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_REQUEST_CODE_CAMERA);
        } else {
            RUNSCAN();
        }




            }


//         writePrice.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 Toast.makeText(MainActivity.this,"To be Implemented ",Toast.LENGTH_SHORT).show();
//            startActivity( new Intent(MainActivity.this,WritePrice.class).putExtra(WritePrice.TOTAL_AMOUNT_TO_WRITE,TOTAL_PRICE));
//             }
//         });




    // get the barcode reader instance

    void Refreshdata() {
        ArrayList<String> details = new ArrayList<>();
        double total_price = 0.0;
        for (String it : ItemMap_Price.keySet()) {
            total_price += (ItemMap_Quan.get(it) * ItemMap_Price.get(it));
            String st = "Item:" + it
                    + "\tPrice: "
                    + ItemMap_Quan.get(it) + "x" + ItemMap_Price.get(it)
                    + "=" + (ItemMap_Quan.get(it) * ItemMap_Price.get(it));
            details.add(st);
        }
        TOTAL_PRICE=total_price;


        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, details);
        itemView.setAdapter(arrayAdapter);

        DetailsTxtView.setText("Total Item " + details.size() + " Amounting Rs. " + total_price);
    }

    private void RUNSCAN() {
        Intent in = new Intent(MainActivity.this, Scan.class);
        startActivityForResult(in, CODE_SCAN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CODE_SCAN) {
            String ITEM_CODE = data.getStringExtra("code");


            if (ItemMap_Price.containsKey(ITEM_CODE)) {

                ItemMap_Quan.put(ITEM_CODE, ItemMap_Quan.get(ITEM_CODE) + 1);
                // ItemMap_Price.put(ITEM_CODE,ItemMap_Price.get(ITEM_CODE)*2);


            } else {
                Double pr = random.nextDouble() * 50;
                ItemMap_Quan.put(ITEM_CODE, 1);
                ItemMap_Price.put(ITEM_CODE, Double.valueOf(String.format("%.2f", pr)));
            }
            Refreshdata();

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_REQUEST_CODE_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(getApplicationContext(), "Granted Permission ", Toast.LENGTH_SHORT).show();
                    RUNSCAN();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(), "NO CAMREA PERMISSION ", Toast.LENGTH_LONG).show();
                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


}
package com.vastgk.paytapmercent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;

import android.Manifest;
import android.content.ClipData;
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




public class MainActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE_CAMERA = 9;
    private static  double TOTAL_PRICE = 0.0;
    private HashMap<String,ItemDetails> Items=new HashMap<>();//Store each info
    private final int CODE_SCAN = 7;

    private static final String TAG = "RAAJ";
    Random random = new Random();
    RecyclerView itemView;
    TextView DetailsTxtView;
    Button scanCode, writePrice;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemView = findViewById(R.id.lstViewItems);
        DetailsTxtView = findViewById(R.id.txtView_DetailsItem);

        bottomNavigationView = findViewById(R.id.bottomnavigation);

        //bottom Navigation ----------RK-------------

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            Intent intent;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {

                    case R.id.activity_main_bottomnavigation_scanner:
                        scan();
                        break;
                    case R.id.activity_main_bottomnavigation_nfc:
                        write();
                        break;
                }
                return true;
            }
        });

    }

    private void write() {
        startActivity(new Intent(MainActivity.this,WritePrice.class).putExtra(WritePrice.TOTAL_AMOUNT_TO_WRITE,String.format("%.2f",TOTAL_PRICE)));
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

void ChangeQty(String itemCode,int qty)
{
    if (qty==0)
    {
        Items.remove(itemCode);
    }else
    {
        Items.get(itemCode).setItemQuantity(qty);
    }
    Refreshdata();
}
void DeleteItem(String itemCode)
{
    ChangeQty(itemCode,0);
}

    void Refreshdata() {
        ArrayList<ItemDetails> details = new ArrayList<>();
        TOTAL_PRICE=0.0;

        for(String codes:Items.keySet())
        {

            details.add(Items.get(codes));
            TOTAL_PRICE+=Items.get(codes).getItemQuantity()*Float.valueOf( Items.get(codes).getItemPrice());

        }
        ItemRecyclerAdapter myadapter=new ItemRecyclerAdapter(MainActivity.this,details);
        itemView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        itemView.setHasFixedSize(true);
        itemView.setAdapter(myadapter);


        DetailsTxtView.setText("Total Item " + details.size() + String.format("%50s \u20B9%.2f","Amounting",TOTAL_PRICE));
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


            if (Items.containsKey(ITEM_CODE)) {
                Items.get(ITEM_CODE).setItemQuantity(Items.get(ITEM_CODE).getItemQuantity()+1);

            } else {//TODO fetch Details and Put from firebase
                float pr = random.nextFloat()*100;
                String price=String.format("%.2f",pr);
                TOTAL_PRICE+=Float.valueOf(price);
                String itemName=ITEM_CODE.equals("725272730706")?"Parle G":ITEM_CODE;
                ItemDetails itemDetails=new ItemDetails("URL",itemName,ITEM_CODE,price,1);
                Items.put(ITEM_CODE,itemDetails);


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
package com.vastgk.paytapmerchant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.ToneGenerator;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.skyfishjy.library.RippleBackground;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class WritePrice extends AppCompatActivity {
    private static final String TAG ="ERROS" ;
    private  String datas;
    public static String TOTAL_AMOUNT_TO_WRITE="amount_total_write";
    private String VENDOR_NAME="PAYTAP ";
    private String VENDORD_ID="VEN007";
private TextView nfc_status;
private NFCManager nfcManager;
private  NdefMessage ndefMessage;
Tag currentTag;
    private String CUSTOMER_ID="cust565447"; //TODO users mobile number

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_price);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       nfc_status=findViewById(R.id.write_nfc_status);
        nfcManager=new NFCManager(this);
                Map map=new LinkedHashMap();
        map.put("vendorname",VENDOR_NAME);
        map.put("vendorid",VENDORD_ID);
        map.put("amount",getIntent().getStringExtra(TOTAL_AMOUNT_TO_WRITE));
        map.put("custid",CUSTOMER_ID);
        map.put("paid","false");
        JSONObject data=new JSONObject(map);
        datas=data.toString();
        ndefMessage=nfcManager.createTextMessage(datas);

        nfc_status.setText("\tTouch the NFC tag  ...");
        if(ndefMessage!=null)
        {
           // Toast.makeText(this, "Tag an NFC tag to write", Toast.LENGTH_SHORT).show();
        }



    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startNfcAnimation(true);
        try {
            nfcManager.verifyNFC();
            //nfcMger.enableDispatch();

            Intent nfcIntent = new Intent(this, getClass());
            nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, nfcIntent, 0);
            IntentFilter[] intentFiltersArray = new IntentFilter[] {};
            String[][] techList = new String[][] { { android.nfc.tech.Ndef.class.getName() }, { android.nfc.tech.NdefFormatable.class.getName() } };
            NfcAdapter nfcAdpt = NfcAdapter.getDefaultAdapter(this);
            nfcAdpt.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techList);
        }
        catch(NFCManager.NFCNotSupported nfcnsup) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.Write_price_root),"NFC not Supported ",Snackbar.LENGTH_INDEFINITE)
                    .setAction("Close",(v)->finish());
            snackbar.show();
            new Handler().postDelayed(()->{
                finish();
            },10000);

        }
        catch(NFCManager.NFCNotEnabled nfcnEn) {

            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.Write_price_root),"NFC not enabled ",Snackbar.LENGTH_INDEFINITE)
                    .setAction("Open Settings", (v)->
                            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS)));
            snackbar.show();
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("Nfc", "New intent");
        // It is the time to write the tag
        currentTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (ndefMessage != null) {
            nfcManager.writeTag(currentTag, ndefMessage);
            MediaPlayer mediaPlayer=MediaPlayer.create(this,R.raw.payment_unlock);
            mediaPlayer.start();
            nfc_status.setText("Done Writing");
            nfc_status.setTextColor(Color.rgb(0,100,0));
            Snackbar.make(findViewById(R.id.Write_price_root),"Tag Written",Snackbar.LENGTH_LONG).show();
       startNfcAnimation(false);

            new Handler().postDelayed(()->{

                finish();
            },2000);





        } else {
            // Handle intent

        }
    }
    private void startNfcAnimation(boolean state) {
        final RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);
        if (state)
            rippleBackground.startRippleAnimation();
        else rippleBackground.stopRippleAnimation();
    }
}

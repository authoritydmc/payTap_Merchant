package com.vastgk.paytapmercent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class WritePrice extends AppCompatActivity {
    private static final String TAG ="ERROS" ;
    private  String datas;
    NfcAdapter nfcAdapter;
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
        nfcAdapter=NfcAdapter.getDefaultAdapter(this);

        Map map=new LinkedHashMap();
        map.put("VendorName",VENDOR_NAME);
        map.put("VendorId",VENDORD_ID);
        map.put("Amount",getIntent().getStringExtra(TOTAL_AMOUNT_TO_WRITE));
        map.put("Cust_ID",CUSTOMER_ID);
        JSONObject data=new JSONObject(map);
        datas=data.toString();
        dataTxtView.setText(datas.toString());



    }

    @Override
    protected void onResume() {
        super.onResume();

        if (nfcAdapter!=null && nfcAdapter.isEnabled())
        {   Toast.makeText(this, "Touch the NFC tag to complete the writing", Toast.LENGTH_LONG).show();

            startForegroundWriting();
        }else
        {if (nfcAdapter==null)
            Toast.makeText(this, "NFC not Supported in your Phone\nClosing in 10 seconds", Toast.LENGTH_LONG).show();
        else if (!nfcAdapter.isEnabled())
            Toast.makeText(this, "Enable NFC in Your Phone", Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            },10000);
        }


    }

    private void startForegroundWriting() {

        Intent intent=new Intent(this,WritePrice.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);
        IntentFilter[] intentFilters=new IntentFilter[]{};
        nfcAdapter.enableForegroundDispatch(this,pendingIntent,intentFilters,null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter!=null && nfcAdapter.isEnabled())
        nfcAdapter.disableForegroundDispatch(this);
    }


    private  void Write_to_NFC(Tag tag, NdefMessage ndefMessage)
    {
        try {
            if (    tag==null)
            {
                Toast.makeText(this, "Tag object is null", Toast.LENGTH_SHORT).show();
                return;
            }
            Ndef ndef=Ndef.get(tag);
            if (ndef==null)
            {
formatTag(tag,ndefMessage);
            }else
            {
                if (!ndef.isWritable())
                {
                    Toast.makeText(this, "Current Tag not Writeble", Toast.LENGTH_SHORT).show();
                    ndef.close();

                    return;
                }
ndef.writeNdefMessage(ndefMessage);
                ndef.close();
                Toast.makeText(this, "Tag Written", Toast.LENGTH_LONG).show();


            }


        }
        catch (Exception e)
        {
            Log.e(TAG, "Write_to_NFC: "+e.getLocalizedMessage() );
        }
    }

    private void formatTag(Tag tag, NdefMessage ndefMessage) {

        try {

            NdefFormatable ndefFormatable=NdefFormatable.get(tag);
            if (ndefFormatable==null)
            {
                return;
            }

            ndefFormatable.connect();
            ndefFormatable.format(ndefMessage);
            ndefFormatable.close();

        }catch (Exception e)
        {
            Log.e(TAG, "formatTag: "+e.getLocalizedMessage());

        }



    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.hasExtra(NfcAdapter.EXTRA_TAG)){
            Toast.makeText(this, "NFCintent!", Toast.LENGTH_SHORT).show();
            Tag tag=intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            NdefMessage ndefMessage=createNdefMessage(datas);
            Write_to_NFC(tag,ndefMessage);
        }
    }

    private  NdefMessage createNdefMessage(String Content)
    {
        NdefRecord ndefRecord=createTextRecord(Content);
        NdefMessage ndefMessage=new NdefMessage(new NdefRecord[]{ndefRecord});
        return  ndefMessage;
    }

    private NdefRecord createTextRecord(String content) {

        try {
            byte[] language;
            language= Locale.getDefault().getLanguage().getBytes("UTF-8");
            final byte [] text=content.getBytes("UTF-8");
            final int textlength=text.length;
            final int languagelength=language.length;
            final ByteArrayOutputStream payload=new ByteArrayOutputStream(1+languagelength+textlength);
            payload.write((byte)(languagelength&0x1F));
            payload.write(language,0,languagelength);
            payload.write(text,0,textlength);
            return  new NdefRecord(NdefRecord.TNF_WELL_KNOWN,NdefRecord.RTD_TEXT,new byte[0],payload.toByteArray());


        }
        catch (Exception e)
        {
            Log.e(TAG, "createTextRecord: "+e.getLocalizedMessage() );
        }
        return  null;
    }
}

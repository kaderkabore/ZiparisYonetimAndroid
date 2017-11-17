package oceannet.com.ziparisyonetim.UTILS.GCM;
/**
 * Created by oceannet on 06/01/17.
 */

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import oceannet.com.ziparisyonetim.R;
import oceannet.com.ziparisyonetim.UTILS.AndyConstants;


/**
 * Created by oceannet on 01/12/16.
 */
public class RegistrationIntentService extends IntentService {
    // ...



    public RegistrationIntentService() {
        super("intent push");
    }
    @Override
    public void onHandleIntent(Intent intent) {
        // ...

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //  PREF_PUSHTOKEN

        //  Bundle[{google.sent_time=1485179231082, data=[{"TypeID":2,"TypeName":"Ruhsat","ID":10,"EndDate":"2016-12-17T00:00:00"},{"TypeID":3,"TypeName":"Trafik Sigortası","ID":1010,"EndDate":"2017-01-13T00:00:00"}], google.message_id=0:1485179231118753%f27a9ad8f9fd7ecd, collapse_key=do_not_collapse}]
        try {
            // [START register_for_gcm]
            // Initially this call goes out to the network to retrieve the token, subsequent calls
            // are local.
            // R.string.gcm_defaultSenderId (the Sender ID) is typically derived from google-services.json.
            // See https://developers.google.com/cloud-messaging/android/start for details on this file.
            // [START get_token]
            InstanceID instanceID = InstanceID.getInstance(this);


            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            System.out.println("zasss"  + R.string.gcm_defaultSenderId);

            System.out.println("zasss"  );

            // [END get_token]
            Log.i("push", " zasssGCM Registration Token oo: " + token);

            sharedPreferences.edit().putString(AndyConstants.PREF_PUSHTOKEN,token).commit();


            // [END register_for_gcm]
        } catch (Exception e) {
            Log.d("push", "Failed to complete token refresh", e);

        }




    }


//    @Override
//    public void onTokenRefresh() {
//        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
//        Intent intent = new Intent(this, RegistrationIntentService.class);
//        startService(intent);
//    }

    // ...
}

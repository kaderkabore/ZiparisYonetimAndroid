package oceannet.com.ziparisyonetim;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.widget.Toast;

import oceannet.com.ziparisyonetim.UTILS.AndyConstants;
import oceannet.com.ziparisyonetim.UTILS.GCM.RegistrationIntentService;
import oceannet.com.ziparisyonetim.UTILS.PushRefresher;

/**
 * Created by oceannet on 28/10/17.
 */

public class AppController   extends Application {





      public static  AppController instance  = null;
    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        ///GCM iNTENT

        Intent i = new Intent(this, RegistrationIntentService.class);
        startService(i);

    }

    public static AppController getInstance(){


        return instance;
    }


    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }


    public void showToast(String text){

        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();

    }

    public String getToken(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String  token =pref.getString(AndyConstants.PREF_TOKEN, "zas");

        return   token;
    }

    public String getUserName(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String  token =pref.getString(AndyConstants.PREF_USERNAME, "zas");

        return   token;
    }


    public String getName(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String  token =pref.getString(AndyConstants.PREF_NAMEE, "");

        return   token;
    }


    PushRefresher pushRefresher = null;

    public PushRefresher getPushRefresher() {
        return pushRefresher;
    }

    public void setPushRefresher(PushRefresher pushRefresher) {
        this.pushRefresher = pushRefresher;
    }
}

package oceannet.com.ziparisyonetim.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import oceannet.com.ziparisyonetim.AppController;
import oceannet.com.ziparisyonetim.R;
import oceannet.com.ziparisyonetim.UTILS.AndyConstants;

public class Avt_loading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avt_loading);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Intent i = new Intent(this,Avt_login.class);
//        startActivity(i);

    }

    @Override
    protected void onResume() {
        super.onResume();

        login();
    }

    public void login(){


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

           String pass = sharedPreferences.getString(AndyConstants.PREF_PASSWORD,"");
        String httpPostBody = "username="+AppController.getInstance().getUserName()+""+"&password=" + pass +"&deviceToken=" + sharedPreferences.getString(AndyConstants.PREF_PUSHTOKEN,"11") +"&deviceType=" + "1"+  "&grant_type=password";


        AndroidNetworking.post(AndyConstants.URL_LOGIN)
                .addByteBody(httpPostBody.getBytes())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        System.out.println("zasssssss log"+response.toString());

                        try {
                            String Token = response.getString("access_token");


                            String userName =  response.getString("userName");

                            Boolean FirstLogin =  response.getBoolean("FirstLogin");

                            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(Avt_loading.this);

                            SharedPreferences.Editor editor = pref.edit()  ;
                            editor.putString(AndyConstants.PREF_TOKEN,Token);
                            editor.putString(AndyConstants.PREF_USERNAME,userName);


                            if  (Token.length() < 3 ){

                                getUserdetail();

                                Intent i = new Intent(Avt_loading.this,Avt_login.class);
                                startActivity(i);


                            }  else{

                                Intent i = new Intent(Avt_loading.this,MainMenu.class);
                                startActivity(i);

                            }





                        }
                        catch (JSONException e){

                            e.printStackTrace();
                            System.out.println();

                        }




                    }

                    @Override
                    public void onError(ANError anError) {



                        //    pDialog.dismiss();

                        Intent i = new Intent(Avt_loading.this,Avt_login.class);
                        startActivity(i);
//                        AppController.getInstance().showToast("Lütfen giriş bilgilerini kontrol et. Eksik yada yanlış girmiş olabilirsin.");

                        System.out.println("zas"+anError.getErrorBody());

                    }
                });


    }


    public void click_hamburger(View view) {
    }

    public void click_hamburger2(View view) {
    }




    public void getUserdetail(){


        String token = AppController.getInstance().getToken();
        String headertoken =  "Bearer "+token;

        AndroidNetworking.get(AndyConstants.URL_GETUSERDETAILS)
                .addHeaders("Content-Type", "application/x-www-form-urlencoded")
                .addHeaders("Authorization", headertoken)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("zasssssss log order order list"+response.toString());
                        try {
                            int status = response.getJSONObject("status").getInt("response_code");

                            if (status == 200){


                                JSONObject data  = response.getJSONObject("data");


                                String  ID = data.getString("ID");
                                String  Name = data.getString("Name") ;
                                String  Surname = data.getString("Surname");
                                String  CompanyName = data.getString("CompanyName");
                                String  BlockName = data.getString("BlockName");
                                String  Email = data.getString("Email");
                                String  Phone = data.getString("Phone");
                                String  UserCode = data.getString("UserCode") ;
                                String  Position = data.getString("Position");
                                String  Location = data.getString("Location");




                                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(Avt_loading.this);

                                SharedPreferences.Editor editor = pref.edit()  ;
                                editor.putString(AndyConstants.PREF_NAMEE,Name+Surname);
                                editor.commit();



                            }  else {

                                String statustext = response.getJSONObject("response").getString("response_desc");

                                AppController.getInstance().showToast(statustext);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }
}

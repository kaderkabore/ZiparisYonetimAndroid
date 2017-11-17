package oceannet.com.ziparisyonetim.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import oceannet.com.ziparisyonetim.AppController;
import oceannet.com.ziparisyonetim.R;
import oceannet.com.ziparisyonetim.UTILS.AndyConstants;

public class Avt_login extends AppCompatActivity {


       EditText edt_eposta,edt_sifre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avt_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edt_eposta = (EditText) findViewById(R.id.edt_eposta);
        edt_sifre = (EditText) findViewById(R.id.edt_sifre);


        edt_eposta.setText("naipliii51@hotmail.com");
        edt_sifre.setText("123456");

        setDialog();

    }


    ProgressDialog pDialog ;
    public  void setDialog(){

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Yukeniyor ....");
    }

    public void click_sifreunutum(View view) {

        Intent i = new Intent(this,Avt_sifreUnuttum.class);
        startActivity(i);
    }

    public void click_girsiYap(View view) {


        login(edt_eposta.getText().toString(),edt_sifre.getText().toString());
    }

    public void login(String username,String password){

        if (!isValidEmail (edt_eposta.getText().toString().trim()) ){

            AppController.getInstance().showToast("Lütfen giriş bilgilerini kontrol et. Eksik yada yanlış girmiş olabilirsin.");
            return;
        }


        if (edt_sifre.getText().toString().trim().length() <3){


            AppController.getInstance().showToast("Lütfen giriş bilgilerini kontrol et. Eksik yada yanlış girmiş olabilirsin.");

            return;
        }




        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String numm = edt_eposta.getText().toString().replace("-","");
        final    String numm2 = numm.replace(" ","");

        String httpPostBody = "username="+numm2+""+"&password=" + edt_sifre.getText().toString() +"&deviceToken=" + sharedPreferences.getString(AndyConstants.PREF_PUSHTOKEN,"11") +"&deviceType=" + "1"+  "&grant_type=password";

        pDialog.show();
        AndroidNetworking.post(AndyConstants.URL_LOGIN)
                .addByteBody(httpPostBody.getBytes())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        pDialog.dismiss();
                        System.out.println("zasssssss log"+response.toString());

                        try {
                            String Token = response.getString("access_token");


                            String userName =  response.getString("userName");

                            Boolean FirstLogin =  response.getBoolean("FirstLogin");

                            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(Avt_login.this);

                         SharedPreferences.Editor editor = pref.edit()  ;
                            editor.putString(AndyConstants.PREF_TOKEN,Token);
                            editor.putString(AndyConstants.PREF_USERNAME,userName);
                            editor.putString(AndyConstants.PREF_PASSWORD,edt_sifre.getText().toString());
                            editor.commit();

                            if  (Token.length() > 3 ){


                                getUserdetail();
                                if  (FirstLogin){


                                    Intent i = new Intent(Avt_login.this,Avt_YeniSifre.class);
                                    startActivity(i);


                                }  else{

                                    Intent i = new Intent(Avt_login.this,MainMenu.class);
                                    startActivity(i);

                                }
                            }






                        }
                        catch (JSONException e){

                            e.printStackTrace();
                            System.out.println();

                        }




                    }

                    @Override
                    public void onError(ANError anError) {


                        pDialog.dismiss();
                        //    pDialog.dismiss();


                        AppController.getInstance().showToast("Lütfen giriş bilgilerini kontrol et. Eksik yada yanlış girmiş olabilirsin.");

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




                                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(Avt_login.this);

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



    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}

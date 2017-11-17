package oceannet.com.ziparisyonetim.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import oceannet.com.ziparisyonetim.AppController;
import oceannet.com.ziparisyonetim.R;
import oceannet.com.ziparisyonetim.UTILS.AndyConstants;

public class Avt_YeniSifre extends AppCompatActivity {



    EditText edt_eposta,edt_sifre,edt_yenisifre,edt_yenisifreTekrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avt__yeni_sifre);
        edt_eposta = (EditText) findViewById(R.id.edt_eposta);
        edt_sifre = (EditText) findViewById(R.id.edt_geciciSifre);;

        edt_yenisifre = (EditText) findViewById(R.id.edt_yenisifre);
        edt_yenisifreTekrar = (EditText) findViewById(R.id.edt_yenisifreTekrar);

        edt_eposta.setText(AppController.getInstance().getUserName());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        edt_sifre.setText(sharedPreferences.getString(AndyConstants.PREF_PASSWORD,"1234"));


        setDialog();
    }



    public void click_kaydet(View view) {



        String token = AppController.getInstance().getToken();
        String headertoken =  "Bearer "+token;

        String username = AppController.getInstance().getUserName();
        Map<String,String> param = new HashMap<String,String>();

        param.put("OldPassword",edt_sifre.getText().toString());

        param.put("NewPassword",edt_yenisifre.getText().toString());

        param.put("ConfirmPassword",edt_yenisifreTekrar.getText().toString());



        if (edt_yenisifre.getText().toString().trim().length() <3){


            AppController.getInstance().showToast("Lütfen giriş bilgilerini kontrol et. Eksik yada yanlış girmiş olabilirsin.");

            return;
        }
        pDialog.show();

        AndroidNetworking.post(AndyConstants.URL_CHANGEPASSWORD+username)
                .addHeaders("Content-Type", "application/x-www-form-urlencoded")
                .addHeaders("Authorization", headertoken)
                .addBodyParameter(param)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        pDialog.dismiss();
                        System.out.println("zasssssss log"+response.toString());

                        try {


                                  int status  =  response.getInt("response_code");

                            if (status == 200){

                                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(Avt_YeniSifre.this);



                                pref.edit().putString(AndyConstants.PREF_PASSWORD ,edt_yenisifre.getText().toString()).commit();

                                Intent i = new Intent(Avt_YeniSifre.this,MainMenu.class);
                                startActivity(i);

                            }   else{

                                String statustext = response.getString("response_desc");

                                AppController.getInstance().showToast(statustext);

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


                        AppController.getInstance().showToast("Lütfen giriş bilgilerini kontrol et. Eksik yada yanlış girmiş olabilirsin.");

                        System.out.println("zas"+anError.getErrorBody());

                    }
                });




    }


    public void click_hamburger(View view) {

        this.finish();
    }

    public void click_hamburger2(View view) {
    }


    ProgressDialog pDialog ;
    public  void setDialog(){

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Yukeniyor ....");
    }
}

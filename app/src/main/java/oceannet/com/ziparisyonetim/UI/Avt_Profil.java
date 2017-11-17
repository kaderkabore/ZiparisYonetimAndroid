package oceannet.com.ziparisyonetim.UI;

import android.app.ProgressDialog;
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

public class Avt_Profil extends AppCompatActivity {


    EditText edt_name,edt_surName,edt_phone,edt_email,edt_company,edt_position,edt_building,edt_location,edt_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avt_profil);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);



        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_surName = (EditText) findViewById(R.id.edt_surName);
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_company = (EditText) findViewById(R.id.edt_company);
        edt_position = (EditText) findViewById(R.id.edt_position);
        edt_building = (EditText) findViewById(R.id.edt_building);
        edt_location = (EditText) findViewById(R.id.edt_location);
        edt_id = (EditText) findViewById(R.id.edt_id);

        edt_company.setFocusable(false);
        edt_position.setFocusable(false);
        edt_building.setFocusable(false);
        edt_location.setFocusable(false);
        edt_id.setFocusable(false);

        setDialog();
        getUserdetail();




        //        ,edt_phone,edt_email,edt_company,edt_position,edt_building,edt_location,edt_id;

    }

    public void getUserdetail(){


        String token = AppController.getInstance().getToken();
        String headertoken =  "Bearer "+token;
        pDialog.show();
        AndroidNetworking.get(AndyConstants.URL_GETUSERDETAILS)
                .addHeaders("Content-Type", "application/x-www-form-urlencoded")
                .addHeaders("Authorization", headertoken)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        pDialog.dismiss();
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


                                edt_name.setText(isNulll(Name));
                                edt_surName.setText(isNulll(Surname));
                                edt_email.setText(isNulll(Email));
                                edt_company.setText(isNulll(CompanyName));
                                edt_building.setText(isNulll(BlockName));
                                edt_phone.setText(isNulll(Phone));
                                edt_id.setText(isNulll(UserCode));
                                edt_position.setText(isNulll(Position));
                                edt_location.setText(isNulll(Location));

                                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(Avt_Profil.this);

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


                        pDialog.dismiss();
                    }
                });

    }

    public String isNulll(String text){



        if (text.equalsIgnoreCase("null")){
            return  "" ;
        }
        return text;
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

    public void click_kaydet(View view) {


        String token = AppController.getInstance().getToken();
        String headertoken =  "Bearer "+token;

        String username = AppController.getInstance().getUserName();
        Map<String,String> param = new HashMap<String,String>();

        param.put("ID","");

        param.put("Name",edt_name.getText().toString());

        param.put("Surname",edt_surName.getText().toString());
        param.put("CompanyName",edt_company.getText().toString());
        param.put("BlockName",edt_building.getText().toString());
        param.put("Email",edt_email.getText().toString());
        param.put("Phone",edt_phone.getText().toString());
        param.put("UserCode",edt_id.getText().toString());
        param.put("Position",edt_position.getText().toString());
        param.put("Location",edt_location.getText().toString());


        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(Avt_Profil.this);

        SharedPreferences.Editor editor = pref.edit()  ;
        editor.putString(AndyConstants.PREF_NAMEE,edt_name.getText().toString()+edt_surName.getText().toString());
        editor.commit();

        pDialog.show();

        AndroidNetworking.post(AndyConstants.URL_EDITUSERDETAIL)
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


                             Avt_Profil.this.finish();

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
}

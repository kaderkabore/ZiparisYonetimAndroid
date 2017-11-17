package oceannet.com.ziparisyonetim.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import oceannet.com.ziparisyonetim.AppController;
import oceannet.com.ziparisyonetim.R;
import oceannet.com.ziparisyonetim.UTILS.AndyConstants;

import static oceannet.com.ziparisyonetim.UI.Avt_login.isValidEmail;

public class Avt_sifreUnuttum extends AppCompatActivity {


    EditText edt_eposta,edt_sifre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avt_sifre_unuttum);


        edt_eposta = (EditText) findViewById(R.id.edt_eposta);
        setDialog();

    }

    ProgressDialog pDialog ;
    public  void setDialog(){

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Yukeniyor ....");
    }

    public void click_devamET(View view) {


//        Intent i = new Intent(this,Avt_login.class);
//        startActivity(i);

        if (!isValidEmail (edt_eposta.getText().toString().trim()) ){

            AppController.getInstance().showToast("Lütfen giriş bilgilerini kontrol et. Eksik yada yanlış girmiş olabilirsin.");
            return;
        }


        String token = AppController.getInstance().getToken();
        String headertoken =  "Bearer "+token;
        pDialog.show();
        AndroidNetworking.post(AndyConstants.URL_RESETPASSWORD+""+edt_eposta.getText().toString().trim())
                .addHeaders("Content-Type", "application/x-www-form-urlencoded")

                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();



                        AppController.getInstance().showToast(" Postanıza yeni bir şifre gönderildi.");


                        Intent i = new Intent(Avt_sifreUnuttum.this,Avt_login.class);
//        startActivity(i);
                    }

                    @Override
                    public void onError(ANError anError) {
                        pDialog.dismiss();
                    }
                });

    }



    public void click_hamburger(View view) {

        this.finish();
    }

    public void click_hamburger2(View view) {
    }
}

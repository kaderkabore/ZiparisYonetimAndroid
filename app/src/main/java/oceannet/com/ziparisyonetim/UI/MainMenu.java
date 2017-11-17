package oceannet.com.ziparisyonetim.UI;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import oceannet.com.ziparisyonetim.AppController;
import oceannet.com.ziparisyonetim.MODELS.OrderCurrent;
import oceannet.com.ziparisyonetim.MODELS.Product;
import oceannet.com.ziparisyonetim.R;
import oceannet.com.ziparisyonetim.UTILS.Adapters.Adapter_product;
import oceannet.com.ziparisyonetim.UTILS.Adapters.Adapter_siparisler;
import oceannet.com.ziparisyonetim.UTILS.AndyConstants;
import oceannet.com.ziparisyonetim.UTILS.PushRefresher;
import oceannet.com.ziparisyonetim.UTILS.RecyclerItemClickListener;

public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,PushRefresher {


         TextView txt_garcon,txt_adresTittle,txt_adresdetail,txt_time,txt_status,txt_ratingStatus,txt_comments,txt_name;

        Button btn_teslimEt;
    RatingBar ratingBar;
    RecyclerView rcl_ozet,rcl_gelen,rcl_kabul;

    View viewAlert,viewAlert1,viewAlert2;

    Adapter_siparisler adapter_siparisler,adapter_siparisler_Accept;
   ArrayList<OrderCurrent> orderGelen = new ArrayList<OrderCurrent>();
    ArrayList<OrderCurrent> orderAccept = new ArrayList<OrderCurrent>();

    OrderCurrent currentOrder;

    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        txt_garcon = (TextView)findViewById(R.id.txt_garcon);
        txt_adresTittle = (TextView)findViewById(R.id.txt_adressTittle);
        txt_adresdetail = (TextView)findViewById(R.id.txt_adresdetail);
        txt_time = (TextView)findViewById(R.id.txt_time);
        txt_status = (TextView)findViewById(R.id.txt_status);
        txt_ratingStatus = (TextView)findViewById(R.id.txt_ratingStatus);
        txt_comments = (TextView)findViewById(R.id.txt_comments);
        txt_name = (TextView)findViewById(R.id.txt_name);

        btn_teslimEt = (Button)findViewById(R.id.btn_teslimEt);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);


        rcl_ozet = (RecyclerView)findViewById(R.id.rcl_ozet);
        rcl_gelen = (RecyclerView)findViewById(R.id.rcl_gelen);
        rcl_kabul = (RecyclerView)findViewById(R.id.rcl_kabuleden);


        viewAlert = (View)findViewById(R.id.viewAlert);
        viewAlert1 = (View)findViewById(R.id.viewAlert1);
        viewAlert2 = (View)findViewById(R.id.viewAlert2);


        viewAlert.setVisibility(View.GONE);

        setDialog();

         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);






    }

    ProgressDialog pDialog ;
    public  void setDialog(){

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Yukeniyor ....");
    }
    @Override
    protected void onResume() {
        super.onResume();

        getGelen();
        getAcceptorder();
        AppController.getInstance().setPushRefresher(this);

          txt_name.setText(AppController.getInstance().getName());



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {


            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void getGelen(){





        String token = AppController.getInstance().getToken();
        String headertoken =  "Bearer "+token;
        pDialog.show();
        AndroidNetworking.get(AndyConstants.URL_GETUSERORDERLIST)
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


                                JSONArray data  = response.getJSONArray("data");
                                orderGelen.clear();
                                for (int i = 0; i <data.length() ; i++) {

                                    JSONObject jsoorder = data.getJSONObject(i);
                                    ArrayList<Product> products = new ArrayList<Product> ();


                  for (int j = 0; j <jsoorder.getJSONArray("OrderListDetails").length() ; j++) {
                      JSONObject projson = jsoorder.getJSONArray("OrderListDetails").getJSONObject(j);

                      Product product = new Product(projson.getString("ProductID"),projson.getString("Image"),projson.getString("ProductName"),"",0.0,projson.getInt("Stock"));
                      products.add(product);


                                    }

                 OrderCurrent order = new OrderCurrent(jsoorder.getInt("OrderID"),jsoorder.getInt("Status"),jsoorder.getString("RoomName"),jsoorder.getString("FullName"),jsoorder.getString("Email"),jsoorder.getString("Description"),jsoorder.getString("BlockName"),jsoorder.getString("FloorName"),jsoorder.getString("CompanyName"),jsoorder.getString("OrderDateTime"),products);

                       orderGelen.add(order);
                                }


                             /////////

                                initializeGelen();






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


    public void getAcceptorder(){


        String token = AppController.getInstance().getToken();
        String headertoken =  "Bearer "+token;

        AndroidNetworking.get(AndyConstants.URL_GETMYORDERLIST)
                .addHeaders("Content-Type", "application/x-www-form-urlencoded")
                .addHeaders("Authorization", headertoken)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println("zasssssss order list acept"+response.toString());
                        try {
                            int status = response.getJSONObject("status").getInt("response_code");

                            if (status == 200){


                                JSONArray data  = response.getJSONArray("data");


                                orderAccept.clear();
                                for (int i = 0; i <data.length() ; i++) {

                                    JSONObject jsoorder = data.getJSONObject(i);
                                    ArrayList<Product> products = new ArrayList<Product> ();


                                    for (int j = 0; j <jsoorder.getJSONArray("OrderListDetails").length() ; j++) {
                                        JSONObject projson = jsoorder.getJSONArray("OrderListDetails").getJSONObject(j);

                                        Product product = new Product(projson.getString("ProductID"),projson.getString("Image"),projson.getString("ProductName"),"",0.0,projson.getInt("Stock"));
                                        products.add(product);


                                    }

                                    OrderCurrent order = new OrderCurrent(jsoorder.getInt("OrderID"),jsoorder.getInt("Status"),jsoorder.getString("RoomName"),jsoorder.getString("FullName"),jsoorder.getString("Email"),jsoorder.getString("Description"),jsoorder.getString("BlockName"),jsoorder.getString("FloorName"),jsoorder.getString("CompanyName"),jsoorder.getString("OrderDateTime"),products);

                                    orderAccept.add(order);
                                }


                                initializeAceptGelen();





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




    public void initializeGelen(){

        LinearLayoutManager manager2 = new LinearLayoutManager(MainMenu.this,LinearLayoutManager.VERTICAL,false);


        adapter_siparisler = new Adapter_siparisler(MainMenu.this,MainMenu.this,orderGelen);
        rcl_gelen.setLayoutManager(manager2);
        rcl_gelen.setAdapter(adapter_siparisler);



        rcl_gelen.addOnItemTouchListener(new RecyclerItemClickListener(MainMenu.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                currentOrder = orderGelen.get(position);
                     initializeAlert();
            }
        }));


    }


    public void initializeAceptGelen(){

        LinearLayoutManager manager2 = new LinearLayoutManager(MainMenu.this,LinearLayoutManager.VERTICAL,false);


        adapter_siparisler_Accept = new Adapter_siparisler(MainMenu.this,MainMenu.this,orderAccept);
        rcl_kabul.setLayoutManager(manager2);
        rcl_kabul.setAdapter(adapter_siparisler_Accept);



        rcl_kabul.addOnItemTouchListener(new RecyclerItemClickListener(MainMenu.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                currentOrder = orderAccept.get(position);
                initializeAlert();
            }
        }));


    }

    public void initializeAlert(){


        viewAlert.setVisibility(View.VISIBLE);
        viewAlert1.setVisibility(View.VISIBLE);
        viewAlert2.setVisibility(View.GONE);


      txt_adresTittle.setText(currentOrder.getRoomName());
       txt_adresdetail.setText(currentOrder.getCompanyName());
        txt_garcon.setText(currentOrder.getFullName());
        ratingBar.setVisibility(View.GONE);
        txt_ratingStatus.setVisibility(View.GONE);
        btn_teslimEt.setVisibility(View.VISIBLE);

        txt_comments.setText(currentOrder.getDescription());
        if (currentOrder.getStatus() == 0){

            txt_status.setText("Bekliyor");
            btn_teslimEt.setText("Hazırla");

            viewAlert1.setBackground(ActivityCompat.getDrawable(this,R.drawable.bcg_item_y));
            viewAlert2.setBackground(ActivityCompat.getDrawable(this,R.drawable.bcg_item_y));
        }   else{
            txt_status.setText("Hazırlanyor");
            btn_teslimEt.setText("Teslim Et");

            viewAlert1.setBackground(ActivityCompat.getDrawable(this,R.drawable.bcg_item_y2));
            viewAlert2.setBackground(ActivityCompat.getDrawable(this,R.drawable.bcg_item_y2));

        }


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");

        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(currentOrder.getOrderDateTime());
            SimpleDateFormat  formatter = new SimpleDateFormat(" dd-MM-yyyy hh:mm ");

            txt_time.setText(formatter.format(convertedDate).toString());


        } catch (ParseException e) {


            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        LinearLayoutManager manager2 = new LinearLayoutManager(MainMenu.this,LinearLayoutManager.VERTICAL,false);


     Adapter_product adapter_siparisler = new Adapter_product(MainMenu.this,currentOrder.getProducts());
        rcl_ozet.setLayoutManager(manager2);
        rcl_ozet.setAdapter(adapter_siparisler);


        rcl_gelen.removeOnItemTouchListener(new RecyclerItemClickListener(MainMenu.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


            }
        }));

        rcl_kabul.removeOnItemTouchListener(new RecyclerItemClickListener(MainMenu.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


            }
        }));

        rcl_gelen.addOnItemTouchListener(new RecyclerItemClickListener(MainMenu.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


            }
        }));

        rcl_kabul.addOnItemTouchListener(new RecyclerItemClickListener(MainMenu.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


            }
        }));


    }




//    public void getAcceptorder(){
//
//
//
//        LinearLayoutManager manager2 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
//
//
//        adapter_siparisler = new Adapter_siparisler(this,this);
//        rcl_gelen.setLayoutManager(manager2);
//        rcl_gelen.setAdapter(adapter_siparisler);
//
//        String token = AppController.getInstance().getToken();
//        String headertoken =  "Bearer "+token;
//
//        AndroidNetworking.get(AndyConstants.URL_GETUSERORDERLIST)
//                .addHeaders("Content-Type", "application/x-www-form-urlencoded")
//                .addHeaders("Authorization", headertoken)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        try {
//                            int status = response.getJSONObject("response").getInt("response_code");
//
//                            if (status == 200){
//
//
//
//
//                            }  else {
//
//                                String statustext = response.getJSONObject("response").getString("response_desc");
//
//                                AppController.getInstance().showToast(statustext);
//
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//
//                    }
//                });
//
//    }



    public void click_cikis(View view) {


        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor editor = pref.edit()  ;
        editor.putString(AndyConstants.PREF_TOKEN,"");
        editor.putString(AndyConstants.PREF_USERNAME,"");
        editor.putString(AndyConstants.PREF_PASSWORD,"");
        editor.commit();


        Intent i = new Intent(this,Avt_login.class);
        startActivity(i);

    }


    public void click_siparisler(View view) {

        drawer.closeDrawer(GravityCompat.START);
    }

    public void click_pastSiparis(View view) {

        Intent i = new Intent(this,Avt_gecmisSiparisler.class);
        startActivity(i);
    }

    public void click_profil(View view) {

        Intent i = new Intent(this,Avt_Profil.class);
        startActivity(i);
    }

    public void click_ayarlar(View view) {


    }

    public void click_siparisNotu(View view) {


        viewAlert1.setVisibility(View.GONE);

        viewAlert2.setVisibility(View.VISIBLE);
        rcl_gelen.addOnItemTouchListener(new RecyclerItemClickListener(MainMenu.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                currentOrder = orderGelen.get(position);
                initializeAlert();
            }
        }));


        rcl_kabul.addOnItemTouchListener(new RecyclerItemClickListener(MainMenu.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                currentOrder = orderAccept.get(position);
                initializeAlert();
            }
        }));


    }

    public void click_closeAlert(View view) {

        viewAlert.setVisibility(View.GONE);
        viewAlert1.setVisibility(View.GONE);
    }

    public void click_teslimet(View view) {

        if (currentOrder.getStatus() == 0){


            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("SİPARİŞ HAZIRLANSIN MI") ;
            builder1.setMessage("Siparişi  hazırlamak  istediğinize emin misiniz ?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "EVET",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {



                            AcceptOrder();
                            dialog.cancel();
                        }
                    });

            builder1.setNegativeButton(
                    "VAZGEÇ",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();




        }   else {



            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("SİPARİŞ TESLIM EDINSIN MI") ;
            builder1.setMessage("Siparişinizi teslim etmek istediğinize emin misiniz ?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "EVET",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {



                            teslimET();
                            dialog.cancel();
                        }
                    });

            builder1.setNegativeButton(
                    "VAZGEÇ",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }



    }


    public void AcceptOrder() {

        String token = AppController.getInstance().getToken();
        String headertoken =  "Bearer "+token;

        pDialog.show();
        AndroidNetworking.post(AndyConstants.URL_POSTORDERRATE+""+currentOrder.getOrderID())
                .addHeaders("Content-Type", "application/x-www-form-urlencoded")
                .addHeaders("Authorization", headertoken)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            int status = response.getInt("response_code");

                            if (status == 200){


                                viewAlert.setVisibility(View.GONE);
                                viewAlert1.setVisibility(View.GONE);
                                viewAlert2.setVisibility(View.GONE);

                                getGelen();
                                getAcceptorder();
                                String statustext = "Başarılı";

                                AppController.getInstance().showToast(statustext);





                            }  else {

                                String statustext = response.getString("response_desc");

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


    public void teslimET() {


        String token = AppController.getInstance().getToken();
        String headertoken =  "Bearer "+token;
        pDialog.show();
        AndroidNetworking.post(AndyConstants.URL_DELIVERORDER+""+currentOrder.getOrderID())
                .addHeaders("Content-Type", "application/x-www-form-urlencoded")
                .addHeaders("Authorization", headertoken)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            int status = response.getInt("response_code");

                            if (status == 200){

                                viewAlert.setVisibility(View.GONE);
                                viewAlert1.setVisibility(View.GONE);
                                viewAlert2.setVisibility(View.GONE);
                                getGelen();
                                getAcceptorder();
                                String statustext = "Başarılı";

                                AppController.getInstance().showToast(statustext);


                            }  else {

                                String statustext = response.getString("response_desc");

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

    @Override
    public void refresh() {
        getGelen();
        getAcceptorder();
    }

    public void click_hamburger(View view) {

        drawer.openDrawer(GravityCompat.START);

    }

    public void click_hamburger2(View view) {

        Intent i = new Intent(this,Avt_gecmisSiparisler.class);
        startActivity(i);
    }
}

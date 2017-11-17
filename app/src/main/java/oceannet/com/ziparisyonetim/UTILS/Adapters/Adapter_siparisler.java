package oceannet.com.ziparisyonetim.UTILS.Adapters;

import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import oceannet.com.ziparisyonetim.MODELS.OrderCurrent;
import oceannet.com.ziparisyonetim.R;
import oceannet.com.ziparisyonetim.UI.MainMenu;

/**
 * Created by oceannet on 31/10/17.
 */

public class Adapter_siparisler   extends RecyclerView.Adapter{


    Context context;


    MainMenu mainMenu;
  ArrayList<OrderCurrent>  orderCurrent;
    public Adapter_siparisler(Context context, MainMenu mainMenu,  ArrayList<OrderCurrent>  orderCurrent) {

        this.context = context;
        this.mainMenu = mainMenu;
        this.orderCurrent = orderCurrent;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.item_siparislerim, parent, false);

        RecyclerView.ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vholder = (ViewHolder) holder;


        OrderCurrent order  = orderCurrent.get(position);

              vholder.txt_adresTittle.setText(order.getRoomName());
        vholder.txt_adresdetail.setText(order.getCompanyName());


        if (order.getStatus() == 0){
            vholder.txt_ratingStatus.setText("Bekliyor");

          vholder.lr_background.setBackground(ActivityCompat.getDrawable(context,R.drawable.bcg_item_y));
        }   else{
            vholder.txt_ratingStatus.setText("Hazırlanyor");

            vholder.lr_background.setBackground(ActivityCompat.getDrawable(context,R.drawable.bcg_item_y2));
        }


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");

        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(order.getOrderDateTime());
            SimpleDateFormat  formatter = new SimpleDateFormat(" dd-MM-yyyy hh:mm ");

            vholder.txt_time.setText(formatter.format(convertedDate).toString());


        } catch (ParseException e) {


            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return orderCurrent.size();
    }


    private  static  class  ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txt_adresTittle,txt_adresdetail,txt_time,txt_ratingStatus;
        RatingBar ratingBar;

        LinearLayout lr_background;
        public ViewHolder(View itemView) {
            super(itemView);


            txt_adresTittle = (TextView)itemView.findViewById(R.id.txt_adressTittle);
            txt_adresdetail = (TextView)itemView.findViewById(R.id.txt_adresdetail);
            txt_ratingStatus = (TextView)itemView.findViewById(R.id.txt_ratingStatus);
            txt_time = (TextView)itemView.findViewById(R.id.txt_time);

            ratingBar = (RatingBar)itemView.findViewById(R.id.ratingBar);
            lr_background = (LinearLayout) itemView.findViewById(R.id.lr_background);

        }

        @Override
        public void onClick(View v) {

        }
    }

}

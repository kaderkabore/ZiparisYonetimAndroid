package oceannet.com.ziparisyonetim.UTILS.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import oceannet.com.ziparisyonetim.MODELS.Product;
import oceannet.com.ziparisyonetim.R;
import oceannet.com.ziparisyonetim.UI.MainMenu;

/**
 * Created by oceannet on 02/11/17.
 */

public class Adapter_product extends RecyclerView.Adapter {


    Context context;


    MainMenu mainMenu;
    ArrayList<Product> orderCurrent;

    public Adapter_product(Context context, ArrayList<Product> orderCurrent) {

        this.context = context;
        this.orderCurrent = orderCurrent;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.item_productrow, parent, false);

        RecyclerView.ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vholder = (ViewHolder) holder;




        if (position == orderCurrent.size() )   {
            vholder.lr_1.setVisibility(View.INVISIBLE);

            vholder.txt_price.setVisibility(View.GONE);
            vholder.txt_productName.setText("");
            vholder.txt_productQty.setText(orderCurrent.size()+ "");
        } else{

            Product order = orderCurrent.get(position);

            vholder.txt_price.setText(order.getPrice()+"");

            vholder.txt_price.setVisibility(View.GONE);
            vholder.txt_productName.setText(order.getProductName()+"");
            vholder.txt_productQty.setText(order.getStock()+"");
            vholder.lr_1.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public int getItemCount() {
        return orderCurrent.size() +1;
    }


    private static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_productName, txt_productQty, txt_price;
        LinearLayout lr_1;

        public ViewHolder(View itemView) {
            super(itemView);


            txt_productName = (TextView) itemView.findViewById(R.id.txt_productName);
            txt_productQty = (TextView) itemView.findViewById(R.id.txt_qty);
            txt_price = (TextView) itemView.findViewById(R.id.txt_price);
            lr_1 = (LinearLayout) itemView.findViewById(R.id.lr_1);


        }

        @Override
        public void onClick(View v) {

        }
    }

}
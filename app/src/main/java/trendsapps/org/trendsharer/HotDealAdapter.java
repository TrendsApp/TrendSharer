package trendsapps.org.trendsharer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by jawadhsr on 8/1/16.
 */
public class HotDealAdapter extends RecyclerView.Adapter<HotDealAdapter.ViewHolder>{

    ArrayList<HotDeal> hotdeals;

    public HotDealAdapter(ArrayList<HotDeal> deals){
        this.hotdeals = deals;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.shopName.setText(hotdeals.get(position).getShopName());
        holder.discount.setText(hotdeals.get(position).getDiscount());
        holder.content.setText(hotdeals.get(position).getContent());
        boolean hasImg = hotdeals.get(position).hasImageinDeal();
        if(hasImg)
            holder.viewImage.setImageBitmap(hotdeals.get(position).getImageAsBitMap());
        System.out.println("gfgsfsdfs");
    }




    @Override
    public int getItemCount() {
            return hotdeals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView shopName;
        TextView discount;
        TextView content;
        TextView dateTime;
        ImageView viewImage;
        public ViewHolder(View itemView)
        {
            super(itemView);
            shopName = (TextView) itemView.findViewById(R.id.deal_shop_name);
            discount = (TextView) itemView.findViewById(R.id.deal_discount);
            content = (TextView) itemView.findViewById(R.id.deal_content);
            //dateTime = (TextView) itemView.findViewById(R.id.deal_data_time);
            viewImage = (ImageView) itemView.findViewById(R.id.deal_view_image);
        }
    }
}
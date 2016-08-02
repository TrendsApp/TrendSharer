package trendsapps.org.trendsharer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jawadhsr on 8/1/16.
 */
public class HotDealAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

@Override
public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view,parent,false));
        }

@Override
public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

@Override
public int getItemCount() {
        return 10;
        }

public class ViewHolder extends RecyclerView.ViewHolder{

    public ViewHolder(View itemView) {
        super(itemView);
    }
}
}
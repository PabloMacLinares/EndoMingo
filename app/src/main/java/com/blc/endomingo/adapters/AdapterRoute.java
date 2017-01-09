package com.blc.endomingo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blc.endomingo.R;
import com.blc.endomingo.pojo.Route;
import com.blc.endomingo.utils.Utils;

import java.util.List;

/**
 * Created by Pablo on 01/01/2017.
 */

public class AdapterRoute extends RecyclerView.Adapter<AdapterRoute.ViewHolderRoute>{

    private List<Route> routes;

    public AdapterRoute(List<Route> routes){
        this.routes = routes;
    }

    public class ViewHolderRoute extends RecyclerView.ViewHolder{

        public TextView tvTitle;
        public TextView tvDetails;

        public ViewHolderRoute(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDetails = (TextView) itemView.findViewById(R.id.tvDetails);
        }
    }

    @Override
    public ViewHolderRoute onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_route, parent, false);
        return new ViewHolderRoute(v);
    }

    @Override
    public void onBindViewHolder(ViewHolderRoute holder, int position) {
        final Route route = routes.get(position);
        holder.tvTitle.setText("Id: " + route.getId() + ", " + route.getDate() + ", Dur: " + Utils.formatTime(route.getDuration()));
        String details = String.format("Dst: %.2f m, Cls: %.2f, Pts: %d", route.getDistance(), route.getCalories(), route.getRoutePoints().size());
        holder.tvDetails.setText(details);
    }

    @Override
    public int getItemCount() {
        if(routes != null) {
            return routes.size();
        }else{
            return 0;
        }
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        if(routes != null) {
            this.routes = routes;
            notifyDataSetChanged();
        }
    }
}

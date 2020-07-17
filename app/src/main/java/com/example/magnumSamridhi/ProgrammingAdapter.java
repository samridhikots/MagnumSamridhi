package com.example.magnumSamridhi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProgrammingAdapter extends RecyclerView.Adapter<ProgrammingAdapter.ProgrammingHolder>  {

    private Context context;
    private ArrayList<User> data;
    public ProgrammingAdapter(Context context, ArrayList<User> data)
    {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public ProgrammingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from (parent.getContext ());
        View view = inflater.inflate (R.layout.list_item_layout, parent, false);
        return new ProgrammingHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgrammingHolder holder, int position) {
        final User user = data.get (position);
        holder.txtUser.setText (user.getLogin ());
        Glide.with (holder.imgUser.getContext ()).load (user.getAvatarUrl ()).into (holder.imgUser);
        holder.itemView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //Toast.makeText (context,user.getLogin () +"was clicked", Toast.LENGTH_SHORT).show ();
                String url = user.getHtmlUrl ();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                i.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size ();
    }

    public class ProgrammingHolder extends RecyclerView.ViewHolder{
        ImageView imgUser;
        TextView txtUser;
        public ProgrammingHolder(@NonNull View itemView) {
            super (itemView);
            imgUser = (ImageView) itemView.findViewById (R.id.imgUser);
            txtUser = (TextView) itemView.findViewById (R.id.txtUser);

        }
    }

}

package com.ankit.educommunicate.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ankit.educommunicate.R;
import com.ankit.educommunicate.model.MessagesModel;
import com.ankit.educommunicate.utils.ItemClickListener;
import com.ankit.educommunicate.views.fragments.OutboxFragment;

import org.ocpsoft.prettytime.PrettyTime;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class OutboxAdapter extends RecyclerView.Adapter<OutboxAdapter.MyViewHolder> {
    private ArrayList<MessagesModel> outbox_messages;
    private ItemClickListener clickListener;
    Context mcontext;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView entity_name_initial_TV, recipient_TV, subject_TV, message_TV, time_TV;

        public MyViewHolder(View view) {
            super(view);
            entity_name_initial_TV = view.findViewById(R.id.entity_name_initial);
            recipient_TV = view.findViewById(R.id.recepient_id);
            subject_TV = view.findViewById(R.id.subject);
            message_TV = view.findViewById(R.id.message);
            time_TV = view.findViewById(R.id.time);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }

    public void setClickListener(OutboxFragment itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public OutboxAdapter(ArrayList<MessagesModel> outbox_messages) {
        this.outbox_messages = outbox_messages;
    }

    @Override
    public OutboxAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_messages, parent, false);
        mcontext = parent.getContext();

        return new OutboxAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OutboxAdapter.MyViewHolder holder, int position) {
        MessagesModel message = outbox_messages.get(position);
        holder.entity_name_initial_TV.setText(message.getRecipient_Id().charAt(0)+"");
        holder.recipient_TV.setText(message.getRecipient_Id());

        String subject = message.getSubject();
        if(subject.length()>40){
            subject = subject.substring(0, 39)+"...";
        }
        holder.subject_TV.setText(subject);

        String message_data = message.getMsgData();
        String message_formatted = HtmlCompat.fromHtml(message_data, 0).toString();
        if(message_formatted.length()>50){
            message_formatted = message_formatted.substring(0, 49)+"...";
        }
        holder.message_TV.setText(message_formatted);

        String date_time = message.getSentDate().substring(0, message.getSentDate().indexOf("T"))+" "+message.getSentTime();
        Timestamp timestamp = Timestamp.valueOf(date_time);
        PrettyTime p = new PrettyTime();
        Date date = new Date(timestamp.getTime());
        holder.time_TV.setText(p.format(date));
    }

    @Override
    public int getItemCount() {
        return outbox_messages.size();
    }
}
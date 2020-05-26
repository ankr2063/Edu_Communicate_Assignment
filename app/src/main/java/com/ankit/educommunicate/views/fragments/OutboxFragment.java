package com.ankit.educommunicate.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ankit.educommunicate.utils.ItemClickListener;
import com.ankit.educommunicate.model.MessagesModel;
import com.ankit.educommunicate.R;
import com.ankit.educommunicate.network.RetrofitRestRepository;
import com.ankit.educommunicate.utils.SimpleDividerItemDecoration;
import com.ankit.educommunicate.views.activities.MessageDetailsActivity;
import com.ankit.educommunicate.views.adapters.OutboxAdapter;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class OutboxFragment extends Fragment implements ItemClickListener {

    ArrayList<MessagesModel> outbox_messages = new ArrayList<>();
    RecyclerView recyclerView;
    OutboxAdapter itemsAdapter;
    CompositeSubscription compositeSubscription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);

        itemsAdapter = new OutboxAdapter(outbox_messages);
        recyclerView = view.findViewById(R.id.rview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemsAdapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        itemsAdapter.setClickListener(this);
        getOutboxMessages();
        return view;
    }

    @Override
    public void onClick(View view, int position) {
        Intent i = new Intent(view.getContext(), MessageDetailsActivity.class);
        i.putExtra("message_object", outbox_messages.get(position));
        startActivity(i);
    }

    protected void getOutboxMessages(){
        compositeSubscription = new CompositeSubscription();
        compositeSubscription.add(RetrofitRestRepository.getRetrofit().getOutboxMessages("S011", "TID0006")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleOutboxMessagesResponse,this::handleErrorOutboxMessagesResponse));
    }

    protected void handleOutboxMessagesResponse(ArrayList<MessagesModel> outbox_messages_api){
        outbox_messages.addAll(outbox_messages_api);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    protected void handleErrorOutboxMessagesResponse(Throwable error){
        Toast.makeText(getContext(), "Some error occurred", Toast.LENGTH_LONG).show();

    }
}

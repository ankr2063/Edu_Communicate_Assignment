package com.ankit.educommunicate.views.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ankit.educommunicate.R;
import com.ankit.educommunicate.model.ComposeMessageRequest;
import com.ankit.educommunicate.model.FacultyModel;
import com.ankit.educommunicate.model.SenderModel;
import com.ankit.educommunicate.network.RetrofitRestRepository;
import com.ankit.educommunicate.utils.Constants;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ComposeMessageActivity extends AppCompatActivity {

    CompositeSubscription compositeSubscription;
    ArrayList<FacultyModel> faculty_list = new ArrayList<>();
    ArrayList<SenderModel> sender_list = new ArrayList<>();
    ArrayList<String> recipient_list_selected_ids = new ArrayList<>();
    ArrayList<String> recipient_list_selected_names = new ArrayList<>();
    ArrayList<String> sender_names = new ArrayList<>();
    ArrayList<String> recipient_names = new ArrayList<>();
    ComposeMessageRequest composeMessageRequest = new ComposeMessageRequest();
    EditText subject_ET, message_ET;
    TextView select_senders_TV, select_recipients_TV, from_data_TV;
    Integer sender_position;
    ImageView clear_sender, clear_recipients, cross_1, cross_2;
    ListView selected_recipients_list;
    int counter;
    LinearLayout loader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_message);

        subject_ET = findViewById(R.id.subject_data);
        message_ET = findViewById(R.id.message_editText);
        select_recipients_TV = findViewById(R.id.select_recipients);
        select_senders_TV = findViewById(R.id.select_sender);
        from_data_TV = findViewById(R.id.from_data);
        clear_sender = findViewById(R.id.clear_sender);
        clear_recipients = findViewById(R.id.clear_recipients);
        selected_recipients_list = findViewById(R.id.selected_recipients_list);
        cross_1 = findViewById(R.id.cross_1);
        cross_2 = findViewById(R.id.cross_2);
        loader = findViewById(R.id.loader);
        loader.setVisibility(View.VISIBLE);

        clear_sender.setVisibility(View.GONE);
        clear_recipients.setVisibility(View.GONE);
        cross_1.setVisibility(View.GONE);
        cross_2.setVisibility(View.GONE);

        clear_sender.setOnClickListener(view -> {
            sender_position = null;
            from_data_TV.setVisibility(View.GONE);
            select_senders_TV.setVisibility(View.VISIBLE);
            clear_sender.setVisibility(View.GONE);
            cross_1.setVisibility(View.GONE);
        });

        clear_recipients.setOnClickListener(view -> {
            recipient_list_selected_names.clear();
            recipient_list_selected_ids.clear();
            selected_recipients_list.setVisibility(View.GONE);
            select_recipients_TV.setVisibility(View.VISIBLE);
            clear_recipients.setVisibility(View.GONE);
            cross_2.setVisibility(View.GONE);
        });

        getSenderList();

        select_senders_TV.setOnClickListener(view -> {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.selector_dialog);
            TextView select_sender = dialog.findViewById(R.id.select_sender_TV);
            ImageView apply = dialog.findViewById(R.id.apply);
            apply.setVisibility(View.GONE);
            select_sender.setText("Select Sender");
            ListView sender_listview = dialog.findViewById(R.id.list);
            ArrayAdapter adapter = new ArrayAdapter<>(this,
                    R.layout.list_sender, sender_names);
            sender_listview.setAdapter(adapter);
            sender_listview.setOnItemClickListener((AdapterView<?> adapterView, View view1, int i, long l) -> {
                select_senders_TV.setVisibility(View.GONE);
                from_data_TV.setVisibility(View.VISIBLE);
                from_data_TV.setText(sender_names.get(i));
                sender_position = i;
                clear_sender.setVisibility(View.VISIBLE);
                cross_1.setVisibility(View.VISIBLE);
                dialog.dismiss();
            });
            dialog.show();

        });

        select_recipients_TV.setOnClickListener(view -> {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.selector_dialog);
            TextView select_sender = dialog.findViewById(R.id.select_sender_TV);
            ImageView apply = dialog.findViewById(R.id.apply);
            apply.setVisibility(View.VISIBLE);
            select_sender.setText("Select Recipients");
            ListView recipient_listview = dialog.findViewById(R.id.list);
            ArrayAdapter adapter = new ArrayAdapter<>(this,
                    R.layout.list_sender, recipient_names);
            recipient_listview.setAdapter(adapter);
            recipient_listview.setOnItemClickListener((AdapterView<?> adapterView, View view1, int i, long l) -> {
                TextView textView = view1.findViewById(R.id.label);
                if(recipient_list_selected_ids!=null){
                    if(!recipient_list_selected_ids.contains(faculty_list.get(i).getFaculty_Id())){
                        textView.setBackgroundColor(getResources().getColor(R.color.gray));
                        recipient_list_selected_ids.add(faculty_list.get(i).getFaculty_Id());
                        recipient_list_selected_names.add(recipient_names.get(i));
                    }else {
                        textView.setBackgroundColor(getResources().getColor(R.color.white));
                        recipient_list_selected_ids.remove(faculty_list.get(i).getFaculty_Id());
                        recipient_list_selected_names.remove(recipient_names.get(i));
                    }
                }
            });
            apply.setOnClickListener(view12 -> {
                setTextViews();
                clear_recipients.setVisibility(View.VISIBLE);
                cross_2.setVisibility(View.VISIBLE);
                select_recipients_TV.setVisibility(View.GONE);
                dialog.dismiss();
            });
            dialog.show();

        });

    }

    public void setTextViews(){
        selected_recipients_list.setVisibility(View.VISIBLE);
        ArrayAdapter adapter = new ArrayAdapter<>(this,
                R.layout.list_sender, recipient_list_selected_names);
        selected_recipients_list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_send) {
            counter = 0;
            for (int i = 0; i<recipient_list_selected_ids.size(); i++) {
                String subject = subject_ET.getText().toString();
                String message = message_ET.getText().toString();
                composeMessageRequest.setStud_Id(sender_list.get(sender_position).getStud_Id());
                composeMessageRequest.setRecipient_Id(recipient_list_selected_ids.get(i));
                composeMessageRequest.setSchool_Id("S011");
                composeMessageRequest.setMsgData(message);
                composeMessageRequest.setSubject(subject);
                composeMessageRequest.setSentBy("parent");
                composeMessageRequest.setSenderIP("47.8.25.35");
                sendMessage();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void sendMessage(){
        compositeSubscription = new CompositeSubscription();
        compositeSubscription.add(RetrofitRestRepository.getRetrofit(Constants.token).sendMessage(Constants.token, composeMessageRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleSendMessageResponse, this::handleErrorSendMessageResponse));

    }

    protected void handleSendMessageResponse(String message){
        counter++;
        if(counter == recipient_list_selected_ids.size()){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    protected void handleErrorSendMessageResponse(Throwable error){
        Toast.makeText(this, "Some error occurred", Toast.LENGTH_LONG).show();

    }

    protected void getFacultyList(){
        compositeSubscription = new CompositeSubscription();
        compositeSubscription.add(RetrofitRestRepository.getRetrofit().getFacultyList("S011")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleFacultyListResponse, this::handleErrorFacultyListResponse));

    }

    protected void handleFacultyListResponse(ArrayList<FacultyModel> faculty_list_api){
        faculty_list.addAll(faculty_list_api);
        for (int i = 0; i<faculty_list.size(); i++){
            recipient_names.add(faculty_list.get(i).getFName());
        }
        loader.setVisibility(View.GONE);
    }

    protected void handleErrorFacultyListResponse(Throwable error){
        Toast.makeText(this, "Some error occurred", Toast.LENGTH_LONG).show();

    }

    protected void getSenderList(){
        compositeSubscription = new CompositeSubscription();
        compositeSubscription.add(RetrofitRestRepository.getRetrofit().getSenderList("S011")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleSenderListResponse, this::handleErrorSenderListResponse));

    }

    protected void handleSenderListResponse(ArrayList<SenderModel> sender_list_api){
        sender_list.addAll(sender_list_api);
        for (int i = 0; i<sender_list.size(); i++){
            sender_names.add(sender_list.get(i).getSName());
        }
        getFacultyList();
    }

    protected void handleErrorSenderListResponse(Throwable error){
        Toast.makeText(this, "Some error occurred", Toast.LENGTH_LONG).show();

    }

}

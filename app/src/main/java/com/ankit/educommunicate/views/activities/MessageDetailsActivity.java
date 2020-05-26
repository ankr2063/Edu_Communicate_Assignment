package com.ankit.educommunicate.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.ankit.educommunicate.R;
import com.ankit.educommunicate.model.MessagesModel;

import org.ocpsoft.prettytime.PrettyTime;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MessageDetailsActivity extends AppCompatActivity {

    Intent intent;
    MessagesModel message;
    TextView entity_name_initial_TV, sender_TV, time_TV, recipient_TV, message_TV, subject_TV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        intent = getIntent();
        message = (MessagesModel) intent.getSerializableExtra("message_object");

        entity_name_initial_TV = findViewById(R.id.entity_name_initial);
        sender_TV = findViewById(R.id.sender);
        time_TV = findViewById(R.id.time);
        recipient_TV = findViewById(R.id.recipient);
        message_TV = findViewById(R.id.message);
        subject_TV = findViewById(R.id.subject);

        entity_name_initial_TV.setText(message.getSender_Id().charAt(0)+"");
        sender_TV.setText(message.getSender_Id());
        recipient_TV.setText("to "+message.getRecipient_Id());
        message_TV.setText(HtmlCompat.fromHtml(message.getMsgData(), 0).toString());
        subject_TV.setText(message.getSubject());

        String date_time = message.getSentDate().substring(0, message.getSentDate().indexOf("T"))+" "+message.getSentTime();
        Timestamp timestamp = Timestamp.valueOf(date_time);
        Date date = new Date(timestamp.getTime());

        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.US);
        String formatted_date = format.format(date);

        time_TV.setText(formatted_date);


    }
}

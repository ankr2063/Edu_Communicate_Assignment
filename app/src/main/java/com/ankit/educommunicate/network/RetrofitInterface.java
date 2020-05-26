package com.ankit.educommunicate.network;


import com.ankit.educommunicate.model.ComposeMessageRequest;
import com.ankit.educommunicate.model.FacultyModel;
import com.ankit.educommunicate.model.MessagesModel;
import com.ankit.educommunicate.model.SenderModel;

import java.util.ArrayList;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface RetrofitInterface {

    @POST("InboxList")
    Observable<ArrayList<MessagesModel>> getInboxMessages(@Query("School_Id") String School_Id, @Query("User_Id") String User_Id);

    @POST("OutboxList")
    Observable<ArrayList<MessagesModel>> getOutboxMessages(@Query("School_Id") String School_Id, @Query("User_Id") String User_Id);

    @POST("FacultyList")
    Observable<ArrayList<FacultyModel>> getFacultyList(@Query("School_Id") String School_Id);

    @POST("SenderDetail")
    Observable<ArrayList<SenderModel>> getSenderList(@Query("School_Id") String School_Id);

    @POST("ComposeEntry")
    Observable<String> sendMessage(@Header("Authorization") String token, @Body ComposeMessageRequest composeMessageRequest);

}

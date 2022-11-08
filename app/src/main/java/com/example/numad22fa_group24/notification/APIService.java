package com.example.numad22fa_group24.notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAANUKK7UY:APA91bGXw37tzCT9rHMK-1oxMTNsQawrTtwyeKRw4_H2kX-vCBad4n4G_Duy4wKfpPr5sRqMqFRlKWzNht0kTiIUS8GUJn7WtR4GwhFo48wo4xx35KpknhxU037fXYMXmXVkmw4MFwMz"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}

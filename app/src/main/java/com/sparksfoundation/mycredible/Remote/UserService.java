package com.sparksfoundation.mycredible.Remote;

import com.sparksfoundation.mycredible.LoginPOJOClasses.LoginSignupData;
import com.sparksfoundation.mycredible.LoginPOJOClasses.ServerTest;
import com.sparksfoundation.mycredible.LoginPOJOClasses.User;
import com.sparksfoundation.mycredible.PersonalDetailsPOJOClasses.PersonalDetails;
import com.sparksfoundation.mycredible.PersonalDetailsPOJOClasses.PersonalDetailsData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    @GET("test")
    Call<ServerTest> getServerStatus();

    @POST("user/login")
    Call<LoginSignupData> getUser(@Body User user);

    @POST("user/signup")
    Call<LoginSignupData> addUser(@Body User user);

    @GET("user/personaldetail/{id}")
    Call<PersonalDetailsData> getPersonalDetails(@Path("id") int id);

    @POST("user/personaldetail/{id}")
    Call<PersonalDetailsData> setPersonalDetails(@Path("id") int id, @Body PersonalDetails personalDetails);

}

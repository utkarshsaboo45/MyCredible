package com.sparksfoundation.mycredible.Remote;

import com.sparksfoundation.mycredible.EducationDetailsPOJOClasses.EducationDetails;
import com.sparksfoundation.mycredible.EducationDetailsPOJOClasses.EducationDetailsData;
import com.sparksfoundation.mycredible.LoginPOJOClasses.LoginSignupData;
import com.sparksfoundation.mycredible.LoginPOJOClasses.ServerTest;
import com.sparksfoundation.mycredible.LoginPOJOClasses.User;
import com.sparksfoundation.mycredible.PersonalDetailsPOJOClasses.PersonalDetails;
import com.sparksfoundation.mycredible.PersonalDetailsPOJOClasses.PersonalDetailsData;
import com.sparksfoundation.mycredible.ProfessionalDetailsPOJOClasses.ProfessionalDetails;
import com.sparksfoundation.mycredible.ProfessionalDetailsPOJOClasses.ProfessionalDetailsData;
import com.sparksfoundation.mycredible.ProfilePicPOJOClasses.Photo;
import com.sparksfoundation.mycredible.StatusMessage;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Path;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;

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

    @PUT("user/personaldetail/{id}")
    Call<PersonalDetailsData> updatePersonalDetails(@Path("id") int id, @Body PersonalDetails personalDetails);

    @GET("user/personaldetail/profilepic/{id}")
    Call<byte[]> getProfilePic(@Path("id") int id);

//    @POST("user/personaldetail/profilepic")
//    Call<StatusMessage> setProfilePic(String photo, int id);

    @POST("user/personaldetail/pp/post")
    Call<StatusMessage> setProfilePic(@Body Photo photo);

    @GET("user/educationdetail/{id}")
    Call<EducationDetailsData> getEducationalDetails(@Path("id") int id);

    @POST("user/educationdetail/{id}")
    Call<EducationDetailsData> setEducationalDetails(@Path("id") int id, @Body EducationDetails educationDetails);

    @PUT("user/educationdetail/{id}")
    Call<EducationDetailsData> updateEducationalDetails(@Path("id") int id, @Body EducationDetails educationDetails);

    @DELETE("user/educationdetail/{id}")
    Call<StatusMessage> deleteEducationalDetails(@Path("id") int id);

//    @GET("user/educationdetail/certificate/{id}")

//    @POST("user/educationdetail/certificate")

    @GET("user/professionaldetail/{id}")
    Call<ProfessionalDetailsData> getProfessionalDetails(@Path("id") int id);

    @POST("user/professionaldetail/{id}")
    Call<ProfessionalDetailsData> setProfessionalDetails(@Path("id") int id, @Body ProfessionalDetails professionalDetails);

    @PUT("user/professionaldetail/{id}")
    Call<ProfessionalDetailsData> updateProfessionalDetails(@Path("id") int id, @Body ProfessionalDetails professionalDetails);

    @DELETE("user/professionaldetail/{id}")
    Call<StatusMessage> deleteProfessionalDetails(@Path("id") int id);

}

package todolist.balvinder.com.todolist.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import todolist.balvinder.com.todolist.util.ApiResponse;
import todolist.balvinder.com.todolist.util.DeleteApiResponse;
import todolist.balvinder.com.todolist.util.NewTask;

public interface ApiService {

    @GET("tasks")
    Call<List<ApiResponse>> getTask();

    @POST("tasks")
    Call<ApiResponse> createTask(@Body NewTask newTask);

    @DELETE("tasks/{taskId}")
    Call<DeleteApiResponse> deleteTask(@Path("taskId") String TaskId);

}
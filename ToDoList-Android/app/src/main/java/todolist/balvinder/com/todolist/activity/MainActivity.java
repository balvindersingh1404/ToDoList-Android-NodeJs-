package todolist.balvinder.com.todolist.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import todolist.balvinder.com.todolist.util.NewTask;
import todolist.balvinder.com.todolist.R;
import todolist.balvinder.com.todolist.rest.ApiClient;
import todolist.balvinder.com.todolist.util.ApiResponse;

public class MainActivity extends AppCompatActivity {

    static List<ApiResponse> data;
    static ListAdapter adapter;
    ProgressDialog progress;
    Context context;
    Call<List<ApiResponse>> call;
    Call<ApiResponse> call1;
    ImageView editImageView, deleteImageView;
    FloatingActionButton floatingActionButton;
    String nameOfTask = " ";
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        init();
        registerListeners();

        //get the list of to-do tasks
        getTaskList();

    }

    //************************************Initalize variables**************************************
    public void init() {
        lv = (ListView) findViewById(R.id.offerslist);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button_fab_with_listview);
        editImageView = (ImageView) findViewById(R.id.editImageView);
        deleteImageView = (ImageView) findViewById(R.id.deleteImageView);
    }


    //************************************Register the listeners**************************************
    public void registerListeners() {

        //for floating action button -------------ADD task
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("CREATE TASK");
                alertDialog.setMessage("Enter new task");

                final EditText input = new EditText(MainActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                alertDialog.setIcon(R.drawable.task);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                nameOfTask = input.getText().toString();
                                createNewTask();
                            }
                        });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }

        });


//        //for edit ImageView -------------EDIT task
//        editImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//
//        //for delete ImageView -------------DELETE task
//        deleteImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }


    //************************************Get the list of to-do works**************************************
    private void getTaskList() {
        progress = new ProgressDialog(context);
        progress.setMessage("Please Wait. ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        call = ApiClient.getInstance().getTask();
        call.enqueue(new Callback<List<ApiResponse>>() {
            @Override
            public void onResponse(Call<List<ApiResponse>> call, Response<List<ApiResponse>> response) {
                progress.dismiss();
                Log.i("RESPONSE: ", response.body().toString());

                data = (List<ApiResponse>) response.body();
                adapter = new ListAdapter(context, data);
                lv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ApiResponse>> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }


    //************************************Create the new task**************************************
    private void createNewTask() {
        progress = new ProgressDialog(context);
        progress.setMessage("Please Wait. ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        NewTask newTask = new NewTask(nameOfTask);

        call1 = ApiClient.getInstance().createTask(newTask);
        call1.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                progress.dismiss();
                Log.i("RESPONSE: ", response.body().toString());

                data.add((ApiResponse) response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }

    //************************************Create the new task**************************************
//     void deleteTask(String taskId) {
//        progress = new ProgressDialog(context);
//        progress.setMessage("Please Wait. ");
//        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progress.show();
//
//        call1 = ApiClient.getInstance().deleteTask(taskId);
//        call1.enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                progress.dismiss();
//                Log.i("RESPONSE: ", response.body().toString());
//
//                data.add((ApiResponse) response.body());
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse> call, Throwable t) {
//                Log.e("error", t.toString());
//            }
//        });
//    }


}

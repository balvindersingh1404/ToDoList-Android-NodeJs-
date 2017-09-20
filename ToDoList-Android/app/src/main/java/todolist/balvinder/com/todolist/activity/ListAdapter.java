package todolist.balvinder.com.todolist.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import todolist.balvinder.com.todolist.util.DeleteApiResponse;
import todolist.balvinder.com.todolist.R;
import todolist.balvinder.com.todolist.rest.ApiClient;
import todolist.balvinder.com.todolist.util.ApiResponse;

public class ListAdapter extends ArrayAdapter<ArrayList<ApiResponse>> {
    Context context;
    List<ApiResponse> list;
    ImageView editImageView, deleteImageView;
    ProgressDialog progress;
    Call<DeleteApiResponse> call;


    public ListAdapter(Context context, List<ApiResponse> list) {
        super(context, R.layout.list_item_layout);
        this.list = list;
        this.context = context;

    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ApiResponse data = list.get(position);
        ViewHolder holder = null;
        if (convertView == null) {

            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (data != null) {
            holder.name.setText(data.getName());
            holder.date.setText(data.getCreatedDate());
            holder.status.setText(data.getStatus().toString());

        }


        deleteImageView = (ImageView) convertView.findViewById(R.id.deleteImageView);
        editImageView = (ImageView) convertView.findViewById(R.id.editImageView);

        deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteTask(data.getId().toString(), position);
            }
        });

        editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }
//****************************************DELETE the task on click of delete imageView*****************************
    void deleteTask(String taskId, final int position) {
        progress = new ProgressDialog(context);
        progress.setMessage("Please Wait. ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        call = ApiClient.getInstance().deleteTask(taskId);
        call.enqueue(new Callback<DeleteApiResponse>() {
            @Override
            public void onResponse(Call<DeleteApiResponse> call, Response<DeleteApiResponse> response) {
                progress.dismiss();
                Log.i("RESPONSE: ", response.body().toString());
                MainActivity.data.remove(position);
                MainActivity.adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<DeleteApiResponse> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView editImageView, deleteImageView;
        private TextView name, date, status;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            name = (TextView) itemLayoutView.findViewById(R.id.name);
            date = (TextView) itemLayoutView.findViewById(R.id.date);
            status = (TextView) itemLayoutView.findViewById(R.id.status);

        }


    }

}
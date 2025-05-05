package com.example.gradconnect2025;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ManageJobsAdapter extends RecyclerView.Adapter<ManageJobsAdapter.JobViewHolder> {

    public interface OnJobActionListener {
        void onEdit(Job job);
        void onDelete(Job job);
    }

    private Context context;
    private List<Job> jobList;
    private OnJobActionListener listener;

    public ManageJobsAdapter(Context context, List<Job> jobList, OnJobActionListener listener) {
        this.context = context;
        this.jobList = jobList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_job, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        Job job = jobList.get(position);
        holder.title.setText(job.getTitle());
        holder.description.setText(job.getDescription());
        holder.company.setText(job.getCompany());

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(job));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(job));
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, company;
        Button btnEdit, btnDelete;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.jobTitle);
            description = itemView.findViewById(R.id.jobDescription);
            company = itemView.findViewById(R.id.jobCompany);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}

package co.edu.eci.ieti.android.network;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import co.edu.eci.ieti.R;
import co.edu.eci.ieti.android.model.Task;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder>
{

    List<Task> taskList = null;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType )
    {
        return new ViewHolder( LayoutInflater.from( parent.getContext() ).inflate( R.layout.task_row, parent, false ) );
    }

    @Override
    public void onBindViewHolder( @NonNull ViewHolder holder, int position )
    {
        Task task = taskList.get( position );
        ((TextView) holder.itemView.findViewById(R.id.task_description)).setText(task.getDescription());
        ((TextView) holder.itemView.findViewById(R.id.task_date)).setText(task.getDueDate().toString());
        ((TextView) holder.itemView.findViewById(R.id.task_priority)).setText(task.getPriority());
        ((TextView) holder.itemView.findViewById(R.id.task_assignedTo)).setText(task.getAssignedTo().getEmail());
    }

    @Override
    public int getItemCount()
    {
        return taskList != null ? taskList.size() : 0;
    }

    public void updateTasks(List<Task> tasks){
        this.taskList = tasks;
        notifyDataSetChanged();
    }

    class ViewHolder
            extends RecyclerView.ViewHolder
    {
        ViewHolder( @NonNull View itemView )
        {
            super( itemView );
        }
    }

}

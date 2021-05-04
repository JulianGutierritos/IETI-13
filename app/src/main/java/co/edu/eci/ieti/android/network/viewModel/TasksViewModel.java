package co.edu.eci.ieti.android.network.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import co.edu.eci.ieti.android.model.Task;
import co.edu.eci.ieti.android.repository.TaskRepository;

public class TasksViewModel extends AndroidViewModel {
    private MutableLiveData<List<Task>> tasks;
    private TaskRepository taskRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool( 1 );

    public TasksViewModel(Application application){
        super(application);
    }

    public LiveData<List<Task>> getTasks(String token){
        if (tasks == null){
            tasks = new MutableLiveData<List<Task>>();
            taskRepository = new TaskRepository(getApplication() ,token);
            loadTasks();
        }
        return tasks;
    }

    public void loadTasks(){
        executorService.execute(() -> {
            tasks.postValue(taskRepository.getAllTasks());
        });
    }

}

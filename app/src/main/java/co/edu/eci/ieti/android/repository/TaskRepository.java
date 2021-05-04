package co.edu.eci.ieti.android.repository;

import android.app.Application;

import java.io.IOException;
import java.util.List;

import co.edu.eci.ieti.android.dao.TaskDao;
import co.edu.eci.ieti.android.database.TaskDatabase;
import co.edu.eci.ieti.android.model.Task;
import co.edu.eci.ieti.android.network.RetrofitNetwork;
import co.edu.eci.ieti.android.network.service.TaskService;

public class TaskRepository {
    private TaskDao mTaskDao;
    private TaskService taskService;

    public TaskRepository(Application application, String token) {
        TaskDatabase db = TaskDatabase.getDatabase(application);
        mTaskDao = db.taskDao();
        taskService = new RetrofitNetwork(token).getTaskService();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public List<Task> getAllTasks()  {
        List<Task> tasks;
        mTaskDao.deleteAll();
        try {
            tasks = taskService.getTasks().execute().body();
            for (Task task : tasks){
                mTaskDao.insert(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mTaskDao.getAllTask();
    }

    void insert(Task task) {
        mTaskDao.insert(task);
    }
}

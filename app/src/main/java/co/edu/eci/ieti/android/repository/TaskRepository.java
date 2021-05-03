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

    TaskRepository(Application application, String token) {
        TaskDatabase db = TaskDatabase.getDatabase(application);
        mTaskDao = db.taskDao();
        taskService = new RetrofitNetwork(token).getTaskService();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    List<Task> getAllTasks() throws IOException {
        List<Task> tasks;
        mTaskDao.deleteAll();
        tasks = taskService.getTasks().execute().body();
        for (Task task : tasks){
            mTaskDao.insert(task);
        }
        return tasks;
    }

    void insert(Task task) {
        mTaskDao.insert(task);
    }
}

package com.example.workflow_s.ui.task;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.ui.task.adapter.TaskAdapter;

import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity implements TaskContract.TaskView{

    private RecyclerView taskRecyclerView;
    private RecyclerView.Adapter mTaskAdapter;
    private RecyclerView.LayoutManager taskLayoutManager;
    private List<Task> mTask;

    private TaskContract.TaskPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        setupRecyclerView();
        initData();
    }

    public void initData() {
        mPresenter = new TaskPresenterImpl(this, new TaskInteractor());
        mPresenter.loadTasks(2);
    }

    private void setupRecyclerView() {
        //get task from checlistId
        taskRecyclerView = findViewById(R.id.rv_task);
        taskRecyclerView.setHasFixedSize(true);
        taskLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        taskRecyclerView.setLayoutManager(taskLayoutManager);

        mTask = new ArrayList<>();
        mTaskAdapter = new TaskAdapter((List<Task>) mTask);
        taskRecyclerView.setAdapter(mTaskAdapter);
    }


    @Override
    public void setDataToTaskRecyclerView(ArrayList<Task> datasource) {
        mTaskAdapter = new TaskAdapter(datasource);
        taskRecyclerView.setAdapter(mTaskAdapter);
    }
}

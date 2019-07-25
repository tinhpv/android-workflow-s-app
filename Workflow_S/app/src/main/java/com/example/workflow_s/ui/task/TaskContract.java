package com.example.workflow_s.ui.task;

import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.TaskMember;
import com.example.workflow_s.model.User;
import com.example.workflow_s.ui.checklist.ChecklistContract;

import java.util.ArrayList;
import java.util.List;

public interface TaskContract {

    // view comm with presenter
    interface TaskPresenter {
        void onDestroy();
        void loadChecklistData(int orgId, int checklistId);
        void getTaskMember(int taskId, boolean isSelected);
        void loadTasks(int checklistId);
        void changeTaskStatus(String userId, int taskId, String taskStatus);
        void changeChecklistStatus(int checklistId, String status);
        void getUserInfor(String userId);
        void renameTask(int taskId, String taskName);
    }

    interface TaskView {
        void finishGetChecklist(Checklist checklist);
        void finishedChangeTaskStatus(String status);
        void finishChangeChecklistStatus(String status);
        void finishGetInfo(User user);
        void finishRenameTask();
        void finishedLoadAllTasks(List<Task> taskList);
        void finishGetTaskMember(List<TaskMember> taskMemberList, boolean isSelected, int taskId);
    }

    interface TemplateView {
        void setDataToTaskRecyclerView(ArrayList<Task> datasource);
    }

    //presenter comm with model
    interface GetTaskDataInteractor {

        interface OnFinishedGetTasksListener {
            void onFinishedGetTasks(ArrayList<Task> taskArrayList);
            void onFailure(Throwable t);
        }

        interface OnFinishedChangeTaskStatusListener {
            void onFinishedChangeTaskStatus(String taskStatus);
            void onFailure(Throwable t);
        }

        interface OnFinishedChangeChecklistStatusListener {
            void onFinishedChangeChecklistStatus(String status);
            void onFailure(Throwable t);
        }

        interface OnFinishedLoadChecklistDataListener {
            void onFinishedGetChecklistData(Checklist checklist);
            void onFailure(Throwable t);
        }

        interface OnFinishedGetTaskMemberListener {
            void onFinishedGetTaskMember(List<TaskMember> taskMemberList, boolean isSelected, int taskId);
            void onFailure(Throwable t);
        }

        interface OnFinishedGetInforUserListener {
            void onFinishedGetUser(User user);
            void onFailure(Throwable t);
        }

        interface OnFinishedRenameTaskListener {
            void onFinishedRenameTask();
            void onFailure(Throwable t);
        }


        void renameTask(int taskId, String taskName, OnFinishedRenameTaskListener listener);
        void getUserById(String userId, OnFinishedGetInforUserListener listener);
        void getTaskMember(int taskId, boolean isSelected, OnFinishedGetTaskMemberListener listener);
        void getAllTasks(int checklistId, OnFinishedGetTasksListener onFinishedLIstener);
        void changeTaskStatus(String userId, int taskId, String taskStatus, OnFinishedChangeTaskStatusListener listener);
        void completeChecklist(int checklistId, String taskStatus, OnFinishedChangeChecklistStatusListener listener);
        void getChecklistData(int orgId, int checklistId, OnFinishedLoadChecklistDataListener listener);

    }
}

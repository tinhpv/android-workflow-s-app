package com.example.workflow_s.ui.task;

import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.TaskMember;
import com.example.workflow_s.model.User;

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
        void changeChecklistStatus(String userId, int checklistId, String status);
        void getUserInfor(String userId);
        void renameTask(int taskId, String taskName);
        void renameChecklist(int checklistId, String name);
        void deleteChecklist(int checklistId, String userId);
        void changePriorityTaskList(List<Task> taskList);
        void getUserList(int orgId);
    }

    interface TaskView {
        void finishGetChecklist(Checklist checklist);
        void finishedChangeTaskStatus(String status);
        void finishChangeChecklistStatus(String status);
        void finishGetInfo(User user);
        void finishRenameTask();
        void finishedLoadAllTasks(List<Task> taskList);
        void finishGetTaskMember(List<TaskMember> taskMemberList, boolean isSelected, int taskId);
        void finishGetUserList(List<User> userList);
        void finishedChangePriority();
        void finishRenameChecklist();
        void finishDeleteChecklist();
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

        interface OnFinishedGetMembers {
            void onFinishedGetMembers(List<User> userList);
            void onFailure(Throwable t);
        }

        interface OnFinishedChangePriorityTasks {
            void onFinishedChangePriorityTasks();
            void onFailure(Throwable t);
        }

        interface OnFinishedRenameChecklist {
            void onFinishedRenameChecklist();
            void onFailure(Throwable t);
        }

        interface OnFinishedDeleteChecklist {
            void onFinishedDeleteChecklist();
            void onFailure(Throwable t);
        }

        void deleteChecklist(int checklistId, String userId, OnFinishedDeleteChecklist listener);
        void renameChecklist(int checklistId, String name, OnFinishedRenameChecklist listener);
        void getMemberOrganization(int orgId, OnFinishedGetMembers onFinishedListener);
        void renameTask(int taskId, String taskName, OnFinishedRenameTaskListener listener);
        void getUserById(String userId, OnFinishedGetInforUserListener listener);
        void getTaskMember(int taskId, boolean isSelected, OnFinishedGetTaskMemberListener listener);
        void getAllTasks(int checklistId, OnFinishedGetTasksListener onFinishedLIstener);
        void changeTaskStatus(String userId, int taskId, String taskStatus, OnFinishedChangeTaskStatusListener listener);
        void completeChecklist(String userId, int checklistId, String taskStatus, OnFinishedChangeChecklistStatusListener listener);
        void getChecklistData(int orgId, int checklistId, OnFinishedLoadChecklistDataListener listener);
        void changePriorityTasks(List<Task> taskList, OnFinishedChangePriorityTasks listener);

    }
}

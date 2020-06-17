package com.example.workflow_s.ui.comment;

import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.Comment;

import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-23
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public interface CommentContract {

    interface CommentPresenter {
        void getAllComment(int taskId);
        void writeComment(Comment comment);
    }

    interface CommentView {
        void finishedGetAllComment(List<Comment> commentList);
        void finishedAddComment();
    }

    interface CommentDataInteractor {
        interface OnFinishedGetComments {
            void onFinishedGetComment(List<Comment> comments);
            void onFailure(Throwable t);
        }

        interface OnFinishedWriteComments {
            void onFinishedWriteComment();
            void onFailure(Throwable t);
        }

        void getAllComments(int taskId, OnFinishedGetComments onFinishedListener);
        void writeComment(Comment comment, OnFinishedWriteComments listener);
    }


}

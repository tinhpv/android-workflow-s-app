package com.example.workflow_s.ui.comment;

import android.util.Log;

import com.example.workflow_s.model.Comment;

import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-23
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class CommentPresenterImp implements CommentContract.CommentPresenter,
        CommentContract.CommentDataInteractor.OnFinishedGetComments,
        CommentContract.CommentDataInteractor.OnFinishedWriteComments {

    private CommentContract.CommentView mCommentView;
    private CommentContract.CommentDataInteractor mDataInteractor;

    public CommentPresenterImp(CommentContract.CommentView commentView, CommentContract.CommentDataInteractor dataInteractor) {
        mCommentView = commentView;
        mDataInteractor = dataInteractor;
    }

    @Override
    public void getAllComment(int taskId) {
        mDataInteractor.getAllComments(taskId, this);
    }

    @Override
    public void onFinishedGetComment(List<Comment> comments) {
        mCommentView.finishedGetAllComment(comments);
    }

    @Override
    public void writeComment(Comment comment) {
        mDataInteractor.writeComment(comment, this);
    }

    @Override
    public void onFinishedWriteComment() {
        mCommentView.finishedAddComment();
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d("ERROR:COMMENT", "onFailure: " + t.getMessage());
    }
}

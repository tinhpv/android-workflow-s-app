package com.example.workflow_s.ui.comment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Comment;
import com.example.workflow_s.utils.CommonUtils;
import com.example.workflow_s.utils.SharedPreferenceUtils;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-23
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class CommentFragment extends Fragment implements CommentContract.CommentView, View.OnClickListener {

    View view;
    private EditText commentEdt;
    private ImageButton sendButton;

    private RecyclerView commentRV;
    private CommentAdapter mCommentAdapter;
    private RecyclerView.LayoutManager commentLayoutManager;

    private LinearLayout commentDataNotFound;

    private ShimmerFrameLayout mShimmerFrameLayout;
    private CommentContract.CommentPresenter mCommentPresenter;
    private Comment mComment;
    private List<Comment> mCommentList;

    int taskId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerFrameLayout.startShimmerAnimation();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_comment, container, false);
        getActivity().setTitle("Comment");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViewUI();
        requestData();
    }

    private void bindViewUI() {

        commentDataNotFound = view.findViewById(R.id.comment_data_notfound_message);
        mShimmerFrameLayout = view.findViewById(R.id.comment_shimmer_view_container);

        sendButton = view.findViewById(R.id.button_chatbox_send);
        sendButton.setOnClickListener(this);

        commentEdt = view.findViewById(R.id.edt_comment);

        commentRV = view.findViewById(R.id.rv_comment);
        commentLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        commentRV.setLayoutManager(commentLayoutManager);

        mCommentAdapter = new CommentAdapter(getContext());
        commentRV.setAdapter(mCommentAdapter);
    }

    private void requestData() {
        taskId = getArguments().getInt("taskId");
        mCommentPresenter = new CommentPresenterImp(this, new CommentInteractor());
        mCommentPresenter.getAllComment(taskId);
    }

    @Override
    public void finishedGetAllComment(List<Comment> commentList) {
        mShimmerFrameLayout.stopShimmerAnimation();
        mShimmerFrameLayout.setVisibility(View.GONE);
        if (null != commentList) {
            mCommentList = commentList;
            mCommentAdapter.setCommentList(commentList);

            if (commentList.size() == 0) {
                commentDataNotFound.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_chatbox_send) {
            String content = commentEdt.getText().toString();
            String userId = SharedPreferenceUtils.retrieveData(getContext(), getContext().getString(R.string.pref_userId));
            String userName = SharedPreferenceUtils.retrieveData(getContext(), getContext().getString(R.string.pref_username));
            String userImg = SharedPreferenceUtils.retrieveData(getContext(), getContext().getString(R.string.pref_avatar));
            mComment = new Comment(null, taskId, userId, content, null, false, userImg, userName);
            mCommentPresenter.writeComment(mComment);
        }
    }

    @Override
    public void finishedAddComment() {
        Toast.makeText(getContext(), "Add comment successfully", Toast.LENGTH_SHORT).show();
        commentEdt.setText("");
        if (null == mCommentList) {
            mCommentList = new ArrayList<>();
        }

        mCommentList.add(mComment);
        mCommentAdapter.setCommentList(mCommentList);
        commentDataNotFound.setVisibility(View.GONE);

        CommonUtils.hideKeyboard(getActivity());
    }
}

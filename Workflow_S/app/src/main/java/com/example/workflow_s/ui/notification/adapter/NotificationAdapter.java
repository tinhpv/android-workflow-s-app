package com.example.workflow_s.ui.notification.adapter;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.workflow_s.R;
import com.example.workflow_s.model.Notification;
import com.example.workflow_s.ui.task.task_checklist.ChecklistTaskFragment;
import com.example.workflow_s.ui.taskdetail.checklist.ChecklistTaskDetailFragment;
import com.example.workflow_s.utils.CommonUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> mComments;
    private RecyclerView mRecyclerView;

    public void setComments(List<Notification> commentList) {
        mComments = commentList;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int layoutId = R.layout.recyclerview_item_notification;
        View view = layoutInflater.inflate(layoutId, viewGroup, false);
        final NotificationViewHolder viewHolder = new NotificationViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = mRecyclerView.getChildLayoutPosition(v);
                if (mComments.get(index).getTaskId() == null && mComments.get(index).getChecklistId() != null) {
                    Bundle args = new Bundle();
                    args.putString("checklistId", String.valueOf(mComments.get(index).getChecklistId()));
                    args.putInt("location", 1);
                    CommonUtils.replaceFragments(v.getContext(), ChecklistTaskFragment.class, args, true);
                } else {
                    Bundle args = new Bundle();

                    args.putString("taskId", String.valueOf(mComments.get(index).getTaskId()));
                    //args.putString("taskName", mComments.get(index).getName());
                    args.putInt("location_activity", 1);
                    //args.putInt("checklistId", mComments.get(index).getChecklistId());

                    CommonUtils.replaceFragments(viewGroup.getContext(), ChecklistTaskDetailFragment.class, args, true);
                }

            }
        });
        return viewHolder;
    }

    //FIXME set fake data
    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder notificationViewHolder, int i) {
        String urlImg = mComments.get(i).getImageUrl();
        if (urlImg == null) {
            notificationViewHolder.mCircleImageView.setImageDrawable(ContextCompat.getDrawable(notificationViewHolder.view.getContext(),R.drawable.default_avatar));
        } else {
            Glide.with(notificationViewHolder.view.getContext()).load(urlImg).into(notificationViewHolder.mCircleImageView);
        }
        notificationViewHolder.mTextViewAvatar.setText(mComments.get(i).getText());
    }

    @Override
    public int getItemCount() {
        if (mComments == null) {
            return 0;
        }
        return mComments.size();
    }

    //viewholder
    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView mCircleImageView;
        private TextView mTextViewAvatar;
        private View view;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            mCircleImageView = itemView.findViewById(R.id.img_user_avatar_notification);
            mTextViewAvatar = itemView.findViewById(R.id.tv_notification);
        }
    }

}

package com.example.workflow_s.ui.organization.dialog_fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.workflow_s.R;

import java.util.EventListener;
import java.util.List;

public class OrganizationDialogAdapter extends RecyclerView.Adapter<OrganizationDialogAdapter.OrganizationDialogViewHolder> {

    List<String> mOrganizationNameList;
    String selectedOrgName;
    RecyclerView mRecyclerView;

    EventListener listener;

    public interface EventListener {
        void onEvent(String orgName);
    }

    public void setmOrganizationNameList(List<String> organizationNameList) {
        mOrganizationNameList = organizationNameList;
        notifyDataSetChanged();
    }

    public void setSelectedOrgName(String selectedOrgName) {
        this.selectedOrgName = selectedOrgName;
    }

    public OrganizationDialogAdapter(EventListener listener) {
        this.listener = listener;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @NonNull
    @Override
    public OrganizationDialogViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recyclerview_item_organization_name, viewGroup, false);
        final OrganizationDialogViewHolder viewHolder = new OrganizationDialogViewHolder(view);

        //item click
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_confirm_organization);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                Button confirmButton = dialog.findViewById(R.id.btn_confirm);
                Button cancelButton = dialog.findViewById(R.id.btn_cancel);

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v2) {
                        int index = mOrganizationNameList.indexOf(selectedOrgName);
                        OrganizationDialogViewHolder oldViewHolder = (OrganizationDialogViewHolder) mRecyclerView.findViewHolderForAdapterPosition(index);
                        oldViewHolder.imgChecked.setVisibility(View.INVISIBLE);

                        OrganizationDialogViewHolder currentViewHolder = (OrganizationDialogViewHolder) mRecyclerView.findContainingViewHolder(v);
                        currentViewHolder.imgChecked.setVisibility(View.VISIBLE);
                        selectedOrgName = mOrganizationNameList.get(mRecyclerView.getChildLayoutPosition(v));

                        listener.onEvent(selectedOrgName);
                        dialog.dismiss();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onEvent(null);
                        dialog.dismiss();
                    }
                });

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizationDialogViewHolder organizationDialogViewHolder, int i) {
        organizationDialogViewHolder.mOrganiztonName.setText(mOrganizationNameList.get(i));
        if (mOrganizationNameList.get(i).equals(selectedOrgName)) {
            organizationDialogViewHolder.imgChecked.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (mOrganizationNameList == null) {
            return 0;
        }
        return mOrganizationNameList.size();
    }

    public class OrganizationDialogViewHolder extends RecyclerView.ViewHolder {

        public TextView mOrganiztonName;
        public ImageView imgChecked;
        public View view;

        public OrganizationDialogViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            mOrganiztonName = itemView.findViewById(R.id.tv_organization_name);
            imgChecked = itemView.findViewById(R.id.img_org_checked);
        }
    }
}

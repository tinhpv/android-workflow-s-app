package com.example.workflow_s.ui.organization;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Organization;
import com.example.workflow_s.model.User;
import com.example.workflow_s.model.UserOrganization;
import com.example.workflow_s.ui.organization.adapter.OrganizationMemberAdapter;
import com.example.workflow_s.ui.organization.dialog_fragment.OrganizationDialogFragment;
import com.example.workflow_s.utils.SharedPreferenceUtils;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-26
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class OrganizationFragment extends Fragment implements OrganizationContract.OrganizationView, OrganizationDialogFragment.DataBackContract {

    private final static String TAG = "ORGANIZATION_FRAGMENT";

    View view;
    private RecyclerView organizationRecyclerView;
    private OrganizationMemberAdapter mAdapter;
    private RecyclerView.LayoutManager organizationLayoutManager;

    private OrganizationContract.OrganizationPresenter mPresenter;

    private ShimmerFrameLayout mOrgShimmerLayout;

    private ArrayList<String> orgNameList;
    private List<UserOrganization> userOrganizationArrayList;
    private String selectedOrgName;
    private ArrayList<Organization> organizationArrayList;
    Organization targetOrganization = null, currentOrganization = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_org, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_switch:
                prepareShowOrgDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_organization, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        mOrgShimmerLayout = view.findViewById(R.id.org_shimmer_view_container);
        setupData();
        setupOrganizationRV();
    }

    private void prepareShowOrgDialog() {
        String orgName = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_orgName));
        Bundle bundle = new Bundle();
        bundle.putSerializable("org_list", orgNameList);
        bundle.putString("selected_org_name", orgName);
        OrganizationDialogFragment organizationDialogFragment = OrganizationDialogFragment.newInstance();
        organizationDialogFragment.setTargetFragment(OrganizationFragment.this, 0);
        organizationDialogFragment.setArguments(bundle);
        organizationDialogFragment.show(getActivity().getSupportFragmentManager(), TAG);
    }

    @Override
    public void onResume() {
        super.onResume();
        mOrgShimmerLayout.startShimmerAnimation();
    }


    private void setupOrganizationRV() {
        organizationRecyclerView = view.findViewById(R.id.rv_organization);
        organizationRecyclerView.setHasFixedSize(true);
        organizationLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        organizationRecyclerView.setLayoutManager(organizationLayoutManager);
        
        mAdapter = new OrganizationMemberAdapter(getActivity());
        organizationRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void setupData() {
        mOrgShimmerLayout.startShimmerAnimation();
        mPresenter = new OrganizationPresenterImpl(this, new OrganizationInteractor());

        String userId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_userId));
        String orgId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_orgId));
        String orgName = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_orgName));
        Organization organization = new Organization(Integer.parseInt(orgId), orgName);
        //Log.i("orgId", orgId);

        mPresenter.requestListOrganization(userId);
        mPresenter.requestOrganizationData(Integer.parseInt(orgId));

        //saveData
        getActivity().setTitle(organization.getName());
        //SharedPreferenceUtils.saveCurrentUserData(getActivity(), null, organization);

    }

    @Override
    public void finishedGetMemeber(List<User> userList) {
        mOrgShimmerLayout.stopShimmerAnimation();
        mOrgShimmerLayout.setVisibility(View.INVISIBLE);
        if (userList != null) {
            mAdapter.setUserList(userList);
            mAdapter.notifyDataSetChanged();
        }

    }


    // GET ALL ORGANIZATIONS THAT USER BELONGS TO
    @Override
    public void finishedGetListUserOrganization(List<UserOrganization> userOrganizationList) {
        userOrganizationArrayList = new ArrayList<>();
        userOrganizationArrayList = userOrganizationList;
        organizationArrayList = new ArrayList<>();
        orgNameList = new ArrayList<>();

        if (!userOrganizationArrayList.isEmpty()) {
            for (int i = 0; i < userOrganizationArrayList.size(); i++) {
                organizationArrayList.add(userOrganizationArrayList.get(i).getOrganization());
            }
        }

        if (!organizationArrayList.isEmpty()) {
            for (int i = 0; i < organizationArrayList.size(); i++) {
                orgNameList.add(organizationArrayList.get(i).getName());
            }

        }

    }

    @Override
    public void finishedSwitchOrganization() {
        getActivity().setTitle(targetOrganization.getName());
        SharedPreferenceUtils.saveCurrentUserData(getActivity(), null, targetOrganization);
        mPresenter.requestOrganizationData(targetOrganization.getId());

        //reload fragment
        this.getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    @Override
    public void onFinishSelectOrgName(String orgName) {

        targetOrganization = null;
        for (int i = 0; i < organizationArrayList.size(); i++) {
            if (organizationArrayList.get(i).getName().equals(orgName)) {
                targetOrganization = organizationArrayList.get(i);
                break;
            }
        }

        String userId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_userId));
        String oldOrgId = SharedPreferenceUtils.retrieveData(getActivity(), getString(R.string.pref_orgId));
        if (targetOrganization != null) {
            mPresenter.switchOrganization(userId, targetOrganization.getId(), Integer.parseInt(oldOrgId));
        }

    }
}

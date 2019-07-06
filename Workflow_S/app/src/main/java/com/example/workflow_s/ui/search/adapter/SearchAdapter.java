package com.example.workflow_s.ui.search.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workflow_s.R;
import com.example.workflow_s.ui.search.SearchModel;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> implements Filterable {

    private List<String> searchList;
    private List<String> searchListFull;
    private List<Integer> listId;
    private List<SearchModel> listModel;
    private List<SearchModel> listModelFull;
    private View view;


    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item_search, viewGroup, false);
        SearchViewHolder viewHolder = new SearchViewHolder(view);
        //viewHolder.textView.setOnClickListener(mOnClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {
        searchViewHolder.textView.setText(searchList.get(i));
        //searchViewHolder.textViewId.setText(listId.get(i));
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(searchListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (String item : searchListFull) {
                    if (item.toLowerCase().contains(filterPattern)) {
                        filterList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterList;
            return  results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            searchList.clear();
            //listId.clear();
            searchList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        //TextView textViewId;

        public SearchViewHolder(@NonNull final View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_search_item);
            //textViewId = itemView.findViewById(R.id.tv_id_search_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), textView.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public SearchAdapter(List<String> searchList, List<Integer> listId) {
        this.searchList = searchList;
        this.listId = listId;
        searchListFull = new ArrayList<>(searchList);
        //setModel();
    }

    private void setModel() {
        listModel = new ArrayList<>();
        for (int i = 0; i < searchList.size(); i++) {
            SearchModel model = new SearchModel(searchList.get(i), listId.get(i));
            listModel.add(model);
        }
        listModelFull = new ArrayList<>(listModel);
    }
}

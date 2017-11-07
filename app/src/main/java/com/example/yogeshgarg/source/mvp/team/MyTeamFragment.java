package com.example.yogeshgarg.source.mvp.team;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yogeshgarg.source.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyTeamFragment extends Fragment implements MyTeamContractor.View{

    @BindView(R.id.recyclerViewMyTeam)
    RecyclerView recyclerViewMyTeam;

    ArrayList<MyTeamModel.Result> items;
    MyTeamContractor.Presenter presenter;

    public MyTeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_team, container, false);

        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter=new MyTeamPresenterImpl(this);
        presenter.getTeam();

    }

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showAlert(String message) {

    }

    @Override
    public void getTeam(ArrayList<MyTeamModel.Result> items) {
       this.items=items;
        if(items.size()>0){
            setLayoutManager();
        }

    }

    @Override
    public void getTeamFailure(String msg) {

    }

    @Override
    public void getTeamInternetError() {
    }

    private void setLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewMyTeam.setLayoutManager(linearLayoutManager);
        recyclerViewMyTeam.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMyTeam.setHasFixedSize(true);

        setAdapter();
    }
    private void setAdapter(){
        TeamAdapter adapter=new TeamAdapter(getActivity(),items);
        recyclerViewMyTeam.setAdapter(adapter);
    }
}

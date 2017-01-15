package test.ivacompany.com.test.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import test.ivacompany.com.test.R;
import test.ivacompany.com.test.TestApp;
import test.ivacompany.com.test.adapters.EmployeeRecyclerViewAdapter;
import test.ivacompany.com.test.intefaces.FragmentRequestListener;
import test.ivacompany.com.test.models.Employee;
import test.ivacompany.com.test.utils.Constants;
import test.ivacompany.com.test.utils.RecycleViewItemDecoration;
import test.ivacompany.com.test.utils.Utils;

/**
 * Created by root on 13.01.17.
 */

public class MainFragment extends Fragment {

    public static final String TAG = "MainFragment";

    @BindView(R.id.addButton) FloatingActionButton addButton;
    @BindView(R.id.noData) TextView noData;
    @BindView(R.id.userList) RecyclerView userList;

    FragmentRequestListener requestListener;
    List<Employee> listEm;
    EmployeeRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        requestListener = (FragmentRequestListener) getActivity();
        listEm = Utils.readFromRealm();
        if (listEm.isEmpty()){
            noData.setVisibility(View.VISIBLE);
        } else {
            initRecyclerView();
        }
    }

    private void initRecyclerView() {
        adapter = new EmployeeRecyclerViewAdapter(listEm, requestListener);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(TestApp.getAppContext());
        userList.setLayoutManager(mLayoutManager);
        userList.addItemDecoration(
                new RecycleViewItemDecoration(
                        TestApp.getAppContext(),
                        LinearLayoutManager.VERTICAL
                )
        );
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.remove(viewHolder.getAdapterPosition());
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
            }

        });
        helper.attachToRecyclerView(userList);
        userList.setAdapter(adapter);
    }

    @OnClick(R.id.addButton)
    public void addButton() {
        requestListener.startProfile(Constants.NEW_EMPLOYEE);
    }

}

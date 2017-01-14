package test.ivacompany.com.test.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.ivacompany.com.test.R;
import test.ivacompany.com.test.intefaces.FragmentRequestListener;
import test.ivacompany.com.test.models.Employee;

/**
 * Created by root on 13.01.17.
 */

public class EmployeeRecyclerViewAdapter  extends RecyclerView.Adapter<EmployeeRecyclerViewAdapter.FileHolder> {

    private static final String TAG = "EmployeeRecyclerViewAdapter";
    private final List<Employee> employeeList;
    private final FragmentRequestListener requestListener;


    public EmployeeRecyclerViewAdapter(List<Employee> employeeList, FragmentRequestListener requestListener) {
        this.employeeList = employeeList;
        this.requestListener = requestListener;
    }

    @Override
    public EmployeeRecyclerViewAdapter.FileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new FileHolder(listItemView);
    }

    @Override
    public void onBindViewHolder(final FileHolder holder, int position) {
        Employee employee = employeeList.get(position);
        holder.name.setText(employee.getName());
        holder.surname.setText(employee.getSurname());
        holder.secondName.setText(employee.getMiddleName());
        holder.city.setText((employee.getCity()));
        long diff = new Date().getTime() - employee.getBirthday().getTime();
        float dayCount = (float) diff / (24 * 60 * 60 * 1000);
        holder.old.setText(getYear(new Date(), employee.getBirthday()));
        holder.position.setText(employee.getPosition());
    }

    private String getYear(Date date1,Date date2){
        SimpleDateFormat simpleDateformat=new SimpleDateFormat("yyyy");
        int years = Integer.parseInt(simpleDateformat.format(date1))- Integer.parseInt(simpleDateformat.format(date2));

        return String.valueOf(years);

    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }


    public class FileHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name) TextView name;
        @BindView(R.id.surname) TextView surname;
        @BindView(R.id.secondName) TextView secondName;
        @BindView(R.id.old) TextView old;
        @BindView(R.id.city) TextView city;
        @BindView(R.id.position) TextView position;

        public FileHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(clickListener);

        }

        private View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestListener.startProfile(employeeList.get(getAdapterPosition()).getId());
            }
        };

    }

}
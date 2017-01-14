package test.ivacompany.com.test.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import test.ivacompany.com.test.R;
import test.ivacompany.com.test.TestApp;
import test.ivacompany.com.test.models.Employee;
import test.ivacompany.com.test.utils.Constants;
import test.ivacompany.com.test.utils.Utils;

/**
 * Created by root on 13.01.17.
 */

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";

    @BindView(R.id.addEmployee)
    Button addEmployee;
    @BindView(R.id.surname)
    EditText surname;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.secondName)
    EditText secondName;
    @BindView(R.id.date)
    EditText date;
    @BindView(R.id.city)
    Spinner city;
    @BindView(R.id.position)
    Spinner position;

    private static DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.US);

    private Employee employee;

    public static ProfileFragment newInstance(long id) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle arguments = new Bundle();
        arguments.putLong(Constants.ID, id);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, root);

        employee = Utils.getEmployee(getArguments().getLong(Constants.ID));

        if (employee != null) {
            addEmployee.setText(getString(R.string.update));
            name.setText(employee.getName());
            surname.setText(employee.getSurname());
            secondName.setText(employee.getMiddleName());
            date.setText(formatter.format(employee.getBirthday()));
            city.setSelection(getCityPosition(employee.getCity()));
            position.setSelection(getWorkPosition(employee.getPosition()));
        }

        return root;
    }

    private int getCityPosition(String name) {
        String[] cityArr = getResources().getStringArray(R.array.city);
        for (int i = 0; i < cityArr.length; i++) {
            if (name.equals(cityArr[i])) {
                return i;
            }
        }
        return 0;
    }

    private int getWorkPosition(String name) {
        String[] positionArr = getResources().getStringArray(R.array.position);
        for (int i = 0; i < positionArr.length; i++) {
            if (name.equals(positionArr[i])) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @OnClick(R.id.addEmployee)
    public void addEmployee() {
        if (isFieldEmpty()){
            Toast.makeText(
                    TestApp.getAppContext(),
                    "Fill all field",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (employee != null) {
            try {
                employee.setName(name.getText().toString());
                employee.setSurname(surname.getText().toString());
                employee.setMiddleName(secondName.getText().toString());
                employee.setBirthday(formatter.parse(date.getText().toString()));
                employee.setCity(city.getSelectedItem().toString());
                employee.setPosition(position.getSelectedItem().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Utils.updateUser(employee);
        } else {
            try {
                Utils.addEmployee(
                        new Employee(
                                0,
                                name.getText().toString(),
                                surname.getText().toString(),
                                secondName.getText().toString(),
                                formatter.parse(date.getText().toString()),
                                city.getSelectedItem().toString(),
                                position.getSelectedItem().toString()
                        )
                );
            } catch (ParseException e) {
                Toast.makeText(
                        TestApp.getAppContext(),
                        "Wrong Date",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }
        getActivity().onBackPressed();
    }

    private boolean isFieldEmpty() {
        if (name.getText().toString().isEmpty() ||
                surname.getText().toString().isEmpty() ||
                secondName.getText().toString().isEmpty() ||
                date.getText().toString().isEmpty()) {
            return true;
        }
        return false;
    }
}

package test.ivacompany.com.test.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import test.ivacompany.com.test.R;
import test.ivacompany.com.test.utils.Utils;
import test.ivacompany.com.test.fragments.MainFragment;
import test.ivacompany.com.test.fragments.ProfileFragment;
import test.ivacompany.com.test.intefaces.FragmentRequestListener;

public class MainActivity extends AppCompatActivity implements FragmentRequestListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.initDB();
        startMainWindow();
    }

    @Override
    public void startProfile(long id) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer,
                        ProfileFragment.newInstance(id),
                        ProfileFragment.TAG)
                .addToBackStack(ProfileFragment.TAG)
                .commit();
    }

    @Override
    public void startMainWindow() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer,
                        new MainFragment(),
                        MainFragment.TAG)
                .commit();
    }
}

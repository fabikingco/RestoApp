package com.wposs.buc.restpapp.activitys;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.activitys.helper.BottomNavigationBehavior;
import com.wposs.buc.restpapp.fragment.AccountFragment;
import com.wposs.buc.restpapp.fragment.ProfileFragment;
import com.wposs.buc.restpapp.fragment.SettingsFragment;

public class MainActivity2 extends AppCompatActivity {

    private ActionBar toolbar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        toolbar = getSupportActionBar();

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        toolbar.setTitle("");
        loadFragment(new ProfileFragment());
        bottomNavigationView.setSelectedItemId(R.id.pedidos);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.setting_app:
                    toolbar.setTitle("Configuraciones");
                    fragment = new SettingsFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.pedidos:
                    toolbar.setTitle("RestoApp");
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.setting_account:
                    toolbar.setTitle("Usuarios");
                    fragment = new AccountFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        doExitApp();
    }

    private long exitTime = 0;

    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "Press again to exit app", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}

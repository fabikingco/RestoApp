package com.wposs.buc.restpapp.activitys;

import android.os.Bundle;
import android.view.Menu;
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
import com.google.firebase.auth.FirebaseAuth;
import com.wposs.buc.restpapp.R;
import com.wposs.buc.restpapp.Tools;
import com.wposs.buc.restpapp.activitys.helper.BottomNavigationBehavior;
import com.wposs.buc.restpapp.fragment.AccountFragment;
import com.wposs.buc.restpapp.fragment.MesasFragment;
import com.wposs.buc.restpapp.fragment.SettingsFragment;

public class MainActivity2 extends AppCompatActivity {

    private ActionBar toolbar;
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mAuth = FirebaseAuth.getInstance();

        toolbar = getSupportActionBar();

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        toolbar.setTitle("");
        loadFragment(new MesasFragment());
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
                    fragment = new MesasFragment();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_CerrarSesion){
            cerrarSesion();

            Tools.startView(this, LoginActivity.class, false);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void cerrarSesion(){
        mAuth.signOut();
    }
}

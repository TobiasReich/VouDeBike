package br.dayanelima.voudebike;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import br.dayanelima.voudebike.bike.FragmentBikeList;
import br.dayanelima.voudebike.customers.FragmentCustomersList;


public class MainActivity extends AppCompatActivity implements IAppNavigation, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private final FragmentManager fMan = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    @Override
    public void openBikesList() {
        switchToFragment(new FragmentBikeList());
    }

    @Override
    public void openClientsList() {
        switchToFragment(new FragmentCustomersList());
    }


    /** Switches to a given Fragment using a side scrolling animation
     *
     * @param fragment SupportFragment to show */
    private void switchToFragment(final Fragment fragment) {
        try {
            fMan.beginTransaction()
                    .setCustomAnimations(
                            R.anim.enter_from_right, R.anim.exit_to_left,
                            R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();

            fMan.executePendingTransactions();
        } catch (Exception e) {
            Log.e(TAG, "Exception switching Fragments! ${e.message}");
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_bikes_list)
            openBikesList();
        else if (id == R.id.nav_my_clients)
            openClientsList();

        //TODO: show bookings


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

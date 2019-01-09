package skrb.appprueba;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import org.apache.poi.hssf.record.aggregates.FormulaRecordAggregate;

import skrb.appprueba.Fragments.AboutFragment;
import skrb.appprueba.Fragments.AgregarClienteFragment;
import skrb.appprueba.Fragments.BuscarClienteFragment;
import skrb.appprueba.Fragments.CalcularFragment;
import skrb.appprueba.Fragments.EstablecerPrecioFragment;

public class MainActivity extends AppCompatActivity  {

    private final int FRAGMENT_AGREGAR=0;
    private final int FRAGMENT_BUSCAR=1;
    private final int FRAGMENT_ABOUT=2;
    private final int FRAGMENT_CALCULAR=3;
    private final int FRAGMENT_PRECIO=4;
    public static final String PRICE_PREFS="prices";
    public static final String PRICE_20="price_20";
    public static final String PRICE_12="price_12";

    private DrawerLayout mDrawerLayout;
    private Toolbar tb;
    private ActionBar actBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        actBar = getSupportActionBar();
        actBar.setDisplayHomeAsUpEnabled(true);
        actBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);



        NavigationView navView= findViewById(R.id.NavigationView);
        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.AgregarCliente:
                                setFragment(FRAGMENT_AGREGAR);
                                break;
                            case R.id.BuscarCliente:
                                setFragment(FRAGMENT_BUSCAR);
                                break;
                            case R.id.About:
                                setFragment(FRAGMENT_ABOUT);
                                break;
                            case R.id.Calcular:
                                setFragment(FRAGMENT_CALCULAR);
                                break;
                            case R.id.EstablecerPrecio:
                                setFragment(FRAGMENT_PRECIO);
                                break;
                        }

                        mDrawerLayout.closeDrawers();

                        return true;
                    }
                }
        );

        initializePrefs();

        Window window = getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));


    }

    public void initializePrefs(){
        SharedPreferences preferences = getSharedPreferences(PRICE_PREFS,0);

        if (preferences.getBoolean("first_time", true)){
            SharedPreferences.Editor edit = preferences.edit();
            edit.putFloat(PRICE_20,70);
            edit.putFloat(PRICE_12,50);
            edit.putBoolean("first_time", false);
            edit.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setFragment(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        Fragment frag= null;
        switch (position){
            case FRAGMENT_AGREGAR:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                frag= new AgregarClienteFragment();
                fragmentTransaction.replace(R.id.fragment, frag);
                fragmentTransaction.commit();
                break;
            case FRAGMENT_BUSCAR:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                frag= new BuscarClienteFragment();
                fragmentTransaction.replace(R.id.fragment, frag);
                fragmentTransaction.commit();
                break;
            case FRAGMENT_ABOUT:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                frag= new AboutFragment();
                fragmentTransaction.replace(R.id.fragment, frag);
                fragmentTransaction.commit();
                break;
            case FRAGMENT_CALCULAR:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                frag= new CalcularFragment();
                fragmentTransaction.replace(R.id.fragment, frag);
                fragmentTransaction.commit();
                break;
            case FRAGMENT_PRECIO:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                frag= new EstablecerPrecioFragment();
                fragmentTransaction.replace(R.id.fragment, frag);
                fragmentTransaction.commit();
                break;
        }
    }
}

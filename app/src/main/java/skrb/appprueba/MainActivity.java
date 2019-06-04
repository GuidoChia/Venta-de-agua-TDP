package skrb.appprueba;


import android.Manifest.permission;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.transition.Slide;
import androidx.transition.Visibility;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import java.util.Objects;

import skrb.appprueba.Fragments.AboutFragment;
import skrb.appprueba.Fragments.AgregarClienteFragment;
import skrb.appprueba.Fragments.BuscarClienteFragment;
import skrb.appprueba.Fragments.CalcularAnioFragment;
import skrb.appprueba.Fragments.CalcularDiaFragment;
import skrb.appprueba.Fragments.CalcularFragment;
import skrb.appprueba.Fragments.EstablecerPrecioFragment;
import skrb.appprueba.Fragments.RecorridoFragment;
import skrb.appprueba.R.drawable;
import skrb.appprueba.R.id;
import skrb.appprueba.R.layout;

/**
 * Main activity for the app. It handles the flow between the principal fragments.
 */
public class MainActivity extends AppCompatActivity {

    private static final int FRAGMENT_AGREGAR = 0;
    private static final int FRAGMENT_BUSCAR = 1;
    private static final int FRAGMENT_ABOUT = 2;
    private static final int FRAGMENT_CALCULAR = 3;
    private static final int FRAGMENT_PRECIO = 4;
    private static final int FRAGMENT_CALCULAR_DIA = 5;
    private static final int FRAGMENT_CALCULAR_RECORRIDO = 6;
    private static final int FRAGMENT_CALCULAR_AÑO = 7;
    private static final int PERMISSION_WRITE = 0;
    private static final int ANIMATION_DEFAULT_TIME= 300;

    private DrawerLayout mDrawerLayout;
    private ActionBar actBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        Toolbar tb = findViewById(id.toolbar);
        setSupportActionBar(tb);

        actBar = getSupportActionBar();
        Objects.requireNonNull(actBar).setDisplayHomeAsUpEnabled(true);
        actBar.setHomeAsUpIndicator(drawable.ic_menu);

        mDrawerLayout = findViewById(id.drawer_layout);


        NavigationView navView = findViewById(id.NavigationView);
        navView.setNavigationItemSelectedListener(
                menuItem -> {
                    switch (menuItem.getItemId()) {
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
                        case id.Calcular_dia:
                            setFragment(FRAGMENT_CALCULAR_DIA);
                            break;
                        case id.Calcular_recorrido:
                            setFragment(FRAGMENT_CALCULAR_RECORRIDO);
                            break;
                        case id.Calcular_anio:
                            setFragment(FRAGMENT_CALCULAR_AÑO);
                    }

                    mDrawerLayout.closeDrawers();

                    return true;
                }
        );

        if (savedInstanceState == null) {
            setFragment(FRAGMENT_AGREGAR);
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

    private void setFragment(int position) {
        if (ContextCompat.checkSelfPermission(this,
                permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_WRITE);

        }
        switch (position) {
            case FRAGMENT_AGREGAR:
                changeFragment(new AgregarClienteFragment());
                break;
            case FRAGMENT_BUSCAR:
                changeFragment(new BuscarClienteFragment());
                break;
            case FRAGMENT_ABOUT:
                changeFragment(new AboutFragment());
                break;
            case FRAGMENT_CALCULAR:
                changeFragment(new CalcularFragment());
                break;
            case FRAGMENT_PRECIO:
                changeFragment(new EstablecerPrecioFragment());
                break;
            case FRAGMENT_CALCULAR_DIA:
                changeFragment(new CalcularDiaFragment());
                break;
            case FRAGMENT_CALCULAR_RECORRIDO:
                changeFragment(new RecorridoFragment());
                break;
            case FRAGMENT_CALCULAR_AÑO:
                changeFragment(new CalcularAnioFragment());
                break;
        }
    }

    private void changeFragment(Fragment frag) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        Visibility transition = new Slide(Gravity.END);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        frag.setEnterTransition(transition);
        frag.setExitTransition(transition);
        fragmentTransaction.replace(id.fragment, frag);
        fragmentTransaction.commit();
    }
}

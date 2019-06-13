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
                    if (ContextCompat.checkSelfPermission(this,
                            permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(this,
                                new String[]{permission.WRITE_EXTERNAL_STORAGE},
                                PERMISSION_WRITE);

                    }

                    switch (menuItem.getItemId()) {
                        case R.id.AgregarCliente:
                            setFragment(new AgregarClienteFragment());
                            break;
                        case R.id.BuscarCliente:
                            setFragment(new BuscarClienteFragment());
                            break;
                        case R.id.About:
                            setFragment(new AboutFragment());
                            break;
                        case R.id.Calcular:
                            setFragment(new CalcularFragment());
                            break;
                        case R.id.EstablecerPrecio:
                            setFragment(new EstablecerPrecioFragment());
                            break;
                        case id.Calcular_dia:
                            setFragment(new CalcularDiaFragment());
                            break;
                        case id.Calcular_recorrido:
                            setFragment(new RecorridoFragment());
                            break;
                        case id.Calcular_anio:
                            setFragment(new CalcularAnioFragment());
                    }

                    mDrawerLayout.closeDrawers();

                    return true;
                }
        );

        if (savedInstanceState == null) {
            setFragment(new AgregarClienteFragment());
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



    private void setFragment(Fragment frag) {
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

package skrb.appprueba.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import reader.ConcreteCustomerManager;
import reader.ConcreteReader;
import reader.CustomerManager;
import skrb.appprueba.MainActivity;
import skrb.appprueba.R;
import skrb.appprueba.helpers.FileHelper;

public class CalcularAnioFragment extends Fragment {
    private static final int FRAGMENT_RESULTADOS = 0;
    private static final int MIN_YEAR = 1990;
    private static final int MAX_YEAR = 2050;
    @Nullable
    private
    CustomerManager manager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calcular_anio, container, false);
        final MainActivity act = (MainActivity) getActivity();
        Objects.requireNonNull(act.getSupportActionBar()).setTitle("Calculos anuales");

        initYearButton(view);

        File path = FileHelper.getPath();

        Button btnDinero = view.findViewById(R.id.calcular_dinero_anual);
        Button btn12 = view.findViewById(R.id.calcular_bidones_12_anual);
        Button btn20 = view.findViewById(R.id.calcular_bidones_20_anual);
        Button btnTot = view.findViewById(R.id.calcular_total_bidones_anual);
        CheckBox checkBox = view.findViewById(R.id.checkbox_lista_anio);

        if (ContextCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            btn12.setEnabled(false);
            btn20.setEnabled(false);
            btnDinero.setEnabled(false);
            btnTot.setEnabled(false);
        } else {
            btnDinero.setOnClickListener(v -> {
                CustomerManager manager = getYearManager(view, path);

                Bundle bnd = new Bundle();

                if (checkBox.isChecked()) {
                    bnd.putString("list", manager.toString());
                } else {
                    bnd.putString("list", "");
                }

                bnd.putString("result", String.valueOf(manager.getPaid()));

                setFragment(FRAGMENT_RESULTADOS, bnd);
            });

            btn12.setOnClickListener(v -> {
                CustomerManager manager = getYearManager(view, path);

                Bundle bnd = new Bundle();

                if (checkBox.isChecked()) {
                    bnd.putString("list", manager.toString());
                } else {
                    bnd.putString("list", "");
                }

                bnd.putString("result", String.valueOf(manager.getTwelveBought()));

                setFragment(FRAGMENT_RESULTADOS, bnd);
            });

            btn20.setOnClickListener(v -> {

                CustomerManager manager = getYearManager(view, path);

                Bundle bnd = new Bundle();


                if (checkBox.isChecked()) {
                    bnd.putString("list", manager.toString());
                } else {
                    bnd.putString("list", "");
                }
                bnd.putString("result", String.valueOf(manager.getTwentyBought()));

                setFragment(FRAGMENT_RESULTADOS, bnd);
            });

            btnTot.setOnClickListener(v -> {
                CustomerManager manager = getYearManager(view, path);

                Bundle bnd = new Bundle();

                if (checkBox.isChecked()) {
                    bnd.putString("list", manager.toString());
                } else {
                    bnd.putString("list", "");
                }

                int res = manager.getTwelveBought() + manager.getTwentyBought();
                bnd.putString("result", String.valueOf(res));

                setFragment(FRAGMENT_RESULTADOS, bnd);
            });
        }

        return view;
    }

    @NonNull
    private CustomerManager getYearManager(View view, File path) {
        if (manager == null) {
            Button btnAnio = view.findViewById(R.id.buttonAnio);
            String dateString = "1/01/" + btnAnio.getText().toString();
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date[] date = null;
            try {
                date = new Date[]{format.parse(dateString)};
            } catch (ParseException e) {
                Log.e("Parse error ", e.getClass().toString(), e);
            }

            manager = new ConcreteCustomerManager(ConcreteReader.getInstance().readCustomersYear(date, path));
        }

        return manager;
    }


    private void initYearButton(View view) {
        int year;
        Calendar actualDate = Calendar.getInstance();
        year = actualDate.get(Calendar.YEAR);

        Button btn = view.findViewById(R.id.buttonAnio);
        btn.setText(Integer.toString(year));

        btn.setOnClickListener(v -> {
            MonthPickerDialog.Builder builder;
            builder = new MonthPickerDialog.Builder(getContext(), (selectedMonth, selectedYear) -> {
                btn.setText(Integer.toString(selectedYear));
                manager = null;
            }, year, 0);

            builder.showYearOnly()
                    .setYearRange(MIN_YEAR,MAX_YEAR)
                    .setTitle(getString(R.string.elija_anio))
                    .build()
                    .show();
        });
    }

    private void setFragment(int position, Bundle bnd) {
        MainActivity act = (MainActivity) getActivity();

        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        Fragment frag;
        switch (position) {
            case FRAGMENT_RESULTADOS:
                fragmentManager = Objects.requireNonNull(act).getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                frag = new ResultadosCalculosFragment();
                frag.setArguments(bnd);
                fragmentTransaction.replace(R.id.fragment_resultados_anio, frag);
                fragmentTransaction.commit();
                break;

        }
    }
}

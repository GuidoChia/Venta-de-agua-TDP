package skrb.appprueba.Fragments;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import skrb.appprueba.R.id;
import skrb.appprueba.R.layout;

public class CalcularFragment extends Fragment {
    private static final int FRAGMENT_RESULTADOS = 0;
    public static final int MIN_YEAR = 1990;
    public static final int MAX_YEAR = 2050;
    @Nullable
    CustomerManager manager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layout.fragment_calcular, container, false);
        final MainActivity act = (MainActivity) getActivity();
        Objects.requireNonNull(act.getSupportActionBar()).setTitle("Calcular");

        initMonthButton(view);

        File path = Environment.getExternalStorageDirectory();

        Button btn = view.findViewById(id.calcular_dinero_mensual);
        btn.setOnClickListener(v -> {
            CustomerManager manager = getMonthManager(view, path);

            Bundle bnd = new Bundle();
            bnd.putString("result", String.valueOf(manager.getPaid()));

            setFragment(FRAGMENT_RESULTADOS, bnd);
        });

        btn = view.findViewById(id.calcular_bidones_12);
        btn.setOnClickListener(v -> {
            CustomerManager manager = getMonthManager(view, path);

            Bundle bnd = new Bundle();
            bnd.putString("result", String.valueOf(manager.getTwelveBought()));

            setFragment(FRAGMENT_RESULTADOS, bnd);
        });

        btn = view.findViewById(id.calcular_bidones_20);
        btn.setOnClickListener(v -> {

            CustomerManager manager = getMonthManager(view, path);

            Bundle bnd = new Bundle();
            bnd.putString("result", String.valueOf(manager.getTwentyBought()));

            setFragment(FRAGMENT_RESULTADOS, bnd);
        });

        btn = view.findViewById(id.calcular_total_bidones);
        btn.setOnClickListener(v -> {
            CustomerManager manager = getMonthManager(view, path);

            Bundle bnd = new Bundle();
            int res = manager.getTwelveBought() + manager.getTwentyBought();
            bnd.putString("result", String.valueOf(res));

            setFragment(FRAGMENT_RESULTADOS, bnd);
        });

        return view;
    }

    @NonNull
    CustomerManager getMonthManager(View view, File path) {
        if (manager == null) {
            Button btnMes = view.findViewById(id.buttonMesAño);
            String dateString = "1/" + btnMes.getText().toString();
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date[] date = null;
            try {
                date = new Date[]{format.parse(dateString)};
            } catch (ParseException e) {
                Log.e("Parse error ", e.getStackTrace().toString());
            }

            manager = new ConcreteCustomerManager(ConcreteReader.getInstance().readCostumersMonth(date, path));
        }

        return manager;
    }


    private void initMonthButton(View view) {
        int month, year;
        Calendar actualDate = Calendar.getInstance();
        month = actualDate.get(Calendar.MONTH);
        year = actualDate.get(Calendar.YEAR);

        Button btn = view.findViewById(id.buttonMesAño);
        btn.setText((month + 1) + "/" + year);

        btn.setOnClickListener(v -> {
            MonthPickerDialog.Builder builder;
            builder = new MonthPickerDialog.Builder(getContext(), (selectedMonth, selectedYear) -> {
                btn.setText((selectedMonth + 1) + "/" + selectedYear);
                manager = null;
            }, year, month);

            builder.setActivatedMonth(month)
                    .setMinYear(MIN_YEAR)
                    .setActivatedYear(year)
                    .setMaxYear(MAX_YEAR)
                    .setMinMonth(Calendar.JANUARY)
                    .setTitle(getString(R.string.elija_mes))
                    .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                    .build()
                    .show();
        });
    }

    public void setFragment(int position, Bundle bnd) {
        MainActivity act = (MainActivity) getActivity();

        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        Fragment frag;
        switch (position) {
            case FRAGMENT_RESULTADOS:
                fragmentManager = Objects.requireNonNull(act).getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                frag = new ResultadosMensualesFragment();
                frag.setArguments(bnd);
                fragmentTransaction.replace(id.fragment_resultados_mes, frag);
                fragmentTransaction.commit();
                break;

        }
    }
}

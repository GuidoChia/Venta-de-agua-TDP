package skrb.appprueba.Fragments;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.io.File;
import java.util.Calendar;

import reader.ConcreteMonthManager;
import reader.ConcreteReader;
import reader.MonthManager;
import skrb.appprueba.MainActivity;
import skrb.appprueba.R;
import utils.Pair;

public class CalcularFragment extends Fragment {
    private static final int FRAGMENT_RESULTADOS = 0;
    MonthManager manager = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calcular, container, false);
        final MainActivity act = (MainActivity) getActivity();
        act.getSupportActionBar().setTitle("Calcular");

        initMonthButton(view);

        File path = Environment.getExternalStorageDirectory();

        Button btn = view.findViewById(R.id.calcular_dinero_mensual);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MonthManager manager = getMonthManager(view, path);

                Bundle bnd = new Bundle();
                bnd.putString("result", manager.getPaid() + "");

                setFragment(FRAGMENT_RESULTADOS, bnd);
            }
        });

        btn = view.findViewById(R.id.calcular_bidones_12);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthManager manager = getMonthManager(view, path);

                Bundle bnd = new Bundle();
                bnd.putString("result", manager.getTwelveBought() + "");

                setFragment(FRAGMENT_RESULTADOS, bnd);
            }
        });

        btn = view.findViewById(R.id.calcular_bidones_20);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthManager manager = getMonthManager(view, path);

                Bundle bnd = new Bundle();
                bnd.putString("result", manager.getTwentyBought() + "");

                setFragment(FRAGMENT_RESULTADOS, bnd);
            }
        });

        btn = view.findViewById(R.id.calcular_total_bidones);
        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                MonthManager manager = getMonthManager(view, path);

                Bundle bnd = new Bundle();
                int res = manager.getTwelveBought()+manager.getTwentyBought();
                bnd.putString("result", res + "");

                setFragment(FRAGMENT_RESULTADOS, bnd);
            }
        });

        return view;
    }

    @NonNull
    private MonthManager getMonthManager(View view, File path) {

        if (manager == null) {
            Button btnMes = view.findViewById(R.id.buttonMesAño);
            String[] strings = btnMes.getText().toString().split("/");
            int month = Integer.parseInt(strings[0]);
            int year = Integer.parseInt(strings[1]);

            Pair<Integer, Integer> monthAndYear = new Pair<>(month, year);

            Pair<Integer, Integer>[] monthsAndYears = new Pair[]{monthAndYear};
            manager = new ConcreteMonthManager(ConcreteReader.getInstance().readCostumers(monthsAndYears, path));
        }

        return manager;
    }


    private void initMonthButton(View view) {
        int month, year;
        Calendar actualDate = Calendar.getInstance();
        month = actualDate.get(Calendar.MONTH);
        year = actualDate.get(Calendar.YEAR);

        Button btn = view.findViewById(R.id.buttonMesAño);
        btn.setText((month + 1) + "/" + year);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthPickerDialog.Builder builder;
                builder = new MonthPickerDialog.Builder(getContext(), new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        btn.setText((selectedMonth + 1) + "/" + selectedYear);
                        manager = null;
                    }
                }, year, month);

                builder.setActivatedMonth(month)
                        .setMinYear(1990)
                        .setActivatedYear(year)
                        .setMaxYear(2030)
                        .setMinMonth(Calendar.JANUARY)
                        .setTitle(getString(R.string.elija_mes))
                        .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                        .build()
                        .show();
            }
        });
    }

    public void setFragment(int position, Bundle bnd) {
        MainActivity act = (MainActivity) getActivity();

        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        Fragment frag = null;
        switch (position) {
            case FRAGMENT_RESULTADOS:
                fragmentManager = act.getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                frag = new ResultadosMensualesFragment();
                frag.setArguments(bnd);
                fragmentTransaction.replace(R.id.fragment_resultados_mes, frag);
                fragmentTransaction.commit();
                break;
        }
    }
}

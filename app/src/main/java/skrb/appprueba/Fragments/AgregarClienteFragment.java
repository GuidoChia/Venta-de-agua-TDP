package skrb.appprueba.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import infos.BuyInfo;
import infos.ConcreteBuyInfo;
import infos.ConcretePriceInfo;
import infos.PriceInfo;
import skrb.appprueba.MainActivity;
import skrb.appprueba.R;
import writer.ConcreteWriter;
import writer.ExcelWriter;

import static skrb.appprueba.helpers.fileRW.findFileWrite;


public class AgregarClienteFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_agregar_cliente, container, false);

        MainActivity act = (MainActivity) getActivity();
        act.getSupportActionBar().setTitle("Agregar Cliente");

        int day, month, year;
        Calendar actualDate = Calendar.getInstance();
        day = actualDate.get(Calendar.DAY_OF_MONTH);
        month = actualDate.get(Calendar.MONTH);
        year = actualDate.get(Calendar.YEAR);
        final DatePickerDialog dialogFecha = new DatePickerDialog(this.getContext(), AgregarClienteFragment.this, year, month, day);

        Button botonFecha = view.findViewById(R.id.BotonFecha);
        botonFecha.setText(day + "/" + (month + 1) + "/" + year);
        botonFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFecha.show();
            }
        });

        Button botonConfirmar = view.findViewById(R.id.BotonConfirmar);
        botonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExcelWriter writer = ConcreteWriter.getInstance();

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

                TextInputLayout lay = view.findViewById(R.id.InputLayoutClient);
                EditText[] editTexts = {
                        lay.getEditText(),
                        view.findViewById(R.id.BidonesLlevoDe20),
                        view.findViewById(R.id.BidonesLlevoDe12),
                        view.findViewById(R.id.BidonesDevueltos20),
                        view.findViewById(R.id.BidonesDevueltos12),
                        view.findViewById(R.id.DineroPagado)
                };

                if (checkInputs(editTexts)) {
                    writeToFile(writer, editTexts, view);
                } else {
                    Bundle bnd = new Bundle();
                    bnd.putInt("msg", R.string.errorAgregar);
                    ErrorFragment frag = new ErrorFragment();
                    frag.setArguments(bnd);
                    frag.show(getFragmentManager(), "error");
                }


            }

            private boolean checkInputs(EditText[] inputs) {
                for (EditText input : inputs) {
                    if (TextUtils.isEmpty(input.getText().toString())) {
                        return false;
                    }
                }
                return true;
            }
        });

        return view;
    }

    private void writeToFile(ExcelWriter writer, EditText[] editTexts, View view) {


        int i = 0;

        EditText input = editTexts[i];
        String name = input.getText().toString();
        i++;

        input = editTexts[i];
        int bidones20 = Integer.parseInt(input.getText().toString());
        i++;

        input = editTexts[i];
        int bidones12 = Integer.parseInt(input.getText().toString());
        i++;

        input = editTexts[i];
        int bidones_devueltos_20 = Integer.parseInt(input.getText().toString());
        i++;

        input = editTexts[i];
        int bidones_devueltos_12 = Integer.parseInt(input.getText().toString());
        i++;

        input = editTexts[i];
        int dinero_pagado = Integer.parseInt(input.getText().toString());

        Button bt = view.findViewById(R.id.BotonFecha);
        String dateString = bt.getText().toString();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        BuyInfo info = new ConcreteBuyInfo(dinero_pagado, bidones20, bidones12, bidones_devueltos_20,
                bidones_devueltos_12, date, name);


        double twelvePrice,
                twentyPrice;

        SharedPreferences preferences = getContext().getSharedPreferences(MainActivity.PRICE_PREFS, 0);
        twelvePrice = preferences.getFloat(MainActivity.PRICE_12, 50);
        twentyPrice = preferences.getFloat(MainActivity.PRICE_20, 70);

        PriceInfo prices = new ConcretePriceInfo(twelvePrice, twentyPrice);

        File file = findFileWrite(name);

        writer.WriteBuy(info, prices, file);

        Snackbar snackbarAgregado = Snackbar.make(view, R.string.msg_agregado, Snackbar.LENGTH_LONG);
        snackbarAgregado.show();
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        MainActivity act = (MainActivity) getActivity();
        Button btn = act.findViewById(R.id.BotonFecha);
        btn.setText(day + "/" + (month + 1) + "/" + year);
    }


}

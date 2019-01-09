package skrb.appprueba.Fragments;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;


import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
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

                    /* esto va a cambiar*/
                    PriceInfo prices = new ConcretePriceInfo(50, 70);

                    File file = findFileWrite(name);

                    writer.WriteBuy(info, prices, file);
                } else {
                    ErrorFragment frag = new ErrorFragment();
                    frag.show(getFragmentManager(), "error");
                }

            }

            private boolean checkInputs(EditText[] inputs) {
                for (EditText input : inputs) {
                    if (TextUtils.isEmpty(input.getText().toString())){
                        return false;}
                }
                return true;
            }
        });

        return view;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        MainActivity act = (MainActivity) getActivity();
        Button btn = act.findViewById(R.id.BotonFecha);
        btn.setText(day + "/" + (month + 1) + "/" + year);
    }
}

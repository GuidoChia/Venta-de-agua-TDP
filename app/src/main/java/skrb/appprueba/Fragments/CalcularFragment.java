package skrb.appprueba.Fragments;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;
import java.util.Collection;

import customer.Customer;
import reader.ConcreteReader;
import reader.ExcelReader;
import skrb.appprueba.MainActivity;
import skrb.appprueba.R;

public class CalcularFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calcular, container, false);

        File path = Environment.getExternalStorageDirectory();
        ExcelReader reader = ConcreteReader.getInstance();
        <Collection<Customer> customers = reader.readCostumers()
        final MainActivity act = (MainActivity) getActivity();
        act.getSupportActionBar().setTitle("Calcular");

        Button btn = view.findViewById(R.id.calcular_dinero_mensual);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}

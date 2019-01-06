package skrb.appprueba.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import skrb.appprueba.MainActivity;
import skrb.appprueba.R;

public class CalcularFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calcular, container, false);

        final MainActivity act = (MainActivity) getActivity();
        act.getSupportActionBar().setTitle("Calcular");

        return view;
    }
}

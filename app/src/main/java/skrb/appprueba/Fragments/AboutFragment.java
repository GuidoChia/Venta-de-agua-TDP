package skrb.appprueba.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import skrb.appprueba.MainActivity;
import skrb.appprueba.R;

public class AboutFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_about, container, false);

        MainActivity act = (MainActivity) getActivity();
        act.getSupportActionBar().setTitle("About");


        return view;
    }


}

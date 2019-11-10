package skrb.appprueba.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import java.util.Objects;

import daos.CustomerDAO;
import database.YporaDatabase;
import entities.CustomerEntity;
import skrb.appprueba.MainActivity;
import skrb.appprueba.R.layout;

public class AboutFragment extends Fragment {
    boolean light = true;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(layout.fragment_about, container, false);

        MainActivity act = (MainActivity) getActivity();
        Objects.requireNonNull(act.getSupportActionBar()).setTitle("About");

        YporaDatabase db = Room.databaseBuilder(this.getContext(), YporaDatabase.class, "db").allowMainThreadQueries().build();

        CustomerDAO dao = db.getCustomerDAO();
        //dao.insert("Guido");
        //dao.insert("Pepe");

        dao.updateBalance(10, "Guido");

        for (CustomerEntity entity: dao.getAll()){
            Log.d("Ypora", entity.getCustomerName()+" balance "+entity.getCustomerBalance());
        }

        return view;
    }


}

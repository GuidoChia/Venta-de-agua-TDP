package skrb.appprueba.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import database.DatabaseBuilderHelper;
import database.YporaDatabase;
import entities.BuyEntity;
import entities.CustomerEntity;
import entities.ExtraBuyEntity;
import entities.TwelveBuyEntity;
import entities.TwentyBuyEntity;
import skrb.appprueba.MainActivity;
import skrb.appprueba.R.layout;

public class AboutFragment extends Fragment {
    boolean light = true;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(layout.fragment_about, container, false);

        MainActivity act = (MainActivity) getActivity();
        Objects.requireNonNull(act.getSupportActionBar()).setTitle("About");

        YporaDatabase db = DatabaseBuilderHelper.getDatabase(this.getContext());

        TwelveBuyEntity twelveBuyEntity = new TwelveBuyEntity();
        twelveBuyEntity.setTwelveBoughtAmount(1);
        twelveBuyEntity.setTwelveReturnedAmount(1);
        twelveBuyEntity.setTwelvePrice(70);

        ExtraBuyEntity extraBuyEntity = new ExtraBuyEntity();
        extraBuyEntity.setExtraBuyPrice(100);
        extraBuyEntity.setExtraBuyDescription("Compra A");

        TwentyBuyEntity twentyBuyEntity = new TwentyBuyEntity();
        twentyBuyEntity.setTwentyBoughtAmount(1);
        twentyBuyEntity.setTwentyReturnedAmount(1);
        twentyBuyEntity.setTwentyPrice(100);

        for (CustomerEntity entity : db.getCustomerDAO().getAll()) {
            Log.d("Ypora", entity.getCustomerName() + " balance " + entity.getCustomerBalance());
        }

        for (BuyEntity entity : db.getBuyDAO().getAll()) {
            Log.d("Ypora", "buy: " + entity.getCustomerName() + " " + entity.getBuyDate() + " " + entity.getBuyPaid() + " extraId " + entity.getExtraBuyId() + " twelveId " + entity.getTwelveId() + " twentyId " + entity.getTwentyId());
        }

        for (TwelveBuyEntity entity : db.getTwelveBuyDAO().getAllTwelveBuys()) {
            Log.d("Ypora", "Twelve buy id " + entity.getTwelveId() + " comprados: " + entity.getTwelveBoughtAmount());
        }

        for (TwentyBuyEntity entity : db.getTwentyBuyDAO().getAllTwentyBuys()) {
            Log.d("Ypora", "Twenty buy id " + entity.getTwentyId() + " comprados: " + entity.getTwentyBoughtAmount());
        }

        for (ExtraBuyEntity entity : db.getExtraBuyDAO().getAllExtraBuys()) {
            Log.d("Ypora", "Extra buy id " + entity.getExtraBuyId() + " comprados: " + entity.getExtraBuyDescription());
        }

        return view;
    }


}

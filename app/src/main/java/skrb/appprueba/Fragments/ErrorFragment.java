package skrb.appprueba.Fragments;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

import skrb.appprueba.R.string;

public class ErrorFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());

        int id = Objects.requireNonNull(getArguments()).getInt("msg");
        String msg = getString(id);
        builder.setMessage(msg)
                .setPositiveButton(string.OK, (dialog, id1) -> {
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
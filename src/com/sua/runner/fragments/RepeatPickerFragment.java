package com.sua.runner.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import com.sua.runner.R;
import com.sua.runner.Utils;

public class RepeatPickerFragment extends DialogFragment {

    public static final int MIN_VALUE = 0;
    public static final int REPEAT_MAX_VALUE = 100;

    private TextView textView;

    public RepeatPickerFragment(TextView textView) {
        this.textView = textView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_repeat_picker, null);

        final NumberPicker repeatPicker = (NumberPicker) dialogView.findViewById(R.id.picker_repeat);
        repeatPicker.setMinValue(MIN_VALUE);
        repeatPicker.setMaxValue(REPEAT_MAX_VALUE);

        int tagRepeat = (Integer)textView.getTag();
        repeatPicker.setValue(tagRepeat);

        builder.setView(dialogView)
                .setTitle(getString(R.string.time_picker_dialog_title))
                .setPositiveButton(R.string.set, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        int value = repeatPicker.getValue();
                        String repeat = Utils.repeatInString(value, getActivity());
                        textView.setText(repeat);
                        textView.setTag(value);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RepeatPickerFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
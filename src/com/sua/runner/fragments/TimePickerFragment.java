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
import com.sua.runner.utilities.Utils;

public class TimePickerFragment extends DialogFragment {

    public static final int MIN_VALUE = 0;
    public static final int MINUTES_MAX_VALUE = 60;
    public static final int SECONDS_MAX_VALUE = 59;

    private TextView textView;

    public TimePickerFragment(TextView textView) {
        this.textView = textView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_time_picker, null);

        final NumberPicker minutesPicker = (NumberPicker) dialogView.findViewById(R.id.picker_minutes);
        minutesPicker.setMinValue(MIN_VALUE);
        minutesPicker.setMaxValue(MINUTES_MAX_VALUE);
        final NumberPicker secondsPicker = (NumberPicker) dialogView.findViewById(R.id.picker_seconds);
        secondsPicker.setMinValue(MIN_VALUE);
        secondsPicker.setMaxValue(SECONDS_MAX_VALUE);

        int tagTime = (Integer)textView.getTag();
        int tagMinutes = Utils.getMinutes(tagTime);
        int tagSeconds = Utils.getSeconds(tagTime);
        minutesPicker.setValue(tagMinutes);
        secondsPicker.setValue(tagSeconds);

        builder.setView(dialogView)
                .setTitle(getString(R.string.time_picker_dialog_title))
                .setPositiveButton(R.string.set, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        int timeInSeconds = Utils.timeInSeconds(minutesPicker.getValue(), secondsPicker.getValue());
                        String time = Utils.timeInString(timeInSeconds, getActivity());
                        textView.setText(time);
                        textView.setTag(timeInSeconds);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TimePickerFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
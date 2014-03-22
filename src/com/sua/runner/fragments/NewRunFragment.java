package com.sua.runner.fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sua.runner.R;
import com.sua.runner.Utils;

import java.util.ArrayList;

public class NewRunFragment extends Fragment {

    public static final String TIME_PICKER = "time_picker";
    public static final int PRESET_RUN = 60;
    public static final int PRESET_WALK_IN_RUN = 2 * 60;
    public static final int PRESET_REPEAT = 10;
    public static final int PRESET_WALK = 5 * 60;

    private LinearLayout layoutRuns;
    private ArrayList<View> runViews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_run, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        runViews = new ArrayList<View>();
        layoutRuns = (LinearLayout) view.findViewById(R.id.layout_runs);
        Button btnAddRun = (Button) view.findViewById(R.id.btn_add_run);
        Button btnStartRun = (Button) view.findViewById(R.id.btn_start_run);

        //add first default run
        addRunBlock();

        initWalks(view);

        btnAddRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addRunBlock();
            }
        });
        btnStartRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: start run!!
            }
        });
    }

    private void initWalks(View view) {
        final View beforeWalkView = view.findViewById(R.id.walk_before);
        final View afterWalkView = view.findViewById(R.id.walk_after);

        ((TextView)afterWalkView.findViewById(R.id.text_type)).setText(getString(R.string.walk_after));
        presetWalkValues(beforeWalkView, afterWalkView);

        beforeWalkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment((TextView)beforeWalkView.findViewById(R.id.value));
                newFragment.show(getFragmentManager(), TIME_PICKER);
            }
        });
        afterWalkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment((TextView)afterWalkView.findViewById(R.id.value));
                newFragment.show(getFragmentManager(), TIME_PICKER);
            }
        });
    }

    private void addRunBlock() {
        final View runView = getActivity().getLayoutInflater().inflate(R.layout.run_block, layoutRuns, false);
        presetRunValues(runView);

        ImageButton btnDelete = (ImageButton)runView.findViewById(R.id.btn_delete);
        final View rowRun = runView.findViewById(R.id.row_run);
        final View rowWalk = runView.findViewById(R.id.row_walk);
        final View rowRepeat = runView.findViewById(R.id.row_repeat);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutRuns.removeView(runView);
            }
        });
        rowRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment((TextView)rowRun.findViewById(R.id.value_run));
                newFragment.show(getFragmentManager(), TIME_PICKER);
            }
        });
        rowWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment((TextView)rowWalk.findViewById(R.id.value_walk));
                newFragment.show(getFragmentManager(), TIME_PICKER);
            }
        });
        rowRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new RepeatPickerFragment((TextView)rowRepeat.findViewById(R.id.value_repeat));
                newFragment.show(getFragmentManager(), TIME_PICKER);
            }
        });

        runViews.add(runView);
        layoutRuns.addView(runView);
    }

    private void presetRunValues(View runView) {
        TextView textRun = (TextView)runView.findViewById(R.id.value_run);
        (textRun).setText(Utils.timeInString(PRESET_RUN, getActivity()));
        textRun.setTag(PRESET_RUN);
        TextView textWalk  = (TextView)runView.findViewById(R.id.value_walk);
        (textWalk).setText(Utils.timeInString(PRESET_WALK_IN_RUN, getActivity()));
        textWalk.setTag(PRESET_WALK_IN_RUN);
        TextView textRepeat = (TextView)runView.findViewById(R.id.value_repeat);
        (textRepeat).setText(Utils.repeatInString(PRESET_REPEAT, getActivity()));
        textRepeat.setTag(PRESET_REPEAT);
    }

    private void presetWalkValues(View beforeWalkView, View afterWalkView) {
        TextView textBeforeWalk = (TextView)beforeWalkView.findViewById(R.id.value);
        (textBeforeWalk).setText(Utils.timeInString(PRESET_WALK, getActivity()));
        textBeforeWalk.setTag(PRESET_WALK);
        TextView textAfterWalk = (TextView)afterWalkView.findViewById(R.id.value);
        (textAfterWalk).setText(Utils.timeInString(PRESET_WALK, getActivity()));
        textAfterWalk.setTag(PRESET_WALK);
    }
}
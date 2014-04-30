package com.sua.runner.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sua.runner.model.Training;
import com.sua.runner.utilities.Config;
import com.sua.runner.utilities.PreferencesManager;
import com.sua.runner.R;
import com.sua.runner.utilities.Utils;
import com.sua.runner.model.RunBlock;
import com.sua.runner.views.FloatingTimeView;

import java.util.ArrayList;

public class CurrentRunFragment extends Fragment {

    private LinearLayout runsLayout;
    private FloatingTimeView layoutTime;

    private int index = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_run, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        runsLayout = (LinearLayout)view.findViewById(R.id.layout_runs);
        layoutTime = (FloatingTimeView)view.findViewById(R.id.layout_time);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        PreferencesManager prefs = new PreferencesManager(getActivity());
        if(prefs.getCurrentActionType() != Config.TYPE_NONE && prefs.getTimeStartedAction() != 0) {
            initRun();
            layoutTime.setNextAnimation();
        }
    }

    private void addWalkItem(int duration) {
        View walkView = getActivity().getLayoutInflater().inflate(R.layout.current_list_item, runsLayout, false);
        TextView nameText = (TextView)walkView.findViewById(R.id.name);
        nameText.setBackgroundColor(getResources().getColor(R.color.dark_blue_transparent));
        nameText.setText("Walk #" + index++);
        TextView timeText = (TextView)walkView.findViewById(R.id.time);
        timeText.setText(Utils.timeInString(duration, getActivity()));
        ViewGroup.LayoutParams layoutParams = walkView.getLayoutParams();
        layoutParams.height = Utils.getTrainingItemHeight(getActivity(), duration);
        walkView.setLayoutParams(layoutParams);
        runsLayout.addView(walkView);
    }

    private void addRunItem(int duration) {
        View runView = getActivity().getLayoutInflater().inflate(R.layout.current_list_item, runsLayout, false);
        TextView nameText = (TextView)runView.findViewById(R.id.name);
        nameText.setBackgroundColor(getResources().getColor(R.color.orange_transparent));
        nameText.setText("Training #" + index);
        TextView timeText = (TextView)runView.findViewById(R.id.time);
        timeText.setText(Utils.timeInString(duration, getActivity()));
        ViewGroup.LayoutParams layoutParams = runView.getLayoutParams();
        layoutParams.height = Utils.getTrainingItemHeight(getActivity(), duration);
        runView.setLayoutParams(layoutParams);
        runsLayout.addView(runView);
    }



    public void initRun() {
        index = 0;

        Training training = new PreferencesManager(getActivity()).getTraining();
        runsLayout.removeAllViews();

        if(training != null) {
            addWalkItem(training.getWalkBeforeTime());

            ArrayList<RunBlock> runBlocks = training.getRunBlocks();
            for(int i = 0; i < runBlocks.size(); i++) {
                for(int j = 0; j < runBlocks.get(i).getRepeat(); j++) {
                    addRunItem(runBlocks.get(i).getRunTime());
                    addWalkItem(runBlocks.get(i).getWalkTime());
                }
            }

            addWalkItem(training.getWalkAfterTime());
            runsLayout.invalidate();
        }
    }

    public void setNextActionUI() {
        layoutTime.setNextAnimation();
    }
}
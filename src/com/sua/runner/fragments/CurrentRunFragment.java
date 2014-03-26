package com.sua.runner.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sua.runner.R;

public class CurrentRunFragment extends Fragment {

    private static int HEIGHT_MULTIPLIER = 40;
    private static int MIN_HEIGHT = 60;
    private static int MAX_HEIGHT = 60*5;

    private LinearLayout runsLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_run, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        runsLayout = (LinearLayout)view.findViewById(R.id.layout_runs);
        for(int i = 0; i < 20; i++) {
            addRunItem(i+1);
            addWalkItem(i+1);
        }
    }

    private void addWalkItem(int duration) {
        View walkView = getActivity().getLayoutInflater().inflate(R.layout.current_list_item, runsLayout, false);
        TextView nameText = (TextView)walkView.findViewById(R.id.name);
        nameText.setBackgroundColor(getResources().getColor(R.color.dark_blue_transparent));
        nameText.setText("Walk #" + duration);
        TextView timeText = (TextView)walkView.findViewById(R.id.time);
        timeText.setText(duration + " min");
        ViewGroup.LayoutParams layoutParams = walkView.getLayoutParams();
        layoutParams.height = getHeight(duration);
        walkView.setLayoutParams(layoutParams);
        runsLayout.addView(walkView);
    }

    private void addRunItem(int duration) {
        View runView = getActivity().getLayoutInflater().inflate(R.layout.current_list_item, runsLayout, false);
        TextView nameText = (TextView)runView.findViewById(R.id.name);
        nameText.setBackgroundColor(getResources().getColor(R.color.orange_transparent));
        nameText.setText("Run #" + duration);
        TextView timeText = (TextView)runView.findViewById(R.id.time);
        timeText.setText(duration + " min");
        ViewGroup.LayoutParams layoutParams = runView.getLayoutParams();
        layoutParams.height = getHeight(duration);
        runView.setLayoutParams(layoutParams);
        runsLayout.addView(runView);
    }

    private int getHeight(int duration) {
        float dp = getResources().getDisplayMetrics().density;
        int height = (int)(duration * HEIGHT_MULTIPLIER * dp);
        if(height < MIN_HEIGHT * dp) {
            return (int)(MIN_HEIGHT * dp);
        }
        if(height > MAX_HEIGHT * dp) {
            return (int)(MAX_HEIGHT * dp);
        }
        return height;
    }
}
package com.dino.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.dino.customview.widget.WaveView2;

/**
 * Created by Dino on 4/19 0019.
 */

public class Wave2Fragment extends Fragment implements SeekBar.OnSeekBarChangeListener{
    private WaveView2 waveView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wave2,container,false);
        init(view);
        return view;
    }

    private void init(View view){
        waveView = (WaveView2)view.findViewById(R.id.waveView2);
        ((SeekBar)view.findViewById(R.id.waveheight_seek)).setOnSeekBarChangeListener(this);
        ((SeekBar)view.findViewById(R.id.wavecount_seek)).setOnSeekBarChangeListener(this);
        ((SeekBar)view.findViewById(R.id.wavelen_seek)).setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            case R.id.waveheight_seek:
                waveView.setWaveHeight(progress*10);
                break;
            case R.id.wavecount_seek:
                waveView.setmWaveCount(progress);
                break;
            case R.id.wavelen_seek:
                waveView.setmWL(progress*1000);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

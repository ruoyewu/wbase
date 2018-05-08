package com.wuruoye.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wuruoye.library.ui.WBaseActivity;
import com.wuruoye.library.widget.chart.SortView;

/**
 * @Created : wuruoye
 * @Date : 2018/5/6 11:33.
 * @Description :
 */

public class SortActivity extends WBaseActivity implements View.OnClickListener {
    private SortView sv;
    private Button btnGen;

    @Override
    protected int getContentView() {
        return R.layout.activity_sort;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView() {
        sv = findViewById(R.id.sv_sort);
        btnGen = findViewById(R.id.btn_gen_sort);

        btnGen.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_gen_sort:
                sv.setValues(generateValues());
                break;
        }
    }

    private int[] generateValues() {
        int length = (int) (Math.random() * 10) + 10;
        int[] values = new int[length];
        for (int i = 0; i < length; i++) {
            values[i] = (int) (Math.random() * 20);
        }
        return values;
    }
}

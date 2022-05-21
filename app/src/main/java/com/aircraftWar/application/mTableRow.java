package com.aircraftWar.application;

import android.content.Context;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

public class mTableRow extends TableRow {
    private int index;
    private boolean isChosen;

    public void setChosen(boolean chosen) {
        isChosen = chosen;
    }

    public boolean isChosen() {
        return isChosen;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public mTableRow(Context context) {
        super(context);
    }
}

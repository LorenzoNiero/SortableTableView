package de.codecrafters.tableviewexample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;
import de.codecrafters.tableview.TableDataAdapterRecycler;
import de.codecrafters.tableview.ViewHolderBase;
import de.codecrafters.tableviewexample.data.Car;


public class CarTableDataAdapter extends TableDataAdapterRecycler<Car> {


    public CarTableDataAdapter(Context context, List<Car> myDataset) {
        super(context, myDataset);
    }


    @Override
    protected ViewHolderBase getHolder(LinearLayout v, ViewGroup parent, int widthUnit) {
return new ViewHolderTextView(getContext(),v, parent, widthUnit, getColumnCount(), getColumnModel());
    }





}

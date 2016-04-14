package de.codecrafters.tableview;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.codecrafters.tableview.listeners.TableHeaderClickListener;
import de.codecrafters.tableview.providers.SortStateViewProvider;

/**
 *
 * @author LorenzoNiero
 */
public class SortableTablePagingView<T> extends SortableTableView<T> {

    protected Button mPrevButton;
    protected Button mNextButton;
    protected TextView pageTextView;

    private int pageCount=0 ;
    /**
     * Using this increment value we can move the listview items
     */
    private int increment = 0;

    public int TOTAL_LIST_ITEMS = 1030;
    public int NUM_ITEMS_PAGE   = 5;

    List<T> allData ;


    public SortableTablePagingView(Context context) {
        super(context, null);
    }

    public SortableTablePagingView(Context context, AttributeSet attributes) {
        super(context, attributes, 0);
    }

    public SortableTablePagingView(Context context, AttributeSet attributes, int styleAttributes) {
        super(context, attributes, styleAttributes);


        View child = LayoutInflater.from(this.getContext()).inflate( R.layout.paging_footer, null);
        child.setId(R.id.table_footer_view);

        mPrevButton = (Button) child.findViewById(R.id.prev);
        mNextButton = (Button) child.findViewById(R.id.next);
        pageTextView = (TextView) child.findViewById(R.id.pageTextView);

        addView(child);

        //set position
        RelativeLayout.LayoutParams layoutParamsRelative =
                (RelativeLayout.LayoutParams)child.getLayoutParams();
        layoutParamsRelative.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParamsRelative.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParamsRelative.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        child.setLayoutParams(layoutParamsRelative);

        //position list above footer
        layoutParamsRelative =
                (RelativeLayout.LayoutParams)tableDataView.getLayoutParams();
        layoutParamsRelative.addRule(RelativeLayout.ABOVE, R.id.table_footer_view);
        tableDataView.setLayoutParams(layoutParamsRelative);

        /**
         * this block is for checking the number of pages
         * ====================================================
         */

        //int val = TOTAL_LIST_ITEMS%NUM_ITEMS_PAGE;
        //val = val==0?0:1;
        //pageCount = TOTAL_LIST_ITEMS/NUM_ITEMS_PAGE+val;
        /**
         * =====================================================
         */

        mNextButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                increment++;
                loadList(increment);
                CheckEnable();
            }
        });

        mPrevButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                increment--;
                loadList(increment);
                CheckEnable();
            }
        });

    }

    /**
     * Method for enabling and disabling Buttons
     */
    private void CheckEnable()
    {
        if(increment+1 == pageCount)
        {
            mNextButton.setEnabled(false);
        }
        else if(increment == 0)
        {
            mPrevButton.setEnabled(false);
        }
        else
        {
            mPrevButton.setEnabled(true);
            mNextButton.setEnabled(true);
        }
    }

    /**
     * Method for loading data in listview
     * @param number
     */
    private void loadList(int number)
    {
        ArrayList<T> sort = new ArrayList<T>();
        pageTextView.setText("Page " + (number + 1) + " of " + pageCount);

        int start = number * NUM_ITEMS_PAGE;
        for(int i=start;i<(start)+NUM_ITEMS_PAGE;i++)
        {
            if(i<allData.size())
            {
                sort.add(allData.get(i));
            }
            else
            {
                break;
            }
        }
        ;

        tableDataAdapter.setList(sort);
    }

    public List<T> getAllData() {
        return allData;
    }

    public void setAllData(List<T> allData) {
        this.allData = allData;
        TOTAL_LIST_ITEMS = allData.size();

        //block is for checking the number of pages
        int val = TOTAL_LIST_ITEMS%NUM_ITEMS_PAGE;
        val = val==0?0:1;
        pageCount = TOTAL_LIST_ITEMS/NUM_ITEMS_PAGE+val;

        loadList(0);
    }

    @Override
    public void setDataAdapter(final TableDataAdapter<T> dataAdapter) {
        super.setDataAdapter(dataAdapter);

        setAllData(dataAdapter.getData());
    }

}




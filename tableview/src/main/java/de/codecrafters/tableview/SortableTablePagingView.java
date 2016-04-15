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

    public enum ENUM_MODE_TABLE{
        INFINITY_LIST,
        PAGING,
        ENDLESS_LIST

    }

    protected View mFooterContainer = null;
    protected Button mPrevButton;
    protected Button mNextButton;
    protected TextView pageTextView;

    private int pageCount=0 ;
    /**
     * Using this increment value we can move the listview items
     */
    private int increment = 0;

    private int TOTAL_LIST_ITEMS = 0;
    private int NUM_ITEMS_PAGE   = 5;

    List<T> allData ;

    private ENUM_MODE_TABLE mModeTable = ENUM_MODE_TABLE.INFINITY_LIST; //default infinity list


    public SortableTablePagingView(Context context) {
        super(context, null);
    }

    public SortableTablePagingView(Context context, AttributeSet attributes) {
        super(context, attributes, 0);
    }

    public SortableTablePagingView(Context context, AttributeSet attributes, int styleAttributes) {
        super(context, attributes, styleAttributes);
        mModeTable = ENUM_MODE_TABLE.INFINITY_LIST; //default infinity list
        initFooter();

    }

    /*
    Set num max item for page. Available only in MODE PAGING
     */
    public void setNumItemsPage(int num){
        NUM_ITEMS_PAGE = num;
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

    private void setAllData(List<T> allData) {

        if ( mModeTable != ENUM_MODE_TABLE.INFINITY_LIST ) {
            //block is for checking the number of pages
            int val = TOTAL_LIST_ITEMS % NUM_ITEMS_PAGE;
            val = val == 0 ? 0 : 1;
            pageCount = TOTAL_LIST_ITEMS / NUM_ITEMS_PAGE + val;

            loadList(0);
        }
        else if ( mModeTable == ENUM_MODE_TABLE.INFINITY_LIST ){
           tableDataAdapter.setList(allData);
        }
    }

    @Override
    public void setDataAdapter(final TableDataAdapter<T> dataAdapter) {
        super.setDataAdapter(dataAdapter);

        this.allData = dataAdapter.getData();
        TOTAL_LIST_ITEMS = allData.size();

        setModeTable(mModeTable);

    }

    public void setModeTable(ENUM_MODE_TABLE mode){
        mModeTable = mode;

        switch (mode) {
            case INFINITY_LIST:
                initFooter();
                break;
            case PAGING:
                initFooter();
                //setAllData(allData);
                break;
            case ENDLESS_LIST:
                throw new RuntimeException("ENDLESS_LIST not implementation");
                //break;

        }

        if(allData != null){
            setAllData(allData);
        }

    }

    private void initFooter(){

        if (mModeTable!=ENUM_MODE_TABLE.INFINITY_LIST && mFooterContainer == null) {
            mFooterContainer = LayoutInflater.from(this.getContext()).inflate(R.layout.paging_footer, null);
            mFooterContainer.setId(R.id.table_footer_view);

            mPrevButton = (Button) mFooterContainer.findViewById(R.id.prev);
            mNextButton = (Button) mFooterContainer.findViewById(R.id.next);
            pageTextView = (TextView) mFooterContainer.findViewById(R.id.pageTextView);

            addView(mFooterContainer);

            //set position
            LayoutParams layoutParamsRelative =
                    (LayoutParams) mFooterContainer.getLayoutParams();
            layoutParamsRelative.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParamsRelative.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParamsRelative.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            mFooterContainer.setLayoutParams(layoutParamsRelative);

            //position list above footer
            layoutParamsRelative =
                    (LayoutParams) tableDataView.getLayoutParams();
            layoutParamsRelative.addRule(RelativeLayout.ABOVE, R.id.table_footer_view);
            tableDataView.setLayoutParams(layoutParamsRelative);

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

        switch (mModeTable) {
            case PAGING:
                mFooterContainer.setVisibility(VISIBLE);
                break;
            case INFINITY_LIST:
                if (mFooterContainer != null) {
                    mFooterContainer.setVisibility(GONE);
                }
                break;
        }
    }

}




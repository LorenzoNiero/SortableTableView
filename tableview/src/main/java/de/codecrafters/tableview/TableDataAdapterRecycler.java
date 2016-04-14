package de.codecrafters.tableview;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.codecrafters.tableview.colorizers.TableDataRowColorizer;

import static android.widget.LinearLayout.LayoutParams;


/**
 * The abstract implementation of an adapter used to bring data to a {@link TableView}.
 *
 * @author ISchwarz, lz91
 */
public abstract class TableDataAdapterRecycler<T> extends  RecyclerView.Adapter<ViewHolderBase> {
    private static final String LOG_TAG = TableDataAdapterRecycler.class.getName();

    private TableColumnModel columnModel;
    private final List<T> data;
    Context context;
    private TableDataRowColorizer<? super T> rowColoriser;
    private OnItemClickListener mItemClickListener;


    // Provide a suitable constructor (depends on the kind of dataset)
    public TableDataAdapterRecycler(Context context, final List<T> myDataset) {
        this (context,0, myDataset);
    }


    public TableDataAdapterRecycler(Context context, final T[] data) {
        this(context, 0,  new ArrayList<>(Arrays.asList(data)));
    }

    protected TableDataAdapterRecycler(Context context, final int columnCount, final List<T> data) {
        this( context, new TableColumnModel(columnCount), data);
    }

    protected TableDataAdapterRecycler(Context context,  final TableColumnModel columnModel, final List<T> data) {
        this.context = context;
        this.columnModel = columnModel;
        this.data = data;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolderBase onCreateViewHolder(
            ViewGroup parent,
            int viewType) {

        // create a new view
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        final LinearLayout rowView = new LinearLayout(getContext());

        final AbsListView.LayoutParams rowLayoutParams = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        rowView.setLayoutParams(rowLayoutParams);
        rowView.setGravity(Gravity.CENTER_VERTICAL);
        rowView.setClickable(true);

        final int widthUnit = (parent.getWidth() / columnModel.getColumnWeightSum());
        //H vh = new ViewHolder(v);
        ViewHolderBase vh = getHolder(rowView, parent, widthUnit);

        for (int columnIndex = 0; columnIndex < getColumnCount(); columnIndex++) {
            View cellView = vh.initCell( columnIndex);
            if (cellView == null) {
                cellView = new TextView(getContext());
            }

            final int width = widthUnit * columnModel.getColumnWeight(columnIndex);

            final LinearLayout.LayoutParams cellLayoutParams = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            cellLayoutParams.weight = columnModel.getColumnWeight(columnIndex);
            cellView.setLayoutParams(cellLayoutParams);
            rowView.addView(cellView);
        }

        return vh;


    }


    protected abstract ViewHolderBase getHolder(LinearLayout v, ViewGroup parent, int widthUnit);

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolderBase holder, int rowIndex) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        T rowData = null;
        try {
            rowData = getItem(rowIndex);
        } catch (final IndexOutOfBoundsException e) {
            Log.w(LOG_TAG, "No row date available for row with index " + rowIndex + ". " +
                    "Caught Exception: " + e.getMessage());
        }
        holder.setBackgroundColor(rowColoriser.getRowColor(rowIndex, rowData));

        //final int widthUnit = holder.getWidthUnit();
        //holder.setmItemClickListener( mItemClickListener);

        for (int columnIndex = 0; columnIndex < getColumnCount(); columnIndex++) {
            View cellView = holder.bindCell(rowData, columnIndex);
            if (cellView == null) {
                cellView = new TextView(getContext());
            }

        }



    }

    public T getItem(int position){
        return data.get(position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * Gives the data object that shall be displayed in the row with the given index.
     *
     * @param rowIndex
     *         The index of the row to get the data for.
     * @return The data that shall be displayed in the row with the given index.
     */
    public T getRowData(final int rowIndex) {
        return getItem(rowIndex);
    }

    /**
     * Gives the data that is set to this adapter.
     *
     * @return The data this adapter is currently working with.
     */
    public List<T> getData() {
        return data;
    }

    /**
     * Gives the {@link Context} of this adapter. (Hint: use this method in the {@code getHeaderView()}-method
     * to programmatically initialize new views.)
     *
     * @return The {@link Context} of this adapter.
     */
    public Context getContext() {
        return context;

    }

    /**
     * Gives the {@link LayoutInflater} of this adapter. (Hint: use this method in the
     * {@code getHeaderView()}-method to inflate xml-layout-files.)
     *
     * @return The {@link LayoutInflater} of the context of this adapter.
     */
    public LayoutInflater getLayoutInflater() {
        return (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Gives the {@link Resources} of this adapter. (Hint: use this method in the
     * {@code getCellView()}-method to resolve resources.)
     *
     * @return The {@link Resources} of the context of this adapter.
     */
    public Resources getResources() {
        return getContext().getResources();
    }

    /**
     * Method that gives the cell views for the different table cells.
     *
     * @param rowIndex
     *         The index of the row to return the table cell view.
     * @param columnIndex
     *         The index of the column to return the table cell view.
     * @param parentView
     *         The view to which the returned view will be added.
     * @return The created header view for the given column.
     */
    //public abstract View getCellView(int rowIndex, int columnIndex, ViewGroup parentView);


    /**
     * Sets the {@link TableDataRowColorizer} that will be used to colorise the table data rows.
     *
     * @param rowColorizer
     *         The {@link TableDataRowColorizer} that shall be used.
     */
    protected void setRowColoriser(final TableDataRowColorizer<? super T> rowColorizer) {
        this.rowColoriser = rowColorizer;
    }

    /**
     * Sets the {@link TableColumnModel} that will be used to render the table cells.
     *
     * @param columnModel
     *         The {@link TableColumnModel} that should be set.
     */
    protected void setColumnModel(final TableColumnModel columnModel) {
        this.columnModel = columnModel;
    }

    /**
     * Gives the {@link TableColumnModel} that is currently used to render the table headers.
     */
    protected TableColumnModel getColumnModel() {
        return columnModel;
    }

    /**
     * Sets the column count which is used to render the table headers.
     *
     * @param columnCount
     *         The column count that should be set.
     */
    protected void setColumnCount(final int columnCount) {
        columnModel.setColumnCount(columnCount);
    }

    /**
     * Gives the column count that is currently used to render the table headers.
     *
     * @return The number of columns.
     */
    protected int getColumnCount() {
        return columnModel.getColumnCount();
    }

    /**
     * Sets the column weight (the relative width of a column) of the column at the given index.
     *
     * @param columnIndex
     *         The index of the column to which this weight should be assigned.
     * @param columnWeight
     *         The weight that should be set to the column at the given index.
     */
    protected void setColumnWeight(final int columnIndex, final int columnWeight) {
        columnModel.setColumnWeight(columnIndex, columnWeight);
    }

    /**
     * Gives the column weight (the relative width of a column) of the column at the given index.
     *
     * @param columnIndex
     *         The index of the column to receive the column weight.
     * @return The column weight of the column at the given index.
     */
    protected int getColumnWeight(final int columnIndex) {
        return columnModel.getColumnWeight(columnIndex);
    }

    /**
     * Gives the overall column weight (sum of all column weights).
     *
     * @return The collumn weight sum.
     */
    protected int getColumnWeightSum() {
        return columnModel.getColumnWeightSum();
    }


    public void add(int position, T item) {
        data.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(T item) {
        int position = data.indexOf(item);
        data.remove(position);
        notifyItemRemoved(position);
    }

    public OnItemClickListener getItemClickListener() {
        return mItemClickListener;
    }

    public void setItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public interface OnItemClickListener
    {
        public void onItemClick(View view , int position);

    }

}

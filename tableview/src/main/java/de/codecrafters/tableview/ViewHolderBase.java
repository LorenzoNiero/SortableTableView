package de.codecrafters.tableview;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by lorenzoniero on 13/04/16.
 */
public abstract class ViewHolderBase<T> extends RecyclerView.ViewHolder
{
    protected Context context;
    private int widthUnit;

    protected View viewContainer;

    //create view
    public ViewHolderBase(Context context, LinearLayout itemView) {
        super(itemView);
        this.context = context;
        viewContainer = itemView;

    }

    //create view column and save references view
    public abstract View initCell(int columnIndex);

    //set value on view
    public abstract void bindCell(T item, int columnIndex);


    public int getWidthUnit(){
        return widthUnit;
    }

    //set listener item click
    public void setmItemClickListener(final TableDataAdapter.OnItemClickListener mItemClickListener) {

        viewContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(viewContainer, getAdapterPosition());
                }
                else{
                    Log.w(this.getClass().getName(), "item click listener null");
                }
            }
        });
    }

    //set color background row
    public void setBackgroundColor(@ColorInt int idColore){
        viewContainer.setBackgroundColor(idColore);
    }

}


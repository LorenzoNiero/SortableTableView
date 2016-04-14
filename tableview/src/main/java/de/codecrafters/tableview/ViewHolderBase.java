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

    //create column
    public abstract View initCell(int columnIndex);

    //set value on view
    public abstract View bindCell(T item, int columnIndex);


    public int getWidthUnit(){
        return widthUnit;
    }

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

    public void setBackgroundColor(@ColorInt int idColore){
        viewContainer.setBackgroundColor(idColore);
    }

}


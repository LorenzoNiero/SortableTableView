package de.codecrafters.tableview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by lorenzoniero on 13/04/16.
 */
public class ViewHolderTextViewTest<T> extends ViewHolderBase<T>
{
    final TextView textView;
    public ViewHolderTextViewTest(Context context, LinearLayout itemView) {
        super( context, itemView);

        textView = new TextView(context);
        textView.setText("- -");
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(16);

        itemView.addView(textView);

    }

    @Override
    public View initCell(int columnIndex) {
        return textView;
    }

    @Override
    public View bindCell(T item, int columnIndex) {
        textView.setText(" test ");
        return textView;
    }


}

package de.codecrafters.tableviewexample;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.NumberFormat;

import de.codecrafters.tableview.TableColumnModel;
import de.codecrafters.tableview.ViewHolderBase;
import de.codecrafters.tableviewexample.data.Car;

/**
 * Created by lorenzoniero on 13/04/16.
 */
public class ViewHolderTextView extends ViewHolderBase<Car>
{

    private static final int TEXT_SIZE = 14;
    private static final NumberFormat PRICE_FORMATTER = NumberFormat.getNumberInstance();

    ImageView iconLogo;
    TextView kWTextView;
    TextView psTextView;
    TextView nameTextView;
    TextView priceTextView;


    public ViewHolderTextView(Context context, LinearLayout itemView, ViewGroup parent, int widthUnit, int numColumn, TableColumnModel columnModel) {
        super(context, itemView);

    }

    @Override
    public View initCell(int columnIndex) {
        //Car car = getRowData(rowIndex);

        View renderedView = null;

        switch (columnIndex){
            case 0:
                renderedView = renderProducerLogo(null );
                break;
            case 1:
                nameTextView = renderString( "**");
                renderedView = nameTextView;
                break;
            case 2:
                renderedView = renderPower(null);
                break;
            case 3:
                priceTextView = renderString( "**");
                renderedView = priceTextView;
                break;
            default:
                renderedView = renderString("*");
        }

        return renderedView;


    }

    @Override
    public void bindCell(Car item, int columnIndex) {

                iconLogo.setImageResource(item.getProducer().getLogo());

                nameTextView.setText(item.getName());

                kWTextView.setText(item.getKw() + " kW");
                psTextView.setText(item.getPs() + " PS");

                String priceString = PRICE_FORMATTER.format(item.getPrice()) + " €";

                priceTextView.setText(priceString);
                if (item.getPrice() < 50000) {
                    priceTextView.setTextColor(0xFF2E7D32);
                } else if (item.getPrice() > 100000) {
                    priceTextView.setTextColor(0xFFC62828);
                }

    }


    private TextView renderString(String value) {
        TextView textView = new TextView(context);
        textView.setText(value);
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(16);
        return textView;
    }

    private View renderPrice(Car car) {

        TextView textView = new TextView(context);
        textView.setText("null");
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(TEXT_SIZE);

        return textView;
    }

    private View renderPower(ViewGroup parentView) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_cell_power, parentView, false);
        kWTextView = (TextView) view.findViewById(R.id.kw_view);
        psTextView = (TextView) view.findViewById(R.id.ps_view);

        return view;
    }

    private View renderCatName(Car car) {
        return renderString(car.getName());
    }

    private View renderProducerLogo(ViewGroup parentView) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_cell_image, parentView, false);
        iconLogo = (ImageView) view.findViewById(R.id.imageView);

        return view;
    }



}
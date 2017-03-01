package pos.sd.omakasa.Utils;

/**
 * Created by jabbir on 9/10/15.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import pos.sd.omakasa.R;

public class CoolPriceView extends LinearLayout {

    private TextView coolprice_textView_currency;
    private TextView coolprice_textView_integer;
    private TextView coolprice_textView_decimal;


    public CoolPriceView(Context context, AttributeSet attrs) {
        super(context, attrs);


        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View xView = inflater.inflate(R.layout.coolprice_custom_layout, this, true);

        coolprice_textView_integer = (TextView) xView.findViewById(R.id.coolprice_textView_integer);
        coolprice_textView_decimal = (TextView) xView.findViewById(R.id.coolprice_textView_decimal);
        coolprice_textView_currency = (TextView) xView.findViewById(R.id.coolprice_textView_currency);


    }

    public CoolPriceView(Context context) {
        this(context, null);
    }

    public void setPrice(double price) {
        String[] splitter = roundWithTwoDecimals(price).split("\\.");
        String integer_part = String.format(Locale.US, "%s.", splitter[0]);
        String decimal_part = String.format(Locale.US, "%s", splitter[1]);
        coolprice_textView_integer.setText(integer_part);
        coolprice_textView_decimal.setText(decimal_part);

        coolprice_textView_currency.setText(getContext().getString(R.string.default_currency));
    }

    public void setStyle(float size_sp_integer, float size_sp_decimal, float size_sp_currency, int color_integer, int color_decimal, int color_currency){

        coolprice_textView_integer.setTextSize(TypedValue.COMPLEX_UNIT_SP, size_sp_integer);
        coolprice_textView_decimal.setTextSize(TypedValue.COMPLEX_UNIT_SP, size_sp_decimal);
        coolprice_textView_currency.setTextSize(TypedValue.COMPLEX_UNIT_SP, size_sp_currency);

        coolprice_textView_integer.setTextColor(color_integer);
        coolprice_textView_decimal.setTextColor(color_decimal);
        coolprice_textView_currency.setTextColor(color_currency);
    }


    private String roundWithTwoDecimals(double number) {
        try {
            DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
            df.setDecimalFormatSymbols(dfs);
            df.applyPattern("#####0.00");
            return df.format(number);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}

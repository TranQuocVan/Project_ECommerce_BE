package service.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class FormatPriceServices {

    public String formatPrice() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');

        DecimalFormat df = new DecimalFormat("#,###", symbols);
        return  df.format(0) + "đ";

    }
}

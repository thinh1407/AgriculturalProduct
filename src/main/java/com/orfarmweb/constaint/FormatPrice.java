package com.orfarmweb.constaint;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

@Component
public class FormatPrice {
    public String formatPrice(float num) {
        String format = "###,###,###";
        Locale locale = new Locale("en", "EN");
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        decimalFormat.applyPattern(format);
        return decimalFormat.format(num);
    }
}

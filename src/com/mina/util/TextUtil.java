/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.util;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

/**
 *
 * @author mina
 */
public class TextUtil {

    public static int getByteLength(String str) {
        final String ab2_ad1 = "["
                + "αβγδεζηθικλμνξοπρσςτυφχψω"
                + "ΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩ]";
        str = str.replaceAll(ab2_ad1, "a");
        //try {
        return str.getBytes().length;
        //} catch (UnsupportedEncodingException ex) {
        //    throw new CoreException(ex);
        //}
    }

    public static String fill(String str, int length, char fill, boolean align_left) {
        if (str == null) {
            str = "";
        }
        StringBuilder ret = new StringBuilder(str);
        while (getByteLength(ret.toString()) < length) {
            if (align_left) {
                ret.append(fill);
            } else {
                ret.insert(0, fill);
            }
        }
        return ret.toString();
    }

    public static StringSquare parseStringSquare(Object value) {
        if (value == null) {
            return new StringSquare("");
        }
        if (value instanceof String[]) {
            return new StringSquare((String[]) value);
        }
        boolean align_left = !(value instanceof Number);
        return new StringSquare(parseString(value), align_left);
    }

    public static String parseString(Object value) {
        if (value == null) {
            return "";
        }
        if (value instanceof BigDecimal) {
            BigDecimal bd = (BigDecimal) value;
            NumberFormat nf = NumberFormat.getInstance();
            if (nf instanceof DecimalFormat) {
                DecimalFormat df = (DecimalFormat) nf;
                df.setMaximumFractionDigits(bd.scale());
                df.setMinimumFractionDigits(bd.scale());
                return df.format(bd);
            } else {
                //Terminal.println("DEBUG:not a DecimalFormat!!!");
            }
        }
        if (value instanceof Number) {
            return NumberFormat.getInstance().format((Number) value);
        }
        if (value instanceof Time) {
            return new Hhmiss((Time) value).dispString();
        }
        if (value instanceof Hhmiss) {
            return ((Hhmiss) value).dispString();
        }
        if (value instanceof Timestamp) {
            return new Yyyymmddhhmiss((Timestamp) value).dispString();
        }
        if (value instanceof Yyyymmddhhmiss) {
            return ((Yyyymmddhhmiss) value).dispString();
        }
        if (value instanceof Date) {
            return new Yyyymmdd((Date) value).dispString();
        }
        if (value instanceof Yyyymmdd) {
            return ((Yyyymmdd) value).dispString();
        }
        if (value instanceof byte[]) {
            return "binary";
        }
        return value.toString();
    }

    public static String[] separate(String str, String separator) {
        if (str == null) {
            return new String[0];
        }
        ArrayList<String> vec = new ArrayList<String>();
        int index = 0;
        if (!str.endsWith(separator)) {
            str += separator;
        }
        while (true) {
            index = str.indexOf(separator, index);
            if (index < 0) {
                break;
            }
            str = str.substring(0, index) + " " + str.substring(index, str.length());
            index += 2;
        }
        StringTokenizer st = new StringTokenizer(str, separator);
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            vec.add(token.substring(0, token.length() - 1));
        }
        String[] ret = new String[vec.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = vec.get(i);
        }
        return ret;
    }

    public static String convertNullToEmpty(String str) {
        return str == null ? "" : str;
    }
}

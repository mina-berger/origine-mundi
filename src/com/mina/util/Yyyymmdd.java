/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.util;

import com.mina.util.TextBox.MultiColumnDatum;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author mina
 */
public class Yyyymmdd extends TextUtil implements Comparable<Yyyymmdd>, MultiColumnDatum {

    private String yyyymmdd;

    public Yyyymmdd(Yyyymmdd _yyyymmdd) {
        set(_yyyymmdd);
    }

    public Yyyymmdd(String yyyymmdd) {
        set(yyyymmdd);
    }

    public Yyyymmdd(Date d) {
        set(toString(d));
    }

    public Yyyymmdd(Calendar c) {
        set(toString(c));
    }

    public final void set(Yyyymmdd _yyyymmdd) {
        set(_yyyymmdd.yyyymmdd);
    }

    public final void set(String yyyymmdd) {
        this.yyyymmdd = yyyymmdd;
        if (!check()) {
            throw new CoreException("illegal argument(" + yyyymmdd + " for Yyyymmdd)");
        }
    }

    public void set(Date d) {
        set(toString(d));
    }

    public void set(Calendar c) {
        set(toString(c));
    }

    public boolean before(Yyyymmdd _yyyymmdd) {
        return toCalendar().before(_yyyymmdd.toCalendar());
    }

    public boolean after(Yyyymmdd _yyyymmdd) {
        return toCalendar().after(_yyyymmdd.toCalendar());
    }

    public boolean check() {
        return check(yyyymmdd);
        //if(yyyymmdd.length() != 8) return false;
        //return yyyymmdd.equals(toString(toCalendar(yyyymmdd)));
    }

    public static boolean check(String yyyymmdd) {
        if (yyyymmdd.length() != 8) {
            return false;
        }
        return yyyymmdd.equals(toString(toCalendar(yyyymmdd)));
    }

    public Yyyymm getYyyymm() {
        return new Yyyymm(getYyyy() + getMm());
    }

    public String getYyyy() {
        return yyyymmdd.substring(0, 4);
    }

    public String getMm() {
        return yyyymmdd.substring(4, 6);
    }

    public String getDd() {
        return yyyymmdd.substring(6, 8);
    }

    @Override
    public int compareTo(Yyyymmdd date) {
        return new Integer(hashCode()).compareTo(new Integer(date.hashCode()));
    }

    @Override
    public boolean equals(Object o) {
        return hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.yyyymmdd != null ? this.yyyymmdd.hashCode() : 0);
        return hash;
    }

    public Calendar toCalendar() {
        return toCalendar(yyyymmdd);
    }

    @Override
    public String toString() {
        return yyyymmdd;
    }

    public String dispString() {
        return dispYMD(yyyymmdd) + dispDayOfWeek(yyyymmdd);
    }

    public Yyyymmdd shift(int shift_dates) {
        Calendar c = toCalendar(yyyymmdd);
        c.add(Calendar.DATE, shift_dates);
        return new Yyyymmdd(c);
    }

    public Yyyymmdd[] getYyyymmdds(int day_count) {
        if (day_count == 0) {
            throw new CoreException("day_count cannot be 0.");
        }
        int count = Math.abs(day_count);
        boolean positive = (day_count > 0);
        Yyyymmdd[] yyyymmdds = new Yyyymmdd[count];
        Calendar c = toCalendar(yyyymmdd);
        for (int i = 0; i < count; i++) {
            if (i > 0) {
                c.add(Calendar.DATE, (positive ? 1 : -1));
            }
            yyyymmdds[i] = new Yyyymmdd(c);
        }
        return yyyymmdds;
    }

    public static Yyyymmdd now() {
        return new Yyyymmdd(Calendar.getInstance());
    }

    private static String toString(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return toString(c);
    }

    private static String toString(Calendar c) {
        return TextUtil.fill(Integer.toString(c.get(Calendar.YEAR)), 4, '0', false)
                + TextUtil.fill(Integer.toString(c.get(Calendar.MONTH) + 1), 2, '0', false)
                + TextUtil.fill(Integer.toString(c.get(Calendar.DATE)), 2, '0', false);
    }

    private static Calendar toCalendar(String yyyymmdd) {
        String y = yyyymmdd.substring(0, 4);
        String m = yyyymmdd.substring(4, 6);
        String d = yyyymmdd.substring(6);
        return new GregorianCalendar(
                Integer.parseInt(y, 10),
                Integer.parseInt(m, 10) - 1,
                Integer.parseInt(d, 10));
    }

    private static String dispYMD(String yyyymmdd) {
        String y = yyyymmdd.substring(0, 4);
        String m = yyyymmdd.substring(4, 6);
        String d = yyyymmdd.substring(6);
        if (m.startsWith("0")) {
            m = " " + m.substring(1);
        }
        if (d.startsWith("0")) {
            d = " " + d.substring(1);
        }
        return y + "/" + m + "/" + d + "";
    }

    private static String dispDayOfWeek(String yyyymmdd) {
        Calendar c = toCalendar(yyyymmdd);
        int day_of_week = c.get(Calendar.DAY_OF_WEEK);
        String week;
        switch (day_of_week) {
            case Calendar.SUNDAY:
                week = "Sun.";
                break;
            case Calendar.MONDAY:
                week = "Mon.";
                break;
            case Calendar.TUESDAY:
                week = "Tue.";
                break;
            case Calendar.WEDNESDAY:
                week = "Wed.";
                break;
            case Calendar.THURSDAY:
                week = "Thu.";
                break;
            case Calendar.FRIDAY:
                week = "Fri.";
                break;
            case Calendar.SATURDAY:
                week = "Sat.";
                break;
            default:
                week = "----";
                break;
        }
        return week;
    }

    @Override
    public String getHeader(int h) {
        return "Date";
    }

    @Override
    public StringSquare getValue(int h) {
        return new StringSquare(dispString());
    }

    /* public boolean alignLeft(int h) {
        return true;
    }*/
    @Override
    public int hLength() {
        return 1;
    }
}

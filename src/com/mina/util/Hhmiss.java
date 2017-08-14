/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.util;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author mina
 */
public class Hhmiss extends TextUtil implements Comparable<Hhmiss> {

    private String hhmiss;

    public Hhmiss(Hhmiss _hhmiss) {
        set(_hhmiss);
    }

    public Hhmiss(String hhmiss) {
        set(hhmiss);
    }

    public Hhmiss(Time t) {
        set(toString(t));
    }

    public Hhmiss(Calendar c) {
        set(toString(c));
    }

    public final void set(Hhmiss _hhmiss) {
        set(_hhmiss.hhmiss);
    }

    public final void set(String hhmiss) {
        this.hhmiss = hhmiss;
        if (!check()) {
            throw new CoreException("illegal argument(" + hhmiss + " for Hhmiss)");
        }
    }

    public void set(Time t) {
        set(toString(t));
    }

    public void set(Calendar c) {
        set(toString(c));
    }

    public boolean before(Hhmiss _hhmiss) {
        return toCalendar().before(_hhmiss.toCalendar());
    }

    public boolean after(Hhmiss _hhmiss) {
        return toCalendar().after(_hhmiss.toCalendar());
    }

    public boolean check() {
        return check(hhmiss);
    }

    public static boolean check(String hhmiss) {
        if (hhmiss.length() != 6) {
            return false;
        }
        return hhmiss.equals(toString(toCalendar(hhmiss)));
    }

    public String getHh() {
        return hhmiss.substring(0, 2);
    }

    public String getMi() {
        return hhmiss.substring(2, 4);
    }

    public String getSs() {
        return hhmiss.substring(4, 6);
    }

    @Override
    public int compareTo(Hhmiss time) {
        return new Integer(hashCode()).compareTo(time.hashCode());
    }

    @Override
    public boolean equals(Object o) {
        return hashCode() == ((Hhmiss) o).hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.hhmiss != null ? this.hhmiss.hashCode() : 0);
        return hash;
    }

    public Calendar toCalendar() {
        return toCalendar(hhmiss);
    }

    @Override
    public String toString() {
        return hhmiss;
    }

    public String dispString() {
        return dispHMS(hhmiss);
    }

    /*public Hhmmss shift(int shift_hour, int shift_minute, int shift_second){
        Calendar c = toCalendar(hhmmss);
        c.add(Calendar.HOUR_OF_DAY, shift_hour);
        c.add(Calendar.MINUTE,      shift_minute);
        c.add(Calendar.SECOND,      shift_second);
        return new Hhmmss(c);
    }*/
    public static Hhmiss now() {
        return new Hhmiss(Calendar.getInstance());
    }

    private static String toString(Time time) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        return toString(c);
    }

    private static String toString(Calendar c) {
        return TextUtil.fill(Integer.toString(c.get(Calendar.HOUR_OF_DAY)), 2, '0', false)
                + TextUtil.fill(Integer.toString(c.get(Calendar.MINUTE)), 2, '0', false)
                + TextUtil.fill(Integer.toString(c.get(Calendar.SECOND)), 2, '0', false);
    }

    private static Calendar toCalendar(String hhmmss) {
        String h = hhmmss.substring(0, 2);
        String m = hhmmss.substring(2, 4);
        String s = hhmmss.substring(4);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(0));
        return new GregorianCalendar(
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DATE),
                Integer.parseInt(h, 10),
                Integer.parseInt(m, 10),
                Integer.parseInt(s, 10));
    }

    private static String dispHMS(String hhmiss) {
        String h = hhmiss.substring(0, 2);
        String m = hhmiss.substring(2, 4);
        String s = hhmiss.substring(4);
        if (h.startsWith("0")) {
            h = " " + h.substring(1);
        }
        if (m.startsWith("0")) {
            m = " " + m.substring(1);
        }
        if (s.startsWith("0")) {
            s = " " + s.substring(1);
        }
        return h + "時" + m + "分" + s + "秒";
    }

    public String getHeader(int h) {
        return "時間";
    }

    public StringSquare getValue(int h) {
        return new StringSquare(dispString());
    }

    public int hLength() {
        return 1;
    }
}

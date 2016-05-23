/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.util;

import java.util.ArrayList;

/**
 *
 * @author hiyamamina
 */
public class StringSquare extends ArrayList<String> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private boolean align_left;

    public StringSquare() {
        this(new String[0], true);
    }

    public StringSquare(String str) {
        this(new String[]{str}, true);
    }

    public StringSquare(String str, boolean align_left) {
        this(new String[]{str}, align_left);
    }

    public StringSquare(String[] strs) {
        this(strs, true);
    }

    public StringSquare(String[] strs, boolean align_left) {
        add(strs);
        this.align_left = align_left;
        if (!isSquare()) {
            makeSquare();
        }
    }

    public final void add(String[] strs) {
        if (strs == null) {
            throw new CoreException("string array is null");
        }
        for (int i = 0; i < strs.length; i++) {
            add(strs[i]);
        }
    }

    public int width() {
        int length = 0;
        for (int i = 0; i < size(); i++) {
            length = Math.max(length, TextUtil.getByteLength(get(i)));
        }
        return length;

    }

    public int height() {
        return size();
    }

    public void appendRight(StringSquare sr) {
        int height = Math.max(height(), sr.height());
        makeSquare(width(), height);
        sr.makeSquare(sr.width(), height);
        for (int i = 0; i < height; i++) {
            set(i, get(i) + sr.get(i));
        }
        align_left = true;
    }

    public void appendRight(String str) {
        for (int i = 0; i < height(); i++) {
            set(i, get(i) + str);
        }
    }

    public void appendBottom(StringSquare sr) {
        int width = Math.max(width(), sr.width());
        makeSquare(width, height());
        sr.makeSquare(width, sr.height());
        for (int i = 0; i < sr.height(); i++) {
            add(sr.get(i));
        }

    }

    public void appendTop(StringSquare sr) {
        int width = Math.max(width(), sr.width());
        makeSquare(width, height());
        sr.makeSquare(width, sr.height());
        for (int i = sr.height() - 1; i >= 0; i--) {
            this.add(0, sr.get(i));
            //insertElementAt(sr.get(i), 0);
        }

    }

    private boolean isSquare() {
        if (size() < 2) {
            return true;
        }
        int width = TextUtil.getByteLength(get(0));
        for (int i = 1; i < height(); i++) {
            if (width != TextUtil.getByteLength(get(i))) {
                return false;
            }
        }
        return true;
    }

    private void makeSquare() {
        makeSquare(width(), height());
    }

    public void makeSquare(int width, int height) {
        int min_width = width();
        if (width < min_width) {
            throw new CoreException("width is too small(width=" + width + ", minimum=" + min_width + ")");
        }
        int min_height = height();
        if (height < min_height) {
            throw new CoreException("height is too small(height=" + height + ", minimum=" + min_height + ")");
        }
        for (int h = 0; h < height; h++) {
            if (h < min_height) {
                set(h, TextUtil.fill(get(h), width, ' ', align_left));
            } else {
                add(TextUtil.fill("", width, ' ', align_left));
            }
        }
    }

    public String getLine(int row_index) {
        return get(row_index);
    }

    public String[] getLines() {
        String[] strs = new String[height()];
        for (int i = 0; i < height(); i++) {
            strs[i] = get(i);
        }
        return strs;
    }

}

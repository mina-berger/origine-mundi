/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.util;

import java.io.PrintStream;

/**
 *
 * @author mina
 */
public class TextBox extends TextUtil {

    private StringSquare[][] valuess;
    private StringSquare[] headers;
    private StringSquare[] row_numbers;
    private int devider_row_count;
    private boolean show_row_number;
    private boolean show_header;
    private int parallel_count;
    private Metric metric;

    public TextBox(Data data, boolean show_row_number) {
        this(data, show_row_number, 1, 1);
    }

    public TextBox(Data data, boolean show_row_number, int parallel_count) {
        this(data, show_row_number, parallel_count, 1);
    }

    public TextBox(Data data, boolean show_row_number, int parallel_count, int start_index) {
        if (parallel_count < 1) {
            throw new CoreException("parallel count must be 1 or geater");
        }
        this.show_row_number = show_row_number;
        this.parallel_count = parallel_count;
        valuess = new StringSquare[data.vLength()][data.hLength()];
        for (int v = 0; v < data.vLength(); v++) {
            for (int h = 0; h < data.hLength(); h++) {
                valuess[v][h] = data.getValue(v, h);
            }
        }
        show_header = data.showHeader();
        headers = new StringSquare[data.hLength()];
        for (int h = 0; h < data.hLength(); h++) {
            headers[h] = show_header ? new StringSquare(data.getHeader(h)) : new StringSquare();
        }
        //show_row_number = data.showRowNumber();
        row_numbers = new StringSquare[data.vLength()];
        for (int v = 0; v < data.vLength(); v++) {
            row_numbers[v] = show_row_number ? new StringSquare(Integer.toString(start_index + v) + ":", false) : new StringSquare();
        }
        devider_row_count = data.deviderRowCount();
        //metric
        metric = new Metric();
        metric.col_width = new int[data.hLength()];
        metric.row_heights = new int[data.vLength()];
        metric.row_num_width = 0;
        for (int h = 0; h < data.hLength(); h++) {
            metric.col_width[h] = headers[h].width();
            for (int v = 0; v < data.vLength(); v++) {
                metric.col_width[h]
                        = Math.max(metric.col_width[h], valuess[v][h].width());
            }
        }
        for (int v = 0; v < data.vLength(); v++) {
            metric.row_heights[v] = 0;
            for (int h = 0; h < data.hLength(); h++) {
                metric.row_heights[v]
                        = Math.max(metric.row_heights[v], valuess[v][h].height());
            }
        }
        for (int h = 0; h < data.hLength(); h++) {
            headers[h].makeSquare(metric.col_width[h], headers[h].height());
            for (int v = 0; v < data.vLength(); v++) {
                valuess[v][h].makeSquare(metric.col_width[h], metric.row_heights[v]);
            }
        }

        for (int v = 0; v < data.vLength(); v++) {
            metric.row_num_width
                    = Math.max(metric.row_num_width, row_numbers[v].width());
        }
        for (int v = 0; v < data.vLength(); v++) {
            row_numbers[v].makeSquare(metric.row_num_width, metric.row_heights[v]);
        }
    }

    //public void print() {
    //    Terminal.printlns(getLines());
    //}

    public void print(PrintStream stream) {
        String[] lines = getLines();
        for (int i = 0; i < lines.length; i++) {
            stream.println(lines[i]);
        }
    }

    public String[] getLines() {
        StringSquare devider = new StringSquare("");
        if (show_row_number) {
            devider.appendRight(fill("", metric.row_num_width, '-', true) + "+");
        }
        for (int h = 0; h < metric.col_width.length; h++) {
            if (h > 0) {
                devider.appendRight("+");
            }
            devider.appendRight(fill("", metric.col_width[h], '-', true));
        }

        StringSquare header;
        if (show_header) {
            header = new StringSquare("");
            if (show_row_number) {
                header.appendRight(fill("", metric.row_num_width, ' ', true) + " ");
            }
            for (int h = 0; h < metric.col_width.length; h++) {
                if (h > 0) {
                    header.appendRight(" ");
                }
                header.appendRight(headers[h].getLine(0));
            }
            header.appendTop(devider);
            header.appendBottom(devider);
        } else {
            header = new StringSquare();
        }

        StringSquare[] rows = new StringSquare[metric.row_heights.length];
        for (int v = 0; v < metric.row_heights.length; v++) {
            rows[v] = new StringSquare();
            if (show_row_number) {
                rows[v].appendRight(row_numbers[v]);
                rows[v].appendRight(" ");
            }
            for (int h = 0; h < metric.col_width.length; h++) {
                if (h > 0) {
                    rows[v].appendRight(" ");
                }
                rows[v].appendRight(valuess[v][h]);
            }
        }

        StringSquare ss = new StringSquare();
        StringSquare ss_header = new StringSquare();
        StringSquare ss_devider = new StringSquare();
        for (int i = 0; i < parallel_count; i++) {
            if (i > 0) {
                ss_header.appendRight(" ");
                ss_devider.appendRight(" ");
            }
            ss_header.appendRight(header);
            ss_devider.appendRight(devider);
        }
        ss.appendBottom(ss_header);
        int ss_height = (metric.row_heights.length - 1) / parallel_count + 1;
        for (int v = 0; v < ss_height; v++) {
            if ((show_header && devider_row_count > 0 && v % devider_row_count == 0 && v != 0)
                    || (!show_header && devider_row_count > 0 && v % devider_row_count == 0)) {
                ss.appendBottom(ss_devider);
            }
            StringSquare ss_row = new StringSquare();
            for (int i = 0; i < parallel_count; i++) {
                if (i > 0) {
                    ss_row.appendRight(" ");
                }
                int rows_index = i * ss_height + v;
                if (rows_index < metric.row_heights.length) {
                    ss_row.appendRight(rows[rows_index]);
                }
            }
            ss.appendBottom(ss_row);
        }
        if (devider_row_count > 0 || show_header) {
            ss.appendBottom(ss_devider);
        }
        return ss.getLines();
    }

    class Metric {

        int[] col_width;
        int[] row_heights;
        int row_num_width;
    }

    public interface Data {

        public int deviderRowCount();
        //public boolean showRowNumber();

        public boolean showHeader();

        public String getHeader(int h);

        public StringSquare getValue(int v, int h);

        //public boolean alignLeft(int v, int h);
        public int vLength();

        public int hLength();
    }



    public static interface MultiColumnDatum {

        public String getHeader(int h);

        public StringSquare getValue(int h);

        //public boolean alignLeft(int h);
        public int hLength();
    }

    public static class MultiColumnArrayData implements Data {

        //protected boolean show_row_number;
        protected MultiColumnDatum[] mcd_array;

        public MultiColumnArrayData(MultiColumnDatum[] mcd_array) {
            //this.show_row_number = show_row_number;
            this.mcd_array = mcd_array;
            if (mcd_array.length == 0) {
                throw new CoreException("MultiColumnDatumの空配列が設定されました。");
            }
        }

        @Override
        public int deviderRowCount() {
            return 0;
        }

        //public boolean showRowNumber() {return show_row_number;}
        @Override
        public boolean showHeader() {
            return true;
        }

        @Override
        public String getHeader(int h) {
            return mcd_array[0].getHeader(h);
        }

        @Override
        public StringSquare getValue(int v, int h) {
            return mcd_array[v].getValue(h);
        }

        //public boolean alignLeft(int v, int h) {return mcd_array[v].alignLeft(h);}
        public int vLength() {
            return mcd_array.length;
        }

        public int hLength() {
            return mcd_array[0].hLength();
        }
    }

    public static class ObjectArrayData2D implements Data {

        protected Object[][] valuess;
        protected String[] header;
        //protected boolean show_row_number;
        protected boolean show_header;
        protected int devider_row_count;

        public ObjectArrayData2D(Object[][] valuess) {
            this(null, valuess, 0);
        }

        public ObjectArrayData2D(String[] header, Object[][] valuess) {
            this(header, valuess, 0);
        }

        public ObjectArrayData2D(String[] header, Object[][] valuess, int devider_row_count) {
            this.valuess = valuess;
            //this.show_row_number = show_row_number;
            show_header = (header != null && header.length > 0);
            this.header = header;
            this.devider_row_count = devider_row_count;
        }

        @Override
        public int deviderRowCount() {
            return devider_row_count;
        }

        //public boolean showRowNumber() {return show_row_number;}
        @Override
        public boolean showHeader() {
            return show_header;
        }

        @Override
        public String getHeader(int h) {
            return header[h];
        }

        @Override
        public StringSquare getValue(int v, int h) {
            return parseStringSquare(valuess[v][h]);
        }

        //public boolean alignLeft(int v, int h) {
        //    return !(valuess[v][h] instanceof Number);
        //}
        @Override
        public int vLength() {
            return valuess.length;
        }

        @Override
        public int hLength() {
            return valuess[0].length;
        }
    }

    public static class ObjectArrayData extends StringArrayData {

        public ObjectArrayData(Object[] objs) {
            super(new String[objs.length]);
            for (int i = 0; i < objs.length; i++) {
                strs[i] = objs[i].toString();
            }
        }
    }

    public static class StringArrayData implements Data {

        protected String[] strs;

        //protected boolean show_row_number;
        public StringArrayData(String[] strs) {
            this.strs = strs;
            //this.show_row_number = show_row_number;
        }

        @Override
        public int deviderRowCount() {
            return 0;
        }

        //public boolean showRowNumber() {return show_row_number;}
        @Override
        public boolean showHeader() {
            return false;
        }

        @Override
        public String getHeader(int h) {
            return null;
        }

        @Override
        public StringSquare getValue(int v, int h) {
            return new StringSquare(strs[v], true);
        }

        //public boolean alignLeft(int v, int h) { return true; }
        @Override
        public int vLength() {
            return strs.length;
        }

        @Override
        public int hLength() {
            return 1;
        }
    }
}

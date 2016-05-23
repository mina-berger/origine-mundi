/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.util;

import static com.mina.util.TextUtil.parseStringSquare;
import java.io.PrintStream;

/**
 *
 * @author hiyamamina
 */
public class ExplainedObject {

    private final String expl;
    private final Object obj;

    public ExplainedObject(String expl, Object obj) {
        this.expl = expl;
        this.obj = obj;
    }

    public String getExplanation() {
        return expl;
    }

    public Object getObject() {
        return obj;
    }

    public static class ExplainedObjectArrayData implements TextBox.Data {

        private final ExplainedObject[] eos;

        public ExplainedObjectArrayData(ExplainedObject... eos) {
            this.eos = eos;
        }

        @Override
        public int deviderRowCount() {
            return 5;
        }

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
            if (h == 0) {
                return new StringSquare(eos[v].getExplanation(), true);
            }
            return parseStringSquare(eos[v].getObject());
        }

        //public boolean alignLeft(int v, int h) {
        //    if(h == 0) return true;
        //    return !(eos[v].obj instanceof Number);
        //}
        @Override
        public int vLength() {
            return eos.length;
        }

        @Override
        public int hLength() {
            return 2;
        }
    }

    public static void print(PrintStream out, ExplainedObject... eos) {
        new TextBox(new ExplainedObjectArrayData(eos), false).print(out);
    }
}

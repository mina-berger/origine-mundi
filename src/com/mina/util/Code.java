/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.util;

import java.util.ArrayList;

/**
 *
 * @author mina
 */
public class Code implements Cloneable {

    protected String code_name;
    protected Option[] options;

    protected Code() {
        this(null, new Option[]{});
    }

    protected Code(String code_name) {
        this(code_name, new Option[]{});
    }

    public Code(String code_name, ArrayList<Option> options) {
        this(code_name, options.toArray(new Option[0]));
    }

    public Code(String code_name, Option[] options) {
        this.code_name = code_name;
        this.options = options;
    }

    public boolean isSolo() {
        return options.length == 1;
    }

    public boolean isMulti() {
        return options.length > 1;
    }

    public boolean isEmpty() {
        return options.length == 0;
    }

    public String getName() {
        return code_name;
    }

    public int length() {
        return options.length;
    }

    public boolean hasCode(String code) {
        return getOption(code) != null;
    }

    public Object getOptionValue(int index) {
        return getOption(index).getValue();
    }

    public Object getOptionValue(String code) {
        Option option = getOption(code);
        if (option == null) {
            return null;
        }
        return option.getValue();
    }

    public String getOptionName(int index) {
        return getOption(index).getName();
    }

    public String getOptionName(String code) {
        Option option = getOption(code);
        if (option == null) {
            return "<no option>";
        }
        return option.getName();
    }

    public Option getOption(int index) {
        return options[index];
    }

    public Option getOption(String code) {
        int index = getOptionIndex(code);
        if (index < 0) {
            return null;
        }
        return getOption(index);
    }

    protected int getOptionIndex(String code) {
        for (int i = 0; i < options.length; i++) {
            if (options[i].getCode().equals(code)) {
                return i;
            }
        }
        return -1;
    }

    public void removeOption(String code) {
        int index = getOptionIndex(code);
        if (index < 0) {
            throw new CoreException("no option for code(" + code + ")");
        }
        removeOption(index);
    }

    public void removeOption(int index) {
        ArrayList<Option> vec = new ArrayList<Option>();
        for (int i = 0; i < options.length; i++) {
            if (i != index) {
                vec.add(options[i]);
            }
        }
        Option[] new_options = new Option[vec.size()];
        for (int i = 0; i < vec.size(); i++) {
            new_options[i] = vec.get(i);
        }
        options = new_options;
    }

    public static Code clone(Code code) {
        try {
            return (Code) code.clone();
        } catch (CloneNotSupportedException ex) {
            throw new CoreException(ex);
        }
    }

    public String[] getOptionNames() {
        return getOptionNames(options);
    }

    public static String[] getOptionNames(Option[] options) {
        String[] strs = new String[options.length];
        for (int i = 0; i < options.length; i++) {
            strs[i] = options[i].getName();
        }
        return strs;
    }

    public static Object[] getOptionValues(Option[] options) {
        Object[] values = new Object[options.length];
        for (int i = 0; i < options.length; i++) {
            values[i] = options[i].getValue();
        }
        return values;
    }

    public static class Option {

        private final String code;
        private final String name;
        private final Object value;

        public Option(String code, String name, Object value) {
            this.code = code;
            this.name = name;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public Object getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "code=" + code + ":name=" + name + ":value=" + value.toString();
        }
    }

    public void print(boolean show_code) {
        TextBox textbox = new TextBox(new TextBoxDataCode(this, show_code), false);
        textbox.print(System.out);
    }

    /*public void print(boolean show_code, boolean multiple_col) {
        TextBox textbox = new TextBox(new TextBoxDataCode(this, show_code, multiple_col), false);
        textbox.print(System.out);
    }*/

    class TextBoxDataCode implements TextBox.Data {

        Code code;
        int column, row;
        boolean show_code;
        boolean multiple_col;

        public TextBoxDataCode(Code code, boolean show_code) {
        //    this(code, show_code, true);
        //}

        //public TextBoxDataCode(Code code, boolean show_code, boolean multiple_col) {
            this.code = code;
            //if (multiple_col) {
            //    column = code.length() / Terminal.LINE_COUNT + 1;
             //   row = (code.length() + column - 1) / column;
            //} else {
                column = 1;
                row = code.length();
            //}
            this.show_code = show_code;
            this.multiple_col = multiple_col;
        }

        public int deviderRowCount() {
            return 0;
        }

        //public boolean showRowNumber() {return false;}
        public boolean showHeader() {
            return false;
        }

        public String getHeader(int h) {
            return null;
        }

        public StringSquare getValue(int v, int h) {
            int index = h / 2 * row + v;
            if (index >= code.length()) {
                return new StringSquare();
            }
            if (h % 2 == 0) {
                return new StringSquare(
                        (show_code ? code.getOption(index).getCode() : Integer.toString(index + 1)) + ":", false);
            }
            return new StringSquare(code.getOption(index).getName());
        }

        //public boolean alignLeft(int v, int h) { return (h % 2 == 0)?false:true; }
        public int vLength() {
            return row;
        }

        public int hLength() {
            return column * 2;
        }
    }

}

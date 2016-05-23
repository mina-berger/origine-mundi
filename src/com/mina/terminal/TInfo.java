/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author hiyamamina
 */
public class TInfo extends HashMap<String, Object> {

    private final BufferedReader in;
    private final PrintStream out;
    //private final TMOption top;
    private ArrayList<TMOptionAbstract> bread;

    public TInfo(BufferedReader in, PrintStream out, TMOptionAbstract top) {
        this.in = in;
        this.out = out;
        //this.top = top;
        bread = new ArrayList<>();
        bread.add(top);
    }

    public void setCurrent(TMOptionAbstract menu) {
        bread.add(menu);
    }

    public void top() {
        TMOptionAbstract top = bread.get(0);
        bread.clear();
        bread.add(top);
    }

    public void parent() {
        bread.remove(bread.size() - 1);
    }

    public void showOption() {
        TMOptionAbstract option = bread.get(bread.size() - 1);
        option.action(this);
    }

    public void printBread(String action) {
        String str = "";
        for (TMOptionAbstract option : bread) {
            if (!str.isEmpty()) {
                str += ">";
            }
            str += "[" + option.getName() + "]";
        }
        if (action != null && !action.isEmpty()) {
            if (!str.isEmpty()) {
                str += ">";
            }
            str += "[" + action + "]";
        }
        print("");
        print(str);
    }

    public BufferedReader getIn() {
        return in;
    }

    public PrintStream getOut() {
        return out;
    }

    public void warn(String line) {
        out.println("[warn] " + line);
    }
    public void info(String line) {
        out.println("[info] " + line);
    }

    public void print(String line) {
        out.println(line);
    }
    
    public double readDouble(String text, Double default_value, Double min, Double max) {
        while (true) {
            String line = readString(text, default_value == null?null:default_value.toString());
            double d;
            try {
                d = Double.parseDouble(line);
            } catch (Exception e) {
                continue;
            }
            if (min != null && d < min) {
                warn("out of range(minimum=" + min + ")");
                continue;
            }
            if (max != null && d >= max) {
                warn("out of range(maximum=" + max + ")");
                continue;
            }
            return d;
        }
    }
    public int readInt(String text, Integer default_value, List<Integer> options) {
        while (true) {
            int read = readInt(text, default_value);
            if (options.contains(read)) {
                return read;
            }
        }

    }
    public int readInt(String text, Integer default_value) {
        while (true) {
            String line = readString(text, default_value == null?null:default_value.toString());
            int i;
            try {
                i = Integer.parseInt(line);
            } catch (Exception e) {
                continue;
            }
            return i;
        }
    }

    public int readInt(String text) {
        return readInt(text, (Integer)null, (Integer)null);
    }

    public int readInt(String text, Integer min, Integer max) {
        while (true) {
            String line = readString(text, true);
            int i;
            try {
                i = Integer.parseInt(line);
            } catch (Exception e) {
                continue;
            }
            if (min != null && i < min) {
                warn("out of range(minimum=" + min + ")");
                continue;
            }
            if (max != null && i >= max) {
                warn("out of range(maximum=" + max + ")");
                continue;
            }
            return i;
        }
    }

    public char readChar(String text) {
        while (true) {
            String line = readString(text, true);
            if (line.length() != 1) {
                warn("type 1 character");
            } else {
                return line.charAt(0);
            }
        }
    }

    public String readString(String text, boolean notnull) {
        while (true) {
            out.print(text + ">");
            String line;
            try {
                line = in.readLine();
            } catch (IOException ex) {
                continue;
            }
            if (notnull && line.isEmpty()) {
                warn("should not be empty!");
            } else {
                return line;
            }
        }

    }
    
    public void prompt(String text){
        out.print(text + ">");
        try {
            in.readLine();
        } catch (IOException ex) {
        }
    }

    public String readString(String text, String default_value) {
        default_value = default_value == null || default_value.isEmpty() ? null : default_value;
        while (true) {
            out.print(text + (default_value == null ? "" : "(default=" + default_value + ")") + ">");
            String line;
            try {
                line = in.readLine();
            } catch (IOException ex) {
                continue;
            }
            if (!line.isEmpty()) {
                return line;
            } else if (default_value == null) {
                warn("should not be empty!");
            } else {
                return default_value;
            }
        }

    }

    public boolean readBoolean(String text, Boolean default_value) {
        while (true) {
            String line = readString(text, default_value.toString());
            if (line.equalsIgnoreCase("y") || line.equalsIgnoreCase("t")) {
                return true;
            } else if (line.equalsIgnoreCase("n") || line.equalsIgnoreCase("f")) {
                return false;
            }
            try {
                return Boolean.parseBoolean(line);
            } catch (Exception e) {
            }
        }
    }

}

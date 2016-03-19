/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.util;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import static la.clamor.OscillatorUtil.toIugum;

/**
 *
 * @author mina
 */
public class Mjson {
    private static ScriptEngine se = new ScriptEngineManager().getEngineByName("JavaScript");

    public static int INDENT_LENGTH = 4;
    public static String QUOTE = "\"";
    private MjsonElement element;

    public Mjson(MjsonElement element) {
        if(element == null){
            throw new IllegalArgumentException("element is null");
        }
        this.element = element;
        
    }
    public Mjson(String str) throws ScriptException{
        if(element == null){
            throw new IllegalArgumentException("element is null");
        }
        element = parse(str);
    }
    public ArrayList<String> toStringArray(){
        return element.copyStringArray(new ArrayList<String>(), null, 0, true);
    }
    public void print(){
        for (String str : toStringArray()) {
            System.out.println(str);
        }
    }
    /*public MjsonElement get(String str){
        String[] pathes = str.split(".");
        return get(element, pathes, 0);
    }*/
 
    public static void main(String[] args) throws ScriptException {
        MjsonElement element = map()
            .unit("sho", map()
                .list("name", "sho", "hiyama")
                .unit("birthday", "2000-02-20")
                .unit("school", "shinobugaoka highschool"))
            .unit("rin", map()
                .list("name", "rin", "hiyama")
                .unit("birthday", "2003-10-09")
                .unit("school", "waseda elementary school"))
            .unit("mina", map()
                .list("name", "mina", "hiyama")
                .unit("birthday", "1971-07-15")
                .unit("company", "orion research"));
        String str = "{\n" +
"    \"mina\":{\n" +
"        \"birthday\":\"1971-07-15\",\n" +
"        \"company\":\"orion research\",\n" +
"        \"name\":[\n" +
"            \"mina\",\n" +
"            \"hiyama\"\n" +
"        ]\n" +
"    },\n" +
"    \"rin\":{\n" +
"        \"birthday\":2003-10-09,\n" +
"        \"name\":[\n" +
"            \"rin\",\n" +
"            \"hiyama\"\n" +
"        ],\n" +
"        \"school\":\"waseda elementary school\"\n" +
"    },\n" +
"    \"sho\":{\n" +
"        \"birthday\":\"2000-02-20\",\n" +
"        \"name\":[\n" +
"            \"sho\",\n" +
"            'hiyama'\n" +
"        ],\n" +
"        \"school\":\"shinobugaoka highschool\"\n" +
"    }\n" +
"}";
        Mjson json = new Mjson(str);
        //System.out.println(json.get(""));
//Mjson json = new Mjson("[1, true, [2,3,4], {'a':23}]");
        //Mjson json = new Mjson(element);
        //json.print();
        //ScriptEngineManager sem = new ScriptEngineManager();
        //ScriptEngine se = sem.getEngineByName("JavaScript");
        //Object obj = se.eval("var json_object = " + str + ";json_object.sho.name[0];");
        //System.out.println(obj.toString());
    }

    public interface MjsonElement {

        public ArrayList<String> copyStringArray(ArrayList<String> lines, String key, int index, boolean last);
    }

    public static class MjsonMap extends TreeMap<String, MjsonElement> implements MjsonElement {

        public MjsonMap unit(String key, Object element) {
            put(key, toElement(element));
            return this;
        }
        public MjsonMap list(String key, Object... elements) {
            MjsonList array = new MjsonList();
            for(Object element:elements){
                array.add(toElement(element));
            }
            put(key, array);
            return this;
        }
        /*public MjsonMap pono(String key, MjsonElement element) {
            put(key, element);
            return this;
        }*/

        @Override
        public ArrayList<String> copyStringArray(ArrayList<String> lines, String key, int indent, boolean last) {
            String line_primo = getIndent(indent) + keyToString(key);
            if (isEmpty()) {
                lines.add(line_primo + "{}" + lastToString(last));
                return lines;
            }
            lines.add(line_primo + "{");
            Set<String> keys = keySet();
            int index = 0;
            for (String _key : keys) {
                MjsonElement element = get(_key);
                element.copyStringArray(lines, _key, indent + 1, index == keys.size() - 1);
                index++;
            }
            lines.add(getIndent(indent) + "}" + lastToString(last));
            return lines;
        }
    }

    public static class MjsonList extends ArrayList<MjsonElement> implements MjsonElement {
        public MjsonList(Object... elements){
            for(Object element:elements){
                add(toElement(element));
            }
        }

        public MjsonList addo(Object element) {
            add(toElement(element));
            return this;
        }

        @Override
        public ArrayList<String> copyStringArray(ArrayList<String> lines, String key, int indent, boolean last) {
            String line_primo = getIndent(indent) + keyToString(key);
            if (isEmpty()) {
                lines.add(line_primo + "[]" + lastToString(last));
                return lines;
            }
            lines.add(line_primo + "[");
            int index = 0;
            for (MjsonElement element : this) {
                element.copyStringArray(lines, null, indent + 1, index == size() - 1);
                index++;
            }
            lines.add(getIndent(indent) + "]" + lastToString(last));
            return lines;
        }
    }

    public static class MjsonSingle implements MjsonElement {

        private Object value;

        public MjsonSingle(Integer value) {
            this((Object) value);
        }

        public MjsonSingle(Double value) {
            this((Object) value);
        }

        public MjsonSingle(Boolean value) {
            this((Object) value);
        }

        public MjsonSingle(String value) {
            this((Object) value);
        }

        public MjsonSingle(Object value) {
            this.value = value;
        }

        public Integer getInteger() {
            if (value == null) {
                return null;
            }
            if (value instanceof Integer) {
                return (Integer) value;
            }
            return Integer.parseInt(value.toString());
        }

        public Double getDouble() {
            if (value == null) {
                return null;
            }
            if (value instanceof Double) {
                return (Double) value;
            }
            return Double.parseDouble(value.toString());
        }

        public Boolean getBoolean() {
            if (value == null) {
                return null;
            }
            if (value instanceof Boolean) {
                return (Boolean) value;
            }
            return Boolean.parseBoolean(value.toString());
        }

        public String getString() {
            if (value == null) {
                return null;
            }
            if (value instanceof String) {
                return (String) value;
            }
            return value.toString();
        }

        @Override
        public ArrayList<String> copyStringArray(ArrayList<String> lines, String key, int indent, boolean last) {
            String quote = (value instanceof String) ? QUOTE : "";
            if(value == null){
                System.err.println("value is null:" + key);
            }
            String line = getIndent(indent) + keyToString(key) + quote + value.toString() + quote + lastToString(last);
            lines.add(line);
            return lines;
        }
        @Override
        public String toString(){
            return getString();
        }
    }

    private static String getIndent(int indent) {
        String ret = "";
        for (int i = 0; i < indent * INDENT_LENGTH; i++) {
            ret += " ";
        }
        return ret;
    }

    private static String keyToString(String key) {
        if (key == null) {
            return "";
        }
        return QUOTE + key + QUOTE + ":";
    }

    private static String lastToString(boolean last) {
        return last ? "" : ",";
    }
    private static MjsonElement toElement(Object element){
        MjsonElement mje;
        if(element == null){
            mje = new MjsonSingle((String)null);
        }else if(element instanceof MjsonElement){
            mje = (MjsonElement)element;
        }else if(element instanceof Integer){
            mje = new MjsonSingle((Integer)element);
        }else if(element instanceof Double){
            mje = new MjsonSingle((Double)element);
        }else if(element instanceof Boolean){
            mje = new MjsonSingle((Boolean)element);
        }else if(element instanceof String){
            mje = new MjsonSingle((String)element);
        }else{
            mje = new MjsonSingle(element.toString());
        }
        return mje;
    }
    public static MjsonMap map(){
        return new MjsonMap();
    }
    public static MjsonList list(Object... elements){
        return new MjsonList(elements);
    }
    public MjsonElement get(String path, MjsonElement _default){
        MjsonElement elem = get(path);
        return elem == null?_default:elem;
    }
    public MjsonElement get(String path){
        Object value;
        ArrayList<String> str_array = toStringArray();
        //System.out.println(str_array);
        try {
            value = se.eval("var json_object = " + String.join(" ", str_array) + ";json_object" + 
                (path == null || path.isEmpty()?"":"." + path) + ";");
        } catch (ScriptException ex) {
            System.err.println("not found path=" + path);
            //System.err.println(String.join("\n", str_array));
            //ex.printStackTrace(System.err);
            return null;
        }
        if(value == null || value.toString().equals("null")){
            return null;
        }
        if(value instanceof ScriptObjectMirror){
            return parseObject((ScriptObjectMirror)value);
        }else{
            return toElement(value);
        }
    }
    public MjsonElement[] getAsArray(String path){
        return toArray(get(path));
    }
    public String[] getAsStringArray(String path){
        MjsonElement[] elems = toArray(get(path));
        String[] ret = new String[elems.length];
        for(int i = 0;i < elems.length;i++){
            ret[i] = toString(elems[i]);
        }
        return ret;
            
    }
    public String getString(String path, boolean strict){
        String s = toString(get(path));
        if(s == null && strict){
            throw new IllegalArgumentException("bad path:" + path);
        }
        return s;
    }
    public int getInteger(String path, int _default){
        Integer i = toInteger(get(path));
        if(i == null){
            return _default;
        }
        return i;
    }
    public double getDouble(String path, double _default){
        Double d = toDouble(get(path));
        if(d == null){
            return _default;
        }
        return d;
    }
    public Integer getInteger(String path, boolean strict){
        Integer i = toInteger(get(path));
        if(i == null && strict){
            throw new IllegalArgumentException("bad path:" + path);
        }
        return i;
    }
    public Double getDouble(String path, boolean strict){
        Double d = toDouble(get(path));
        if(d == null && strict){
            throw new IllegalArgumentException("bad path:" + path);
        }
        return d;
    }
    public Integer getInteger(String path, int index, boolean strict){
        Integer i = toInteger(get(path + "[" + index + "]"));
        if(i == null && strict){
            throw new IllegalArgumentException("bad path:" + path + "[" + index + "]");
        }
        return i;
    }
    public Double getDouble(String path, int index, boolean strict){
        Double d = toDouble(get(path + "[" + index + "]"));
        if(d == null && strict){
            throw new IllegalArgumentException("bad path:" + path + "[" + index + "]");
        }
        return d;
    }
    public static Integer toInteger(MjsonElement elem){
        if(elem instanceof MjsonSingle){
            return ((MjsonSingle)elem).getInteger();
        }else{
            return null;
        }
    }
    public static Double toDouble(MjsonElement elem){
        if(elem instanceof MjsonSingle){
            return ((MjsonSingle)elem).getDouble();
        }else{
            return null;
        }
    }
    public static String toString(MjsonElement elem){
        if(elem instanceof MjsonSingle){
            return ((MjsonSingle)elem).getString();
        }else{
            return null;
        }
    }
    public static MjsonElement[] toArray(MjsonElement elem){
        //new Mjson(elem).print();
        if(elem == null){
            return null;
        }else if(elem instanceof MjsonList){
            MjsonList list = (MjsonList)elem;
            MjsonElement[] array = new MjsonElement[list.size()];
            for(int i = 0;i < list.size();i++){
                array[i] = list.get(i);
            }
            return array;
        }else if(elem instanceof MjsonSingle){
            MjsonSingle single = (MjsonSingle)elem;
            if(single.getString().isEmpty()){
                return new MjsonElement[0];
            }
            return new MjsonElement[]{elem};
        }else{
            return new MjsonElement[]{elem};
        }
    }
    
    public static MjsonElement parse(String str) throws ScriptException{
        ScriptObjectMirror obj = (ScriptObjectMirror)se.eval("var json_object = " + str + ";json_object;");
        return parseObject(obj);
    }
    private static MjsonElement parseObject(ScriptObjectMirror obj){
        if(obj.containsKey("length")){
            MjsonList list = new MjsonList();
            for(int i = 0;i < ((Long)obj.get("length")).intValue();i++){
                Object value = obj.get(Integer.toString(i));
                if(value instanceof ScriptObjectMirror){
                    list.add(parseObject((ScriptObjectMirror)value));
                }else{
                    list.add(toElement(value));
                }
            }
            return list;
        }else{
            MjsonMap map = new MjsonMap();
            for(String key:obj.getOwnKeys(true)){
                Object value = obj.get(key);
                if(value instanceof ScriptObjectMirror){
                    map.put(key, parseObject((ScriptObjectMirror)value));
                }else{
                    map.put(key, toElement(value));
                }
            }
            return map;
        }
    }
    /*private static MjsonElement parseSingle(String str){
        str = str.trim();
        int colon = str.indexOf(":");
        if(colon < 0){
            throw new IllegalArgumentException(str);
        }
        String name = unwrap(str.substring(0, colon));
        
    }
    private static MjsonList parseList(String str){
        String[] elements = str.split(",");
        MjsonList list = new MjsonList();
        for(String element:elements){
            parseValue();
        }
        return null;
    }*/
    private static String unwrap(String str){
        str = str.trim();
        if(str.startsWith("\"") && str.endsWith("\"")){
            return str.substring(1, str.length() - 1);
        }
        if(str.startsWith("'") && str.endsWith("'")){
            return str.substring(1, str.length() - 1);
        }
        return str;
    }

}

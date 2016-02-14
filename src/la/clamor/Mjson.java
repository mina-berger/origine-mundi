/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

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
        this.element = element;
    }
    public Mjson(String str) throws ScriptException{
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
            .pono("sho", map()
                .array("name", "sho", "hiyama")
                .pono("birthday", "2000-02-20")
                .pono("school", "shinobugaoka highschool"))
            .pono("rin", map()
                .array("name", "rin", "hiyama")
                .pono("birthday", "2003-10-09")
                .pono("school", "waseda elementary school"))
            .pono("mina", map()
                .array("name", "mina", "hiyama")
                .pono("birthday", "1971-07-15")
                .pono("company", "orion research"));
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
        System.out.println(json.get("sho.birthday"));
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

        public MjsonMap pono(String key, Object element) {
            put(key, toElement(element));
            return this;
        }
        public MjsonMap array(String key, Object... elements) {
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
                lines.add(line_primo + "[]");
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
    public MjsonElement get(String path) throws ScriptException{
        Object value = se.eval("var json_object = " + String.join(" ", toStringArray()) + ";json_object." + path + ";");
        if(value instanceof ScriptObjectMirror){
            return parseObject((ScriptObjectMirror)value);
        }else{
            return toElement(value);
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

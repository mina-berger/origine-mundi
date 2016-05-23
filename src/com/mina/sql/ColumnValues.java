/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mina.sql;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;

//import com.mina.sql.Name.ColumnType;
import com.mina.util.CoreException;
import com.mina.util.ExplainedObject;

/**
 *
 * @author mina
 */
public class ColumnValues extends ArrayList<ColumnValue>{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ColumnValues(){super();}
    public ColumnValues(ArrayList<ColumnValue> column_values){
        super();
        add(column_values);
    }
    public ExplainedObject[] getExplainedObjectArray(ColumnAlias ca){
        ExplainedObject[] eos = new ExplainedObject[size()];
        for(int i = 0;i < size();i++){
            ColumnValue column_value = get(i);
            eos[i] = new ExplainedObject(
                    ca.convert(column_value.getColumnType(), false), 
                    column_value.getValueExpression());
        }
        return eos;
    }
    public String getValueExpression(ColumnType column_type){
        return getColumnValue(column_type).getValueExpression();
    }
    public boolean hasColumnValue(ColumnType column_type){
        return getColumnValue(column_type) != null;
    }
    public boolean removeColumnValue(ColumnType column_type){
        for(int i = 0;i < size();i++){
            ColumnValue cv = get(i);
            if(cv.getColumnType().equals(column_type)){
                remove(i);
                return true;
            }
        }
        return false;
    }
    public void updateColumnValues(ColumnValues column_values){
        for(int i = 0;i < column_values.size();i++){
            updateColumnValue(column_values.get(i));
        }
    }
    public void updateColumnValue(ColumnValue column_value){
        ColumnValue cv = getColumnValue(column_value.getColumnType());
        if(cv == null){
            add(column_value);
        }else{
            cv.setValueExpression(column_value.getValueExpression());
        }
    }
    public String[] getValueExpressions(){
        String[] value_expressions = new String[size()];
        for(int i = 0;i < size();i++)
            value_expressions[i] = get(i).getValueExpression();
        return value_expressions;
    }
    public ColumnType[] getColumnTypes(){
        ColumnType[] column_types = new ColumnType[size()];
        for(int i = 0;i < size();i++)
            column_types[i] = get(i).getColumnType();
        return column_types;
    }
    public boolean hasAllColumnValues(ArrayList<ColumnType> column_types){
        for(int i = 0;i < column_types.size();i++){
            if(getColumnValue(column_types.get(i)) == null) return false;
        }
        return true;
    }
    public ColumnValue[] getColumnValueArray(ArrayList<ColumnType> column_types){
        ColumnValue[] column_value_array = new ColumnValue[column_types.size()];
        for(int i = 0;i < column_types.size();i++){
            column_value_array[i] = getColumnValue(column_types.get(i));
        }
        return column_value_array;
    }
    public ColumnValue getColumnValue(ColumnType column_type){
        for(int i = 0;i < size();i++){
            ColumnValue cv = get(i);
            if(cv.getColumnType().equals(column_type)) return cv;
        }
        return null;
    }
    public void add(ArrayList<ColumnValue> column_values){
        for(int i = 0;i < column_values.size();i++){
            add(column_values.get(i));
        }
    }
    public void add(DatabaseMetaData dmd, String target_dict_name, Object value){
        add(createColumnValue(dmd, target_dict_name, value));
    }
    public void add(
            DatabaseMetaData dmd, String target_dict_name, 
            Record source_record, String source_dict_name){
        add(createColumnValue(dmd, target_dict_name, source_record, source_dict_name));
    }
    public static ColumnValue createColumnValue(DatabaseMetaData dmd, String target_dict_name, Record record, String source_dict_name){
        return createColumnValue(dmd, target_dict_name, record.getValue(SQLUtil.getColumnType(dmd, source_dict_name)));
    }
    public static ColumnValue createColumnValue(DatabaseMetaData dmd, String target_dict_name, Object value){
        if(value == null) return ColumnValue.nullValue(SQLUtil.getColumnType(dmd, target_dict_name));
        return new ColumnValue(SQLUtil.getColumnType(dmd, target_dict_name), SQLUtil.getValueExpression(dmd, target_dict_name, value));
    }
    public void addCurrentDate(DatabaseMetaData dmd, String dict_name){
        add(ColumnValue.currentDate(SQLUtil.getColumnType(dmd, dict_name)));
    }
    public void addCurrentTime(DatabaseMetaData dmd, String dict_name){
        add(ColumnValue.currentTime(SQLUtil.getColumnType(dmd, dict_name)));
    }
    public void addCurrentTimestamp(DatabaseMetaData dmd, String dict_name){
        add(ColumnValue.currentTimestamp(SQLUtil.getColumnType(dmd, dict_name)));
    }
    public void addNullValue(DatabaseMetaData dmd, String dict_name){
        add(ColumnValue.nullValue(SQLUtil.getColumnType(dmd, dict_name)));
    }
    public static ColumnValues instance(DatabaseMetaData dmd, CVIS inputs){
        return instance(dmd, inputs.toArray(new CVI[0]));
    }
    public static ColumnValues instance(DatabaseMetaData dmd, CVI[] inputs){
        ColumnValues column_values = new ColumnValues();
        for(int i = 0;i < inputs.length;i++){
            CVI input = inputs[i];
            if(input instanceof CVIRecord){
                CVIRecord cvi = (CVIRecord)input;
                column_values.add(dmd, 
                        cvi.target_dict_name, 
                        cvi.source_record, 
                        cvi.source_dict_name);
            }else if(input instanceof CVIValue){
                CVIValue cvi = (CVIValue)input;
                column_values.add(dmd, cvi.dict_name, cvi.value);
            }else if(input instanceof CVICurrentDate){
                CVICurrentDate cvi = (CVICurrentDate)input;
                column_values.addCurrentDate(dmd, cvi.dict_name);
            }else if(input instanceof CVICurrentTime){
                CVICurrentTime cvi = (CVICurrentTime)input;
                column_values.addCurrentTime(dmd, cvi.dict_name);
            }else if(input instanceof CVICurrentTimestamp){
                CVICurrentTimestamp cvi = (CVICurrentTimestamp)input;
                column_values.addCurrentTimestamp(dmd, cvi.dict_name);
            }else if(input instanceof CVINull){
                CVINull cvi = (CVINull)input;
                column_values.addNullValue(dmd, cvi.dict_name);
            }else{
            	throw new CoreException("indefined ColumnValue!");
            }
        }
        
        return column_values;
    }
    public static class CVIS extends ArrayList<CVI>{
        
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CVIS(){
            
        }
        public CVIS(CVI[] cvis){
            add(cvis);
        }
        public void addRecord(String dict_name, Record record){
            add(new CVIRecord(dict_name, record));
        }
        public void addRecord(String target_dict_name, Record source_record, String source_dict_name){
            add(new CVIRecord(target_dict_name, source_record, source_dict_name));
        }
        public void addValue(String dict_name, Object value){
            add(new CVIValue(dict_name, value));
        }
        public void addNull(String dict_name){
            add(new CVINull(dict_name));
        }
        
        public void add(CVI[] cvis){
            for(int i = 0;i < cvis.length;i++){
                add(cvis[i]);
            }
        }
    }
    public static interface CVI{}
    public static class CVIRecord implements CVI{
        private String target_dict_name;
        private Record source_record;
        private String source_dict_name;
        public CVIRecord(String dict_name, Record record){
            this(dict_name, record, dict_name);
        }
        public CVIRecord(String target_dict_name, Record source_record, String source_dict_name){
            this.target_dict_name = target_dict_name;
            this.source_record = source_record;
            this.source_dict_name = source_dict_name;
        }
    }
    public static class CVIValue implements CVI{
        private String dict_name;
        private Object value;
        public CVIValue(String dict_name, Object value){
            this.dict_name = dict_name;
            this.value = value;
        }
    }
    public abstract static class CVIDictName implements CVI{
        protected String dict_name;
        public CVIDictName(String dict_name){
            this.dict_name = dict_name;
        }
    }
    public static class CVICurrentDate extends CVIDictName{
        public CVICurrentDate(String dict_name){super(dict_name);}
    }
    public static class CVICurrentTime extends CVIDictName{
        public CVICurrentTime(String dict_name){super(dict_name);}
    }
    public static class CVICurrentTimestamp extends CVIDictName{
        public CVICurrentTimestamp(String dict_name){super(dict_name);}
    }
    public static class CVINull extends CVIDictName{
        public CVINull(String dict_name){
            super(dict_name);
        }
    }
}

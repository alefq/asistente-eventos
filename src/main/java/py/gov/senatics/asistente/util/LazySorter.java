package py.gov.senatics.asistente.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;

public class LazySorter<T> implements Comparator<T> {

    private String sortField;    
    private boolean sortAsc;
    
    public LazySorter(String sortField, boolean sortAsc) {
        this.sortField = sortField;
        this.sortAsc = sortAsc;
    }

    public int compare(T obj1, T obj2) {
        
    	try {
        	
        	//obtain field and getter by reflection
        	String fieldName = this.sortField.substring(0, 1).toUpperCase() + this.sortField.substring(1);
        	Field field = obj1.getClass().getDeclaredField(this.sortField);
        	Method method = obj1.getClass().getMethod("get" + fieldName);
            
        	//get the field values
        	Object value1 = method.invoke(obj1);
            Object value2 = method.invoke(obj2);
            
            //make a reflection compare
            Class<?> classType = field.getType();
            int value = CompareToBuilder.reflectionCompare(classType.cast(value1), classType.cast(value2));
            
            return (sortAsc)? value : -1 * value;
            
        }
        catch(Exception e) {
        	
            throw new RuntimeException();
            
        }
    	
    }
   

	
}

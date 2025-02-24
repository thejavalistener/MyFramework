package thejavalistener.fwk.frontend.hql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import thejavalistener.fwk.util.string.MyString;

public class Entity
{
	private String tableName;
	private String className;
	private String packageName;
	private List<Attribute> attributes;
	
	public Entity(Object bean)
	{
		this(bean.getClass());
	}
	public Entity(Class<?> clazz)
	{
		attributes = new ArrayList<>();

		_introspect(clazz);
	}

	private void _introspect(Class<?> clazz)
	{		
		className = clazz.getSimpleName();
		
		Table annTable = clazz.getAnnotation(Table.class);
		tableName = annTable!=null?annTable.name():className;
		
		packageName = clazz.getPackageName();
		
		for(Field f:clazz.getDeclaredFields())
		{
			Column annColumn = f.getAnnotation(Column.class);
			JoinColumn annJoinColumn = f.getAnnotation(JoinColumn.class);
			Id annId = f.getAnnotation(Id.class);
			

			// es local field
			if( annColumn!=null )
			{
				Attribute att = new Attribute();
				String fieldName = MyString.ifEmtyOrNull(annColumn.name(),f.getName());
				att.setName(f.getName());
				att.setFieldName(fieldName);
				att.setId(annId!=null);
				att.setFk(false);
				att.setFkTableName(null);
				att.setType(f.getType());
				attributes.add(att);
			}
			else	
			{
				if( annJoinColumn!=null )
				{
					Attribute att = new Attribute();
					String fieldName = MyString.ifEmtyOrNull(annJoinColumn.name(),f.getName());
					att.setName(f.getName());
					att.setFieldName(fieldName);
					att.setId(false);
					att.setFk(true);
					att.setFkTableName(f.getType().getAnnotation(Table.class).name());
					att.setType(f.getType());
					attributes.add(att);
				}				
			}
		}
	}
	
	
	
	public String getPackageName()
	{
		return packageName;
	}

	public String getTableName()
	{
		return tableName;
	}
	public String getClassName()
	{
		return className;
	}

	public List<Attribute> getAttributes()
	{
		return attributes;
	}
	
}

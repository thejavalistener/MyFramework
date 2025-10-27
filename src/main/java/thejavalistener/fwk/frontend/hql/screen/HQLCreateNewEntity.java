package thejavalistener.fwk.frontend.hql.screen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.metamodel.EntityType;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import thejavalistener.fwk.awt.MyComponent;
import thejavalistener.fwk.awt.form.MyForm;
import thejavalistener.fwk.awt.link.MyLink;
import thejavalistener.fwk.awt.list.MyComboBox;
import thejavalistener.fwk.awt.list.MyComboBoxAdapter;
import thejavalistener.fwk.awt.textarea.MyTextField;
import thejavalistener.fwk.frontend.MyAbstractScreen;
import thejavalistener.fwk.frontend.messages.MyScreenMessageEvent;
import thejavalistener.fwk.frontend.messages.MyScreenMessageListener;
import thejavalistener.fwk.util.MyReflection;
import thejavalistener.fwk.util.string.MyString;

@Scope("prototype")
@Component
public class HQLCreateNewEntity extends MyAbstractScreen implements MyScreenMessageListener
{
	@Autowired
	private HQLFacade facade;
	 
	private List<String> labels = new ArrayList<>();	
	private List<MyComponent> myComponents = new ArrayList<>();	
	
	private MyForm form;
		
	private MyComboBox<Class<?>> cbEntities;
	
	private JButton bInsert;
	private JButton bCancel;
	private HQLCreateNewEntity outer = null;
		
	public HQLCreateNewEntity()
	{	
		this.outer = this;

		form = new MyForm(300);
		JScrollPane scrollForm = new JScrollPane();
		scrollForm.setBorder(null);
		
		JPanel pp = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pp.add(form.c());
		scrollForm.setViewportView(pp);
		
		add(scrollForm,BorderLayout.WEST);
		bCancel = new JButton("Cancel");
		bCancel.addActionListener(new EscuchaCancel());
		bInsert = new JButton("Insert");
		bInsert.addActionListener(new EscuchaInsert());
	}

	public void init()
	{
		// entities
		cbEntities = new MyComboBox<>();
		cbEntities.c().setPreferredSize(new Dimension(300,cbEntities.c().getPreferredSize().height));

		cbEntities.setComboBoxListener(new EscuchaEntities());
		cbEntities.setTToString(clazz->clazz.getSimpleName());

		Class<?> entityClass = (Class<?>)removeValueFrom(HQLScreen.class,"entityClass");
		entityClass=entityClass!=null?entityClass:(Class<?>)removeValueFrom(HQLCreateNewEntity.class,"entityClass");
		
		if( entityClass==null )
		{
			for(EntityType<?> entityType:facade.getMetamodel().getEntities())
			{
				cbEntities.addItem(entityType.getJavaType());
			}
		}
		else
		{
			cbEntities.addItem(entityClass);
			cbEntities.setEnabled(false);
		}
		
		// los fields
		cbEntities.forceItemEvent();		
	}
	
	class EscuchaEntities extends MyComboBoxAdapter
	{
		@Override
		public void itemStateChanged(ItemEvent e)
		{
			_generarForm(cbEntities.getSelectedItem());
		}
	}
	
	private void _generarForm(Class<?> entityClass)
	{
		labels.clear();
		myComponents.clear();
		
		double wL = .3;
		double wC = .7;
		
		form.reset();
		
		form.addRow().add(cbEntities.c());
		form.addSeparator();
		
		// trate todos los campos anotados con @Column o con @ManyToOne omitiendo los finales y estáticos
		
		Function<Field,Boolean> func = f->{
			boolean esFinalOEstatico = Modifier.isFinal(f.getModifiers())||Modifier.isStatic(f.getModifiers());
			boolean tieneColumn = f.getAnnotation(Column.class)!=null;
			boolean tieneManyToOne = f.getAnnotation(ManyToOne.class)!=null;
			return !esFinalOEstatico && (tieneColumn || tieneManyToOne);
		};
		
		Field[] fields = MyReflection.clasz.getDeclaredFields(entityClass,func);
		for(int i=0; i<fields.length; i++)
		{
			Field field = fields[i];
			Class<?> typeClass = field.getType();
			String label = field.getName();
			
			JButton btnNew = null;

			MyComponent myComponent;
			
			if(field.getAnnotation(Column.class)!=null) //local
			{
				MyTextField textField = new MyTextField();
				_agregarMascaraAlTF(label,textField,typeClass);
				myComponent = textField;
				
				boolean esId = field.getAnnotation(Id.class)!=null;
				boolean esAutoIncremental = field.getAnnotation(GeneratedValue.class)!=null;
				if(  esId && esAutoIncremental )
				{
					textField.setEnabled(false);
				}
			}
			else // foraneo: comboBox
			{
				List<Object> items = facade.queryMultipleRows("From "+typeClass.getName());
				MyComboBox<Object> comboBox = new MyComboBox<>();
				comboBox.setSpecialItem(" ");
				comboBox.setItems(items);
				myComponent = comboBox;
				btnNew = new JButton("New");

				btnNew.addActionListener(new EscuchaNew(label,typeClass));
			}
			
			labels.add(label);
			myComponents.add(myComponent);
			
			MyLink lbl = new MyLink(label);
//			MyAwt.setPreferredWidth(80,lbl);
			
			if(btnNew!=null)
			{
				double w1 = wC*0.7;
				double w2 = wC*0.3;
//				form.addRow().add(lbl.c(),wL).add(myComponent.c(),w1).add(btnNew,w2);
				form.addRow().add(lbl.c()).add(myComponent.c()).add(btnNew).layout(wL,w1,w2);
			}
			else
			{
//				form.addRow().add(lbl.c(),wL).add(myComponent.c(),wC);				
				form.addRow().add(lbl.c()).add(myComponent.c()).layout(wL,wC);				
			}
		}
		
		form.addSeparator();
		
		form.addRow().add(bCancel).add(bInsert);
		
		form.makeForm();
		
//		getMyDialog().configurator().pack().centerH(150).apply();
		myDialog.configurator().pack().centerH(150);//.apply();
		
//		getMyDialog().c().pack();
//		getMyDialog().centerH(150);
//		validate();		
	}
	
	private Object _generarObject()
	{
		try
		{
			Class<?> entityClass = cbEntities.getSelectedItem();
			
			Object ret = entityClass.getConstructor().newInstance();
			Field[] fields = MyReflection.clasz.getDeclaredFields(entityClass,f->f.getAnnotation(Column.class)!=null||f.getAnnotation(ManyToOne.class)!=null);
			for(Field field:fields)
			{
				int idx = labels.indexOf(field.getName());
				Object value = myComponents.get(idx).getValue();

				if( MyReflection.clasz.isFinalClass(field.getType()))
				{
					String sValue = (String)value;
					if( sValue==null ) continue;
					
					value = MyString.parseTo(sValue,field.getType());
				}
				
				MyReflection.object.invokeSetter(ret,field.getName(),value);					

			}
			
			return ret;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
		
	private void _agregarMascaraAlTF(String label,MyTextField tf,Class<?> clazz)
	{
		String titulo = "Error";
		String mensaje = "El campo "+label+" debe ser: ";
		
		switch(clazz.getCanonicalName())
		{
			case "char","java.lang.Character":
				tf.addMask(MyTextField.MASK_CHAR);
				tf.addValidation(MyTextField.VALID_CHARACTER,mensaje+"char",titulo);
				
				break;
			case "short","java.lang.Short":
				tf.addMask(MyTextField.MASK_INTEGER);
				tf.addValidation(MyTextField.VALID_SHORT,mensaje+"short",titulo);
				break;
			case "int","java.lang.Integer":
				tf.addMask(MyTextField.MASK_INTEGER);
				tf.addValidation(MyTextField.VALID_INTEGER_OR_ENTPY,mensaje+"int",titulo);
				break;
			case "long","java.lang.Long":
				tf.addMask(MyTextField.MASK_INTEGER);
				tf.addValidation(MyTextField.VALID_LONG,mensaje+"long",titulo);
				break;
			case "float","java.lang.Float":
				tf.addMask(MyTextField.MASK_DOUBLE);
				tf.addValidation(MyTextField.VALID_FLOAT,mensaje+"float",titulo);
				break;
			case "double","java.lang.Double":
				tf.addMask(MyTextField.MASK_DOUBLE);
				tf.addValidation(MyTextField.VALID_DOUBLE_OR_EMPTY,mensaje+"double",titulo);
				break;					
			case "boolean","java.lang.Boolean":
				tf.addMask(MyTextField.MASK_BOOLEAN);
				tf.addValidation(MyTextField.VALID_BOOLEAN,mensaje+"true o false",titulo);
				break;					
			case "java.lang.String":
				break;
			case "java.sql.Date":
			case "java.sql.Timestamp":
				tf.addValidation(MyTextField.VALID_DATE_OR_EMPTY,mensaje+"yyyy-mm-dd, NOW, TODAY o SYSDATE",titulo);
				break;
			case "java.sql.Time":
				break;
		}	
	}

	@Override
	public String getName()
	{
		return "Crear un nuevo objeto";
	}

	@Override
	public void onMessageEvent(MyScreenMessageEvent e)
	{
		System.out.println(e.getClazz().getName());
	}

	class EscuchaInsert implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				for(MyComponent cmp:myComponents)
				{
					if( cmp.getClass().isAssignableFrom(MyTextField.class) )
					{
						MyTextField tf = (MyTextField)cmp;
						tf.runValidations();
					}
				}
				
				Object obj = _generarObject();
				facade.persist(obj);
				
				returnValue = obj;
				
				Field fId = MyReflection.clasz.getDeclaredField(obj.getClass(),Id.class);
				Object oId = MyReflection.object.invokeGetter(obj,fId.getName());
				String sClass = obj.getClass().getSimpleName();
				String msg = "Se creó una instancia de "+sClass+" con "+fId.getName()+"="+oId.toString();
				showInformationMessage(msg,"Se creó un nuevo objeto");
				
				dataUpdated();
				
				
				
				
				_resetTextFields();				
			}
			catch(Exception ex)
			{
				showExceptionMessage(ex);
			}
		}
	}
	
	class EscuchaNew implements ActionListener
	{
		private String label;
		private Class<?> typeClass;
		public EscuchaNew(String lbl,Class<?> typeClass)
		{
			this.label = lbl;
			this.typeClass = typeClass;
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			putValueFor(HQLCreateNewEntity.class,"entityClass",typeClass);
			Object newObject = getMyApp().showScreen(HQLCreateNewEntity.class).pack().apply();
			
			List<Object> items = facade.queryMultipleRows("From "+typeClass.getName());
			int idx = labels.indexOf(label);
			MyComboBox<Object> cb = (MyComboBox)myComponents.get(idx);
			cb.setItems(items);
			
			cb.setSelectedItem(t->_equalsById(t,newObject));
		}

		private boolean _equalsById(Object a, Object b)
		{
			if(a==null&&b==null) return true;
			if(a==null&&b!=null||a!=null&&b==null) return false;
	
			Field aId=MyReflection.clasz.getDeclaredField(a.getClass(),Id.class);
			Field bId=MyReflection.clasz.getDeclaredField(b.getClass(),Id.class);
	
			Object aV=MyReflection.object.invokeGetter(a,aId.getName());
			Object bV=MyReflection.object.invokeGetter(b,bId.getName());
	
			return aV.equals(bV);
		}

	}
	
	class EscuchaCancel implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			myDialog.closeDialog();
		}
	}
	
	private void _resetTextFields()
	{
		myComponents.forEach((c)->c.resetValue());
		myComponents.get(0).requestFocus();		
	}
	

	
}
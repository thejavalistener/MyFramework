package thejavalistener.fwk.awt;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

public class MyFocusTraversalPolicy extends FocusTraversalPolicy
{
	private List<JComponent> components;
	private Container container;

	public MyFocusTraversalPolicy(Container container)
	{
		components = new ArrayList<>();
		this.container = container;
        this.container.setFocusTraversalPolicy(this);
		this.container.setFocusCycleRoot(true); // Añade esta línea
	}
	
	public void add(JComponent component)
	{
		this.components.add(component);
	}

	@Override
	public Component getComponentAfter(Container aContainer, Component aComponent)
	{
		int pos = (components.indexOf(aComponent)+1)%components.size();
		Component c = components.get(pos);
		if( c.isVisible() )
		{
			return c;
		}
		else
		{
			return getComponentAfter(aContainer,c);
		}
	}

	@Override
	public Component getComponentBefore(Container aContainer, Component aComponent)
	{
		int pos = components.indexOf(aComponent)-1;
		pos = pos<0?components.size()-1:pos;
		
		Component c = components.get(pos);
		if( c.isVisible() )
		{
			return c;
		}
		else
		{
			return getComponentBefore(aContainer,c);
		}
	}

	@Override
	public Component getFirstComponent(Container aContainer)
	{
		Component c = components.get(0);
		if( c.isVisible() )
		{
			return c;
		}
		else
		{
			return getComponentAfter(aContainer,c);
		}
	}

	@Override
	public Component getLastComponent(Container aContainer)
	{
		Component c = components.get(components.size()-1);
		if( c.isVisible() )
		{
			return c;
		}
		else
		{
			return getComponentBefore(aContainer,c);
		}
	}

	@Override
	public Component getDefaultComponent(Container aContainer)
	{
		return getFirstComponent(aContainer);
	}
}
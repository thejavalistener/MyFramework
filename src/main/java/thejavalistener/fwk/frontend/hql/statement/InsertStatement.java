package thejavalistener.fwk.frontend.hql.statement;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class InsertStatement extends AbstractStatement
{
	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void process()
	{
		String sql=getSql();

		Object x=procesarInsert(sql);
		em.persist(x);

		if(_confirmTransaction(1))
		{
			getScreen().showInformationMessage("Se insertó 1 fila","Transacción exitosa");
		}
		else
		{
			getScreen().showInformationMessage("Ninguna fila resultó afectada","Transacción revertida");
			throw new MyRollbackException();
		}
	}

//	public Object procesarInsert(String hql)
//	{
//		try
//		{
//			hql=hql.trim();
//			String hqlLower=hql.toLowerCase();
//
//			if(!hqlLower.startsWith("insert into")) throw new IllegalArgumentException("Sentencia inválida");
//
//			int idxValues=hqlLower.indexOf("values");
//			if(idxValues==-1) throw new IllegalArgumentException("Falta cláusula VALUES");
//
//			String cabecera=hql.substring(0,idxValues).trim();
//			String valoresRaw=hql.substring(idxValues+6).trim();
//
//			// Paréntesis opcionales
//			String valores;
//			if(valoresRaw.startsWith("(")&&valoresRaw.endsWith(")"))
//			{
//				valores=valoresRaw.substring(1,valoresRaw.length()-1).trim();
//			}
//			else
//			{
//				valores=valoresRaw;
//			}
//
//			// Extraer clase y alias
//			String[] tokens=cabecera.split("\\s+");
//			if(tokens.length<4) throw new IllegalArgumentException("Falta clase o alias.");
//
//			String className=tokens[2]; // Puede ser AlbumArtista o
//										// app.mapping.AlbumArtista
//			String alias=tokens[3];
//
//			// Si no tiene punto, buscar en el Metamodel
//			if(!className.contains("."))
//			{
//				String simpleClassName=className;
//				className=null;
//				for(EntityType<?> entity:em.getMetamodel().getEntities())
//				{
//					if(entity.getJavaType().getSimpleName().equals(simpleClassName))
//					{
//						className=entity.getJavaType().getName();
//						break;
//					}
//				}
//				if(className==null)
//				{
//					throw new IllegalArgumentException("Clase no encontrada en el Metamodel: "+simpleClassName);
//				}
//			}
//
//			// Cargar la clase y crear la instancia
//			Class<?> clazz=Class.forName(className);
//			Object instancia=clazz.getDeclaredConstructor().newInstance();
//
//			// Separar asignaciones respetando comillas simples
//			String[] asignaciones=valores.split(",(?=(?:[^']*'[^']*')*[^']*$)");
//
//			for(String asignacion:asignaciones)
//			{
//				String[] partesAsignacion=asignacion.split("=");
//				if(partesAsignacion.length!=2)
//				{
//					throw new IllegalArgumentException("Asignación inválida: "+asignacion);
//				}
//
//				String campoCompuesto=partesAsignacion[0].trim();
//				String valorStr=partesAsignacion[1].trim();
//
//				if(!campoCompuesto.startsWith(alias+".")) continue;
//
//				String path=campoCompuesto.substring(alias.length()+1);
//				String[] atributos=path.split("\\.");
//
//				if(atributos.length==1)
//				{
//					Field field=clazz.getDeclaredField(atributos[0]);
//					field.setAccessible(true);
//					Object valor=parseValue(field.getType(),valorStr);
//					field.set(instancia,valor);
//
//				}
//				else if(atributos.length==2)
//				{
//					String relacion=atributos[0];
//					String subcampo=atributos[1];
//
//					Field fieldRelacion=clazz.getDeclaredField(relacion);
//					fieldRelacion.setAccessible(true);
//
//					Class<?> claseRelacion=fieldRelacion.getType();
//					Object objetoRelacionado=claseRelacion.getDeclaredConstructor().newInstance();
//
//					Field fieldSub=claseRelacion.getDeclaredField(subcampo);
//					fieldSub.setAccessible(true);
//					Object valor=parseValue(fieldSub.getType(),valorStr);
//					fieldSub.set(objetoRelacionado,valor);
//
//					fieldRelacion.set(instancia,objetoRelacionado);
//
//				}
//				else
//				{
//					throw new IllegalArgumentException("Profundidad no soportada: "+path);
//				}
//			}
//
//			return instancia;
//
//		}
//		catch(Exception e)
//		{
//			throw new RuntimeException("Error procesando INSERT: "+hql,e);
//		}
//	}

	public Object procesarInsert(String hql)
	{
		try
		{
			hql = hql.trim();
			String hqlLower = hql.toLowerCase();

			if(!hqlLower.startsWith("insert into")) throw new IllegalArgumentException("Sentencia inválida");

			int idxValues = hqlLower.indexOf("values");
			if(idxValues == -1) throw new IllegalArgumentException("Falta cláusula VALUES");

			String cabecera = hql.substring(0, idxValues).trim();
			String valoresRaw = hql.substring(idxValues + 6).trim();

			// Paréntesis opcionales
			String valores;
			if(valoresRaw.startsWith("(") && valoresRaw.endsWith(")"))
			{
				valores = valoresRaw.substring(1, valoresRaw.length() - 1).trim();
			}
			else
			{
				valores = valoresRaw;
			}

			// Extraer clase y alias opcional
			String[] tokens = cabecera.split("\\s+");
			if(tokens.length < 3) throw new IllegalArgumentException("Falta clase.");

			String className = tokens[2];
			String alias = null;

			if(tokens.length >= 4)
			{
				alias = tokens[3];
			}

			// Si no tiene punto, buscar en el Metamodel
			if(!className.contains("."))
			{
				String simpleClassName = className;
				className = null;
				for(EntityType<?> entity : em.getMetamodel().getEntities())
				{
					if(entity.getJavaType().getSimpleName().equalsIgnoreCase(simpleClassName))
					{
						className = entity.getJavaType().getName();
						break;
					}
				}
				if(className == null)
				{
					throw new IllegalArgumentException("Clase no encontrada en el Metamodel: " + simpleClassName);
				}
			}

			// Cargar la clase y crear la instancia
			Class<?> clazz = Class.forName(className);
			Object instancia = clazz.getDeclaredConstructor().newInstance();

			// Separar asignaciones respetando comillas simples
			String[] asignaciones = valores.split(",(?=(?:[^']*'[^']*')*[^']*$)");

			for(String asignacion : asignaciones)
			{
				String[] partesAsignacion = asignacion.split("=");
				if(partesAsignacion.length != 2)
				{
					throw new IllegalArgumentException("Asignación inválida: " + asignacion);
				}

				String campoCompuesto = partesAsignacion[0].trim();
				String valorStr = partesAsignacion[1].trim();

				String path;
				if(alias != null)
				{
					if(!campoCompuesto.startsWith(alias + ".")) continue;
					path = campoCompuesto.substring(alias.length() + 1);
				}
				else
				{
					path = campoCompuesto;
				}

				String[] atributos = path.split("\\.");

				if(atributos.length == 1)
				{
					Field field = clazz.getDeclaredField(atributos[0]);
					field.setAccessible(true);
					Object valor = parseValue(field.getType(), valorStr);
					field.set(instancia, valor);
				}
				else if(atributos.length == 2)
				{
					String relacion = atributos[0];
					String subcampo = atributos[1];

					Field fieldRelacion = clazz.getDeclaredField(relacion);
					fieldRelacion.setAccessible(true);

					Class<?> claseRelacion = fieldRelacion.getType();
					Object objetoRelacionado = claseRelacion.getDeclaredConstructor().newInstance();

					Field fieldSub = claseRelacion.getDeclaredField(subcampo);
					fieldSub.setAccessible(true);
					Object valor = parseValue(fieldSub.getType(), valorStr);
					fieldSub.set(objetoRelacionado, valor);

					fieldRelacion.set(instancia, objetoRelacionado);
				}
				else
				{
					throw new IllegalArgumentException("Profundidad no soportada: " + path);
				}
			}

			return instancia;

		}
		catch(Exception e)
		{
			throw new RuntimeException("Error procesando INSERT: " + hql, e);
		}
	}
	
	
	private static Object parseValue(Class<?> tipo, String valor) throws Exception
	{
		valor=valor.trim();
		if(valor.equalsIgnoreCase("null")) return null;

		// if (tipo == String.class) return valor.replaceAll("^\"|\"$", "");

		if(tipo==String.class)
		{
			if(!valor.startsWith("'")||!valor.endsWith("'"))
			{
				throw new IllegalArgumentException("Las cadenas deben ir entre comillas simples: '"+valor+"'");
			}
			return valor.substring(1,valor.length()-1); // quita las comillas
														// simples
		}

		if(tipo==int.class||tipo==Integer.class) return Integer.parseInt(valor);
		if(tipo==long.class||tipo==Long.class) return Long.parseLong(valor);
		if(tipo==boolean.class||tipo==Boolean.class) return Boolean.parseBoolean(valor);
		if(tipo==double.class||tipo==Double.class) return Double.parseDouble(valor);
		if(tipo==float.class||tipo==Float.class) return Float.parseFloat(valor);
		if(tipo==BigDecimal.class) return new BigDecimal(valor);
		if(tipo==java.sql.Date.class)
		{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date utilDate=sdf.parse(valor.replaceAll("^\"|\"$",""));
			return new java.sql.Date(utilDate.getTime());
		}

		throw new IllegalArgumentException("Tipo no soportado: "+tipo);
	}

	private boolean _confirmTransaction(int updateCount)
	{
		return getScreen().showConfirmWarningMessage(updateCount+" filas resultarán afectadas.\n¿Confirma o revierte?","Confirmar o revertir");
	}
}

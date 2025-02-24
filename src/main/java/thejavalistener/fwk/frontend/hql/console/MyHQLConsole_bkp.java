package thejavalistener.fwk.frontend.hql.console;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.reflections.Reflections;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class MyHQLConsole_bkp
{
	public static void main(String[] args)
	{
		try
		{

			System.out.println("------");
			String packageBasePath="C:/Java64/Workspace/VFX_v5.6.6/target/classes";
			_createCustomClassLoader(packageBasePath);

			
			String packageBase="app.mapping"; 
			Reflections reflections=new Reflections(packageBase);
			Set<Class<?>> entityClasses=reflections.getTypesAnnotatedWith(Entity.class);


			
			if(entityClasses.isEmpty())
			{
				System.out.println("No se encontraron entidades en el paquete: "+packageBase);
				return;
			}

			System.out.println("Entidades encontradas:");
			for(Class<?> entityClass:entityClasses)
			{
				System.out.println("  - "+entityClass.getName());
			}

			// 🔹 Configurar Hibernate para usar HSQLDB en modo servidor
			StandardServiceRegistry registry=new StandardServiceRegistryBuilder().applySetting("hibernate.dialect","org.hibernate.dialect.HSQLDialect") // Dialecto
																																						// para
																																						// HSQLDB
					.applySetting("hibernate.connection.driver_class","org.hsqldb.jdbc.JDBCDriver") // Driver
																									// de
																									// HSQLDB
					.applySetting("hibernate.connection.url","jdbc:hsqldb:hsql://localhost/miBD") // Modo
																									// servidor
					.applySetting("hibernate.connection.username","SA") // Usuario
																		// por
																		// defecto
																		// de
																		// HSQLDB
					.applySetting("hibernate.connection.password","") // Sin
																		// password
																		// por
																		// defecto
					.applySetting("hibernate.hbm2ddl.auto","update") // Mantener
																		// la BD
																		// actualizada
																		// con
																		// las
																		// entidades
					.applySetting("hibernate.show_sql","true") // Mostrar las
																// consultas SQL
					.applySetting("hibernate.format_sql","true") // Formatear el
																	// SQL para
																	// mejor
																	// lectura
					.build();

			MetadataSources metadataSources=new MetadataSources(registry);

			for(Class<?> entityClass:entityClasses)
			{
				metadataSources.addAnnotatedClass(entityClass);
			}

			SessionFactory sessionFactory=metadataSources.buildMetadata().buildSessionFactory();
			System.out.println("✅ Hibernate configurado correctamente con HSQLDB en modo servidor.");

			
		    // levanto el contexto de spring		
			ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:thejavalistener/fwk/frontend/hql/console/springx.xml");
//			ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:"+MyHQLConsole.class.getPackageName().replace('.','/')+"/springx.xml");

			
			String hql = "FROM Alumno i ";
			
			EntityManagerFactory emf = ctx.getBean("entityManagerFactory", EntityManagerFactory.class);
	        
	        // Crear un EntityManager a partir de la fábrica
	        EntityManager em = emf.createEntityManager();
	        
	        
	        Query q = em.createQuery(hql);
	        for(Object pp:q.getResultList())	
	        {
	        	System.out.println("-->"+pp.toString());
	        }
		}
		catch(Exception e)
		{
			System.err.println("Error: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static void _createCustomClassLoader(String baseClassPath) throws Exception
	{
		String packageBasePath="C:/Java64/Workspace/VFX_v5.6.6/target/classes";

		File file=new File(packageBasePath);
		URL[] urls= {file.toURI().toURL()};
		ClassLoader customClassLoader=new URLClassLoader(urls,Thread.currentThread().getContextClassLoader());

		// Establecer el ClassLoader en el hilo actual
		Thread.currentThread().setContextClassLoader(customClassLoader);	
		

	}
}

package thejavalistener.fwk.frontend.hql.console;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hsqldb.server.Server;
import org.reflections.Reflections;

public class MyHQLConsole2
{

	private static String dbUrl;
	private static String dbUser;
	private static String dbPassword;
	private static String dbFilePath;
	private static Server hsqlServer;
	private static EntityManagerFactory entityManagerFactory;

	public static void main(String[] args)
	{

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
		
		

		// 🔹 1. PEDIR AL USUARIO LOS DATOS DE LA BASE DE DATOS
		dbFilePath="C:/Java64/Workspace/VFX_v5.6.6/DATABASE/xdb";
		dbUrl="jdbc:hsqldb:hsql://localhost/xdb";
		dbUser="sa";
		dbPassword="";

		// 🔹 2. VERIFICAR SI LA BASE DE DATOS ESTÁ LEVANTADA
		if(!isDatabaseRunning())
		{
			startDatabaseServer(dbFilePath); // Si no está corriendo, iniciamos
												// el servidor HSQLDB con la
												// ruta ingresada
		}

		// 🔹 3. CONFIGURAR ENTITY MANAGER DINÁMICAMENTE
		configureEntityManager();

		// 🔹 4. PROBAR UNA CONSULTA SIMPLE
		testDatabaseQuery();

		// 🔹 5. CERRAR EL ENTITY MANAGER FACTORY CUANDO TERMINE
		entityManagerFactory.close();

		System.out.println("✅ Aplicación finalizada correctamente.");
	}

	private static boolean isDatabaseRunning()
	{
		try (Connection conn=DriverManager.getConnection(dbUrl,dbUser,dbPassword))
		{
			System.out.println("✅ La base de datos ya está corriendo.");
			return true;
		}
		catch(SQLException e)
		{
			System.out.println("❌ La base de datos no está corriendo. Se procederá a iniciarla.");
			return false;
		}
	}

	private static void startDatabaseServer(String databaseFilePath)
	{
		System.out.println("🚀 Iniciando el servidor HSQLDB con la base de datos en: "+databaseFilePath);

		hsqlServer=new Server();
		// 🔹 Deshabilitar logs en consola
		hsqlServer.setLogWriter(null);
		hsqlServer.setErrWriter(null);
		hsqlServer.setDatabaseName(0,"xdb");
		hsqlServer.setDatabasePath(0,"file:"+databaseFilePath+";hsqldb.lock_file=false"); // 🔹
																							// Usa
																							// la
																							// ruta
																							// ingresada
		hsqlServer.start();

		System.out.println("✅ Servidor HSQLDB iniciado correctamente en "+databaseFilePath);
	}

	private static void configureEntityManager()
	{
		System.out.println("⚙️ Configurando EntityManager...");

		StandardServiceRegistry registry=new StandardServiceRegistryBuilder().applySetting("hibernate.dialect","org.hibernate.dialect.HSQLDialect")
				.applySetting("hibernate.connection.driver_class","org.hsqldb.jdbc.JDBCDriver").applySetting("hibernate.connection.url",dbUrl).applySetting("hibernate.connection.username",dbUser)
				.applySetting("hibernate.connection.password",dbPassword).applySetting("hibernate.hbm2ddl.auto","update").applySetting("hibernate.show_sql","true").build();

		MetadataSources metadataSources=new MetadataSources(registry);
		entityManagerFactory=metadataSources.buildMetadata().buildSessionFactory();

		
		
		
		System.out.println("✅ EntityManager configurado correctamente.");
	}

	private static void testDatabaseQuery()
	{
		System.out.println("🔎 Ejecutando una consulta de prueba...");

		EntityManager em=entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		try
		{
			Long count=em.createQuery("SELECT COUNT(e) FROM Alumno e",Long.class).getSingleResult();
			System.out.println("📊 Total de registros en Alumno: "+count);
		}
		catch(Exception e)
		{
			System.out.println("⚠️ Error al ejecutar la consulta: "+e.getMessage());
		}
		em.getTransaction().commit();
		em.close();
	}

	private static void _createCustomClassLoader(String packageBasePath)
	{
		try
		{
			String packageBase="app.mapping"; // 🔹 Paquete donde están las

			File file=new File(packageBasePath);
			URL[] urls= {file.toURI().toURL()};
			ClassLoader customClassLoader=new URLClassLoader(urls,Thread.currentThread().getContextClassLoader());

			// Establecer el ClassLoader en el hilo actual
			Thread.currentThread().setContextClassLoader(customClassLoader);
			
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			URLClassLoader urlClassLoader = (URLClassLoader) classLoader;

			System.out.println("📂 ClassLoader cargando las siguientes rutas:");
			for (URL url : urlClassLoader.getURLs()) {
			    System.out.println("  - " + url);
			}
			
			Class.forName("app.mapping.Alumno");

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}

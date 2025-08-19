//package thejavalistener.fwk.frontend;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ShutdownListener implements DisposableBean
//{
//	@Autowired
//	private DataSource ds;
//	
//	@Override
//	public void destroy() {
//        try (Connection con = ds.getConnection();
//             PreparedStatement ps = con.prepareStatement("SHUTDOWN")) {
//            ps.execute();
//            System.out.println("DB apagada desde @PreDestroy");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}

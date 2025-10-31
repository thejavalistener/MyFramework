//package thejavalistener.fwk.backend;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JdbcSupport
//{
//	@Autowired
//	private DataSource ds;
//
//	public List<Object[]> queryMultipleRows(String sql,Object ...params)
//	{
//		Connection con = null; 
//		PreparedStatement pstm = null;
//		ResultSet rs = null;
//		
//		try
//		{
//			con = ds.getConnection();
//			pstm = con.prepareStatement(sql);
//			for(int i=0; i<params.length; i++)
//			{
//				pstm.setObject(i+1,params[i]);
//			}
//			
//			rs = pstm.executeQuery();
//			
//			List<Object[]> ret = new ArrayList<>();
//			while( rs.next() )
//			{
//				int n=rs.getMetaData().getColumnCount();
//				Object[] row = new Object[n];
//				for(int i=1; i<=n; i++)
//				{
//					row[i-1] = rs.getObject(i);
//				}
//				
//				ret.add(row);
//			}
//			
//			return ret;
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//		finally
//		{
//			try
//			{
//				if( rs!=null ) rs.close();
//				if( pstm!=null ) pstm.close();
//				if( con!=null ) con.close();
//			}
//			catch(Exception e2)
//			{
//				e2.printStackTrace();
//				throw new RuntimeException(e2);
//			}
//		}		
//	}
//	
//	public Object[] querySingleRow(String sql,Object ...params)
//	{
//		List<Object[]> lst = queryMultipleRows(sql,params);
//		int n = lst.size();
//		if( n>1 )
//		{
//			String mssg = "Se esperaba 1 o ninguna fila, pero se encontraron: "+n;
//			throw new RuntimeException(mssg);
//		}
//		
//		return n==1?lst.get(0):null;
//	}	
//	
//	
//	public int update(String sql,Object ...params)
//	{
//		Connection con = null; 
//		PreparedStatement pstm = null;
//		
//		try
//		{
//			con = ds.getConnection();
//			pstm = con.prepareStatement(sql);
//			for(int i=0; i<params.length; i++)
//			{
//				pstm.setObject(i+1,params[i]);
//			}
//			
//			return pstm.executeUpdate();			
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//		finally
//		{
//			try
//			{
//				if( pstm!=null ) pstm.close();
//				if( con!=null ) con.close();
//			}
//			catch(Exception e2)
//			{
//				e2.printStackTrace();
//				throw new RuntimeException(e2);
//			}
//		}		
//		
//	}
//}

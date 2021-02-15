package jspexp.a03_database;
// jspexp.a03_database.A03_ShopDao
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import jspexp.z01_vo.Product2;

public class A03_ShopDao {
	// DAO: Database Access Object
	// 1. 데이터베이스 연결
	private Connection con;
	// 2. 대화
	private Statement stmt;
	private PreparedStatement pstmt;
	// 3. 결과값 받는 객체
	private ResultSet rs;

	// # 연결 처리 기능 메서드
	public void setCon() throws SQLException {
		// 1. driver 메모리 로딩
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// 2. 특정 서버
		// - 접속 정보: 드라이버명:@ip:port:sid
		String info = "jdbc:oracle:thin:@localhost:1521:xe";
		// 드라이버 매니저 객체를 통해 Connection 객체를 생성
		con = DriverManager.getConnection(info, "scott", "tiger");
		System.out.println("접속 성공");
	}

	/*
	 * -- 등록 sql INSERT INTO product2 VALUES (prod2_seq.nextval, '사과', 2000, 100,
	 * sysdate,'좋은농원', sysdate, '홍길동'); -- 조회 sql SELECT * FROM product2 WHERE name
	 * LIKE '%'||''||'%' AND price BETWEEN 0 AND 99999 ORDER BY pno DESC; -- 상세 sql
	 * SELECT * FROM product2 WHERE pno = 10000;
	 */
	/*
	 * - 조회메서드 public ArrayList<ShopProd> shopList(ShopProd sch)
	 */
	public ArrayList<Product2> shopList(Product2 sch) {
		ArrayList<Product2> list = new ArrayList<Product2>();
		try {
			setCon();
			String sql = "SELECT * FROM product2 WHERE name LIKE '%'||'" + sch.getName() + "'||'%' AND price BETWEEN "
					+ sch.getFr_price() + " AND " + sch.getTo_price() + " ORDER BY pno DESC";
			System.out.println("# 조회리스트 #");
			System.out.println(sql);
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			/*
			 * Product2(int pno, String name, int price, int cnt, Date credte, String
			 * company, Date incomedte, String inmanager)
			 */
			while (rs.next()) {
				list.add(new Product2(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getDate(5),
						rs.getString(6), rs.getDate(7), rs.getString(8)));
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB 에러: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("일반 에러: " + e.getMessage());
		}
		return list;
	}

	/*
	 * - 등록메서드 public insertProduct(ShopProd ins)
	 */
	public void insertProduct(Product2 ins) {
		try {
			setCon();
			con.setAutoCommit(false);
			String sql = "INSERT INTO product2 VALUES (prod2_seq.nextval, '" + ins.getName() + "', " + ins.getPrice()
					+ ", " + ins.getCnt() + ", sysdate,'" + ins.getCompany() + "', sysdate, '" + ins.getInmanager()
					+ "')";
			System.out.println("# 등록처리 #");
			System.out.println(sql);
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			con.commit();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("입력 에러: " + e.getMessage());
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println("일반 에러: " + e.getMessage());
		}
	}

	/*
	 * - 단일데이터 메서드 public ShopProd getProd(int prono)
	 */
	public Product2 getProd(int prono) {
		Product2 pro = null;
		try {
			setCon();
			String sql = "SELECT * FROM product2 WHERE pno = " + prono;
			System.out.println("# 상세조회 #");
			System.out.println(sql);
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				pro = new Product2(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getDate(5),
						rs.getString(6), rs.getDate(7), rs.getString(8));
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("상세조회 에러: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("일반 예외: " + e.getMessage());
		}
		return pro;
	}

	public static void main(String[] args) {
		A03_ShopDao dao = new A03_ShopDao();
		dao.shopList(new Product2("", 1000, 9999));
		dao.insertProduct(new Product2(0, "딸기", 12000, 50, null, "딸기마을", null, "딸기맨"));
		dao.getProd(10000);
	}
}

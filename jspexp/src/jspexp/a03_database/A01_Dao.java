package jspexp.a03_database;

import java.sql.*;
import java.util.*;

import jspexp.a00_exp.z01_vo.Member5;
import jspexp.z01_vo.*;

public class A01_Dao { // DAO: Database Access Object
	// 1. 데이터베이스 연결
	private Connection con;
	// 2. 대화
	private Statement stmt;
	private PreparedStatement pstmt; // 0215 수업
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
		 1. SQL 작성
		 2. VO 객체 생성
		 3. 기능 메서드 선언
		 	1) 요청에 의한 입력: 매개변수로 활용
		 	2) 데이터의 결과에 따라 리턴값 지정
		 		- insert, update, delete: void 사용
		 			ex) public void insertEmp(Emp ins)
		 		- 단위 변수나 한 개의 데이터
		 			> 회원 등록 여부: SELECT * FROM member where ...
		 				public boolean void isMember(String id, String pass)
		 			> 상품 개수: SELECT count(*) FROM member where ...
		 				public int memCount(Member sch)
		 			> 회원 상세 정보: SELECT * FROM member where id=@@@
		 				public Member getMember(String id)
		 		- 여러 개의 데이터
		 			ex)
		 			> 공지사항
		 				public ArrayList<Board> boardList(Board sch)
		 			> 회원정보리스트
		 				public ArrayList<Member> mlist(Member sch)
		 		
		 */
		
		
		
		// 조회(매개변수 없는 것)
		public ArrayList<Emp> empList(){
			ArrayList<Emp> list = new ArrayList<Emp>();
			try {
	//			1. 공통 연결 메서드 호출
				setCon();
	//			2. Statement 객체 생성(연결객체 - Connection)
				String sql = "SELECT * FROM emp2 ORDER BY empno desc";
				stmt = con.createStatement();
	//			3. ResultSet 객체 생성(대화객체 - Statement)
				rs = stmt.executeQuery(sql);
				/*
				 * System.out.println(rs.next()); // 1행에 데이터가 있는지 여부 확인 및 사용할 준비 //
				 * rs.get데이터유형(컬럼의 순서) System.out.println("1행 1열: " + rs.getInt(1));
				 * System.out.println("1행 2열: " + rs.getString(2));
				 * 
				 * System.out.println(rs.next()); // 2행에 데이터가 있는지 여부를 확인 및 사용할 준비 //
				 * rs.get데이터유형(컬럼명) System.out.println("2행 JOB열: " + rs.getString("JOB"));
				 * System.out.println("2행 SAL열: " + rs.getDouble("SAL"));
				 */
				
				 int cnt=1; while(rs.next()) { 
				 /* System.out.print(cnt+++":"+rs.getInt(1)+"\t");
				 * // 1을 "empno"로 바꿔도 같은 결과 System.out.print(rs.getString("ename")+"\t");
				 * System.out.print(rs.getString("job")+"\t");
				 * System.out.print(rs.getInt("mgr")+"\t");
				 * System.out.print(rs.getDate("hiredate")+"\t");
				 * System.out.print(rs.getDouble("sal")+"\t");
				 * System.out.print(rs.getDouble("comm")+"\t");
				 * System.out.print(rs.getInt("deptno")+"\n"); }
				 */
					// 1. 객체 생성과 할당
					//		int empno, String ename, String job, int mgr, Date hiredate, double sal, double comm, int deptno
					Emp e = new Emp(rs.getInt("empno"), rs.getString(2), rs.getString(3),
							rs.getInt(4), rs.getDate("hiredate"), rs.getDouble(6),
							rs.getDouble(7), rs.getInt(8));
					// 2. ArrayList에 할당
					list.add(e);
				}
				System.out.println("객체의 개수: "+list.size());
				System.out.println("두 번째의 행의 ename: "+list.get(1).getEname());
	//				> next(): 행단위 변경
	//				> getXXX("컬럼명"): 열단위 호출
	//				==> 1개의 데이터인 경우: VO(단일)
	//				==> 다중행단위 여러 데이터인 경우: ArrayList<VO>, 마지막에 객체의 참조변수 return;
	//			4. 자원의 해제
				rs.close();
				stmt.close();
				con.close();
	//			5. 예외 처리
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return list;
		}
	/*
	 1. SQL 작성
	 2. VO 객체 생성
	 3. 기능 메서드 선언
	 	1) 요청에 의한 입력: 매개변수로 활용
	 	2) 데이터의 결과에 따라 리턴값 지정
	 		- insert, update, delete: void 사용
	 			ex) public void insertEmp(Emp ins)
	 		- 단위 변수나 한 개의 데이터
	 			> 회원 등록 여부: SELECT * FROM member where ...
	 				public boolean void isMember(String id, String pass)
	 			> 상품 개수: SELECT count(*) FROM member where ...
	 				public int memCount(Member sch)
	 			> 회원 상세 정보: SELECT * FROM member where id=@@@
	 				public Member getMember(String id)
	 		- 여러 개의 데이터
	 			ex)
	 			> 공지사항
	 				public ArrayList<Board> boardList(Board sch)
	 			> 회원정보리스트
	 				public ArrayList<Member> mlist(Member sch)
	 		
	 */
	
	
	
	// 조회(매개변수 없는 것)
	public ArrayList<Emp> empList(String ename, String job){
		ArrayList<Emp> list = new ArrayList<Emp>();
		try {
//			1. 공통 연결 메서드 호출
			setCon();
//			2. Statement 객체 생성(연결객체 - Connection)
			String sql = "SELECT * FROM emp2\r\n"
					+ "WHERE ename LIKE '%'||upper('"+ename+"')||'%'\r\n"
					+ "AND job LIKE '%'||upper('"+job+"')||'%'";
			stmt = con.createStatement();
//			3. ResultSet 객체 생성(대화객체 - Statement)
			rs = stmt.executeQuery(sql);
			/*
			 * System.out.println(rs.next()); // 1행에 데이터가 있는지 여부 확인 및 사용할 준비 //
			 * rs.get데이터유형(컬럼의 순서) System.out.println("1행 1열: " + rs.getInt(1));
			 * System.out.println("1행 2열: " + rs.getString(2));
			 * 
			 * System.out.println(rs.next()); // 2행에 데이터가 있는지 여부를 확인 및 사용할 준비 //
			 * rs.get데이터유형(컬럼명) System.out.println("2행 JOB열: " + rs.getString("JOB"));
			 * System.out.println("2행 SAL열: " + rs.getDouble("SAL"));
			 */
			
			 int cnt=1; while(rs.next()) { 
			 /* System.out.print(cnt+++":"+rs.getInt(1)+"\t");
			 * // 1을 "empno"로 바꿔도 같은 결과 System.out.print(rs.getString("ename")+"\t");
			 * System.out.print(rs.getString("job")+"\t");
			 * System.out.print(rs.getInt("mgr")+"\t");
			 * System.out.print(rs.getDate("hiredate")+"\t");
			 * System.out.print(rs.getDouble("sal")+"\t");
			 * System.out.print(rs.getDouble("comm")+"\t");
			 * System.out.print(rs.getInt("deptno")+"\n"); }
			 */
				// 1. 객체 생성과 할당
				//		int empno, String ename, String job, int mgr, Date hiredate, double sal, double comm, int deptno
				Emp e = new Emp(rs.getInt("empno"), rs.getString(2), rs.getString(3),
						rs.getInt(4), rs.getDate("hiredate"), rs.getDouble(6),
						rs.getDouble(7), rs.getInt(8));
				// 2. ArrayList에 할당
				list.add(e);
			}
			System.out.println("객체의 개수: "+list.size());
			System.out.println("두 번째의 행의 ename: "+list.get(1).getEname());
//				> next(): 행단위 변경
//				> getXXX("컬럼명"): 열단위 호출
//				==> 1개의 데이터인 경우: VO(단일)
//				==> 다중행단위 여러 데이터인 경우: ArrayList<VO>, 마지막에 객체의 참조변수 return;
//			4. 자원의 해제
			rs.close();
			stmt.close();
			con.close();
//			5. 예외 처리
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	
	// SELECT * FROM emp2 WHERE empno=7369
	
		// SELECT * FROM emp2 WHERE empno=7369
		
	/*
		 1. SQL 작성
		 2. VO 객체 생성
		 3. 기능 메서드 선언
		 	1) 요청에 의한 입력: 매개변수로 활용
		 	2) 데이터의 결과에 따라 리턴값 지정
		 		- insert, update, delete: void 사용
		 			ex) public void insertEmp(Emp ins)
		 		- 단위 변수나 한 개의 데이터
		 			> 회원 등록 여부: SELECT * FROM member where ...
		 				public boolean void isMember(String id, String pass)
		 			> 상품 개수: SELECT count(*) FROM member where ...
		 				public int memCount(Member sch)
		 			> 회원 상세 정보: SELECT * FROM member where id=@@@
		 				public Member getMember(String id)
		 		- 여러 개의 데이터
		 			ex)
		 			> 공지사항
		 				public ArrayList<Board> boardList(Board sch)
		 			> 회원정보리스트
		 				public ArrayList<Member> mlist(Member sch)
		 		
		 */
		
		
		
		// 조회(매개변수 없는 것)

/*
 # PreparedStatement 객체 활용하기
 1. SQL의 틀을 미리 정해 놓고 나중에 값을 지정하는 방식
 	select * from emp
 	where ename like '%'||?||'%'
 	and job like '%'||?||'%'
 	pstmt.setString(1, "홍"); >> ?의 순서 1부터 붙여서 사용
 	pstmt.setStirng(2, "A");
 2. 왜 사용하는가?
 	1) SQL injection을 막기 위해 사용
 	2) DB 서버의 SQL 해석 속도를 향상시켜 빠른 처리를 한다.
 */
		public ArrayList<Emp> empList2(String ename, String job){
			ArrayList<Emp> list = new ArrayList<Emp>();
			try {
	//			1. 공통 연결 메서드 호출
				setCon();
	//			2. Statement 객체 생성(연결객체 - Connection)
				String sql = "SELECT * FROM emp2\r\n"
						+ "WHERE ename LIKE '%'||upper( ? )||'%'\r\n"
						+ "AND job LIKE '%'||upper( ? )||'%' ORDER BY empno desc";
				System.out.println(sql);
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, ename);
				pstmt.setString(2, job);				
	//			stmt = con.createStatement();
	//			3. ResultSet 객체 생성(대화객체 - Statement)
				rs = pstmt.executeQuery();
				int cnt=1; while(rs.next()) { 
					Emp e = new Emp(rs.getInt("empno"), rs.getString(2), rs.getString(3),
							rs.getInt(4), rs.getDate("hiredate"), rs.getDouble(6),
							rs.getDouble(7), rs.getInt(8));					
					list.add(e);
				}
				System.out.println("객체의 개수: "+list.size());
				System.out.println("두 번째의 행의 ename: "+list.get(1).getEname());
	//				> next(): 행단위 변경
	//				> getXXX("컬럼명"): 열단위 호출
	//				==> 1개의 데이터인 경우: VO(단일)
	//				==> 다중행단위 여러 데이터인 경우: ArrayList<VO>, 마지막에 객체의 참조변수 return;
	//			4. 자원의 해제
				rs.close();
				pstmt.close();
				con.close();
	//			5. 예외 처리
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return list;
		}



		public ArrayList<Emp> empList(String job){
			ArrayList<Emp> list = new ArrayList<Emp>();
			try {
	//			1. 공통 연결 메서드 호출
				setCon();
	//			2. Statement 객체 생성(연결객체 - Connection)
				String sql = "SELECT * FROM emp2 ORDER BY empno desc";
				stmt = con.createStatement();
	//			3. ResultSet 객체 생성(대화객체 - Statement)
				rs = stmt.executeQuery(sql);
				/*
				 * System.out.println(rs.next()); // 1행에 데이터가 있는지 여부 확인 및 사용할 준비 //
				 * rs.get데이터유형(컬럼의 순서) System.out.println("1행 1열: " + rs.getInt(1));
				 * System.out.println("1행 2열: " + rs.getString(2));
				 * 
				 * System.out.println(rs.next()); // 2행에 데이터가 있는지 여부를 확인 및 사용할 준비 //
				 * rs.get데이터유형(컬럼명) System.out.println("2행 JOB열: " + rs.getString("JOB"));
				 * System.out.println("2행 SAL열: " + rs.getDouble("SAL"));
				 */
				
				 int cnt=1; while(rs.next()) { 
				 /* System.out.print(cnt+++":"+rs.getInt(1)+"\t");
				 * // 1을 "empno"로 바꿔도 같은 결과 System.out.print(rs.getString("ename")+"\t");
				 * System.out.print(rs.getString("job")+"\t");
				 * System.out.print(rs.getInt("mgr")+"\t");
				 * System.out.print(rs.getDate("hiredate")+"\t");
				 * System.out.print(rs.getDouble("sal")+"\t");
				 * System.out.print(rs.getDouble("comm")+"\t");
				 * System.out.print(rs.getInt("deptno")+"\n"); }
				 */
					// 1. 객체 생성과 할당
					//		int empno, String ename, String job, int mgr, Date hiredate, double sal, double comm, int deptno
					Emp e = new Emp(rs.getInt("empno"), rs.getString(2), rs.getString(3),
							rs.getInt(4), rs.getDate("hiredate"), rs.getDouble(6),
							rs.getDouble(7), rs.getInt(8));
					// 2. ArrayList에 할당
					list.add(e);
				}
				System.out.println("객체의 개수: "+list.size());
				System.out.println("두 번째의 행의 ename: "+list.get(1).getEname());
	//				> next(): 행단위 변경
	//				> getXXX("컬럼명"): 열단위 호출
	//				==> 1개의 데이터인 경우: VO(단일)
	//				==> 다중행단위 여러 데이터인 경우: ArrayList<VO>, 마지막에 객체의 참조변수 return;
	//			4. 자원의 해제
				rs.close();
				stmt.close();
				con.close();
	//			5. 예외 처리
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return list;
		}



	// ex1) Dept를 조회하는 메서드 선언 Dept VO 객체 활용
	public ArrayList<Dept> deptList(){
		ArrayList<Dept> dlist = new ArrayList<Dept>();
		// 1. 연결
		try {
			setCon();
			// 2. 대화
			String sql = "SELECT * FROM DEPT2";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			// 3. 결과
			int cnt=1;
			while(rs.next()) { // 행단위 변경(행이 있을 때까지)
			
				System.out.print(cnt+++":"+rs.getInt("deptno")+"\t");
				System.out.print(rs.getString("dname")+"\t");
				System.out.print(rs.getString("loc")+"\n");
				dlist.add(new Dept(rs.getInt("deptno"), rs.getString(2), rs.getString(3)));
			}
			System.out.println("객체의 개수: "+dlist.size());
			System.out.println("두 번째의 행의 ename: "+dlist.get(1).getDname());
			// 4. 자원해제
			rs.close();
			stmt.close();
			con.close();
		// 5. 예외처리
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return dlist;
	}
	
	public ArrayList<Dept> deptList(Dept sch){
		ArrayList<Dept> dlist = new ArrayList<Dept>();
		// 1. 연결
		try {
			setCon();
			// 2. 대화 SQL
			String sql="SELECT * FROM dept \r\n"
					+ "WHERE dname LIKE '%'||'"+sch.getDname()+"'||'%'\r\n"
					+ "AND loc LIKE '%'||'"+sch.getLoc()+"'||'%'";
			stmt = con.createStatement();			
			// 3. 결과
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				dlist.add(new Dept(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
			System.out.println("데이터 크기: "+dlist.size());
			// 4. 자원해제
			rs.close();
			stmt.close();
			con.close();
			// 5. 예외처리
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("# DB 예외 처리 #");
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("# 기타 예외 처리 #");
			System.out.println(e.getMessage());
		}
		
		return dlist;
	}
	

// SELECT * FROM emp2 WHERE empno=7369
		
	/*
		 1. SQL 작성
		 2. VO 객체 생성
		 3. 기능 메서드 선언
		 	1) 요청에 의한 입력: 매개변수로 활용
		 	2) 데이터의 결과에 따라 리턴값 지정
		 		- insert, update, delete: void 사용
		 			ex) public void insertEmp(Emp ins)
		 		- 단위 변수나 한 개의 데이터
		 			> 회원 등록 여부: SELECT * FROM member where ...
		 				public boolean void isMember(String id, String pass)
		 			> 상품 개수: SELECT count(*) FROM member where ...
		 				public int memCount(Member sch)
		 			> 회원 상세 정보: SELECT * FROM member where id=@@@
		 				public Member getMember(String id)
		 		- 여러 개의 데이터
		 			ex)
		 			> 공지사항
		 				public ArrayList<Board> boardList(Board sch)
		 			> 회원정보리스트
		 				public ArrayList<Member> mlist(Member sch)
		 		
		 */
		
		
		
		public ArrayList<Dept> deptList2(String dname, String loc){
		ArrayList<Dept> dlist = new ArrayList<Dept>();
		// 1. 연결
		try {
			setCon();
			// 2. 대화 SQL
			String sql="SELECT * FROM dept \r\n"
					+ "WHERE dname LIKE '%'||?||'%'\r\n"
					+ "AND loc LIKE '%'||?||'%'";
			System.out.println(sql);
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dname);
			pstmt.setString(2, loc);
			
			// 3. 결과
			rs = pstmt.executeQuery();
			while(rs.next()) {
				dlist.add(new Dept(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
			System.out.println("데이터 크기: "+dlist.size());
			// 4. 자원해제
			rs.close();
			pstmt.close();
			con.close();
			// 5. 예외처리
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("# DB 예외 처리 #");
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("# 기타 예외 처리 #");
			System.out.println(e.getMessage());
		}
		
		return dlist;
	}



	/*
-- ex1) 하위 구문을 통해서 만들어질 VO와 DAO단에 들어갈 메서드를 선언
-- VO 포함 출력 int deptno, double msal, double asal
-- 		  하나의 데이터일 경우 메서드 매개변수로 double msal 사용
SELECT deptno, max(sal) msal, avg(sal) asal
FROM emp2
GROUP BY DEPTNO 
HAVING max(sal) >= 3000;
 */
	public ArrayList<Emp3> elist(int msal){
		ArrayList<Emp3> elist = null;
		return elist;
	}
/*
-- ex2) String ename, Date hiredate, int workdate
--		메서드 매개변수로 int fromDte, int toDate 사용
SELECT ename, hiredate, floor(MONTHS_BETWEEN(sysdate, hiredate)) workdate
FROM emp2
WHERE floor(MONTHS_BETWEEN(sysdate, hiredate)) BETWEEN 470 AND 480;
 */
	public ArrayList<Emp4> elist(int fromDte, int toDate){
		ArrayList<Emp4> emplist = null;
		return emplist;
	}
/*
-- ex3) empno, ... , int part
--		메서드 매개변수로 int part 사용
SELECT e.*, mod(empno, 2) part
FROM emp2 e
WHERE mod(empno,2)=0;
 */
	public ArrayList<Emp5> elist2(int part){
		ArrayList<Emp5> emplist = null;
		return emplist;
	}
	/*
		 1. SQL 작성
		 2. VO 객체 생성
		 3. 기능 메서드 선언
		 	1) 요청에 의한 입력: 매개변수로 활용
		 	2) 데이터의 결과에 따라 리턴값 지정
		 		- insert, update, delete: void 사용
		 			ex) public void insertEmp(Emp ins)
		 		- 단위 변수나 한 개의 데이터
		 			> 회원 등록 여부: SELECT * FROM member where ...
		 				public boolean void isMember(String id, String pass)
		 			> 상품 개수: SELECT count(*) FROM member where ...
		 				public int memCount(Member sch)
		 			> 회원 상세 정보: SELECT * FROM member where id=@@@
		 				public Member getMember(String id)
		 		- 여러 개의 데이터
		 			ex)
		 			> 공지사항
		 				public ArrayList<Board> boardList(Board sch)
		 			> 회원정보리스트
		 				public ArrayList<Member> mlist(Member sch)
		 */
	public ArrayList<JobSalary> jobSalList(int salary){
		ArrayList<JobSalary> jlist = new ArrayList<JobSalary>();
		try {
			setCon();
			String sql = "SELECT job, count(*) 사원수, round(avg(sal)) avg\r\n"
					+ "FROM emp2\r\n"
					+ "GROUP BY job\r\n"
					+ "HAVING ROUND(avg(sal))>=2000\r\n"
					+ "ORDER BY job";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				jlist.add(new JobSalary(rs.getString(1),rs.getInt(2),rs.getInt(3)));
			}
			System.out.println("데이터 크기: "+jlist.size());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("# DB 예외 처리 #");
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("# 기타 예외 처리 #");
			System.out.println(e.getMessage());
		}
		
		return jlist;
	}
		
		
		/*
		 1. SQL 작성
		 2. VO 객체 생성
		 3. 기능 메서드 선언
		 	1) 요청에 의한 입력: 매개변수로 활용
		 	2) 데이터의 결과에 따라 리턴값 지정
		 		- insert, update, delete: void 사용
		 			ex) public void insertEmp(Emp ins)
		 		- 단위 변수나 한 개의 데이터
		 			> 회원 등록 여부: SELECT * FROM member where ...
		 				public boolean void isMember(String id, String pass)
		 			> 상품 개수: SELECT count(*) FROM member where ...
		 				public int memCount(Member sch)
		 			> 회원 상세 정보: SELECT * FROM member where id=@@@
		 				public Member getMember(String id)
		 		- 여러 개의 데이터
		 			ex)
		 			> 공지사항
		 				public ArrayList<Board> boardList(Board sch)
		 			> 회원정보리스트
		 				public ArrayList<Member> mlist(Member sch)
		 		
		 */
		
		
		
		// 0209 과제2번
		public ArrayList<Member5> memberList(String id, String name){
			ArrayList<Member5> mlist = new ArrayList<Member5>();
			try {
	//			1. 공통 연결 메서드 호출
				setCon();
	//			2. Statement 객체 생성(연결객체 - Connection)
				String sql = "SELECT * FROM member5\r\n"
						+ "WHERE id LIKE '%'||'"+id+"'||'%'\r\n"
						+ "AND name LIKE '%'||'"+name+"'||'%'";
				stmt = con.createStatement();
	//			3. ResultSet 객체 생성(대화객체 - Statement)
				rs = stmt.executeQuery(sql);				
				while(rs.next()) { 		
					// 1. 객체 생성과 할당
					Member5 m = new Member5(rs.getString(1), rs.getString(2), rs.getString(3),
							  rs.getInt(4), rs.getString(5), rs.getDate(6), rs.getString(7));
					// 2. ArrayList에 할당
					mlist.add(m);
				}
				System.out.println("객체의 개수: "+mlist.size());
				System.out.println("두 번째의 행의 ename: "+mlist.get(1).getName());
	//				> next(): 행단위 변경
	//				> getXXX("컬럼명"): 열단위 호출
	//				==> 1개의 데이터인 경우: VO(단일)
	//				==> 다중행단위 여러 데이터인 경우: ArrayList<VO>, 마지막에 객체의 참조변수 return;
	//			4. 자원의 해제
				rs.close();
				stmt.close();
				con.close();
	//			5. 예외 처리
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return mlist;
		}



	// 등록
	// 수정
	// 삭제
/*
 INSERT INTO emp2 VALUES (
 emp21_seq.nextval, '홍길동', '사원', 7780,
 TO_DATE('2021/05/01','YYYY/MM/DD'), 3500, 100, 10)	
 */
	public void insertEmp(Emp ins) {
		// 1. 접속 autocommit(false)
		try {
			setCon();
			// 2. 대화
			stmt = con.createStatement();
			String sql = "INSERT INTO emp2 VALUES (\r\n"
					+ " emp21_seq.nextval, '"+ins.getEname()+"', '"+ins.getJob()+"', "+ins.getMgr()+",\r\n"
					+ " TO_DATE('"+ins.getHiredate_s()+"'), "+ins.getSal()+", "+ins.getComm()+", "+ins.getDeptno()+")";
			System.out.println("###");
			System.out.println(sql);
			stmt.execute(sql); // update
			// 3. commit
			con.commit();
			stmt.close();
			con.close();
			
			
			// 4. 예외처리
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("db처리 에러");
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("기타 에러");
		}
		
	}
	
	public void insertDept(Dept dins) {
		try {
			setCon();
			// 2. 대화
			stmt = con.createStatement();
			String sql = "INSERT INTO dept2 VALUES ("+dins.getDeptno()+", '"+dins.getDname()+"', '"+dins.getLoc()+"')";
			System.out.println("###");
			System.out.println(sql);
			stmt.execute(sql); // update
			// 3. commit
			con.commit();
			stmt.close();
			con.close();
			
			
			// 4. 예외처리
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("db처리 에러");
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("기타 에러");
		}
	}
		
	// SELECT * FROM emp2 WHERE empno=7369

	public Emp getEmp(int empno){
		Emp emp = null;
		try {
			setCon();
			String sql = "SELECT * FROM emp2 WHERE empno= "+empno;
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
			emp = new Emp(rs.getInt("empno"),
							rs.getString(2), rs.getString(3),
							rs.getInt(4), rs.getDate("hiredate"),
							rs.getDouble(6), rs.getDouble(7),
							rs.getInt(8));
			}
			rs.close();
			pstmt.close();
			con.close();
//			5. 예외 처리
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("# DB 관련 에러 #");
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println("# 기타 에러 #");
			System.out.println(e.getMessage());
		}
			
		return emp;
	}



	// SELECT * FROM emp2 WHERE empno=7369
		
	/*
		 1. SQL 작성
		 2. VO 객체 생성
		 3. 기능 메서드 선언
		 	1) 요청에 의한 입력: 매개변수로 활용
		 	2) 데이터의 결과에 따라 리턴값 지정
		 		- insert, update, delete: void 사용
		 			ex) public void insertEmp(Emp ins)
		 		- 단위 변수나 한 개의 데이터
		 			> 회원 등록 여부: SELECT * FROM member where ...
		 				public boolean void isMember(String id, String pass)
		 			> 상품 개수: SELECT count(*) FROM member where ...
		 				public int memCount(Member sch)
		 			> 회원 상세 정보: SELECT * FROM member where id=@@@
		 				public Member getMember(String id)
		 		- 여러 개의 데이터
		 			ex)
		 			> 공지사항
		 				public ArrayList<Board> boardList(Board sch)
		 			> 회원정보리스트
		 				public ArrayList<Member> mlist(Member sch)
		 		
		 */
		
		
		
		public static void main(String[] args) {
		A01_Dao dao = new A01_Dao();
//		try {
//			dao.setCon();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		dao.empList("A","A");
//		dao.deptList(); // dept list 출력 되게 처리
//		dao.deptList(new Dept("",""));
//		dao.jobSalList(0);
//		dao.memberList("", "");
		
//		Emp ins = new Emp(0,"김길동3","대리",7800,"2010/12/12",4000.0,100.0,20);
//		dao.insertEmp(ins);
		
//		Dept dins = new Dept(50,"인사과","서울 서초구");
//		dao.insertDept(dins);
		
//		ArrayList<Emp> elist = dao.empList2("","");
//		System.out.println("크기: "+elist.size());
//		System.out.println("첫 번째: "+elist.get(0).getEname());

		ArrayList<Dept> dlist = dao.deptList2("","");
		System.out.println("크기: "+dlist.size());
		System.out.println("첫 번째: "+dlist.get(0).getDname());
	}
}

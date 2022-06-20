package kr.bit.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

// JDBC->myBatis, JPA
public class MemberDAO {
  
	private static SqlSessionFactory sqlSessionFactory;
	
	//초기화 블럭 -> 프로그램 실행시 딱 한번만 실행되는 코드 영역
	static {
		try { // config.xml이 없을수도있기떄문에  try catch 
		String resource = "kr/bit/mybatis/config.xml";  // 환경설정을 mybatis가 읽어서 Connection Pool 사용
		InputStream inputStream = Resources.getResourceAsStream(resource);  // InputStream을 이용해 resource를 읽음
		sqlSessionFactory =  new SqlSessionFactoryBuilder().build(inputStream); // 커넥션 풀을 만들어내고 sqlSessionFactory에 넣음
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	// 회원 전체 리스트 
	public List<MemberVO> memberList() {
		// Connection(연결) + Statement(전송)  -> SqlSession
		SqlSession session = sqlSessionFactory.openSession();
		List<MemberVO> list = session.selectList("memberList");
		session.close(); // 커넥션풀 반납
		return list;
	}
	
	// 회원 가입
	public int memberInsert(MemberVO vo) {
		SqlSession session = sqlSessionFactory.openSession();
		int cnt = session.insert("memberInsert", vo);
		session.commit();
		session.close(); // 커넥션풀 반납
		return cnt;
	}

	// 회원 삭제
	public int memberDelete(int num) {
		SqlSession session = sqlSessionFactory.openSession();
		int cnt = session.delete("memberDelete",num);
		session.commit();
		session.close(); // 커넥션풀 반납
		return cnt;
	}
	
	// 회원 상세보기
	public MemberVO memberContent(int num) {
		SqlSession session = sqlSessionFactory.openSession();
		MemberVO vo = session.selectOne("memberContent", num);
		session.close(); // 커넥션풀 반납
		return vo;
	}
	
	// 회원 업데이트
	public int memberUpdate(MemberVO vo) {
		SqlSession session = sqlSessionFactory.openSession();
		int cnt = session.update("memberUpdate", vo);
		session.commit();
		session.close(); // 커넥션풀 반납
		return cnt;
	}
	
	
}



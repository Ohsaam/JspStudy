package com.example.demo.dao;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.util.MyBatisCommonFactory;

public class TestDao extends JFrame{
	SqlSessionFactory sqlSessionFactory = null;
	SqlSession sqlSession  = null;
	public TestDao() {
		//메소드 호출을 통해서 객체를 주입받는 코드가 싱글톤 패턴에서 자주 등장하는 방식이다.
		sqlSessionFactory = MyBatisCommonFactory.getSqlSessionFactory();
	}
	public void currentTime() {
		System.out.println("currentTime 호출");
		String time = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			time = sqlSession.selectOne("currentTime");
			System.out.println(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void procEmpcursor() {
		Map<String,Object> pMap = new HashMap<>();
		try {
			sqlSession = sqlSessionFactory.openSession();
			sqlSession.selectOne("proc_empcursor", pMap);
			System.out.println(pMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//개발팀
	//공통팀 - 고급정보 - 간부회의
	public void procLogin1() {
		Map<String,Object> pMap = new HashMap<>();
		try {
			pMap.put("m_id", "kiwi");
			pMap.put("m_pw", "123");
			sqlSession = sqlSessionFactory.openSession();
			sqlSession.selectOne("proc_login1", pMap);
			System.out.println(pMap);
			//System.out.println(pMap.get("key"));
			Object keys[] = pMap.keySet().toArray();
			for(int i=0;i<keys.length;i++) {
				if("r_msg".equals(keys[i])) {
					JOptionPane.showMessageDialog(this, pMap.get("r_msg"));
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		TestDao td = new TestDao();
		td.currentTime();
		//td.procEmpcursor();
		//td.procLogin1();
	}
}

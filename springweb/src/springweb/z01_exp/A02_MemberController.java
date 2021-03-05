package springweb.z01_exp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jspexp.a03_database.A04_MemberDao;
import jspexp.z01_vo.Member;

@Controller
public class A02_MemberController {
	// http://localhost:7080/springweb/memberList.do
	@RequestMapping("/memberList.do")
	public String memberList(Member mem, Model d) {
		A04_MemberDao dao = new A04_MemberDao();
		if(mem.getId()==null) mem.setId("");
		if(mem.getName()==null) mem.setName("");
		d.addAttribute("mlist", dao.memberList(mem.getId(),mem.getName()));
		return "WEB-INF\\views\\z01_exp\\a02_0305_mlist.jsp";
	}
}

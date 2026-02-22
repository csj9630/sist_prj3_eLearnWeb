package kr.co.sist.common.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    private CommonMainService cms;

    @GetMapping("/main")
    public String main(HttpSession session, HttpServletRequest request, Model model) {
        List<CommonMainDomain> categories = cms.getCategoryList();
        List<CommonMainDomain> courses = cms.getCourseList();
        List<CommonMainDomain> topCourses = cms.getTopCoursesByStudentCount(3);

        model.addAttribute("categories", categories);
        model.addAttribute("courses", courses);
        model.addAttribute("topCourses", topCourses);

        return "common/main/mainPage";
    }
    
    // 인터셉터에서 넘겨준 주소로 에러페이지 띄운다.
    @RequestMapping("/common/err") 
    public String showErrorPage() {

        return "common/err/err"; // 실제 HTML/JSP 파일 위치
    }
}

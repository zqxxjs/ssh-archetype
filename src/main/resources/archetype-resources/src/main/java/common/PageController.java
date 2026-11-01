#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面示例：FreeMarker / JSP。
 */
@Controller
public class PageController {

	@GetMapping({ "/", "/index" })
	public String index() {
		return "ftl/index";
	}

	@GetMapping("/jsp")
	public String jsp(Model model) {
		model.addAttribute("message", "JSP 渲染成功");
		return "jsp/index";
	}

	@GetMapping("/login")
	public String login(HttpServletRequest request, Model model) {
		model.addAttribute("error", request.getParameter("error") != null);
		model.addAttribute("logout", request.getParameter("logout") != null);
		return "ftl/login";
	}

	@GetMapping("/secure")
	public String secure(Authentication authentication, HttpSession session,
			Model model) {
		model.addAttribute("username", authentication.getName());
		model.addAttribute("sessionId", session.getId());
		return "ftl/secure";
	}

}

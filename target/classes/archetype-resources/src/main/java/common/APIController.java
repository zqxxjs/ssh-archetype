#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.common;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class APIController {

	@GetMapping("/hello")
	public Map<String, Object> hello() {
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("code", 0);
		result.put("message", "JSON 返回成功");
		result.put("timestamp", new Date());
		return result;
	}

	/**
	 * 验证 Spring Session：请求会创建/复用 Redis 中的 Session，
	 * 响应头会写入 SESSION Cookie。
	 */
	@GetMapping("/session")
	public Map<String, Object> session(HttpSession session) {
		Integer count = (Integer) session.getAttribute("count");
		count = (count == null ? 1 : count + 1);
		session.setAttribute("count", count);

		Map<String, Object> result = new LinkedHashMap<>();
		result.put("sessionId", session.getId());
		result.put("count", count);
		result.put("newSession", session.isNew());
		return result;
	}

	@PostMapping("/list/{uuid}")
	public String list(@RequestBody String entity) {

		return entity;
	}

}

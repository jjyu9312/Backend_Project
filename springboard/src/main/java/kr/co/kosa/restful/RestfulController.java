package kr.co.kosa.restful;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/restful")
public class RestfulController {
	
	@RequestMapping("/rest1")
	public void rest1() {
		
	}
	
	@RequestMapping("/rest2")
	public String rest2() {
		return "restful/rest1";
	}
	
	@RequestMapping("/rest3")
	public String rest3(ModelMap model) {
		model.addAttribute("msg", "Hello restful");
		return "restful/rest1";
	}
	
	@RequestMapping("/{who}/world")
	public String rest4(@PathVariable String who,  Model model) {
		model.addAttribute("msg", who + ", " + "world");
		return "restful/rest1";
	}
	
	@RequestMapping("/{who}/{want]")
	public String rest4(@PathVariable String who, @PathVariable String want,  Model model) {
		model.addAttribute("msg", who + ", " + want + "가 하고 싶어요ㅜㅜ");
		return "restful/rest1";
	}
}

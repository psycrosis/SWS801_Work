package webstore;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@RequestMapping("/welcome")
	public String welcome(Model model)
	{
		model.addAttribute("greeting", "WElcome to the Webstore");
		model.addAttribute("tagline", "One only amainzg theing");
		
		return "welcome";
	}
	

}

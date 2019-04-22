package biosystem.web.controllers;

import biosystem.web.annotations.PageFooter;
import biosystem.web.annotations.PageNavbar;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends BaseController {
    @GetMapping("/")
    @PageNavbar
    @PageFooter
    public ModelAndView index() {
        return super.view("home/index");
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    @PageNavbar
    @PageFooter
    public ModelAndView home() {
        return super.view("home/home");
    }
}

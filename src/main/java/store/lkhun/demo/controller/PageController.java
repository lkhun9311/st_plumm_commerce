package store.lkhun.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String homepage() {
        return "index.html";
    }

    @GetMapping("/register")
    public String registerProductPage() {
        return "registerProduct.html";
    }
}

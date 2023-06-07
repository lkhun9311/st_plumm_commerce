package store.lkhun.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterProduct {

    @GetMapping("/register")
    public String registerProductPage() {
        return "registerProduct.html";
    }

}
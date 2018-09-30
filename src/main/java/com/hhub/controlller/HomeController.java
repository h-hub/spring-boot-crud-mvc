package com.hhub.controlller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.hhub.model.Book;
import com.hhub.service.BookService;

@Controller
public class HomeController {
	
	@Autowired
	private BookService bookService;
	
    @Value("${spring.application.name}")
    String appName;
 
    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        return "home";
    }
    
    @GetMapping("/addbook")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add_book";
    }
    
    @PostMapping("/addbook")
	public String abbBookSubmit(@ModelAttribute Book book) {
		System.out.println("User Page Requested");
		bookService.create(book);
		return "home";
	}
}
package com.example.socialnetwork.controllers;

import com.example.socialnetwork.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
@RequestMapping("/edit_page")
@SuppressWarnings("Duplicates")
public class EditPageController {

    @RequestMapping(value = "make_changes", method = RequestMethod.GET)
    public String changeInfoBlockForm(Model model) {
        model.addAttribute("author", new User());

        return "make_changes";
    }

    @RequestMapping(value = "make_changes", method = RequestMethod.POST)
    public String changeInfoBlockSubmit(@ModelAttribute User user, Model model, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "edit_page";
        }

        model.addAttribute("author", user);

        return "my_page";
    }
}

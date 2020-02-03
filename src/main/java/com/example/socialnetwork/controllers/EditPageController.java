package com.example.socialnetwork.controllers;

import com.example.socialnetwork.models.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/edit_page")
@SuppressWarnings("Duplicates")
public class EditPageController {

    @GetMapping("make_changes")
    public String changeInfoBlockForm(Model model) {
        model.addAttribute("author", new UserEntity());

        return "make_changes";
    }

    @PostMapping("make_changes")
    public String changeInfoBlockSubmit(@ModelAttribute UserEntity userEntity, Model model, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "edit_page";
        }

        model.addAttribute("author", userEntity);

        return "my_page";
    }
}

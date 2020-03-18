package com.example.socialnetwork.controllers;

import com.example.socialnetwork.models.News;
import com.example.socialnetwork.models.Note;
import com.example.socialnetwork.models.UserEntity;
import com.example.socialnetwork.repositories.NoteRepository;
import com.example.socialnetwork.service.FileService;
import com.example.socialnetwork.service.NoteService;
import com.example.socialnetwork.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/my_page")
public class MyPageController {

    @Value("${application.avatar-folder}")
    private String avatarFolder;

    @Value("${application.cover-folder}")
    private String coverFolder;

    private final UserService userService;
    private final FileService fileService;
    private final NoteService noteService;

    @Autowired
    public MyPageController(UserService userService,
                            FileService fileService,
                            NoteService noteService) {

        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
    }

    @GetMapping
    public String showMyPage(Model model) {
        log.info("showMyPage method called");

//        Map<String, Valute> valutes = userService.getAllValutes();
//        for(Map.Entry<String, Valute> valute : valutes.entrySet()) {
//            String name = valute.getKey();
//
//            if(name.equals("USD")) {
//                model.addAttribute("USD", Math.round(valute.getValue().getValue()));
//            }
//
//            if(name.equals("EUR")) {
//                model.addAttribute("EUR", Math.round(valute.getValue().getValue()));
//            }
//        }

        UserEntity currentUser = userService.getAnAuthorizedUser();

        model.addAttribute("id", currentUser.getId());
        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("name", currentUser.getName());
        model.addAttribute("surname", currentUser.getSurname());

        fileService.setDefaultAvatar();
        model.addAttribute("avatar", currentUser.getAvatar());

        fileService.setDefaultCover();
        model.addAttribute("cover", currentUser.getCover());

        model.addAttribute("email", currentUser.getEmail());
        model.addAttribute("birthday", currentUser.getDateOfBirth());
        model.addAttribute("city", currentUser.getCity());
        model.addAttribute("online", currentUser.getOnline());

        model.addAttribute("note", new Note());

        List<Note> notes = noteService.getAllNote();
        model.addAttribute("notes", notes);

        return "my_page";
    }

    @GetMapping("/upload_avatar_form")
    public String getUploadAvatarForm(Model model) {
        log.info("getUploadAvatarForm method called");
        model.addAttribute("avatar", userService.getAnAuthorizedUser().getAvatar());
        model.addAttribute("username", userService.getAnAuthorizedUser().getUsername());

        return "upload_avatar_form";
    }

    @PostMapping("/upload_avatar_form")
    public String submitUploadAvatarForm(@RequestParam MultipartFile avatar) {
        log.info("submitUploadAvatarForm method called");

        fileService.uploadImage(avatar, Paths.get(avatarFolder));
        userService.updateAvatarOfCurrentUser(avatar.getOriginalFilename());

        log.info("Avatar: " + avatar.getOriginalFilename() + " uploaded successfully");
        return "redirect:/my_page";
    }

    @GetMapping("/edit_page")
    public String editInfoForm(Model model) {
        log.info("editInfoForm method called");
        model.addAttribute("avatar", userService.getAnAuthorizedUser().getAvatar());
        model.addAttribute("username", userService.getAnAuthorizedUser().getUsername());

        return "edit_page";
    }

    @PostMapping("/edit_page")
    public String editInfoFormSubmit(@RequestParam String name,
                                     @RequestParam String surname,
                                     @RequestParam String email,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthday,
                                     @RequestParam String city) {
        log.info("editInfoFormSubmit method called");
        userService.updateUser(name,surname,email,birthday,city);

        log.info("Сurrent user updated page data: " + userService.getAnAuthorizedUser().toString());
        return "redirect:/my_page";
    }

    @GetMapping("/upload_cover_form")
    public String getUploadCoverForm(Model model) {
        log.info("getUploadCoverForm method called");
        model.addAttribute("avatar", userService.getAnAuthorizedUser().getAvatar());
        model.addAttribute("username", userService.getAnAuthorizedUser().getUsername());

        return "upload_cover_form";
    }

    @PostMapping("/upload_cover_form")
    public String submitUploadCoverForm(@RequestParam MultipartFile cover, Model model) {
        log.info(userService.getAnAuthorizedUser().getUsername() + " confirmed background change");

        fileService.uploadImage(cover, Paths.get(coverFolder));
        userService.updateCoverOfCurrentUser(cover.getOriginalFilename());

        return "redirect:/my_page";
    }

    @PostMapping("/delete_avatar")
    public String deleteAvatar() {
        log.info("User " + userService.getAnAuthorizedUser().getUsername() + " deleted avatar");
        fileService.deleteAvatar();
        return "redirect:/my_page";
    }

    @PostMapping("/new_note")
    public String postNewNote(@ModelAttribute Note note, Model model) {
        log.info("User " + userService.getAnAuthorizedUser().getUsername() + " добавил новую заметку");

        noteService.createNewNote(note);
        model.addAttribute("note", note);

        log.info("Метка: " + note.getId() + " добавлена");
        return "refirect:/my_page";
    }

    @DeleteMapping("/delete_note")
    public void deleteNote(Long id) {
        Note note = noteService.findById(id);
        noteService.deleteNote(note);
    }
}
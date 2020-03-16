package com.example.socialnetwork.service;


import com.example.socialnetwork.models.Note;
import com.example.socialnetwork.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserService userService;

    @Autowired
    public NoteService(NoteRepository noteRepository, UserService userService) {
        this.noteRepository = noteRepository;
        this.userService = userService;
    }

    public List<Note> getAllNote() {
        return noteRepository.findAll();
    }

    public Note findById(Long id) {
        return noteRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    public Note createNewNote(Note note) {
        note.setUser(userService.getAnAuthorizedUser());
        return noteRepository.save(note);
    }

    public void updateNote(Note newNote) {
        Note updatedNote = new Note();
        updatedNote.setDescription(newNote.getDescription());

        noteRepository.save(updatedNote);
    }

    public void deleteNote(Note note) {
        noteRepository.delete(note);
    }
}

package com.example.easynotes.controller;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.mode.Note;
import com.example.easynotes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {
    @Autowired
    NoteRepository noteRepository;

    @GetMapping("/notes")
    public List<Note> getAllNodes() {
        return noteRepository.findAll();
    }

    @PostMapping("/notes")
    public Note createNode(@Valid @RequestBody Note note) {
        return noteRepository.save(note);
    }

    @GetMapping("/notes/{id}")
    public Note getNode(@PathVariable(value = "id") Long id) {
        return noteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Note", "id", id));
    }

    @PutMapping("/notes/{id}")
    public Note updateNode(@PathVariable(value = "id") Long id, @Valid @RequestBody Note noteDetails) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Note", "id", id));
        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());
        note.setAge(noteDetails.getAge());
        Note updatedNote = noteRepository.save(note);
        return updatedNote;
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<?> deleteNode(@PathVariable(value = "id") Long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Note", "id", id));
        noteRepository.delete(note);
        return ResponseEntity.ok().build();
    }
}

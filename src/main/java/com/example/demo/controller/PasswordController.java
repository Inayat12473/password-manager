package com.example.demo.controller;

import com.example.demo.model.PasswordEntry;
import com.example.demo.service.PasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PasswordController {

    private final PasswordService service;

    public PasswordController(PasswordService service) {
        this.service = service;
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    // LOGIN
    @PostMapping("/init")
    public ResponseEntity<String> init(@RequestBody String masterPassword) {
        try {
            service.init(masterPassword.trim());
            return ResponseEntity.ok("OK");
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Wrong master password");
        }
    }

    // REGISTER – wipe old data and set new PIN
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody String masterPassword) {
        service.register(masterPassword.trim());
        return ResponseEntity.ok("Registered new master PIN (old data cleared)");
    }

    @GetMapping("/passwords")
    public List<PasswordEntry> getAll() {
        return service.getAll();
    }

    @PostMapping("/passwords")
    public String add(@RequestBody PasswordEntry entry) {
        service.add(entry);
        return "Added";
    }

    // NEW: update
    @PutMapping("/passwords/{index}")
    public String update(@PathVariable int index, @RequestBody PasswordEntry entry) {
        service.update(index, entry);
        return "Updated";
    }

    @DeleteMapping("/passwords/{index}")
    public String delete(@PathVariable int index) {
        service.delete(index);
        return "Deleted";
    }

    @GetMapping("/search")
    public List<PasswordEntry> search(@RequestParam String q) {
        return service.search(q);
    }
}

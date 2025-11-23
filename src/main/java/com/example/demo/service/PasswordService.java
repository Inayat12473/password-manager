package com.example.demo.service;

import com.example.demo.model.PasswordEntry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PasswordService {

    private final EncryptedFileService fileService;
    private List<PasswordEntry> entries = new ArrayList<>();
    private String masterPassword;

    public PasswordService(EncryptedFileService fileService) {
        this.fileService = fileService;
    }

    // LOGIN with existing master PIN
    public void init(String masterPassword) {
        this.masterPassword = masterPassword;
        this.entries = fileService.load(masterPassword);  // throws if wrong pin
    }

    // REGISTER: set NEW master PIN, wipe old data
    public void register(String masterPassword) {
        this.masterPassword = masterPassword;
        this.entries = new ArrayList<>();
        fileService.save(entries, masterPassword);
    }

    public List<PasswordEntry> getAll() {
        return entries;
    }

    public void add(PasswordEntry e) {
        entries.add(e);
        fileService.save(entries, masterPassword);
    }

    public void update(int index, PasswordEntry e) {
        if (index < 0 || index >= entries.size()) {
            throw new RuntimeException("Invalid index");
        }
        entries.set(index, e);
        fileService.save(entries, masterPassword);
    }

    public void delete(int index) {
        if (index < 0 || index >= entries.size()) {
            throw new RuntimeException("Invalid index");
        }
        entries.remove(index);
        fileService.save(entries, masterPassword);
    }

    public List<PasswordEntry> search(String query) {
        return entries.stream()
                .filter(e -> e.getSite().toLowerCase().contains(query.toLowerCase())
                        || e.getUsername().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }
}

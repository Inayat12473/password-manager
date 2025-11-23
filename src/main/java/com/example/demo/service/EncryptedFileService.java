package com.example.demo.service;

import com.example.demo.model.PasswordEntry;
import com.example.demo.security.EncryptionUtil;
import com.example.demo.security.EncryptionUtil.EncryptedData;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class EncryptedFileService {

    private static final String FILE_NAME = "passwords.txt";

    // Convert list to simple string
    private String serialize(List<PasswordEntry> list) {
        StringBuilder sb = new StringBuilder();
        for (PasswordEntry e : list) {
            sb.append(e.getSite()).append("\t")
              .append(e.getUsername()).append("\t")
              .append(e.getPassword()).append("\n");
        }
        return sb.toString();
    }

    private List<PasswordEntry> deserialize(String data) {
        List<PasswordEntry> list = new ArrayList<>();
        if (data == null || data.isBlank()) return list;

        for (String line : data.split("\n")) {
            String[] parts = line.split("\t");
            if (parts.length == 3) {
                PasswordEntry e = new PasswordEntry();
                e.setSite(parts[0]);
                e.setUsername(parts[1]);
                e.setPassword(parts[2]);
                list.add(e);
            }
        }
        return list;
    }

    public void save(List<PasswordEntry> entries, String masterPassword) {
        String plain = serialize(entries);
        try {
            EncryptedData ed = EncryptionUtil.encrypt(plain, masterPassword);

            try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
                out.println(Base64.getEncoder().encodeToString(ed.salt));
                out.println(Base64.getEncoder().encodeToString(ed.iv));
                out.println(Base64.getEncoder().encodeToString(ed.cipherText));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<PasswordEntry> load(String masterPassword) {
    File file = new File(FILE_NAME);

    // 🔴 IMPORTANT CHANGE: if file doesn't exist, vault is NOT initialized
    if (!file.exists()) {
        throw new RuntimeException("Vault not initialized");
    }

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {

        String saltLine = br.readLine();
        String ivLine = br.readLine();
        String cipherLine = br.readLine();

        EncryptedData ed = new EncryptedData(
                Base64.getDecoder().decode(saltLine),
                Base64.getDecoder().decode(ivLine),
                Base64.getDecoder().decode(cipherLine)
        );

        String plain = EncryptionUtil.decrypt(ed, masterPassword);
        return deserialize(plain);

    } catch (Exception e) {
        // wrong PIN or corrupted file
        throw new RuntimeException("Wrong master password or corrupted file");
    }
}

}

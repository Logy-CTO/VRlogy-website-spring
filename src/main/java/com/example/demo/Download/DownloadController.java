package com.example.demo.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Controller
public class DownloadController {

    private static final Logger logger = Logger.getLogger(DownloadController.class.getName());

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile() {
        Path path = Paths.get("downloads/VRlogy-main.zip").toAbsolutePath().normalize();
        logger.info("Trying to download file from path: " + path);
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                logger.severe("File not found or not readable: " + path);
                return ResponseEntity.status(404).body(null);
            }
        } catch (MalformedURLException e) {
            logger.severe("Error: " + e.getMessage());
            return ResponseEntity.status(500).body(null);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}

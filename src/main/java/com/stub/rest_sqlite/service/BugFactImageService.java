package com.stub.rest_sqlite.service;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class BugFactImageService {
    private static final int TEXT_X = 300;
    private static final int TEXT_Y = 150;
    private static final int RIGHT_MARGIN = 20;
    private static final int PARAGRAPH_SPACING = 2;

    private final BugFactService factService;
    private final ResourceLoader resourceLoader;
    private final String imageTemplate;

    public BugFactImageService(BugFactService factService, ResourceLoader resourceLoader,
            @Value("${bug-fact.image-template:file:Cool-Bug-Facts-Meme.jpg}") String imageTemplate) {
        this.factService = factService;
        this.resourceLoader = resourceLoader;
        this.imageTemplate = imageTemplate;
    }

    public byte[] renderRandomFact() {
        try {
            return render(factService.findRandom().getFact());
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to render bug fact image", exception);
        }
    }

    private byte[] render(String fact) throws IOException {
        Resource template = resourceLoader.getResource(imageTemplate);
        try (InputStream input = template.getInputStream(); ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(input);
            if (image == null) {
                throw new IOException("Image template is not a readable image: " + imageTemplate);
            }

            Graphics2D graphics = image.createGraphics();
            try {
                graphics.setFont(graphics.getFont().deriveFont(26f));
                graphics.setColor(Color.BLACK);
                drawFact(graphics, image, fact);
            } finally {
                graphics.dispose();
            }

            if (!ImageIO.write(image, "jpg", output)) {
                throw new IOException("JPEG writer is unavailable");
            }
            return output.toByteArray();
        }
    }

    private void drawFact(Graphics2D graphics, BufferedImage image, String fact) {
        FontMetrics metrics = graphics.getFontMetrics();
        int maxWidth = image.getWidth() - TEXT_X - RIGHT_MARGIN;
        int y = TEXT_Y;

        for (String paragraph : fact.split("\\R", -1)) {
            StringBuilder line = new StringBuilder();
            for (String word : paragraph.trim().split("\\s+")) {
                if (word.isEmpty()) {
                    continue;
                }
                String candidate = line.isEmpty() ? word : line + " " + word;
                if (!line.isEmpty() && metrics.stringWidth(candidate) > maxWidth) {
                    graphics.drawString(line.toString(), TEXT_X, y);
                    line.setLength(0);
                    line.append(word);
                    y += metrics.getHeight();
                } else {
                    line.setLength(0);
                    line.append(candidate);
                }
            }
            if (!line.isEmpty()) {
                graphics.drawString(line.toString(), TEXT_X, y);
                y += metrics.getHeight() * PARAGRAPH_SPACING;
            }
        }
    }
}

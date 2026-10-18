package storage;

import exceptions.InvalidFileFormatException;
import geometry2d.Circle;
import geometry2d.Figure;
import geometry2d.Rectangle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TextFigureStorage implements FigureStorage {

    @Override
    public List<Figure> read(Path path) throws IOException {
        List<Figure> figures = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) continue;

                try {
                    figures.add(parseLine(line));
                } catch (Exception e) {
                    System.out.println("Пропуск строки " + lineNumber + ": " + e.getMessage());
                    // уже прочитанные фигуры сохраняются
                }
            }
        }
        return figures;
    }

    private Figure parseLine(String line) throws InvalidFileFormatException {
        String[] parts = line.split("\\s+");
        try {
            if (parts[0].equals("CIRCLE") && parts.length == 2) {
                return new Circle(Double.parseDouble(parts[1]));
            } else if (parts[0].equals("RECTANGLE") && parts.length == 3) {
                return new Rectangle(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
            } else {
                throw new InvalidFileFormatException(0, "неизвестный формат: " + line);
            }
        } catch (InvalidFileFormatException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidFileFormatException(0, "ошибка разбора", e);
        }
    }

    @Override
    public void write(Path path, List<Figure> figures) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            for (Figure f : figures) {
                if (f instanceof Circle c) {
                    writer.write("CIRCLE " + c.getRadius());
                } else if (f instanceof Rectangle r) {
                    writer.write("RECTANGLE " + r.getWidth() + " " + r.getHeight());
                }
                writer.newLine();
            }
        }
    }
}
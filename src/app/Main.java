package app;

import geometry2d.Figure;
import geometry3d.Cylinder;
import storage.BinaryFigureStorage;
import storage.FigureStorage;
import storage.TextFigureStorage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Path textPath = Path.of("data/figures.txt");
        Path binaryPath = Path.of("data/figures.bin");

        FigureStorage textStorage = new TextFigureStorage();
        FigureStorage binaryStorage = new BinaryFigureStorage();

        // Чтение из текстового файла
        List<Figure> figures = null;
        try {
            figures = textStorage.read(textPath);
            System.out.println("Прочитано фигур: " + figures.size());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка чтения текстового файла: " + e.getMessage());
        } finally {
            System.out.println("Попытка чтения текстового файла завершена");
        }

        if (figures == null) {
            System.out.println("Не удалось прочитать фигуры. Завершение.");
            return;
        }

        // Вывод информации о каждой фигуре
        for (Figure f : figures) {
            System.out.println(f + " | площадь=" + f.area() + " | периметр=" + f.perimeter());
        }

        // Создание цилиндра на случайной основе
        if (figures.isEmpty()) {
            System.out.println("Список пуст, создание цилиндра пропущено.");
        } else {
            Figure base = figures.get(new Random().nextInt(figures.size()));
            double height = 10.0;
            Cylinder cylinder = new Cylinder(base, height);
            System.out.println("Цилиндр: " + cylinder);
            System.out.println("Объём цилиндра: " + cylinder.volume());
        }

        // Сохранение в бинарный файл
        try {
            binaryStorage.write(binaryPath, figures);
            System.out.println("Список успешно сохранён в бинарный файл.");
        } catch (IOException e) {
            System.out.println("Ошибка записи бинарного файла: " + e.getMessage());
        } finally {
            System.out.println("Попытка записи бинарного файла завершена");
        }

        // Проверка чтения повреждённого бинарного файла
        System.out.println("\n Проверка чтения повреждённого файла ");
        Path corruptedPath = Path.of("data/corrupted.bin");
        try {
            List<Figure> corrupted = binaryStorage.read(corruptedPath);
            System.out.println("Прочитано: " + corrupted.size() + " фигур");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка чтения повреждённого файла: " + e.getMessage());
        } finally {
            System.out.println("Попытка чтения повреждённого файла завершена");
        }
    }
}
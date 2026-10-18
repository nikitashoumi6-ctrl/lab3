package storage;

import geometry2d.Figure;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BinaryFigureStorage implements FigureStorage {

    @Override
    @SuppressWarnings("unchecked")
    public List<Figure> read(Path path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?> list) {
                List<Figure> figures = new ArrayList<>();
                for (Object item : list) {
                    if (item instanceof Figure f) {
                        figures.add(f);
                    } else {
                        throw new ClassNotFoundException("Эл-т списка не является Figure: " + item.getClass());
                    }
                }
                return figures;
            }
            throw new ClassNotFoundException("Файл не содержит список фигур");
        }
    }

    @Override
    public void write(Path path, List<Figure> figures) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
            oos.writeObject(new ArrayList<>(figures));
        }
    }
}
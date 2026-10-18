package storage;

import geometry2d.Figure;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FigureStorage {
    List<Figure> read(Path path) throws IOException, ClassNotFoundException;
    void write(Path path, List<Figure> figures) throws IOException;
}
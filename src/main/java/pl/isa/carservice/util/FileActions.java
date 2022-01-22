package pl.isa.carservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class FileActions<E> {

    private final ObjectMapper mapper;

    @Autowired
    public FileActions(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public List<E> readObjectListFromFile(String path, Class<E> elementClass) {
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, elementClass);
        try {
            return mapper.readValue(new FileReader(path), listType);
        } catch (IOException ex) {
            System.out.println("\n!!! Broken file !!!");
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<ArrayList<E>> readListOfObjectListsFromDir(String path, Class<E> elementClass) {
        File file = new File(path);
        ArrayList<File> fileBase = null;
        if (file.isDirectory()) {
            fileBase = new ArrayList<>(Arrays.asList(Objects.requireNonNull(file.listFiles())));
        }
        List<ArrayList<E>> objects = new ArrayList<>();
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, elementClass);
        if (fileBase != null) {
            fileBase.forEach(j -> {
                try {
                    objects.add(mapper.readValue(j, listType));
                } catch (IOException ex) {
                    System.out.println("\n!!! Broken file !!!");
                    ex.printStackTrace();
                }
            });
        }
        return objects;
    }

    public void writeObjectListToFile(String path, List<E> objectList) {
        try {
            mapper.writeValue(new FileWriter(path), objectList);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
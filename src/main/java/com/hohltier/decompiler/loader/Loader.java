package com.hohltier.decompiler.loader;

import com.hohltier.decompiler.controllers.ExplorerController;
import com.hohltier.decompiler.models.ArchiveModel;
import com.hohltier.decompiler.models.BaseModel;
import com.hohltier.decompiler.models.PackageModel;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@UtilityClass
public class Loader {

    @SneakyThrows
    public static void load(File file) {
        @Cleanup JarFile jar = new JarFile(file);

        List<JarEntry> entries = Collections.list(jar.entries());
        entries.sort((e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName()));

        ArchiveModel archive = new ArchiveModel(file.getName());
        for (JarEntry entry : entries) {

            // TODO
            if (entry.isDirectory()) {
                archive.getChildren().add(new PackageModel(entry.getName()));
            } else {

            }
            System.out.println(entry.getName());

        }
        ExplorerController.getINSTANCE().setArchive(archive);
    }

    // TODO
    private static void createPackages(List<JarEntry> entries, BaseModel baseModel, String path) {
        for (JarEntry entry : entries) {
            if (entry.isDirectory()) {
                String name = entry.getName();
                if (name.startsWith(path)) {
                    baseModel.getChildren().add(new PackageModel(name.substring(path.length())));
                }
            }
        }
    }

}
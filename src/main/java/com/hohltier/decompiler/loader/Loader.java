package com.hohltier.decompiler.loader;

import com.hohltier.decompiler.controllers.ExplorerController;
import com.hohltier.decompiler.models.ArchiveModel;
import com.hohltier.decompiler.models.PackageModel;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import java.io.File;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@UtilityClass
public class Loader {

    @SneakyThrows
    public static void load(File file) {
        @Cleanup JarFile jar = new JarFile(file);
        ArchiveModel archive = new ArchiveModel(file.getName());
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();

            // TODO
            if (entry.isDirectory()) {
                archive.add(new PackageModel(entry.getName()));
            } else {

            }
            System.out.println(entry.getName());

        }
        ExplorerController.getINSTANCE().setArchive(archive);
    }

}
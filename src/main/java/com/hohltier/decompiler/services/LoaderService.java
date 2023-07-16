package com.hohltier.decompiler.services;

import com.hohltier.decompiler.controllers.ExplorerController;
import com.hohltier.decompiler.models.ArchiveModel;
import com.hohltier.decompiler.models.BaseModel;
import com.hohltier.decompiler.models.FileModel;
import com.hohltier.decompiler.models.PackageModel;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@UtilityClass
public class LoaderService {

    @SneakyThrows
    public static void load(File file) {
        JarFile jar = new JarFile(file);
        Enumeration<JarEntry> entries = jar.entries();;
        ArchiveModel archive = new ArchiveModel(file.getName());

        // TODO: Sort
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            BaseModel packageModel = getChildByPath(archive, entry.getName());
            if (entry.isDirectory())
                packageModel.getChildren().add(new PackageModel(entry.getName()));
            else
                packageModel.getChildren().add(new FileModel(jar, entry));
        }

        ExplorerController.getINSTANCE().setArchive(archive);
    }

    private static BaseModel getChildByPath(@NotNull BaseModel baseModel, String path) {
        for (BaseModel child : baseModel.getChildren())
            if (child instanceof PackageModel)
                if (path.startsWith(child.getPath()))
                    return getChildByPath(child, path);
        return baseModel;
    }

}
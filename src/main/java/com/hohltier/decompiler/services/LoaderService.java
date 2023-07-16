package com.hohltier.decompiler.services;

import com.hohltier.decompiler.controllers.ExplorerController;
import com.hohltier.decompiler.models.ArchiveModel;
import com.hohltier.decompiler.models.BaseModel;
import com.hohltier.decompiler.models.FileModel;
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
public class LoaderService {

    @SneakyThrows
    public static void load(File file) {
        @Cleanup JarFile jar = new JarFile(file);

        List<JarEntry> entries = Collections.list(jar.entries());
        entries.sort((e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName()));

        ArchiveModel archive = new ArchiveModel(file.getName());
        for (JarEntry entry : entries) {
            BaseModel packageModel = getChildByPath(archive, entry.getName());
            if (entry.isDirectory())
                packageModel.getChildren().add(new PackageModel(entry.getName()));
            else
                packageModel.getChildren().add(new FileModel(entry.getName()));
        }

        ExplorerController.getINSTANCE().setArchive(archive);
    }

    private static BaseModel getChildByPath(BaseModel baseModel, String path) {
        for (BaseModel child : baseModel.getChildren())
            if (child instanceof PackageModel)
                if (path.startsWith(child.getPath()))
                    return getChildByPath(child, path);
        return baseModel;
    }

}
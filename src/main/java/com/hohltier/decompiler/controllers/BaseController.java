package com.hohltier.decompiler.controllers;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import java.awt.*;

public abstract class BaseController<T extends Component> {

    @Getter(AccessLevel.PROTECTED) private T view;

    @SneakyThrows
    public BaseController(T view) {
        this.view = view;
    }

    public Component getComponent() {
        return view;
    }

}
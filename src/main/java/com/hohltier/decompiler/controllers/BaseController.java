package com.hohltier.decompiler.controllers;

import lombok.Getter;
import lombok.SneakyThrows;
import java.awt.*;

public abstract class BaseController<T extends Component> {

    @Getter private T view;

    @SneakyThrows
    public BaseController(T view) {
        this.view = view;
    }

}
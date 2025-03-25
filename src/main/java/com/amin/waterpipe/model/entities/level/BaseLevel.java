package com.amin.waterpipe.model.entities.level;

import com.amin.waterpipe.model.entities.Map;

public abstract class BaseLevel {

    protected final Map _map;

    protected abstract Map mapInitialization();

    public BaseLevel() {
        this._map = mapInitialization();
    }

}

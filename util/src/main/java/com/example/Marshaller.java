package com.example;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

//would be nice if there was a way to generate this implicitly
public class Marshaller<Model, Entity> {

    Function<Entity, Model> toModel;

    public Marshaller(Function<Entity, Model> toModel)
    {
        this.toModel = toModel;
    }

    public Model convert(Entity e) {return toModel.apply(e);}
    public Optional<Model> convert(Optional<Entity> e) {return e.map(toModel);}
    public List<Model> convert(List<Entity> e) {return e.stream().map(toModel).toList();}
}

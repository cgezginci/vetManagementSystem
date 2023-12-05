package dev.patika.vetManagementSystem.core.config.modelmapper;

import org.modelmapper.ModelMapper;

public interface IModelMapperService {
    ModelMapper forRequest  ();
    ModelMapper forResponse ();
}

package com.caece.arbol.repository;

import com.caece.arbol.model.Arbol;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArbolRepository extends MongoRepository<Arbol, Long> {
    //COMENTARIO 3
}

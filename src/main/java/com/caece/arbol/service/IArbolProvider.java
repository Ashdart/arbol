package com.caece.arbol.service;

import com.caece.arbol.model.Arbol;
import com.caece.arbol.model.Nodo;
import com.caece.arbol.model.request.ArbolRequestDto;
import com.caece.arbol.model.request.NodoRequestDto;

import java.util.List;
import java.util.Optional;

public interface IArbolProvider {
    //COMENTARIO 4
    Arbol createTree(ArbolRequestDto arbolDto);
    Optional<Nodo> getRoot(Long id);
    Nodo getParent(Long arbolId, Long nodoId);
    Nodo getFirstChild(Long id, Long nodoId);
    Nodo getRightNode(Long arbolId, Long nodoId);
    Object getContent(Long arbolId, Long nodoId);
    List<Object> preOrderContent(Long id);
    void removeChild(Long arbolId, Long nodoId);
    void deleteArbol(Long id);
    void addChild(Long arboldId, Long nodoId, NodoRequestDto nodo);

}

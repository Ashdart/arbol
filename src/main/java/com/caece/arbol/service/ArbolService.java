package com.caece.arbol.service;

import com.caece.arbol.exception.ArbolAlreadyExistException;
import com.caece.arbol.exception.ArbolNotFoundException;
import com.caece.arbol.exception.ChildlessException;
import com.caece.arbol.exception.CreateArbolException;
import com.caece.arbol.exception.EmptyRootException;
import com.caece.arbol.exception.NoBrotherRightException;
import com.caece.arbol.exception.NodoNotFoundException;
import com.caece.arbol.model.Arbol;
import com.caece.arbol.model.Nodo;
import com.caece.arbol.model.request.ArbolRequestDto;
import com.caece.arbol.model.request.NodoRequestDto;
import com.caece.arbol.repository.ArbolRepository;
import com.caece.arbol.util.ModelUtil;
import io.swagger.models.Model;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ArbolService implements IArbolProvider {

    @Autowired
    private ArbolRepository arbolRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Arbol createTree(ArbolRequestDto arbolDto) {
        Optional<Arbol> tree = arbolRepository.findById(arbolDto.getId());
        if (tree.isPresent()) {
            throw new ArbolAlreadyExistException(ModelUtil.ARBOL_ALREADY_EXIST + arbolDto.getId());
        }
        try {
            Arbol arbol = modelMapper.map(arbolDto, Arbol.class);
            return arbolRepository.save(arbol);
        } catch (Exception e) {
            throw new CreateArbolException(ModelUtil.CREATE_TREE_ERROR, e);
        }
    }

    @Override
    public Optional<Nodo> getRoot(Long id) {
        Arbol arbol = arbolRepository.findById(id)
                .orElseThrow(() -> new ArbolNotFoundException(ModelUtil.ARBOL_NOT_FOUND_ERROR + id));
        return Optional.of(arbol.getRoot());
    }

    @Override
    public Nodo getParent(Long arbolId, Long nodoId) {
        Arbol arbol = arbolRepository.findById(arbolId)
                .orElseThrow(() -> new ArbolNotFoundException(ModelUtil.ARBOL_NOT_FOUND_ERROR + arbolId));

        if (isEmpty(arbol)) { throw new EmptyRootException(ModelUtil.EMPTY_ROOT); }
        Nodo searchedNode = new Nodo();
        searchNode(arbol.getRoot(), nodoId, searchedNode);
        if (Objects.isNull(searchedNode.getData())) {
            throw new NodoNotFoundException(ModelUtil.NODO_NOT_FOUND_ERROR + nodoId);
        }
        Nodo searchParentNode = new Nodo();
        searchNode(arbol.getRoot(), searchedNode.getParentId(), searchParentNode);

        return searchParentNode;
    }

    @Override
    public Nodo getFirstChild(Long arbolId, Long nodoId) {
        Arbol arbol = arbolRepository.findById(arbolId)
                .orElseThrow(() -> new ArbolNotFoundException(ModelUtil.ARBOL_NOT_FOUND_ERROR + arbolId));

        if (isEmpty(arbol)) { throw new EmptyRootException(ModelUtil.EMPTY_ROOT); }

        Nodo searchedNode = new Nodo();
        searchNode(arbol.getRoot(), nodoId, searchedNode);

        if (Objects.isNull(searchedNode.getData())) { throw new NodoNotFoundException(ModelUtil.NODO_NOT_FOUND_ERROR + nodoId); }

        if (Objects.isNull(searchedNode.getChildren()) || searchedNode.getChildren().isEmpty()) {
            throw new ChildlessException(ModelUtil.CHILDLESS);
        }

        return searchedNode.getChildren().get(0);
    }

    @Override
    public Nodo getRightNode(Long arbolId, Long nodoId) {
        Arbol arbol = arbolRepository.findById(arbolId)
                .orElseThrow(() -> new ArbolNotFoundException(ModelUtil.ARBOL_NOT_FOUND_ERROR + arbolId));
        Nodo brotherNode = null;

        if (isEmpty(arbol)) { throw new EmptyRootException(ModelUtil.EMPTY_ROOT); }

        Nodo searchedNode = new Nodo();
        searchNode(arbol.getRoot(), nodoId, searchedNode);

        if (Objects.isNull(searchedNode.getData())) {
            throw new NodoNotFoundException(ModelUtil.NODO_NOT_FOUND_ERROR + nodoId);
        }

        Nodo parentNode = new Nodo();
        searchNode(arbol.getRoot(), searchedNode.getParentId(), parentNode);

        for (int i=0; i < parentNode.getChildren().size(); i++) {
            if (parentNode.getChildren().get(i).getId().equals(nodoId)) {
                try {
                    brotherNode = parentNode.getChildren().get(++i);
                } catch (IndexOutOfBoundsException e) {
                    throw new NoBrotherRightException(ModelUtil.NO_BROTHER_RIGHT);
                }
            }
        }

        return brotherNode;
    }

    @Override
    public Object getContent(Long arbolId, Long nodoId) {
        Arbol arbol = arbolRepository.findById(arbolId)
                .orElseThrow(() -> new ArbolNotFoundException(ModelUtil.ARBOL_NOT_FOUND_ERROR + arbolId));
        Nodo searchedNode = new Nodo();

        if (isEmpty(arbol)){
            throw new EmptyRootException(ModelUtil.EMPTY_ROOT);
        } else if (arbol.getRoot().getId().equals(nodoId)){
            return arbol.getRoot().getData();
        } else {
            searchNode(arbol.getRoot(), nodoId, searchedNode);
        }

        if (Objects.isNull(searchedNode.getData())) { throw new NodoNotFoundException(ModelUtil.NODO_NOT_FOUND_ERROR + nodoId); }

        return searchedNode.getData();
    }

    @Override
    public List<Object> preOrderContent(Long id) {
        Arbol arbol = arbolRepository.findById(id)
                .orElseThrow(() -> new ArbolNotFoundException(ModelUtil.ARBOL_NOT_FOUND_ERROR + id));
        if (isEmpty(arbol)) { throw new EmptyRootException(ModelUtil.EMPTY_ROOT); }
        List<Object> data = new ArrayList<>();
        preOrderHelper(arbol.getRoot(), data);
        return data;
    }

    @Override
    @Transactional
    public void removeChild(Long arbolId, Long nodoId) {
        Arbol arbol = arbolRepository.findById(arbolId)
                .orElseThrow(() -> new ArbolNotFoundException(ModelUtil.ARBOL_NOT_FOUND_ERROR + arbolId));

        if (isEmpty(arbol)) { throw new EmptyRootException(ModelUtil.EMPTY_ROOT); }

        arbolRepository.delete(arbol);
        if (arbol.getRoot().getId().equals(nodoId)){
            arbol.setRoot(null);
        } else {
            removeChildHelper(arbol.getRoot(), nodoId);
        }
        arbolRepository.save(arbol);
    }

    @Override
    public void deleteArbol(Long id) {
        Arbol arbol = arbolRepository.findById(id)
                .orElseThrow(() -> new ArbolNotFoundException(ModelUtil.ARBOL_NOT_FOUND_ERROR + id));
        arbolRepository.delete(arbol);
    }

    @Override
    @Transactional
    public void addChild(Long arboldId, Long nodoId, NodoRequestDto nodo) {
        Arbol arbol = arbolRepository.findById(arboldId)
                .orElseThrow(() -> new ArbolNotFoundException(ModelUtil.ARBOL_NOT_FOUND_ERROR + arboldId));
        arbolRepository.delete(arbol);
        Nodo node = modelMapper.map(nodo, Nodo.class);

        if (isEmpty(arbol)) { arbol.setRoot(node); }

        if (arbol.getRoot().getId().equals(nodoId)) {
            node.setParentId(arbol.getRoot().getId());
            if (Objects.isNull(arbol.getRoot().getChildren())) {
                arbol.getRoot().setChildren(Collections.singletonList(node));
            } else {
                arbol.getRoot().getChildren().add(node);
            }
        } else {
            insertChildHelper(arbol.getRoot(), nodoId, node);
        }
        arbolRepository.save(arbol);
    }

    private void searchNode(Nodo nodo, Long id, Nodo searchedNode) {

        if (Objects.isNull(nodo)) { return; }

        if (nodo.getId().equals(id)) {
            searchedNode.setId(nodo.getId());
            searchedNode.setData(nodo.getData());
            searchedNode.setParentId(nodo.getParentId());
            searchedNode.setChildren(nodo.getChildren());
        }
        if (Objects.nonNull(nodo.getChildren())) {
            for (Nodo child : nodo.getChildren()) {
                searchNode(child, id, searchedNode);
            }
        }

    }

    private void insertChildHelper(Nodo nodo, Long id, Nodo saveNodo) {
        if (Objects.isNull(nodo)) { return; }

        if (nodo.getId().equals(id)) {
            saveNodo.setParentId(nodo.getId());
            if (Objects.isNull(nodo.getChildren()) || nodo.getChildren().isEmpty()) {
                nodo.setChildren(Collections.singletonList(saveNodo));
            } else {
                nodo.getChildren().add(saveNodo);
            }
        } else if (Objects.nonNull(nodo.getChildren())) {
            for (Nodo child : nodo.getChildren()) {
                insertChildHelper(child, id, saveNodo);
            }
        }
    }

    private void removeChildHelper(Nodo nodo, Long id) {
        if (Objects.isNull(nodo)) { return; }
        if (Objects.nonNull(nodo.getChildren())
                && !nodo.getChildren().removeIf(node -> node.getId().equals(id))) {
            for (Nodo child : nodo.getChildren()) {
                removeChildHelper(child, id);
            }
        }
    }

    private boolean isEmpty(Arbol arbol) {
        return Objects.isNull(arbol.getRoot());
    }

    private void preOrderHelper(Nodo root, List<Object> data) {
        if (Objects.isNull(root)) { return; }
        data.add(root.getData());
        if (Objects.nonNull(root.getChildren())){
            for (Nodo child : root.getChildren()){
                preOrderHelper(child, data);
            }
        }
    }

}

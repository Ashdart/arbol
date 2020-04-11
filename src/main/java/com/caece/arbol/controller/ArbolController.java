package com.caece.arbol.controller;

import com.caece.arbol.exception.ArbolNotFoundException;
import com.caece.arbol.model.Arbol;
import com.caece.arbol.model.Nodo;
import com.caece.arbol.model.request.ArbolRequestDto;
import com.caece.arbol.model.request.NodoRequestDto;
import com.caece.arbol.service.ArbolService;
import com.caece.arbol.util.SimpleResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tree")
@Validated
@Api(tags = "Arbol API", description = "This API has operations related to Arbol Controller")
public class ArbolController {

    @Autowired
    private ArbolService arbolService;

    @PostMapping("/")
    @ApiOperation("Create a tree base structure.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<Object> createTree(@RequestBody ArbolRequestDto arbolDto) {
        Arbol tree = arbolService.createTree(arbolDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/").build()
                .toUri();
        return ResponseEntity.created(location).body(tree);
    }

    @GetMapping("/{id}")
    @ApiOperation("Get the root of a tree")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<Nodo> getRoot(@NotNull @PathVariable Long id) {
        Optional<Nodo> node = arbolService.getRoot(id);
        return node.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent()
                        .build());
    }

    @GetMapping("/{arbolId}/parent/{nodoId}")
    @ApiOperation("Get the parent of a node")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<Nodo> getParent(@NotNull @PathVariable Long arbolId, @NotNull @PathVariable Long nodoId) {
        Nodo node = arbolService.getParent(arbolId, nodoId);
        return ResponseEntity.ok(node);
    }

    @GetMapping("/{arbolId}/childleft/{nodoId}")
    @ApiOperation("Get the first child more to the left of a node")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<Nodo> getFirstChild(@NotNull @PathVariable Long arbolId, @NotNull @PathVariable Long nodoId) {
        return ResponseEntity.ok(arbolService.getFirstChild(arbolId, nodoId));
    }

    @GetMapping("/{arbolId}/childright/{nodoId}")
    @ApiOperation("Get the right child of a node")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<Nodo> getRightNode(@NotNull @PathVariable Long arbolId, @NotNull @PathVariable Long nodoId) {
        return ResponseEntity.ok(arbolService.getRightNode(arbolId, nodoId));
    }

    @GetMapping("{arbolId}/node/{nodoId}")
    @ApiOperation("Get the content information of a node")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<Object> getContent(@NotNull @PathVariable Long arbolId, @NotNull @PathVariable Long nodoId) {
        Object data = arbolService.getContent(arbolId, nodoId);
        return ResponseEntity.ok(data);
    }

    @GetMapping("preorder/{id}")
    @ApiOperation("Get the content of a tree in mode PreOrder.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<List<Object>> preOrder(@NotNull @PathVariable Long id) {
        return ResponseEntity.ok(arbolService.preOrderContent(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a tree.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<Object> deleteArbol(@NotNull @PathVariable Long id) {
        arbolService.deleteArbol(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{arbolId}/child/{nodoId}")
    @ApiOperation("Remove a child node.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<Object> removeChild(@NotNull @PathVariable Long arbolId, @NotNull @PathVariable Long nodoId) {
        arbolService.removeChild(arbolId, nodoId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{arbolId}/child/{nodoId}")
    @ApiOperation("Insert a child node.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ResponseEntity<Object> addChild(@NotNull @PathVariable Long arbolId,
                                           @PathVariable Long nodoId,
                                           @RequestBody NodoRequestDto node) {
        arbolService.addChild(arbolId, nodoId, node);
        return ResponseEntity.noContent().build();
    }

}

package com.caece.arbol.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"children"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Nodos")
public class Nodo implements Serializable {

    @Id
    private Long id;
    private Long parentId;
    private List<Nodo> children;
    private Object data;

}

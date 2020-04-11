package com.caece.arbol.model.request;

import com.caece.arbol.model.Nodo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArbolRequestDto implements Serializable {

    @ApiModelProperty(notes = "id of the Tree", name = "id", required = true, value = "123456")
    @NotNull(message = "The id cannot be missing or empty")
    private Long id;

    @ApiModelProperty(notes = "Root node of the tree", name = "root")
    private Nodo root;

}

package com.caece.arbol.model.request;

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
public class NodoRequestDto implements Serializable {

    @ApiModelProperty(notes = "Node id", name = "id", required = true, value = "123456")
    @NotNull(message = "The id cannot be missing or empty")
    private Long id;

    @ApiModelProperty(notes = "Node information.", name = "data", required = true, value = "123456")
    @NotNull(message = "The data contained cannot be missing or empty")
    private Object data;

}

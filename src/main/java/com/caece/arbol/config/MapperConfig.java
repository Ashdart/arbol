package com.caece.arbol.config;

import com.caece.arbol.model.Arbol;
import com.caece.arbol.model.request.ArbolRequestDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.STRICT);
        Converter<Arbol, ArbolRequestDto> myConverter = context -> {
            Arbol arbol = context.getSource();

            //ArbolRequestDto
            ArbolRequestDto arbolDto = new ArbolRequestDto();
            arbolDto.setId(arbol.getId());
            arbolDto.setRoot(arbol.getRoot());

            return arbolDto;
        };
        modelMapper.addConverter(myConverter);
        return modelMapper;
    }

}

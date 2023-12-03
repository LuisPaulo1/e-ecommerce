package com.stooom.ecommerce.config;

import com.stooom.ecommerce.controller.dto.ProdutoResumoDTO;
import org.hibernate.collection.internal.PersistentSet;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ModelMapperConfig {
	Converter<PersistentSet, Set> setConverter = context -> {
		PersistentSet source = context.getSource();
		Set<ProdutoResumoDTO> destination = new HashSet(source);
		return destination;
	};

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		modelMapper.typeMap(PersistentSet.class, Set.class).setConverter(setConverter);
		return modelMapper;
	}
}
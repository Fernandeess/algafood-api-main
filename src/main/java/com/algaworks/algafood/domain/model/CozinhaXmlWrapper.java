package com.algaworks.algafood.domain.model;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;
import lombok.NonNull;
@JacksonXmlRootElement(localName = "Cozinhas")
@Data
public class CozinhaXmlWrapper {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty( localName = "Lista")
    @NonNull
    private List<Cozinha> cozinhas;
}

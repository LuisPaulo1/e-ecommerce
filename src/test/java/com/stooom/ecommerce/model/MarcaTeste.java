package com.stooom.ecommerce.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MarcaTeste {

    @Test
    public void testarMarca() {
        Marca marca = new Marca();
        marca.setId(1);
        marca.setNome("Marca 1");

        Assertions.assertEquals(1, marca.getId());
        Assertions.assertEquals("Marca 1", marca.getNome());
    }
}

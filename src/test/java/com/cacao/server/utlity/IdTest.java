package com.cacao.server.utlity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

class IdTest {
    @Test
    void size() {
        var generated = Id.generateOne(8);
        Assertions.assertThat(generated).isEqualTo(8);
    }
}
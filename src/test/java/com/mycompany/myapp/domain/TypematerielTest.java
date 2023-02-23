package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypematerielTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Typemateriel.class);
        Typemateriel typemateriel1 = new Typemateriel();
        typemateriel1.setId("id1");
        Typemateriel typemateriel2 = new Typemateriel();
        typemateriel2.setId(typemateriel1.getId());
        assertThat(typemateriel1).isEqualTo(typemateriel2);
        typemateriel2.setId("id2");
        assertThat(typemateriel1).isNotEqualTo(typemateriel2);
        typemateriel1.setId(null);
        assertThat(typemateriel1).isNotEqualTo(typemateriel2);
    }
}

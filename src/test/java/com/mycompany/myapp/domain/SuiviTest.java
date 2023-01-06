package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SuiviTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Suivi.class);
        Suivi suivi1 = new Suivi();
        suivi1.setId("id1");
        Suivi suivi2 = new Suivi();
        suivi2.setId(suivi1.getId());
        assertThat(suivi1).isEqualTo(suivi2);
        suivi2.setId("id2");
        assertThat(suivi1).isNotEqualTo(suivi2);
        suivi1.setId(null);
        assertThat(suivi1).isNotEqualTo(suivi2);
    }
}

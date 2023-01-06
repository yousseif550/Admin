package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CollaborateursTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Collaborateurs.class);
        Collaborateurs collaborateurs1 = new Collaborateurs();
        collaborateurs1.setId("id1");
        Collaborateurs collaborateurs2 = new Collaborateurs();
        collaborateurs2.setId(collaborateurs1.getId());
        assertThat(collaborateurs1).isEqualTo(collaborateurs2);
        collaborateurs2.setId("id2");
        assertThat(collaborateurs1).isNotEqualTo(collaborateurs2);
        collaborateurs1.setId(null);
        assertThat(collaborateurs1).isNotEqualTo(collaborateurs2);
    }
}

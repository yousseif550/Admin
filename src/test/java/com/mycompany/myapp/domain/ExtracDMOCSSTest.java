package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExtracDMOCSSTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExtracDMOCSS.class);
        ExtracDMOCSS extracDMOCSS1 = new ExtracDMOCSS();
        extracDMOCSS1.setId("id1");
        ExtracDMOCSS extracDMOCSS2 = new ExtracDMOCSS();
        extracDMOCSS2.setId(extracDMOCSS1.getId());
        assertThat(extracDMOCSS1).isEqualTo(extracDMOCSS2);
        extracDMOCSS2.setId("id2");
        assertThat(extracDMOCSS1).isNotEqualTo(extracDMOCSS2);
        extracDMOCSS1.setId(null);
        assertThat(extracDMOCSS1).isNotEqualTo(extracDMOCSS2);
    }
}

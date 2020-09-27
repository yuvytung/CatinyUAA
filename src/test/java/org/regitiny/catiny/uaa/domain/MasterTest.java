package org.regitiny.catiny.uaa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.regitiny.catiny.uaa.web.rest.TestUtil;

import java.util.UUID;

public class MasterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Master.class);
        Master master1 = new Master();
        master1.setMasterId(UUID.randomUUID());
        Master master2 = new Master();
        master2.setMasterId(master1.getMasterId());
        assertThat(master1).isEqualTo(master2);
        master2.setMasterId(UUID.randomUUID());
        assertThat(master1).isNotEqualTo(master2);
        master1.setMasterId(null);
        assertThat(master1).isNotEqualTo(master2);
    }
}

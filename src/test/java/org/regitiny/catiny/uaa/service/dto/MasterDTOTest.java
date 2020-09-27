package org.regitiny.catiny.uaa.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.regitiny.catiny.uaa.web.rest.TestUtil;

import java.util.UUID;

public class MasterDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MasterDTO.class);
        MasterDTO masterDTO1 = new MasterDTO();
        masterDTO1.setMasterId(UUID.randomUUID());
        MasterDTO masterDTO2 = new MasterDTO();
        assertThat(masterDTO1).isNotEqualTo(masterDTO2);
        masterDTO2.setMasterId(masterDTO1.getMasterId());
        assertThat(masterDTO1).isEqualTo(masterDTO2);
        masterDTO2.setMasterId(UUID.randomUUID());
        assertThat(masterDTO1).isNotEqualTo(masterDTO2);
        masterDTO1.setMasterId(null);
        assertThat(masterDTO1).isNotEqualTo(masterDTO2);
    }
}

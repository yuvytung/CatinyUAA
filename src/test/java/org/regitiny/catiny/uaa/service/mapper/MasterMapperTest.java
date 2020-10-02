package org.regitiny.catiny.uaa.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class MasterMapperTest {

    private MasterMapper masterMapper;

    @BeforeEach
    public void setUp() {
        masterMapper = new MasterMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        UUID masterId = UUID.randomUUID();
        assertThat(masterMapper.fromMasterId(masterId).getMasterId()).isEqualTo(masterId);
        assertThat(masterMapper.fromMasterId(null)).isNull();
    }
}

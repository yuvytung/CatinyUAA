package org.regitiny.catiny.uaa.service.mapper;


import org.regitiny.catiny.uaa.domain.*;
import org.regitiny.catiny.uaa.service.dto.MasterDTO;

import org.mapstruct.*;

import java.util.UUID;

/**
 * Mapper for the entity {@link Master} and its DTO {@link MasterDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MasterMapper extends EntityMapper<MasterDTO, Master> {



    default Master fromMasterId(UUID masterId) {
        if (masterId == null) {
            return null;
        }
        Master master = new Master();
        master.masterId(masterId);
        return master;
    }
}

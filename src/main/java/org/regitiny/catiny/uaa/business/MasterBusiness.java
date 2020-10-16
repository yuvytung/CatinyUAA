package org.regitiny.catiny.uaa.business;

import org.regitiny.catiny.uaa.domain.User;
import org.regitiny.catiny.uaa.service.dto.MasterDTO;

public interface MasterBusiness
{
  MasterDTO updateMasterWhenUpdateUser(User user) throws NullPointerException;
}

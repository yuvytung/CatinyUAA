package org.regitiny.catiny.uaa.service;

import org.regitiny.catiny.uaa.domain.Master;
import org.regitiny.catiny.uaa.domain.User;
import org.regitiny.catiny.uaa.service.dto.MasterDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Service Interface for managing {@link org.regitiny.catiny.uaa.domain.Master}.
 */
public interface MasterService
{
  /**
   * create a new master after create user.
   *
   * @param user this is entity to get info put to master.
   * @return masterDTO.
   */
  MasterDTO createMasterWhenRegisterUser(User user) throws Exception;


  MasterDTO updateMasterWhenUpdateUser(User user) throws NullPointerException;


  /**
   * Save a master.
   *
   * @param masterDTO the entity to save.
   * @return the persisted entity.
   */
  MasterDTO save(MasterDTO masterDTO);


  /**
   * Get all the masters.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<MasterDTO> findAll(Pageable pageable);


  /**
   * Get the "id" master.
   *
   * @param masterId the id of the entity.
   * @return the entity.
   */
  MasterDTO fetchOne(UUID masterId);


  /**
   * Delete the "id" master.
   *
   * @param masterId the id of the entity.
   */
  Boolean deleteOne(UUID masterId);


  /**
   * Search for the master corresponding to the query.
   *
   * @param query    the query of the search.
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<MasterDTO> search(String query, Pageable pageable);


  /**
   * get only one master user by userId , groupId , companyId
   *
   * @param userId
   * @param groupId
   * @param companyId
   * @return master.
   */
  MasterDTO fetchOneByUserIdGroupIdCompanyId(Long userId, Long groupId, Long companyId);


  /**
   * get only one master user by userId , groupId , companyId
   *
   * @param userName
   * @param groupId
   * @param companyId
   * @return master.
   */
  MasterDTO fetchOneByUserNameGroupIdCompanyId(String userName, Long groupId, Long companyId);


  /**
   * get list master by username
   *
   * @param username
   * @return list of master
   */
  List<Master> findByUsername(String username);
}

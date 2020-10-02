package org.regitiny.catiny.uaa.service;

import org.regitiny.catiny.uaa.domain.User;
import org.regitiny.catiny.uaa.service.dto.MasterDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
  MasterDTO createMasterWhileRegisterUser(User user) throws Exception;


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
}

package org.regitiny.catiny.uaa.service.impl;

import org.regitiny.catiny.uaa.service.MasterService;
import org.regitiny.catiny.uaa.domain.Master;
import org.regitiny.catiny.uaa.repository.MasterRepository;
import org.regitiny.catiny.uaa.repository.search.MasterSearchRepository;
import org.regitiny.catiny.uaa.service.dto.MasterDTO;
import org.regitiny.catiny.uaa.service.mapper.MasterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Master}.
 */
@Service
@Transactional
public class MasterServiceImpl implements MasterService
{

  private final Logger log = LoggerFactory.getLogger(MasterServiceImpl.class);

  private final MasterRepository masterRepository;

  private final MasterMapper masterMapper;

  private final MasterSearchRepository masterSearchRepository;

  public MasterServiceImpl(MasterRepository masterRepository, MasterMapper masterMapper, MasterSearchRepository masterSearchRepository)
  {
    this.masterRepository = masterRepository;
    this.masterMapper = masterMapper;
    this.masterSearchRepository = masterSearchRepository;
  }

  @Override
  public MasterDTO save(MasterDTO masterDTO)
  {
    log.debug("Request to save Master : {}", masterDTO);
    Master master = masterMapper.toEntity(masterDTO);
    master = masterRepository.save(master);
    masterSearchRepository.save(master);
    MasterDTO result = masterMapper.toDto(master);
    return result;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<MasterDTO> findAll(Pageable pageable)
  {
    log.debug("Request to get all Masters");
    return masterRepository.findAll(pageable)
      .map(masterMapper::toDto);
  }


  @Override
  @Transactional(readOnly = true)
  public MasterDTO fetchOne(UUID masterId)
  {
    log.debug("Request to get Master : {}", masterId);
    return masterMapper.toDto(masterRepository.findOneByMasterId(masterId));
  }

  @Override
  public Boolean deleteOne(UUID masterId)
  {
    log.debug("Request to delete Master : {}", masterId);
    return masterRepository.deleteOneByMasterId(masterId) && masterSearchRepository.deleteOneByMasterId(masterId);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<MasterDTO> search(String query, Pageable pageable)
  {
    log.debug("Request to search for a page of Masters for query {}", query);
    return masterSearchRepository.search(queryStringQuery(query), pageable)
      .map(masterMapper::toDto);
  }
}

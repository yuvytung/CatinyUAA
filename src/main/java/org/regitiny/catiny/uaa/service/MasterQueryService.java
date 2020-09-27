package org.regitiny.catiny.uaa.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import org.regitiny.catiny.uaa.domain.Master;
import org.regitiny.catiny.uaa.domain.*; // for static metamodels
import org.regitiny.catiny.uaa.repository.MasterRepository;
import org.regitiny.catiny.uaa.repository.search.MasterSearchRepository;
import org.regitiny.catiny.uaa.service.dto.MasterCriteria;
import org.regitiny.catiny.uaa.service.dto.MasterDTO;
import org.regitiny.catiny.uaa.service.mapper.MasterMapper;

/**
 * Service for executing complex queries for {@link Master} entities in the database.
 * The main input is a {@link MasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MasterDTO} or a {@link Page} of {@link MasterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MasterQueryService extends QueryService<Master>
{

  private final Logger log = LoggerFactory.getLogger(MasterQueryService.class);

  private final MasterRepository masterRepository;

  private final MasterMapper masterMapper;

  private final MasterSearchRepository masterSearchRepository;

  public MasterQueryService(MasterRepository masterRepository, MasterMapper masterMapper, MasterSearchRepository masterSearchRepository)
  {
    this.masterRepository = masterRepository;
    this.masterMapper = masterMapper;
    this.masterSearchRepository = masterSearchRepository;
  }

  /**
   * Return a {@link List} of {@link MasterDTO} which matches the criteria from the database.
   *
   * @param criteria The object which holds all the filters, which the entities should match.
   * @return the matching entities.
   */
  @Transactional(readOnly = true)
  public List<MasterDTO> findByCriteria(MasterCriteria criteria)
  {
    log.debug("find by criteria : {}", criteria);
    final Specification<Master> specification = createSpecification(criteria);
    return masterMapper.toDto(masterRepository.findAll(specification));
  }

  /**
   * Return a {@link Page} of {@link MasterDTO} which matches the criteria from the database.
   *
   * @param criteria The object which holds all the filters, which the entities should match.
   * @param page     The page, which should be returned.
   * @return the matching entities.
   */
  @Transactional(readOnly = true)
  public Page<MasterDTO> findByCriteria(MasterCriteria criteria, Pageable page)
  {
    log.debug("find by criteria : {}, page: {}", criteria, page);
    final Specification<Master> specification = createSpecification(criteria);
    return masterRepository.findAll(specification, page)
      .map(masterMapper::toDto);
  }

  /**
   * Return the number of matching entities in the database.
   *
   * @param criteria The object which holds all the filters, which the entities should match.
   * @return the number of matching entities.
   */
  @Transactional(readOnly = true)
  public long countByCriteria(MasterCriteria criteria)
  {
    log.debug("count by criteria : {}", criteria);
    final Specification<Master> specification = createSpecification(criteria);
    return masterRepository.count(specification);
  }

  /**
   * Function to convert {@link MasterCriteria} to a {@link Specification}
   *
   * @param criteria The object which holds all the filters, which the entities should match.
   * @return the matching {@link Specification} of the entity.
   */
  protected Specification<Master> createSpecification(MasterCriteria criteria)
  {
    Specification<Master> specification = Specification.where(null);
    if (criteria != null)
    {
      if (criteria.getMasterId() != null)
      {
        specification = specification.and(buildSpecification(criteria.getMasterId(), Master_.masterId));
      }
      if (criteria.getUserId() != null)
      {
        specification = specification.and(buildRangeSpecification(criteria.getUserId(), Master_.userId));
      }
      if (criteria.getGroupId() != null)
      {
        specification = specification.and(buildRangeSpecification(criteria.getGroupId(), Master_.groupId));
      }
      if (criteria.getCompanyId() != null)
      {
        specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), Master_.companyId));
      }
      if (criteria.getMasterUserName() != null)
      {
        specification = specification.and(buildStringSpecification(criteria.getMasterUserName(), Master_.masterUserName));
      }
      if (criteria.getUserName() != null)
      {
        specification = specification.and(buildStringSpecification(criteria.getUserName(), Master_.userName));
      }
      if (criteria.getFirstName() != null)
      {
        specification = specification.and(buildStringSpecification(criteria.getFirstName(), Master_.firstName));
      }
      if (criteria.getLastName() != null)
      {
        specification = specification.and(buildStringSpecification(criteria.getLastName(), Master_.lastName));
      }
      if (criteria.getImageUrl() != null)
      {
        specification = specification.and(buildStringSpecification(criteria.getImageUrl(), Master_.imageUrl));
      }
      if (criteria.getEmail() != null)
      {
        specification = specification.and(buildStringSpecification(criteria.getEmail(), Master_.email));
      }
      if (criteria.getGroupName() != null)
      {
        specification = specification.and(buildStringSpecification(criteria.getGroupName(), Master_.groupName));
      }
      if (criteria.getCompanyName() != null)
      {
        specification = specification.and(buildStringSpecification(criteria.getCompanyName(), Master_.companyName));
      }
      if (criteria.getCreatedDate() != null)
      {
        specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Master_.createdDate));
      }
      if (criteria.getModifiedDate() != null)
      {
        specification = specification.and(buildRangeSpecification(criteria.getModifiedDate(), Master_.modifiedDate));
      }
    }
    return specification;
  }
}

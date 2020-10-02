package org.regitiny.catiny.uaa.web.rest;

import org.regitiny.catiny.uaa.service.MasterService;
import org.regitiny.catiny.uaa.web.rest.errors.BadRequestAlertException;
import org.regitiny.catiny.uaa.service.dto.MasterDTO;
import org.regitiny.catiny.uaa.service.dto.MasterCriteria;
import org.regitiny.catiny.uaa.service.MasterQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing {@link org.regitiny.catiny.uaa.domain.Master}.
 */
@RestController
@RequestMapping("/entity/master")
public class MasterResource
{

  private final Logger log = LoggerFactory.getLogger(MasterResource.class);

  private static final String ENTITY_NAME = "master";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final MasterService masterService;

  private final MasterQueryService masterQueryService;

  public MasterResource(MasterService masterService, MasterQueryService masterQueryService)
  {
    this.masterService = masterService;
    this.masterQueryService = masterQueryService;
  }

  /**
   * {@code POST  /masters} : Create a new master.
   *
   * @param masterDTO the masterDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new masterDTO, or with status {@code 400 (Bad Request)} if the master has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("")
  public ResponseEntity<MasterDTO> createMaster(@Valid @RequestBody MasterDTO masterDTO) throws URISyntaxException
  {
    log.debug("REST request to save Master : {}", masterDTO);
    if (masterDTO.getMasterId() != null)
    {
      throw new BadRequestAlertException("A new master cannot already have an ID", ENTITY_NAME, "idexists");
    }
    MasterDTO result = masterService.save(masterDTO);
    return ResponseEntity.created(new URI("/api/masters/" + result.getMasterId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getMasterId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /masters} : Updates an existing master.
   *
   * @param masterDTO the masterDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated masterDTO,
   * or with status {@code 400 (Bad Request)} if the masterDTO is not valid,
   * or with status {@code 500 (Internal Server Error)} if the masterDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("")
  public ResponseEntity<MasterDTO> updateMaster(@Valid @RequestBody MasterDTO masterDTO) throws URISyntaxException
  {
    log.debug("REST request to update Master : {}", masterDTO);
    if (masterDTO.getMasterId() == null)
    {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    MasterDTO result = masterService.save(masterDTO);
    return ResponseEntity.ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, masterDTO.getMasterId().toString()))
      .body(result);
  }

  /**
   * {@code GET  /masters} : get all the masters.
   *
   * @param pageable the pagination information.
   * @param criteria the criteria which the requested entities should match.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of masters in body.
   */
  @GetMapping("")
  public ResponseEntity<List<MasterDTO>> getAllMasters(MasterCriteria criteria, Pageable pageable)
  {
    log.debug("REST request to get Masters by criteria: {}", criteria);
    Page<MasterDTO> page = masterQueryService.findByCriteria(criteria, pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /masters/count} : count all the masters.
   *
   * @param criteria the criteria which the requested entities should match.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
   */
  @GetMapping("/count")
  public ResponseEntity<Long> countMasters(MasterCriteria criteria)
  {
    log.debug("REST request to count Masters by criteria: {}", criteria);
    return ResponseEntity.ok().body(masterQueryService.countByCriteria(criteria));
  }

  /**
   * {@code GET  /masters/:id} : get the "id" master.
   *
   * @param masterId the id of the masterDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the masterDTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/{id}")
  public ResponseEntity<MasterDTO> getMaster(@PathVariable UUID masterId)
  {
    log.debug("REST request to get Master : {}", masterId);
    MasterDTO masterDTO = masterService.fetchOne(masterId);
    return ResponseEntity.ok(masterDTO);
  }

  /**
   * {@code DELETE  /masters/:id} : delete the "id" master.
   *
   * @param masterId the id of the masterDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMaster(@PathVariable UUID masterId)
  {
    log.debug("REST request to delete Master : {}", masterId);
    masterService.deleteOne(masterId);
    return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, masterId.toString())).build();
  }

  /**
   * {@code SEARCH  /_search/masters?query=:query} : search for the master corresponding
   * to the query.
   *
   * @param query    the query of the master search.
   * @param pageable the pagination information.
   * @return the result of the search.
   */
  @GetMapping("/_search")
  public ResponseEntity<List<MasterDTO>> searchMasters(@RequestParam String query, Pageable pageable)
  {
    log.debug("REST request to search for a page of Masters for query {}", query);
    Page<MasterDTO> page = masterService.search(query, pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }
}

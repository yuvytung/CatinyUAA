package org.regitiny.catiny.uaa.service.impl;

import lombok.extern.log4j.Log4j2;
import org.regitiny.catiny.uaa.domain.User;
import org.regitiny.catiny.uaa.service.MasterService;
import org.regitiny.catiny.uaa.domain.Master;
import org.regitiny.catiny.uaa.repository.MasterRepository;
import org.regitiny.catiny.uaa.repository.search.MasterSearchRepository;
import org.regitiny.catiny.uaa.service.dto.MasterDTO;
import org.regitiny.catiny.uaa.service.mapper.MasterMapper;
import org.regitiny.tools.magic.constant.StringPool;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Master}.
 */
@Log4j2
@Service
@Transactional
public class MasterServiceImpl implements MasterService
{

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
  public MasterDTO createMasterWhenRegisterUser(User user) throws NullPointerException
  {

    if (Objects.isNull(user.getId()))
      throw new NullPointerException("this user id is never null");

    Long userId = user.getId();
    UUID masterId = UUID.randomUUID();

    String firstName = Objects.nonNull(user.getFirstName()) ? user.getFirstName() : StringPool.BLANK;
    String lastName = Objects.nonNull(user.getLastName()) ? user.getLastName() : StringPool.BLANK;
    String masterUsername ;
    if (firstName.equals(StringPool.BLANK) && lastName.equals(StringPool.BLANK))
      masterUsername = lastName + StringPool.SPACE + firstName;
    else
      masterUsername = firstName + lastName;
    masterUsername = masterUsername.equals(StringPool.SPACE) ? user.getLogin() : masterUsername;

    String email = Objects.nonNull(user.getEmail()) ? user.getEmail() : StringPool.BLANK;
    Instant createDate = Objects.nonNull(user.getCreatedDate()) ? user.getCreatedDate() : null;
    Instant modifiedDate = Objects.nonNull(user.getLastModifiedDate()) ? user.getLastModifiedDate() : null;
    String imageUrl = Objects.nonNull(user.getImageUrl()) ? user.getImageUrl() : StringPool.BLANK;
    Long companyId = 0L;
    Long groupId = 0L;
    String companyName = StringPool.BLANK;
    String groupName = StringPool.BLANK;

    String userName = user.getLogin();

    MasterDTO masterDTO = MasterDTO.builder().masterId(masterId).userId(userId).firstName(firstName).lastName(lastName).masterUserName(masterUsername).email(email).
      createdDate(createDate).modifiedDate(modifiedDate).imageUrl(imageUrl).companyId(companyId).groupId(groupId).companyName(companyName).
      groupName(groupName).userName(userName).build();

    log.debug("masterDTO after set . masterDTO= {}",masterDTO);
    masterSearchRepository.save(masterMapper.toEntity(masterDTO));
    return save(masterDTO);
  }

  @Override
  public MasterDTO updateMasterWhenUpdateUser(User user) throws NullPointerException
  {

    if (Objects.isNull(user.getId()))
      throw new NullPointerException("this user id is never null");

    MasterDTO masterDTO = fetchOneByUserIdGroupIdCompanyId(user.getId(),0L,0L);

    if (Objects.nonNull(user.getFirstName())||Objects.nonNull(user.getLastName()))
    {
      String firstName = Objects.nonNull(user.getFirstName()) ? user.getFirstName() : StringPool.BLANK;
      String lastName = Objects.nonNull(user.getLastName()) ? user.getLastName() : StringPool.BLANK;
      String masterUsername;
      if (!firstName.equals(StringPool.BLANK) && !lastName.equals(StringPool.BLANK))
        masterUsername = lastName + StringPool.SPACE + firstName;
      else
        masterUsername = firstName + lastName;

      masterDTO.setFirstName(firstName);
      masterDTO.setLastName(lastName);

      masterDTO.setUserName(user.getLogin());
      masterDTO.setMasterUserName(masterUsername);
    }

    if (Objects.nonNull(user.getEmail()))
      masterDTO.setEmail(user.getEmail());
    if (Objects.nonNull(user.getImageUrl()))
      masterDTO.setImageUrl(user.getImageUrl());
    masterDTO.setModifiedDate(Instant.now());

    log.debug("masterDTO after set value . masterDTO= {} ",masterDTO);
    masterSearchRepository.save(masterMapper.toEntity(masterDTO));
    return save(masterDTO);
  }

  @Override
  public MasterDTO save(MasterDTO masterDTO)
  {
    log.debug("Request to save Master : {}", masterDTO);
    Master master = masterMapper.toEntity(masterDTO);
    master = masterRepository.save(master);
    masterSearchRepository.save(master);
    return masterMapper.toDto(master);
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

  @Override
  public MasterDTO fetchOneByUserIdGroupIdCompanyId(Long userId, Long groupId, Long companyId)
  {
    Master master = masterRepository.findOneByUserIdAndGroupIdAndCompanyId(userId, groupId , companyId);
    return masterMapper.toDto(master);
  }

  @Override
  public MasterDTO fetchOneByUserNameGroupIdCompanyId(String userName, Long groupId, Long companyId)
  {
    Master master = masterRepository.findOneByUserNameAndGroupIdAndCompanyId(userName, groupId , companyId);
    return masterMapper.toDto(master);
  }

  @Override
  public List<Master> findByUsername(String username)
  {
    return masterRepository.findByUserName(username);
  }
}

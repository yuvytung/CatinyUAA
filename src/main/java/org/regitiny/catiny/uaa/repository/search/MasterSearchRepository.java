package org.regitiny.catiny.uaa.repository.search;

import org.regitiny.catiny.uaa.domain.Master;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.UUID;


/**
 * Spring Data Elasticsearch repository for the {@link Master} entity.
 */
public interface MasterSearchRepository extends ElasticsearchRepository<Master, UUID>
{
  List<Master> findByUserId(Long userId);

  Boolean deleteOneByMasterId(UUID masterId);
}

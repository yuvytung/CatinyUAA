package org.regitiny.catiny.uaa.repository.search;

import org.regitiny.catiny.uaa.domain.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the User entity.
 */
public interface UserSearchRepository extends ElasticsearchRepository<User, Long>
{
}

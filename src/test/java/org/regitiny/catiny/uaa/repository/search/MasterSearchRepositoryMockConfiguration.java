package org.regitiny.catiny.uaa.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link MasterSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class MasterSearchRepositoryMockConfiguration {

    @MockBean
    private MasterSearchRepository mockMasterSearchRepository;

}

package mobi.rayson.auth.foundation;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;


@CacheConfig(cacheNames = "clientDetail")
@Service
public class DataSourceCacheClientDetailService implements ClientDetailsService,
    ClientRegistrationService {

  private final static String OAUTH_CLIENT_DETAILS_CACHE = "oauth_client_details";

  @Resource(name = "JdbcClientDetailsService4Wrapper")
  private JdbcClientDetailsService clientDetailsService;

  @Cacheable(value = OAUTH_CLIENT_DETAILS_CACHE, key = "#clientId")
  @Override
  public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
    return clientDetailsService.loadClientByClientId(clientId);
  }

  @CachePut(value = OAUTH_CLIENT_DETAILS_CACHE, key = "#clientDetails.clientId")
  @Override
  public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
    clientDetailsService.addClientDetails(clientDetails);
  }

  @CachePut(value = OAUTH_CLIENT_DETAILS_CACHE, key = "#clientDetails.clientId")
  @Override
  public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
    clientDetailsService.updateClientDetails(clientDetails);
  }

  @CachePut(value = OAUTH_CLIENT_DETAILS_CACHE, key = "#clientId")
  @Override
  public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
    clientDetailsService.updateClientSecret(clientId, secret);
  }

  @CacheEvict(value = OAUTH_CLIENT_DETAILS_CACHE, key = "#clientId")
  @Override
  public void removeClientDetails(String clientId) throws NoSuchClientException {
    clientDetailsService.removeClientDetails(clientId);
  }

  @Override
  public List<ClientDetails> listClientDetails() {
    return clientDetailsService.listClientDetails();
  }
}

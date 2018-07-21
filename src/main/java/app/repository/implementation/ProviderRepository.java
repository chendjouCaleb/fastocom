package app.repository.implementation;

import app.entity.organisation.Provider;
import app.repository.contract.IProviderRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier
public class ProviderRepository extends AbstractRepository<Provider, Integer> implements IProviderRepository {
}

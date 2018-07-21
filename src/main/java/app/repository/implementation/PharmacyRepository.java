package app.repository.implementation;

import app.entity.organisation.Pharmacy;
import app.repository.contract.IPharmacyRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier
public class PharmacyRepository extends AbstractRepository<Pharmacy, Integer> implements IPharmacyRepository {
}

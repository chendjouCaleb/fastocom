package app.repository.implementation;

import app.entity.market.Product;
import app.entity.organisation.Organisation;
import app.repository.contract.IProductRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier
public class ProductRepository extends AbstractRepository<Product, Integer> implements IProductRepository{
    @Override
    public List<Product> listByOrganisation(Organisation organisation) {
        return null;
    }
}

package app.repository.contract;

import app.entity.market.Product;
import app.entity.organisation.Organisation;

import java.util.List;

public interface IProductRepository extends IRepository<Product, Integer> {
    List<Product> listByOrganisation(Organisation organisation);
}

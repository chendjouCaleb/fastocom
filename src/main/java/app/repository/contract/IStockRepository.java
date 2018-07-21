package app.repository.contract;

import app.entity.market.Product;
import app.entity.market.Stock;
import app.entity.organisation.Organisation;
import app.entity.organisation.Pharmacy;

import java.util.List;

public interface IStockRepository extends IRepository<Stock, Integer> {
    List<Stock> listByOrganisation(Organisation organisation);
    Stock findByProductAndOrganisation(Product product, Organisation organisation);
}

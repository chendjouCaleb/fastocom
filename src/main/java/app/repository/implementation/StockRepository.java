package app.repository.implementation;

import app.entity.market.Product;
import app.entity.market.Stock;
import app.entity.organisation.Organisation;
import app.entity.organisation.Pharmacy;
import app.repository.contract.IStockRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Qualifier
public class StockRepository extends AbstractRepository<Stock, Integer> implements IStockRepository{
    @Transactional
    public List<Stock> listByOrganisation(Organisation organisation) {
        return em.createQuery("FROM Stock s WHERE s.organisation.id=:id")
                .setParameter("id", organisation.getId())
                .getResultList();
    }

    @Transactional
    public Stock findByProductAndOrganisation(Product product, Organisation organisation) {

        Query query = em.createQuery("FROM Stock s WHERE s.product.id=:productId AND s.organisation.id=:id")
                .setParameter("id", organisation.getId())
                .setParameter("productId", product.getId());
        try {
            return (Stock) query.getSingleResult();
        }catch (NoResultException ex){
            throw new NoSuchElementException(ex.getMessage());
        }

    }


}

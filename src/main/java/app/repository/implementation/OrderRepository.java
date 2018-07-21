package app.repository.implementation;

import app.entity.market.Order;
import app.repository.contract.IOrderRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier
public class OrderRepository extends AbstractRepository<Order, Integer> implements IOrderRepository {
}

package app.repository.contract;

import java.util.List;

public interface IRepository<Type, IDType> {
    Type findById(IDType id);
    Type save(Type object);
    void update(Type object);
    void delete(Type object);
    void deleteById(IDType object);
    void deleteAll();
    List<Type> findAll();
    Type merge(Type object);
}

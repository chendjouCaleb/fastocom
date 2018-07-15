package app.repository.implementation;

import app.repository.contract.IRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class AbstractRepository<Type, IDType> implements IRepository<Type, IDType> {
    protected Class<Type> type;
    @PersistenceContext EntityManager em;

    public AbstractRepository() {
        this.type = (Class<Type>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (type == null){
            throw new NullPointerException("This Repository don't have entity type");
        }
    }




    @Transactional
    public Type save(Type object) {
        em.persist(object);
        return object;
    }

    @Override
    public Type findById(IDType id) {
        Type object = em.find(type, id);
        if (object == null){
            throw new NoSuchElementException("Nothing object of type Account with " + id + " was found");
        }
        return object;
    }

    @Transactional
    public void update(Type object) {
        if(!em.contains(object)){
            em.merge(object);
        }else {
            throw new NoSuchElementException("You try to update entity which is not saved");
        }

    }

    @Override
    @Transactional
    public void delete(Type object) {
        em.remove(em.contains(object)?object:em.merge(object));
    }

    @Override
    @Transactional
    public void deleteById(IDType id) {
        Type object = findById(id);
        em.remove(object);
    }

    @Override
    @Transactional

    public List<Type> findAll() {
        return em.createQuery("FROM " + type.getSimpleName() + " t").getResultList();
    }

    public Type merge(Type object) {
        return em.merge(object);
    }

    @Override
    @Transactional
    public void deleteAll() {
        List<Type> list = findAll();

        for (Type o: list){
            delete(o);
        }
    }
}

package app.repository.implementation;

import app.entity.Pharmacy;
import app.repository.IPharmacieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PharmacyRepository implements IPharmacieRepository {
    private Integer id = 1;
    private List<Pharmacy> pharmacies = new ArrayList<>();

    public Pharmacy findById(Integer id) {
        return pharmacies.stream().filter(pharmacy -> pharmacy.getId().equals(id)).findFirst().orElse(null);
    }


    public Pharmacy save(Pharmacy object) {
         object.setId(id);
         pharmacies.add(object);
         id++;
        return object;
    }


    public void update(Pharmacy object) {

    }

    @Override
    public void delete(Pharmacy object) {

    }

    @Override
    public void deleteById(Integer object) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Pharmacy> findAll() {
        return null;
    }

    @Override
    public Pharmacy merge(Pharmacy object) {
        return null;
    }
}

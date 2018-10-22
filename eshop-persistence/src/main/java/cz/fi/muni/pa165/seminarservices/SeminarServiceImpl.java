package cz.fi.muni.pa165.seminarservices;

import cz.fi.muni.pa165.dao.ProductDao;
import cz.fi.muni.pa165.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Filip Nguyen on 9.10.17.
 */
@Service
public class SeminarServiceImpl {
    @Autowired
    private ProductDao productDao;


    @Transactional
    public void create(Product p) {
        productDao.create(p);
    }

    @Transactional

    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Transactional
    public Product findById(Long id) {
        return productDao.findById(id);
    }

    @Transactional
    public void remove(Product p) throws IllegalArgumentException {
        productDao.remove(p);
    }

    @Transactional
    public List<Product> findByName(String name) {
        return productDao.findByName(name);
    }
}

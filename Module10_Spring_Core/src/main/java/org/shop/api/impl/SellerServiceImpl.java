package org.shop.api.impl;

import java.util.List;

import org.shop.api.SellerService;
import org.shop.data.Seller;
import org.shop.repository.SellerRepository;

public class SellerServiceImpl implements SellerService {
    
    private SellerRepository repository;

    public void setRepository(SellerRepository repository) {
        this.repository = repository;
    }

    public List<Seller> getSellers() {
        return repository.getSellers();
    }

    public Seller getSellerById(Long sellerId) {
        return repository.getSellerById(sellerId);
    }

    public void importSellers(List<Seller> sellers) {
        for (Seller seller : sellers) {
            repository.createOrUpdateSeller(seller);
        }
    }
}

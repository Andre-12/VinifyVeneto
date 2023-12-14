package com.server.server.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.server.model.Request.ModProductRequest;
import com.server.server.model.Request.ProductRequest;
import com.server.server.model.Request.UserProduct;
import com.server.server.model.Seller.Seller;
import com.server.server.model.Seller.SellerDao;

@Service
public class ProductDao {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellerDao sellerDao;

    public void addProduct(Product product){
        productRepository.save(product);
    }

    public boolean checkSeller(String id, String password){
        return sellerDao.goodSeller(id, password);
    }

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    public boolean existProduct(int id){
        return productRepository.findById(id).isPresent();
    }

    public Product geProduct(int id){
        return productRepository.findById(id).get();
    }

    public List<Product> getProducts(ProductRequest productRequest){
        if(productRequest==null) System.out.println("E' il product che mi uccide");
        List<Product> l = getAllProduct();
        List<Product> lr = new ArrayList<>();
        for(int i=0;i<l.size();++i){
            Product p = l.get(i);
            if(p.getSeller().equals(productRequest.getSeller())){
                lr.add(p);
            }
        }

        for(Product p : l){
            if(!p.getSeller().equals(productRequest.getSeller())){
                l.remove(p);
            }
            else if(productRequest.getType()!=null && !productRequest.getType().equals("Tutti") && !productRequest.getType().equals(productRequest.getType())){
                l.remove(p);
            }
            else if(productRequest.getOrigin()!=null){

            }
        }

        return l;
    }

    boolean deleteProduct(int id){
        if(productRepository.findById(id).isPresent()){
            productRepository.deleteById(id);
            return true;
        }
        else{
            System.out.println("Prodotto da rimuovere non esistente");
            return false;
        }
    }

    boolean modifyProduct(ModProductRequest modRequest){
        if(productRepository.findById(modRequest.getProductId()).isPresent()){
            Product p = productRepository.findById(modRequest.getProductId()).get();
            if(p.getSeller().equals(modRequest.getSellerId())){
                p.setAll(new Product(modRequest.getName(), modRequest.getOrigin(), modRequest.getType(), Float.valueOf(modRequest.getPrice()), modRequest.getDescription(), null));
                productRepository.save(p);
                return true;
            }
            else{
                return false;
            }
        }

        return false;
    }

    public List<Product> getUserProducts(ProductRequest request){
        List<Product> l = productRepository.findAll();
        /*System.out.println("Lista di vini: "+request.getOrigin()+"    "+request.getType());
        for(int i=0;i<l.size();++i){
            Product p = l.get(i);
            boolean remove=false;
            if(!request.getOrigin().equals("Tutti") && !p.getOrigin().equals(request.getOrigin())){
                remove=true;
            }

            if(!request.getType().equals("Tutti") && !p.getType().equals(request.getType())){
                remove=true;
            }

            if(remove){
                l.remove(p);
            }
            else{
                Seller seller = sellerDao.getSeller(p.getSeller());
                p.setSeller(seller.getName());
            }
        }*/


        return l;
    }

    public Product getUserProduct(int id){
        Optional<Product> p = productRepository.findById(id);
        if(p.isPresent())
            return p.get();
        else
            return null;
    }

    public Seller getSellerInfo(String product){
        if(existProduct(Integer.valueOf(product))){
            Product p = productRepository.findById(Integer.valueOf(product)).get();
            Seller s = sellerDao.getSeller(p.getSeller());
            s.setId();
            s.setPassword();
            return s;
        }
        else{
            return null;
        }
    }
}

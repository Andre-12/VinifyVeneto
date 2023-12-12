package com.server.server.model.product;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.server.model.ResponseEntity;
import com.server.server.model.Request.AddProductRequest;
import com.server.server.model.Request.DeleteProductRequest;
import com.server.server.model.Request.ModProductRequest;
import com.server.server.model.Request.ProductRequest;
import com.server.server.model.Request.SingleProductRequest;
import com.server.server.model.Request.UserProduct;
import com.server.server.model.Seller.Seller;
import com.server.server.model.Seller.SellerDao;


@RestController
public class ProductController {
    
    @Autowired
    private ProductDao productDao;


    @PostMapping("/addProduct")
    public ResponseEntity<Void> newProduct(@RequestBody AddProductRequest request){
      System.out.println("Richiesta di aggiunta prodotto "+request.getPrice());
      if(productDao.checkSeller(request.getSellerId(), request.getSellerPassword())){
        productDao.addProduct(new Product(request.getName(), request.getOrigin(), request.getType(), Float.valueOf(request.getPrice()), request.getDescription(), request.getSellerId()));
        return new ResponseEntity<>("Prodotto aggiunto con successo", 200);
      }
      else{
        return new ResponseEntity<>("Aggiunta fallita", 400);
      }
    }

    @PostMapping("/getProducts")
    public ResponseEntity<List<Product>> getProducts(@RequestBody ProductRequest request){
        System.out.println("Il seller è: "+request.getSeller()+"    e il tipo è: "+request.getType());
        List<Product> l = productDao.getProducts(request);
        //System.out.println("La sixe: "+l.size());

        return new ResponseEntity<>(l, "fsdkljk", 200);
    }

    @PostMapping("/getProduct")
    public ResponseEntity<Product> getProduct(@RequestBody SingleProductRequest product){
        System.out.println("Richiesta prodotto "+product.getId());
        if(productDao.existProduct(product.getId())){
            return new ResponseEntity<>(productDao.geProduct(product.getId()),null, 200);
        }
        else{
            return new ResponseEntity<>("Prodotto non esistente", 400);
        }
        
    }

    @PostMapping("/deleteProduct")
    public ResponseEntity<Void> deleteProduct(@RequestBody DeleteProductRequest request){
      System.out.println("Delete richiesta da "+request.getSeller()+" per prodotto: "+request.getProduct());
      if(productDao.checkSeller(request.getSeller(), request.getPassword())){
        if(productDao.deleteProduct(request.getProduct())){
          return new ResponseEntity<>("Prodotto rimosso", 200);
        }
        
        return new ResponseEntity<>("Rimozione fallita", 400);
      }
      else{
        return new ResponseEntity<>("Rimozione fallita", 400);
      }
    }

    @PostMapping("/modifyProduct")
    public ResponseEntity<Void> modifyProduct(@RequestBody ModProductRequest request){
      
      if(productDao.checkSeller(request.getSellerId(), request.getSellerPassword())){
          if(productDao.modifyProduct(request))
            return new ResponseEntity<>("Modifica effettuata", 200);
          else
            return new ResponseEntity<>("Modifica fallita", 400);
      }

      return new ResponseEntity<>("Modifica fallita", 400);
    }


    @PostMapping("/getUserProducts")
    public ResponseEntity<List<Product>> getUserProducts(@RequestBody  ProductRequest request) {
      System.out.println("Richiesta prodotto utente");
      if(request.getSeller()==null){
        List<Product> l = productDao.getUserProducts(request);
        return new ResponseEntity<List<Product>>(l, "Richiesta effettuata", 200);
      }
        
        return new ResponseEntity<>("Richiesta non valida", 400);
    }


    @GetMapping("/getUserProduct/{product}")
    public ResponseEntity<Product> getUserProduct(@PathVariable String product){
      System.out.println("Richiesta get per "+product);
      Product p =productDao.getUserProduct(Integer.parseInt(product));
      System.out.println(p);
      if(p!=null){
        return new ResponseEntity<Product>(p, "ok", 200);
      }
      return new ResponseEntity<>("Errore richiesta", 400);
    }
    
}

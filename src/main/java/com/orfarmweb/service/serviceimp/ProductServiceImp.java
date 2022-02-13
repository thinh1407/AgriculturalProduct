package com.orfarmweb.service.serviceimp;

import com.orfarmweb.constaint.FormatPrice;
import com.orfarmweb.entity.Cart;
import com.orfarmweb.entity.OrderDetail;
import com.orfarmweb.entity.Product;
import com.orfarmweb.modelutil.CartItem;
import com.orfarmweb.modelutil.ProductAdminDTO;
import com.orfarmweb.repository.ProductRepo;
import com.orfarmweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImp implements ProductService {
    private final ProductRepo productRepo;
    final long pageSize = 6;

    public ProductServiceImp(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public List<Product> getListProductByCategoryId(int id) {
        return productRepo.findProductByCategoryId(id);
    }

    @Override
    public int getTotalByFill(float start, float end, int id) {
        return productRepo.getTotalProductByFill(start, end, id);
    }

    @Override
    public int getTotal(int id) {
        return productRepo.getTotal(id);
    }

    @Override
    public List<Product> getListProductByHot() {
        return productRepo.getProductByHot();
    }

    @Override
    public List<Product> getListSaleProduct() {
        return productRepo.getSaleProduct();
    }

    @Override
    public Product getProductById(int id) {
        return productRepo.getById(id);
    }

    @Override
    public long getTotalPage(int id) {
        return (productRepo.countByCategoryId(id).get(0) % pageSize == 0) ?
                productRepo.countByCategoryId(id).get(0) / pageSize
                : (productRepo.countByCategoryId(id).get(0) / pageSize) + 1;
    }

    @Override
    public List<Product> getByPage(long currentPage, int id) {
        return productRepo.findByPage((currentPage - 1) * pageSize, pageSize, id);
    }
    @Override
    public long getTotalPageByFill(float start, float end, int id) {
        return (productRepo.countByCategoryIdAndFill(start,end,id).get(0) % pageSize == 0) ?
                productRepo.countByCategoryIdAndFill(start,end,id).get(0) / pageSize
                : (productRepo.countByCategoryIdAndFill(start,end,id).get(0) / pageSize) + 1;
    }

    @Override
    public List<Product> getListProductFillByPage(float start, float end, long currentPage, int id) {
        return productRepo.listFill(start,end,id,(currentPage-1)*pageSize,pageSize);
    }
    @Override
    public int getCategoryId(int id) {
        return productRepo.findCateGoryIdByProdId(id);
    }

    @Override
    public List<CartItem> getProductFromCart(List<Cart> cartList) {
        List<CartItem> list = new ArrayList<>();
        cartList.forEach(cart -> list.add(new CartItem(cart.getProduct(), cart.getQuantity())));
        return list;
    }

    @Override
    public Float getTempPriceOfCart(List<CartItem> itemList) {
        Float tempPrice = 0f;
        for (CartItem cartItem: itemList) {
            tempPrice += cartItem.getTotalPrice();
        }
        return tempPrice;
    }

    @Override
    public boolean addProduct(Product product) {
        product.setQuantityProd(0);
        productRepo.save(product);
        return true;
    }

    @Override
    public boolean deleteProduct(int id) {
        productRepo.delete(productRepo.getById(id));
        return true;
    }

    @Override
    public void updateProduct(int id, Product product) {
        Product baseProduct = productRepo.getById(id);
        if(!product.getName().isEmpty()) baseProduct.setName(product.getName());
        if(!product.getDescription().isEmpty()) baseProduct.setDescription(product.getDescription());
        if(product.getSalePrice()!=null) baseProduct.setSalePrice(product.getSalePrice());
        if(!product.getBriefDesc().isEmpty()) baseProduct.setBriefDesc(product.getName());
        if(product.getCategory()!=null) baseProduct.setCategory(product.getCategory());
        if(product.getQuantityProd()!=null) baseProduct.setQuantityProd(product.getQuantityProd());
        if(product.getPercentDiscount()!=null) baseProduct.setPercentDiscount(product.getPercentDiscount());
        if(product.getCost()!=null) baseProduct.setCost(product.getCost());
        productRepo.save(baseProduct);
    }

    @Override
    public List<ProductAdminDTO> findAll() {
        List<Product> list = productRepo.findAll();
        List<ProductAdminDTO> productAdminDTOS = new ArrayList<>();
        list.forEach(product -> productAdminDTOS.add(new ProductAdminDTO(product)));
        return productAdminDTOS;
    }

    @Override
    public List<Product> findProductByName(int id, String keyWord, long currentPage) {
        return productRepo.searchByNameAndPage(id,keyWord,(currentPage-1)*pageSize,pageSize);
    }

    @Override
    public long getTotalPageByName(int id, String keyWord) {
        return (productRepo.countByKeyWord(id,keyWord).get(0) % pageSize == 0) ?
                productRepo.countByKeyWord(id, keyWord).get(0) / pageSize
                : (productRepo.countByKeyWord(id, keyWord).get(0) / pageSize) + 1;
    }

    @Override
    public void saveAfterOrder(Product product, OrderDetail orderDetail) {
        product.setQuantityProd(product.getQuantityProd() - orderDetail.getQuantity());
        productRepo.save(product);
    }


}

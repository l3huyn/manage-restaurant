package com.project.restaurant.services;

import com.project.restaurant.dtos.ProductDTO;
import com.project.restaurant.dtos.ProductImageDTO;
import com.project.restaurant.exceptions.DataNotFoundException;
import com.project.restaurant.exceptions.InvalidParamException;
import com.project.restaurant.models.Category;
import com.project.restaurant.models.Product;
import com.project.restaurant.models.ProductImage;
import com.project.restaurant.repositories.CategoryRepository;
import com.project.restaurant.repositories.ProductImageRepository;
import com.project.restaurant.repositories.ProductRepository;
import com.project.restaurant.responses.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ProductImageRepository productImageRepository;


    //HÀM TẠO SẢN PHẨM
    @Override
    public Product createProduct(ProductDTO productDTO) throws Exception {
        //Tìm kiếm category theo Id => Nếu có tồn tại category đó thì mới cho insert vào DB
        Category existingCategory =  categoryRepository
                .findById(productDTO.getCategoryId())
                //Nếu không tìm ra => Trả về ngoại lệ với message bên dưới
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find category with ID: " + productDTO.getCategoryId()));

        //Convert từ ProductDTO sang Product
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .thumbnail(productDTO.getThumbnail())
                .price(productDTO.getPrice())
                .quantitySold(productDTO.getQuantitySold())
                .status(productDTO.getStatus())
                .category(existingCategory)
                .build();

        //Luu vao DB
        return productRepository.save(newProduct);
    }



    //HÀM LẤY SẢN PHẨM THEO ID
    @Override
    public Product getProductById(long id) throws Exception {
        return productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find product with ID: " + id));
    }



    //HÀM LẤY DANH SÁCH TẤT CẢ SẢN PHẨM
    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest)
                .map(ProductResponse::fromProduct);
    }



    //HÀM CẬP NHẬT SẢN PHẨM
    @Override
    public Product updateProduct(long id, ProductDTO productDTO) throws Exception {
        //Lấy ra sản phẩm bằng Id và lưu vào biến existingProduct
        Product existingProduct = getProductById(id);

        //Kiểm tra xem existingProduct có rỗng không?
        // Nếu không rỗng => cập nhật SP, còn rỗng => trả về NULL
        if(existingProduct != null) {
            //Tìm kiếm category theo Id => Nếu có tồn tại category đó thì mới cho insert vào DB
            Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new DataNotFoundException("Cannot find category with ID: " + productDTO.getCategoryId()));

            //Gán các giá trị
            existingProduct.setName(productDTO.getName());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setQuantitySold(productDTO.getQuantitySold());
            existingProduct.setStatus(productDTO.getStatus());
            existingProduct.setCategory(existingCategory);
        }
        //Lưu vào DB
        return productRepository.save(existingProduct);
    }



    //HÀM XÓA SẢN PHẨM
    @Override
    public void deleteProduct(long id) {
        //Tìm sản phẩm cần xóa trước bằng Id
        //Optional<> : để kiểm tra xem một biến có giá trị tồn tại giá trị hay không
        Optional<Product> optionalProduct = productRepository.findById(id);

        //Nếu optionalProduct có tồn tại => Xóa
        optionalProduct.ifPresent(productRepository::delete);
    }



    //HÀM KIỂM TRA TỒN TẠI BẰNG TÊN
    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }




    //HÀM XỬ LÝ ẢNH VÀ LƯU VÀO BẢNG PRODUCT IMAGE
    @Override
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception {
        //Tìm kiếm product theo Id
        Product existingProduct =  productRepository
                .findById(productId)
                //Nếu không tìm ra => Trả về ngoại lệ với message bên dưới
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find product with ID: " + productImageDTO.getProductId()));

        //Convert từ ProductImageDTO sang ProductImage
        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();

        //Không cho insert quá 5 ảnh cho 1 sản phẩm
        int size = productImageRepository.findByProductId(productId).size();
        if(size >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT ) {
            throw new InvalidParamException("Number of images must be <= "
                    + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT );
        }

        //Lưu vào DB
        return productImageRepository.save(newProductImage);
    }
}

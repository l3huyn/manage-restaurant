package com.project.restaurant.controllers;


import com.github.javafaker.Faker;
import com.project.restaurant.dtos.ProductDTO;
import com.project.restaurant.dtos.ProductImageDTO;
import com.project.restaurant.models.Product;
import com.project.restaurant.models.ProductImage;
import com.project.restaurant.responses.ProductListResponse;
import com.project.restaurant.responses.ProductResponse;
import com.project.restaurant.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products") //http://localhost:8088/api/v1/products
@RequiredArgsConstructor //Annotation được dùng cho cơ chế Dependency Injection
public class ProductController {

    //DI
    private final ProductService productService;


    //HÀM THÊM SẢN PHẨM
    @PostMapping("")
    public ResponseEntity<?> createProduct(
            @Valid @RequestBody ProductDTO productDTO,
            BindingResult result
    ) {
        try {
            //Hiển thị lỗi, lỗi được lưu ở biến result
            if(result.hasErrors()) {
                //Trả về mảng errorMessage kiểu String
                List<String> errorMessage =  result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }

            //Tạo sản phẩm
            Product newProduct = productService.createProduct(productDTO);

            return ResponseEntity.ok(newProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    //HÀM UPLOAD FILE ẢNH
    @PostMapping(value = "uploads/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //chỉ định rằng một phương thức chỉ chấp nhận các yêu cầu HTTP có kiểu nội dung là "multipart/form-data".
    //POST : http://localhost:8088/api/v1/products
    public ResponseEntity<?> uploadImages(
            @PathVariable("id") Long productId,
            @ModelAttribute("files") List<MultipartFile> files
    ) {

        try {
            Product existingProduct = productService.getProductById(productId);
            //Trường hợp nếu files rỗng => Tạo ra mảng rỗng để lưu
            files = files == null ? new ArrayList<MultipartFile>() : files;

            //Kiểm tra nếu tải lên quá 5 ảnh => Trả về Bad Request
            // ProductImage.MAXIMUM_IMAGES_PER_PRODUCT => thuộc tính bên ProductImage model
            if(files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can only upload maximum 5 images");
            }

            //Khởi tạo danh sách các ProductImage là mảng rỗng
            List<ProductImage> productImages = new ArrayList<>();

            //Duyệt mảng danh sách file ảnh
            for(MultipartFile file : files) {

                //Nếu số lượng của file = 0 => tiếp tục lặp qua
                if(file.getSize() == 0) {
                    continue;
                }

                //Kiểm tra kích thước file và định dạng
                if(file.getSize() > 10 * 24 * 1024) { //Nếu kích thước lớn hơn 10MB => trả về lỗi
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large! Maximum size is 10MB");
                }

                //Lấy định dạng file ảnh
                String contentType = file.getContentType();

                //Kiểm tra contentType có phải là file ảnh hay không?
                if(contentType == null || !contentType.startsWith("image/")) { //Nếu file rỗng và nó bắt đầu kh có image/ => trả về lỗi
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image");
                }

                //Lưu file và cập nhật thumbnail trong DTO
                String filename = storeFile(file);

                //Lưu vào đối tượng product trong DB => Lưu vào bảng product_images
                ProductImage productImage = productService.createProductImage(
                        existingProduct.getId(),
                        ProductImageDTO.builder()
                                .imageUrl(filename)
                                .build()
                );
                productImages.add(productImage);
            }
            return ResponseEntity.ok().body(productImages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    //HÀM LƯU FILE
    private String storeFile(MultipartFile file) throws IOException {
        //Kiểm tra nếu nó không có đuôi là tên file hoặc nó rỗng => Trả về exception
        if(!isImageFile(file) || file.getOriginalFilename() == null) {
            throw  new IOException("Invalid image format");
        }

        //Lấy ra tên file gốc
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        //Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất => Tránh bị trùng tên file khi upload
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;

        //Đường dẫn đến thư mục muốn lưu trữ file
        java.nio.file.Path uploadDir = Paths.get("upload");

        //Kiểm tra và tạo thư mục nếu nó không tồn tại
        if(!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        //Đường dẫn đến file đích
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);

        //Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFilename;
    }


    //Kiểm tra xem đuôi có phải là file ảnh hay không
    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }


    //HÀM LẤY DANH SÁCH SẢN PHẨM
    @GetMapping("")
    public ResponseEntity<ProductListResponse> getProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        //Tạo Pageable từ thông tin của page và limit
        //pageRequest được tạo ra bằng cách truyền 2 tham số page và limit
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                //Sắp xếp theo thứ tự createdAt giảm dần
                Sort.by("createdAt").descending());

        //Lấy ra danh sách sản phẩm
        //--Trả về ProductResponse để hiển thị cho người dùng
        Page<ProductResponse> productPage = productService.getAllProducts(pageRequest);

        //Lấy tổng số trang
        int totalPages = productPage.getTotalPages();

        //Lấy ra danh sách các Product
        //--Trả về ProductResponse để hiển thị cho người dùng
        List<ProductResponse> products = productPage.getContent();

        return ResponseEntity.ok(ProductListResponse.builder()
                .products(products)
                .totalPages(totalPages)
                .build());
    }


    //HÀM LẤY SẢN PHẨM THEO ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long productId) {
        try {
           Product existingProduct =  productService.getProductById(productId);
           return ResponseEntity.ok(ProductResponse.fromProduct(existingProduct));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //HÀM CẬP NHẬT SẢN PHẨM BẰNG ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDTO productDTO) {
        try {
            Product updateProduct = productService.updateProduct(id, productDTO);
            return ResponseEntity.ok(updateProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //HÀM XÓA SẢN PHẨM
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
       try {
           Product existingProduct = productService.getProductById(id);
           if(existingProduct != null) {
               productService.deleteProduct(id);
           }
           return ResponseEntity.ok("Deleted product with ID: " + id + " successfully");
       } catch (Exception e) {
           return ResponseEntity.badRequest().body(e.getMessage());
       }

    }





/*----------------------------------------------------------------------------------------*/
    //Phương thức fake dữ liệu bằng JavaFaker
//    @PostMapping("/generateFakeProducts") //Comment nó lại để vô hiệu hóa -> Khi nào cần thì bỏ cmt
    private ResponseEntity<String> generateFakeProducts() { //Chuyển về private cũng là 1 cách disable
        //Tạo đối tượng Faker
        Faker faker = new Faker();

        //Lặp lại 100 lần
        for(int i = 0; i <100; i++) {
            //Tạo tên sản phẩm nhờ vào thư viện Javafaker
            String productName = faker.commerce().productName();

            //Kiểm tra nếu tên sản phẩm đã tồn tại => Bỏ qua
            if(productService.existsByName(productName)) {
                continue;
            }

            //Vì hàm createProduct() nhận tham số là ProductDTO nên ta phải có ProductDTO
            ProductDTO productDTO = ProductDTO.builder()
                    .name(productName)
                    .thumbnail("")
                    //Random số từ 10 đến 90 triệu
                    .price((float)faker.number().numberBetween(10, 90_000_000))
                    //Dùng hàm lorem()
                    .quantitySold(faker.number().numberBetween(1,100))
                    .categoryId((long)faker.number().numberBetween(2, 3))
                    .build();

            //Vì createProduct sẽ có Exception nên bỏ nó vào try/catch
            try {
                productService.createProduct(productDTO);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        return ResponseEntity.ok("Fake Products created successfully");
    }

}
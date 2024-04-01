package com.project.restaurant.services;

import com.project.restaurant.dtos.OrderDetailDTO;
import com.project.restaurant.exceptions.DataNotFoundException;
import com.project.restaurant.models.Order;
import com.project.restaurant.models.OrderDetail;
import com.project.restaurant.models.Product;
import com.project.restaurant.repositories.OrderDetailRepository;
import com.project.restaurant.repositories.OrderRepository;
import com.project.restaurant.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService
{

    //DI
    private final OrderDetailRepository orderDetailRepository;

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;


    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception {
        //Tìm xem orderId có tồn tại hay không?
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: "
                        + orderDetailDTO.getOrderId()));

        //Tìm xem product_id có tồn tại hay không
        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find Product with id: "
                        + orderDetailDTO.getProductId()));

        //Lấy giá sản phẩm
        float price = product.getPrice();

        //Lấy số lượng sản phẩm
        int numberOfProducts = orderDetailDTO.getNumberOfProducts();

        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .price(price)
                .numberOfProducts(numberOfProducts)
                .totalMoney(numberOfProducts * price)
                .build();

        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetail(Long id) throws DataNotFoundException {
        return orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order detail with id: "+ id));
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        //Tìm xem Order Detail có tồn tại không (Tìm bằng ID)
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order detail with id: " + id));

        //Kiểm tra order_id có thuộc về order nào đó trong bản Order không
        Order existingOrder = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + id));

        //Tìm xem product_id có tồn tại hay không (Bằng cách kiểm tra product_id có thuộc về một product nào không)
        Product existingProduct = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find Product with id: " +orderDetailDTO.getProductId()));

        //Lấy giá sp và số lượng
        float price = existingProduct.getPrice();
        int numberOfProducts = orderDetailDTO.getNumberOfProducts();

        //Tổng tiền
        float totalMoney = price * numberOfProducts;

        //Gán giá trị cho Order Detail
        existingOrderDetail.setOrder(existingOrder);
        existingOrderDetail.setProduct(existingProduct);
        existingOrderDetail.setPrice(price);
        existingOrderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProducts());
        existingOrderDetail.setTotalMoney(totalMoney);

        //Lưu vào DB
        return orderDetailRepository.save(existingOrderDetail);
    }

    @Override
    public void deleteById(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> findByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}

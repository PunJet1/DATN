package com.watch.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.watch.dao.OrderDetailDao;
import com.watch.dao.OrdersDao;
import com.watch.entity.OrderDetail;
import com.watch.entity.Orders;
import com.watch.service.OrdersService;


@Service
public class OrderServiceImpl implements OrdersService{

	@Autowired
	OrdersDao odao;
	@Autowired
	OrderDetailDao ddao;
	 @Autowired 
	public  HttpSession session;
	private List<OrderDetail> details;
	
	@Override
	public List<Orders> findByUsername(String username,Pageable pageable) {
		return odao.findByUsername(username);
	}

	@Override
	public List<Orders> findAll() {
		// TODO Auto-generated method stub
		return odao.findAll();
	}

	@Override
	public Orders create(JsonNode orders) {
		ObjectMapper mapper=new ObjectMapper();
		Orders order=mapper.convertValue(orders,Orders.class);
		order.setVoucher(null);
		order.setStatus(1);
		odao.save(order);	
		TypeReference<List<OrderDetail>> type=new TypeReference<List<OrderDetail>>() {};
		details = mapper.convertValue(orders.get("orderDetails"),type)
				.stream().peek(d->d.setOrder(order)).collect(Collectors.toList());
		ddao.saveAll(details);
		session.setAttribute("listOrderDetail", details);
		Orders order1 = odao.getGanNhat(order.getAccount().getAccountId());
		if(order1.equals(null)) {
			return order;
		}
		//int maOder = order1.getOrderId();
		session.setAttribute("OrderganNhat", order1);
		return order;
	}

	@Override
	public <S extends Orders> S save(S entity) {
		return odao.save(entity);
	}

	@Override
	public Optional<Orders> findById(Integer id) {
		return odao.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		odao.deleteById(id);
	}

	@Override
	public void delete(Orders entity) {
		odao.delete(entity);
	}

	@Override
	public Orders getById(Integer id) {
		return odao.getById(id);
	}

	@Override
	public List<Orders> findByUsername(String username) {
		// TODO Auto-generated method stub
		return odao.findByUsername(username);
	}

	@Override
	public List<Orders> getByIdVoucher(Long accountId) {
		// TODO Auto-generated method stub
		return odao.getByIdVoucher(accountId);
	}

	@Override
	public List<Orders> findByUserId(Long id) {
		// TODO Auto-generated method stub
		return odao.findByUserId(id);
	}

	@Override
	public Page<Orders> getOrderByUserId(Long id, Pageable pageable) {
		// TODO Auto-generated method stub
		return odao.findByUserId2(id,pageable);
	}


	
	

}
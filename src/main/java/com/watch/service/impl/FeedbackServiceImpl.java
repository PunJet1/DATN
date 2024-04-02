package com.watch.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.watch.dao.AccountDao;
import com.watch.dao.FeedbackDao;
import com.watch.dao.ProductDao;
import com.watch.entity.Accounts;
import com.watch.entity.Feedback;
import com.watch.entity.Product;
import com.watch.entity.UserAcounts;
import com.watch.service.FeedbackService;
@Service
public class FeedbackServiceImpl implements FeedbackService{
	@Autowired
	FeedbackDao feedbackDao;
	@Autowired
	ProductDao productDao;
	@Autowired
	AccountDao accountDao;
	@Autowired
	UserAcounts useAcc;
	

	@Override
	public List<Feedback> findByProductID(Integer id) {
		//String feedbackDate;
		// TODO Auto-generated method stub
		Sort sort = Sort.by(Direction.DESC, "feedbackDate");
		return feedbackDao.findByProductID(id,sort);
	}

	@Override
	public List<Feedback> findAll() {
		// TODO Auto-generated method stub
		Sort sort = Sort.by(Direction.DESC, "feedbackDate");
		return feedbackDao.findAll(sort);
	}

	@Override
	public Feedback create(Feedback feedback) {
		// TODO Auto-generated method stub
		//return feedbackDao.save(feedback);
		return feedbackDao.save(feedback);
	}


	@Override
	public Feedback addFeedback(Feedback feedback, Integer id) {
		// TODO Auto-generated method stub
		Accounts account1 = useAcc.User();	
		Long idAc = account1.getAccountId();
		Product product = productDao.findById(id).get();
		Accounts accounts = accountDao.findById(idAc).get();
		
		feedback.setFeedbackDate(new Date());
		feedback.setAccount(accounts);
		feedback.setProduct(product);
		return feedbackDao.save(feedback);
		//return null;
	}

	@Override
	public void delete(Integer id) {
		feedbackDao.deleteById(id);
		
	}

	
}

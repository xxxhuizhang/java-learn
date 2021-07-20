package com.cbhlife.spring.annotation.service;


//import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.cbhlife.spring.annotation.dao.BookDao;


@Service
public class BookService {
	
	//@Qualifier("bookDao")
	//@Autowired(required=false)
	//@Resource(name="bookDao2")
//	@Inject
	private BookDao bookDao;
	
	public void print(){
		System.out.println(bookDao);
	}

	@Override
	public String toString() {
		return "BookService [bookDao=" + bookDao + "]";
	}
	
	
	
	

}

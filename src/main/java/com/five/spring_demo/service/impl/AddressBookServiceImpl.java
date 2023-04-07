package com.five.spring_demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.five.spring_demo.entity.AddressBook;
import com.five.spring_demo.mapper.AddressBookMapper;
import com.five.spring_demo.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}

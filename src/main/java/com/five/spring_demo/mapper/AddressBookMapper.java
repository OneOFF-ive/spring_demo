package com.five.spring_demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.five.spring_demo.entity.AddressBook;
import org.mapstruct.Mapper;

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}

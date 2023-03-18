package com.five.spring_demo.mapper;

import com.five.spring_demo.entity.Book;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BookMapper {

    @Select("select * from book")
    public List<Book> selectBook();

    @Select("select * from book where isbn = #{isbn}")
    public List<Book> selectBookByIsbn(String isbn);

    @Insert("insert into book values(#{isbn}, #{title}, #{author}, #{totalAmount}, #{currentAmount})")
    public int insertBook(Book book);

    @Update("update book set title = #{title}, author = #{author}, totalAmount = #{totalAmount}, currentAmount = #{currentAmount} where isbn = #{isbn}")
    public int updateBook(Book book);

    @Delete("delete from book where isbn = #{isbn}")
    public int deleteBook(String isbn);
}

package com.huifer.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 描述:
 *
 * @author huifer
 * @date 2019-03-09
 */
@Repository(value = "bookLibDao")
public class BookLibDaoImpl implements BookLibDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void update(String bname, String belone, String to) {
        int update = jdbcTemplate.update("UPDATE book SET toname=? WHERE belone=? AND bname=?", to, belone, bname);

    }

    @Override
    public List<Book> query() {
        return jdbcTemplate.query("select * from book", new BookMapper());
    }
}

class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {

        return
                new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("bname"),
                        resultSet.getString("belone"),
                        resultSet.getString("toname")
                );
    }
}
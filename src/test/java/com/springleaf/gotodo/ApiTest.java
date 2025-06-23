package com.springleaf.gotodo;

import com.springleaf.gotodo.mapper.CategoryMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiTest {

    @Resource
    private CategoryMapper categoryMapper;

    @Test
    public void Test() {
        System.out.println(categoryMapper.listCategory());
    }
}

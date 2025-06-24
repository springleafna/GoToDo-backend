package com.springleaf.gotodo;

import com.springleaf.gotodo.mapper.CategoryMapper;
import com.springleaf.gotodo.mapper.DisplayItemMapper;
import com.springleaf.gotodo.mapper.FavoriteMapper;
import com.springleaf.gotodo.model.entity.Task;
import com.springleaf.gotodo.model.vo.DisplayItemVO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ApiTest {

    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private FavoriteMapper favoriteMapper;
    @Resource
    private DisplayItemMapper displayItemMapper;

    @Test
    public void Test() {
        System.out.println(categoryMapper.listCategory());
    }

    @Test
    public void TestFavorite() {
        List<Task> favoriteList = favoriteMapper.getFavoriteList();
        System.out.println(favoriteList.size());
    }
    
    @Test
    public void TestDisplayItem() {
        List<DisplayItemVO> displayItemVOList = displayItemMapper.listDisplayItem();
        for (DisplayItemVO displayItemVO : displayItemVOList) {
            System.out.println(displayItemVO);
        }
    }
}

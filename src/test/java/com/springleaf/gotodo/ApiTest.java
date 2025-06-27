package com.springleaf.gotodo;

import com.springleaf.gotodo.feishu.FeiShu;
import com.springleaf.gotodo.mapper.CategoryMapper;
import com.springleaf.gotodo.mapper.DisplayItemMapper;
import com.springleaf.gotodo.mapper.FavoriteMapper;
import com.springleaf.gotodo.model.entity.Task;
import com.springleaf.gotodo.model.vo.DisplayItemVO;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class ApiTest {

    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private FavoriteMapper favoriteMapper;
    @Resource
    private DisplayItemMapper displayItemMapper;
    @Resource
    private FeiShu feiShu;

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
    
    @Test
    public void getEnv() throws IOException {
        String key = System.getenv("FEISHU_WEBHOOK");
        System.out.println("key: " + key);;
        feiShu.sendTemplateMessage("title", "categoryName","remark", "2025-06-27 10:05","2025-06-27 12:00");
    }
    
    @Test
    public void timeToString() {
        String string = LocalDateTime.now().toString();
        System.out.println(string);
    }
}

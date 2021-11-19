package com.fourwood.toymall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fourwood.toymall.product.entity.BrandEntity;
import com.fourwood.toymall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ToymallProductApplicationTests {

	@Autowired
	BrandService brandService;

	@Test
	void contextLoads() {

		BrandEntity brandEntity = new BrandEntity();
		brandEntity.setDescript("test");
		brandEntity.setName("testName");
		brandService.save(brandEntity);

		brandEntity.setDescript("desc");
		brandService.updateById(brandEntity);

		List<BrandEntity> list = brandService.list();
		list.forEach(it -> System.out.println(it));

		brandService.remove(new QueryWrapper<BrandEntity>());

	}

}

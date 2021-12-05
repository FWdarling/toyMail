package com.fourwood.toymall.product.service.impl;

import com.fourwood.toymall.product.dto.CategoryListTreeDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fourwood.common.utils.PageUtils;
import com.fourwood.common.utils.Query;

import com.fourwood.toymall.product.dao.CategoryDao;
import com.fourwood.toymall.product.entity.CategoryEntity;
import com.fourwood.toymall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryListTreeDto> listWithTree() {
        List<CategoryEntity> entities = baseMapper.selectList(null);
        Map<Long, List<Long>> parentRelation = new HashMap<>();
        Map<Long, CategoryEntity> dtoMap = new HashMap<>();

        entities.stream()
                .filter(entity -> entity.getParentCid() != 0)
                .forEach(entity -> {
                    Long parentCid = entity.getParentCid();
                    if(!parentRelation.containsKey(parentCid)) {
                        parentRelation.put(parentCid, new ArrayList<>());
                    }
                    parentRelation.get(parentCid).add(entity.getCatId());
                    dtoMap.put(entity.getCatId(), entity);
                });

        List<CategoryListTreeDto> dtos = entities.stream()
                .filter(entity -> entity.getParentCid() == 0)
                .map(this::buildDto)
                .peek(parent -> setChildren(parent, parentRelation, dtoMap))
                .sorted(Comparator.comparingInt(CategoryListTreeDto::getSort))
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    public void removeMenuByIds(List<Long> catIds) {

        //TODO 检查删除的节点是否有子结点
        baseMapper.deleteBatchIds(catIds);
    }

    private CategoryListTreeDto buildDto(CategoryEntity entity) {
        CategoryListTreeDto dto = new CategoryListTreeDto();
        BeanUtils.copyProperties(entity, dto);
        dto.setChildren(new ArrayList<>());
        return dto;
    }

    private void setChildren(CategoryListTreeDto root, Map<Long, List<Long>> parentRelation, Map<Long, CategoryEntity> entityMap) {
        List<CategoryListTreeDto> childrenDto = parentRelation
                .getOrDefault(root.getCatId(), new ArrayList<>())
                .stream()
                .map(entityMap::get)
                .map(this::buildDto)
                .collect(Collectors.toList());

        if (!childrenDto.isEmpty()) {
            root.setChildren(
                    childrenDto.stream()
                    .peek(parent -> setChildren(parent, parentRelation, entityMap))
                    .sorted(Comparator.comparingInt(dto -> (dto.getSort() == null ? 0 : dto.getSort())))
                    .collect(Collectors.toList()));
        }
    }

}
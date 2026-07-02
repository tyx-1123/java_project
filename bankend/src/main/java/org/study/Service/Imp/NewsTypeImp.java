package org.study.Service.Imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.Mapper.NewsTypeMapper;
import org.study.Service.NewsTypeService;
import org.study.entity.NewsType;

@Service
public class NewsTypeImp implements NewsTypeService {

    @Autowired
    private NewsTypeMapper newsTypeMapper;

    @Override
    public List<NewsType> findAllTypes() {
        return newsTypeMapper.selectList(null);
    }
}

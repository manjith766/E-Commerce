package com.manjith.service;

import com.manjith.entity.Home;
import com.manjith.entity.HomeCategory;

import java.util.List;

public interface HomeService {
    Home createHomePageData(List<HomeCategory> allCategories);

}

package com.github.huifer.full.shiro.service;

import com.github.huifer.full.shiro.entity.ShiroCompany;
import com.github.huifer.full.shiro.model.req.company.CompanyCreateParam;

public interface CompanyService {


  boolean create(CompanyCreateParam param);

  boolean update(CompanyCreateParam param, int id);

  boolean delete(int id);

  ShiroCompany byId(int id);

}

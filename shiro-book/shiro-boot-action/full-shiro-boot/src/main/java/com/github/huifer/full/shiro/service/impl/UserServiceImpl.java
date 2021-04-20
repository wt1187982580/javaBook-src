package com.github.huifer.full.shiro.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.huifer.full.shiro.dao.ShiroUserDao;
import com.github.huifer.full.shiro.entity.ShiroUser;
import com.github.huifer.full.shiro.ex.ServerEx;
import com.github.huifer.full.shiro.model.req.user.UserCreateParam;
import com.github.huifer.full.shiro.service.UserService;
import com.github.huifer.full.shiro.utils.EncryptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private ShiroUserDao userDao;

  @Override
  public boolean create(
      UserCreateParam param) {
    String loginName = param.getLoginName();
    if (!StringUtils.hasText(loginName)) {
      throw new ServerEx("login name not null");
    }
    ShiroUser shiroUserEntityByLoginName = userDao
        .findShiroUserEntityByLoginName(param.getLoginName());
    if (shiroUserEntityByLoginName == null) {

      ShiroUser shiroUser = new ShiroUser();
      shiroUser.setLoginName(param.getLoginName());
      shiroUser.setUsername(param.getUsername());
      String salt = EncryptionUtils.randomSalt(EncryptionUtils.SLAT_LEN);
      shiroUser.setPassword(EncryptionUtils.genMD5Hash(param.getPassword(), salt));
      shiroUser.setSalt(salt);
      shiroUser.setEmail(param.getEmail());
      shiroUser.setGender(param.getGender());
      return userDao.insert(shiroUser) > 0;
    }
    throw new ServerEx("登录名已存在");
  }


  @Override
  public boolean update(UserCreateParam param, int id) {
    ShiroUser byId = userDao.selectById(id);
    if (byId != null) {
      byId.setUsername(param.getUsername());
      String salt = EncryptionUtils.randomSalt(EncryptionUtils.SLAT_LEN);
      byId.setPassword(EncryptionUtils.genMD5Hash(param.getPassword(), salt));
      byId.setSalt(salt);
      byId.setEmail(param.getEmail());
      byId.setGender(param.getGender());
      int i = userDao.updateById(byId);
      return i > 0;
    }
    throw new ServerEx("当前id对应用户不存在");

  }

  @Override
  public boolean delete(int id) {
    return this.userDao.deleteById(id) > 0;
  }

  @Override
  public Page<ShiroUser> findByUserList(
      String username, String loginName, Integer gender, String email, int start, int offset) {
    IPage<ShiroUser> userPage = new Page<>(start, offset);
    return this.userDao
        .findByUserList(username, loginName, gender, email, userPage);
  }

  @Override
  public ShiroUser byId(int id) {
    return this.userDao.selectById(id);
  }
}

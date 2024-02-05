package com.hmzhkj.system.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.hmzhkj.system.domain.SysRole;
import com.hmzhkj.system.domain.SysUser;
import com.hmzhkj.system.domain.vo.BreadcrumbVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hmzhkj.common.core.constant.Constants;
import com.hmzhkj.common.core.constant.UserConstants;
import com.hmzhkj.common.core.utils.StringUtils;
import com.hmzhkj.framework.utils.SecurityUtils;
import com.hmzhkj.system.domain.SysMenu;
import com.hmzhkj.system.domain.vo.MetaVo;
import com.hmzhkj.system.domain.vo.RouterVo;
import com.hmzhkj.system.domain.vo.TreeSelect;
import com.hmzhkj.system.mapper.SysMenuMapper;
import com.hmzhkj.system.mapper.SysRoleMapper;
import com.hmzhkj.system.mapper.SysRoleMenuMapper;
import com.hmzhkj.system.service.ISysMenuService;

 
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl implements ISysMenuService
{
    public static final String PREMISSION_STRING = "perms[\"{0}\"]";

    private final SysMenuMapper menuMapper;

    private final SysRoleMapper roleMapper;

    private final SysRoleMenuMapper roleMenuMapper;

     
    @Override
    public List<SysMenu> selectMenuList(String userId) {
        return selectMenuList(new SysMenu(), userId);
    }

     
    @Override
    public List<SysMenu> selectMenuList(SysMenu menu, String userId){
        String menuName = menu.getMenuName();
        if(!StringUtils.isEmpty(menuName)){
            menu.setMenuName(menuName.trim());
        }
        List<SysMenu> menuList = null;
                 if (SysUser.isAdmin(userId))
        {
            menuList = menuMapper.selectMenuList(menu);
        }
        else
        {
            menu.getParams().put("userId", userId);
            menuList = menuMapper.selectMenuListByUserId(menu);
        }
        return menuList;
    }

     
    @Override
    public Set<String> selectMenuPermsByUserId(String userId)
    {
        List<String> perms = menuMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms)
        {
            if (StringUtils.isNotEmpty(perm))
            {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

     
    @Override
    public Set<String> selectMenuPermsByRoleId(String roleId)
    {
        List<String> perms = menuMapper.selectMenuPermsByRoleId(roleId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms)
        {
            if (StringUtils.isNotEmpty(perm))
            {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

     
    @Override
    public List<SysMenu> selectMenuTreeByUserId(String userId)
    {
        List<SysMenu> menus = null;
        if (SecurityUtils.isAdmin(userId))
        {
            menus = menuMapper.selectMenuTreeAll();
        }
        else
        {
            menus = menuMapper.selectMenuTreeByUserId(userId);
        }
        return getChildPerms(menus, "0");
    }

     
    @Override
    public List<String> selectMenuListByRoleId(String roleId)
    {
        SysRole role = roleMapper.selectRoleById(roleId);
        return menuMapper.selectMenuListByRoleId(roleId, role.isMenuCheckStrictly());
    }

     
    @Override
    public List<RouterVo> buildMenus(List<SysMenu> menus)
    {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for (SysMenu menu : menus)
        {
            RouterVo router = new RouterVo();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setQuery(menu.getQuery());
            boolean noCache = true;
            if(menu.getIsCache()!=null && menu.getIsCache()==1){
                noCache =false;
            }
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), noCache, menu.getPath()));
            List<SysMenu> cMenus = menu.getChildren();
            List<BreadcrumbVo> breadcrumbList = new ArrayList<>();
            SysMenu breadcrumb = menu.getBreadcrumbParent();
            while (breadcrumb!=null){
                breadcrumbList.add(new BreadcrumbVo(breadcrumb));
                breadcrumb = breadcrumb.getBreadcrumbParent();
            }
            if (!cMenus.isEmpty() && cMenus.size() > 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType()))
            {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            }
            else if (isMenuFrame(menu))
            {
                router.setMeta(null);
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setName(StringUtils.capitalize(menu.getPath()));
                MetaVo metaVo = new MetaVo(menu.getMenuName(), menu.getIcon(), noCache, menu.getPath());
                metaVo.setBreadcrumbList(breadcrumbList);
                children.setMeta(metaVo);
                children.setQuery(menu.getQuery());
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            else if ("0".equals(menu.getParentId()) && isInnerLink(menu))
            {
                MetaVo metaVo =new MetaVo(menu.getMenuName(), menu.getIcon());
                metaVo.setBreadcrumbList(breadcrumbList);
                router.setMeta(metaVo);
                router.setPath("/");
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                String routerPath = innerLinkReplaceEach(menu.getPath());
                children.setPath(routerPath);
                children.setComponent(UserConstants.INNER_LINK);
                children.setName(StringUtils.capitalize(routerPath));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), menu.getPath()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

     
    @Override
    public List<SysMenu> buildMenuTree(List<SysMenu> menus)
    {
        List<SysMenu> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        for (SysMenu dept : menus)
        {
            tempList.add(dept.getMenuId());
        }
        for (Iterator<SysMenu> iterator = menus.iterator(); iterator.hasNext();)
        {
            SysMenu menu = (SysMenu) iterator.next();
                         if (!tempList.contains(menu.getParentId()))
            {
                recursionFn(menus, menu);
                returnList.add(menu);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = menus;
        }
        return returnList;
    }

     
    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus)
    {
        List<SysMenu> menuTrees = buildMenuTree(menus);
        return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

     
    @Override
    public SysMenu selectMenuById(String menuId)
    {
        return menuMapper.selectMenuById(menuId);
    }

     
    @Override
    public boolean hasChildByMenuId(String menuId)
    {
        int result = menuMapper.hasChildByMenuId(menuId);
        return result > 0;
    }

     
    @Override
    public boolean checkMenuExistRole(String menuId)
    {
        int result = roleMenuMapper.checkMenuExistRole(menuId);
        return result > 0;
    }

     
    @Override
    public int insertMenu(SysMenu menu)
    {
        menu.genId();
        return menuMapper.insertMenu(menu);
    }

     
    @Override
    public int updateMenu(SysMenu menu)
    {
        return menuMapper.updateMenu(menu);
    }

     
    @Override
    public int deleteMenuById(String menuId)
    {
        return menuMapper.deleteMenuById(menuId);
    }

     
    @Override
    public String checkMenuNameUnique(SysMenu menu)
    {
        SysMenu info = menuMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
        if (StringUtils.isNotNull(info) && !info.getMenuId().equals(menu.getMenuId()))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

     
    public String getRouteName(SysMenu menu)
    {
        String routerName = StringUtils.capitalize(menu.getPath());
                 if (isMenuFrame(menu))
        {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

     
    public String getRouterPath(SysMenu menu)
    {
        String routerPath = menu.getPath();
                 if (!"0".equals(menu.getParentId()) && isInnerLink(menu))
        {
            routerPath = innerLinkReplaceEach(routerPath);
        }
                 if ("0".equals(menu.getParentId()) && UserConstants.TYPE_DIR.equals(menu.getMenuType())
                && menu.getIsFrame().equals(0))
        {
            routerPath = "/" + menu.getPath();
        }
                 else if (isMenuFrame(menu))
        {
            routerPath = "/";
        }
        return routerPath;
    }

     
    public String getComponent(SysMenu menu)
    {
        String component = UserConstants.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu))
        {
            component = menu.getComponent();
        }
        else if (StringUtils.isEmpty(menu.getComponent()) && !"0".equals(menu.getParentId()) && isInnerLink(menu))
        {
            component = UserConstants.INNER_LINK;
        }
        else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu))
        {
            component = UserConstants.PARENT_VIEW;
        }
        return component;
    }

     
    public boolean isMenuFrame(SysMenu menu)
    {
        return "0".equals(menu.getParentId()) && UserConstants.TYPE_MENU.equals(menu.getMenuType())
                && menu.getIsFrame().equals(0);
    }

     
    public boolean isInnerLink(SysMenu menu)
    {
        return menu.getIsFrame().equals(0) && StringUtils.ishttp(menu.getPath());
    }

     
    public boolean isParentView(SysMenu menu)
    {
        return ! "0".equals(menu.getParentId()) && UserConstants.TYPE_DIR.equals(menu.getMenuType());
    }

     
    public List<SysMenu> getChildPerms(List<SysMenu> list, String parentId)
    {
        List<SysMenu> returnList = new ArrayList<>();
        for (Iterator<SysMenu> iterator = list.iterator(); iterator.hasNext();)
        {
            SysMenu t = iterator.next();
                         if (t.getParentId().equals(parentId))
            {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

     
    private void recursionFn(List<SysMenu> list, SysMenu t)
    {
                 List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

     
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t)
    {
        List<SysMenu> tlist = new ArrayList<SysMenu>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext())
        {
            SysMenu n =  it.next();
            if (n.getParentId().equals(t.getMenuId()))
            {
                tlist.add(n);
            }
                         if(t.getMenuId().equals(n.getBreadcrumbParentId())){
                n.setBreadcrumbParent(t);
            }
        }
        return tlist;
    }

     
    private boolean hasChild(List<SysMenu> list, SysMenu t)
    {
        return getChildList(list, t).size() > 0;
    }

     
    public String innerLinkReplaceEach(String path)
    {
        return StringUtils.replaceEach(path, new String[] { Constants.HTTP, Constants.HTTPS, Constants.WWW, "." },
                new String[] { "", "", "", "/" });
    }
}

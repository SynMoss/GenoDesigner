import store from '@/store'
import router from '@/router';

export default {
  refreshPage(obj) {
    const { path, query, matched } = router.currentRoute;
    if (obj === undefined) {
      matched.forEach((m) => {
        if (m.components && m.components.default && m.components.default.name) {
          if (!['Layout', 'ParentView'].includes(m.components.default.name)) {
            obj = { name: m.components.default.name, path: path, query: query };
          }
        }
      });
    }
    return store.dispatch('tagsView/delCachedView', obj).then(() => {
      const { path, query } = obj
      router.replace({
        path: '/redirect' + path,
        query: query
      })
    })
  },
  closeOpenPage(obj) {
    store.dispatch("tagsView/delView", router.currentRoute);
    if (obj !== undefined) {
      return router.push(obj);
    }
  },
  closePage(obj) {
    if (obj === undefined) {
      return store.dispatch('tagsView/delView', router.currentRoute).then(({ lastPath }) => {
        return router.push(lastPath || '/');
      });
    }
    return store.dispatch('tagsView/delView', obj);
  },
  closeAllPage() {
    return store.dispatch('tagsView/delAllViews');
  },
  closeLeftPage(obj) {
    return store.dispatch('tagsView/delLeftTags', obj || router.currentRoute);
  },
  closeRightPage(obj) {
    return store.dispatch('tagsView/delRightTags', obj || router.currentRoute);
  },
  closeOtherPage(obj) {
    return store.dispatch('tagsView/delOthersViews', obj || router.currentRoute);
  },
  openPage(title, url, params) {
    var obj = { path: url, meta: { title: title } }
    store.dispatch('tagsView/addView', obj);
    return router.push({ path: url, query: params });
  },
  updatePage(obj) {
    return store.dispatch('tagsView/updateVisitedView', obj);
  }
}

import store from '@/store'

function authPermission(permission) {
  const all_permission = "*:*:*";
  const permissions = store.getters && store.getters.permissions
  if (permission && permission.length > 0) {
    return permissions.some(v => {
      return all_permission === v || v === permission
    })
  } else {
    return false
  }
}

function authRole(role) {
  const super_admin = "admin";
  const roles = store.getters && store.getters.roles
  if (role && role.length > 0) {
    return roles.some(v => {
      return super_admin === v || v === role
    })
  } else {
    return false
  }
}

export default {
  hasPermi(permission) {
    return authPermission(permission);
  },
  hasPermiOr(permissions) {
    return permissions.some(item => {
      return authPermission(item)
    })
  },
  hasPermiAnd(permissions) {
    return permissions.every(item => {
      return authPermission(item)
    })
  },
  hasRole(role) {
    return authRole(role);
  },
  hasRoleOr(roles) {
    return roles.some(item => {
      return authRole(item)
    })
  },
  hasRoleAnd(roles) {
    return roles.every(item => {
      return authRole(item)
    })
  }
}

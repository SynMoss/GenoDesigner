import { login, login2,logout, getInfo, refreshToken } from '@/api/login'
import { getToken, setToken, setExpiresIn, removeToken } from '@/utils/auth'

const user = {
  state: {
    token: getToken(),
    name: '',
    avatar: '',
    roles: [],
    permissions: [],
    nickName: '',
    userId: '',
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_EXPIRES_IN: (state, time) => {
      state.expires_in = time
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_NICK_NAME: (state, nickName) => {
      state.nickName = nickName
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_PERMISSIONS: (state, permissions) => {
      state.permissions = permissions
    },
    LOGIN_ID: (state, userId) => {
      state.userId = userId
    },
  },

  actions: {
        Login({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        login(userInfo).then((res) => {
            let data = res.data
            setToken(data.access_token)
            commit('SET_TOKEN', data.access_token)
            setExpiresIn(data.expires_in)
            commit('SET_EXPIRES_IN', data.expires_in)
            resolve()
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
        Login2({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        login2(userInfo).then((res) => {
            let data = res.data
            setToken(data.access_token)
            commit('SET_TOKEN', data.access_token)
            setExpiresIn(data.expires_in)
            commit('SET_EXPIRES_IN', data.expires_in)
            resolve()
          })
          .catch((error) => {
            reject(error)
          })
      })
    },

        GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo()
          .then((res) => {
            const user = res.user
            const avatar =
              user.avatar == '' || user.avatar == null
                ? require('@/assets/logo/logo.png')
                : user.avatar
            if (res.roles && res.roles.length > 0) {
                            commit('SET_ROLES', res.roles)
              commit('SET_PERMISSIONS', res.permissions)
            } else {
              commit('SET_ROLES', ['ROLE_DEFAULT'])
            }
            commit('LOGIN_ID', user.userId)
            commit('SET_NAME', user.userName)
            commit('SET_NICK_NAME', user.nickName)
            commit('SET_AVATAR', avatar)
            resolve(res)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },

        RefreshToken({ commit, state }) {
      return new Promise((resolve, reject) => {
        refreshToken(state.token)
          .then((res) => {
            setExpiresIn(res.data)
            commit('SET_EXPIRES_IN', res.data)
            resolve()
          })
          .catch((error) => {
            reject(error)
          })
      })
    },

        LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token)
          .then(() => {
            commit('SET_TOKEN', '')
            commit('SET_ROLES', [])
            commit('SET_PERMISSIONS', [])
            removeToken()
            resolve()
          })
          .catch((error) => {
            reject(error)
          })
      })
    },

        FedLogOut({ commit }) {
      return new Promise((resolve) => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    },
  },
}

export default user

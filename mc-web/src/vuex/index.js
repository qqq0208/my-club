import {createStore} from 'vuex'

const vuex = createStore({
    // 共享变量区：存储共享变量，在JS中可以获取变量，但不能修改
    // JS获取方式：vuex.state['变量名']
    state: {
        // 用户登录状态变量: 若sessionStorage中存在token则为true，反之为false
        loginFlag: !!sessionStorage.getItem('token')
    },
    // 同步方法区：用于修改 state 区的共享变量
    // JS调用方式：vuex.commit('方法名')
    mutations: {
        // 该方法用于设置用户登录状态变量
        setLoginFlag: (state, loginFlag) => state.loginFlag = loginFlag
    },
    // 异步方法区：mutations 区的上层封装，额外支持异步调用
    // JS调用方式：vuex.dispatch("方法名")
    actions: {
        // 异步调用mutations中的 `setLoginFlag()` 方法
        setLoginFlag: async (context, loginFlag) => await context.commit('setLoginFlag', loginFlag)
    },
    // 用于在大型项目中将vuex拆分成多个子模块
    modules: {},
    // Vuex基于state的派生状态，可理解为组件中的计算属性
    getters: {}
});

export default vuex;
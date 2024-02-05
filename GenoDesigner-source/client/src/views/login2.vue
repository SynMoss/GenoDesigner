<template>
  <div class="login">
    <div class="login-flex">
      <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
        <h3 class="title" v-text="language==='zh'?zh_list.D:'Welcome to use this system!'"></h3>
        <el-form-item :label="language==='zh'?zh_list.E:'Username'" prop="username">
          <el-input
            v-model="loginForm.username"
            type="text"
            auto-complete="off"
            :placeholder="language==='zh'?zh_list.Username:'Username'"
          >
            <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
          </el-input>
        </el-form-item>
        <el-form-item :label="language==='zh'?zh_list.F:'Password'" prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            auto-complete="off"
            :placeholder="language==='zh'?zh_list.F:'Password'"
            @keyup.enter.native="handleLogin"
          >
            <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
          </el-input>
        </el-form-item>
        <el-form-item prop="code" v-if="captchaEnabled">
          <el-input
            v-model="loginForm.code"
            auto-complete="off"

            :placeholder="language==='zh'?zh_list.G:'verification code'"
            style="width: 63%"
            @keyup.enter.native="handleLogin"
          >
            <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
          </el-input>
          <div class="login-code">
            <img :src="codeUrl" @click="getCode" class="login-code-img"/>
          </div>
        </el-form-item>
        <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;" :label="language==='zh'?zh_list.H:'automatic login'"></el-checkbox>
        <el-form-item style="width:100%;">
          <el-button
            :loading="loading"
            size="medium"
            type="primary"
            style="width:100%;font-size:18px;height:45px;"
            @click.native.prevent="handleLogin"
          >
            <span v-if="!loading" v-text="language==='zh'?zh_list.I:'Enter the System'"></span>
            <span v-else v-text="language==='zh'?zh_list.J:'Entering...'"></span>
          </el-button>
          <div style="float: left">
            <!-- <router-link class="link-type" :to="'/forgetPassword'"
              >{{ language==='zh'?zh_list.forgetpassword:'forget password' }}</router-link
            > -->
          </div>
          <div style="float: right;" v-if="register">
            <router-link class="link-type" :to="'/register'">{{ language==='zh'?zh_list.K:'register' }}</router-link>
          </div>

        </el-form-item>
      </el-form>
    </div>
            </div>
</template>

<script>
import { getCodeImg } from "@/api/login";
import { storage } from '@/utils/auth'
import { zh_list } from "@/data/constant";
export default {
  name: "Login",
  data() {
    return {
      zh_list,
      language: process.env.VUE_APP_LANGUAGE,
      codeUrl: "",
      loginForm: {
        username: "",
        password: "",
        rememberMe: true,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "Please enter your account number" }
        ],
        password: [
          { required: true, trigger: "blur", message: "please enter your PIN" }
        ],
        code: [{ required: true, trigger: "change", message: "Please enter the verification code" }]
      },
      loading: false,
            captchaEnabled: true,
            register: true,
      redirect: undefined
    };
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
        console.log('redirect',this.redirect)
      },
      immediate: true
    }
  },
  created() {
    let rememberMe = storage("rememberMe")
    if(rememberMe!==null){
      this.loginForm.rememberMe = rememberMe
    }
        if(process.env.VUE_APP_LOGIN==='f'){
      this.loginForm.username="123@qq.com"
      this.loginForm.password="ab123456"
      this.$store.dispatch("Login", this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || "/" }).catch(()=>{});
          }).catch(() => {
          });
    }
    this.getCode();
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled;
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.loginForm.uuid = res.uuid;
        }
      });
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          this.loginForm.username = this.loginForm.username.trim()
          storage("rememberMe",this.loginForm.rememberMe)
          this.$store.dispatch("Login", this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || "/" }).catch(()=>{});
          }).catch(() => {
            this.loading = false;
            if (this.captchaEnabled) {
              this.getCode();
            }
          });
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
    background-size: cover;
  background-color: #f6fafb;
}
.title {
  margin: 20px auto 24px 0;
  text-align: left;
  font-size:20px;
  font-weight: 400;
  color: #333;
}
.login-flex{
  display: flex;
  align-items: center;
  border-radius: 5px;
box-shadow: 0px 2px 2px 0px rgba(0,0,0,0.20);
overflow: hidden;
  background: #ffffff;
  position: relative;
  min-height: 574px;
  max-width: 990px;
  //padding-left: 396px;
    .login-bac{
  width: 396px;
  height: 574px;
  position: absolute;
  left: 0;
  top: 0;
}

}

.login-form {

  padding: 25px 100px 5px 100px;
  width: 594px;
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-size: 12px;
  letter-spacing: 1px;
}
.login-code-img {
  height: 38px;
}
</style>

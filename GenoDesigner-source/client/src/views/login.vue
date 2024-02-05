<template>
  <div class="login">
    <div class="login-flex">
      <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
        <h3 class="title">Welcome to use this system!</h3>
        <el-form-item label="Username" prop="username">
          <el-input
            v-model="loginForm.username"
            type="text"
            auto-complete="off"
            placeholder="Username"
            @keyup.enter.native="handleLogin"
            maxlength="40"
            show-word-limit
          >
            <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
          </el-input>
        </el-form-item>
                 <input type="text" style="display:none;">
        <el-form-item prop="code" v-if="captchaEnabled">
          <el-input
            v-model="loginForm.code"
            auto-complete="off"
            placeholder="verification code"
            style="width: 63%"

          >
            <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
          </el-input>
          <div class="login-code">
            <img :src="codeUrl" @click="getCode" class="login-code-img"/>
          </div>
        </el-form-item>
                 <el-form-item style="width:100%;">
          <el-button
            :loading="loading"
            size="medium"
            type="primary"
            style="width:100%;font-size:18px;height:45px;"
            @click.native.prevent="handleLogin"
          >
            <span v-if="!loading">Enter the system</span>
            <span v-else>Entering...</span>
          </el-button>

        </el-form-item>
      </el-form>
      <img class="login-bac" src="@/assets/images/login01.png" alt="">
    </div>
            </div>
</template>

<script>
import { getCodeImg } from "@/api/login";
import { storage } from '@/utils/auth'
import {addUser} from "@/api/system/user";
import { checkUserEmail } from "@/api/system/user";
export default {
  name: "Login",
  data() {
    return {
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
          { required: true, trigger: "blur", message: "Please enter your username" },
        ],
        password: [
          { required: true, trigger: "blur", message: "please enter your PIN" }
        ],
        code: [{ required: true, trigger: "change", message: "Please enter the verification code" }]
      },
      loading: false,
            captchaEnabled: true,
            register: true,
      redirect: undefined,
      userForm:''
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
          const password = 'eiGgNMyVO5NPGV0e';
          this.$store.dispatch("Login2", {email:this.loginForm.username,password:password}).then(() => {
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
  flex-direction:column;
  align-items: center;
  border-radius: 5px;
  box-shadow: 0px 2px 2px 0px rgba(0,0,0,0.20);
  overflow: hidden;
  background: #ffffff;
  position: relative;
  min-height: 450px;
  max-width: 990px;
     .login-bac{
    width: 480px;
                          }
}
.login-form {

  padding: 25px 50px 5px 50px;
  width: 580px;
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

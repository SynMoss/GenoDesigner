<template>
  <div class="login">
    <div class="login-flex">
        <el-form
        ref="forgetForm"
        :model="forgetForm"
        :rules="language==='zh'?forgetZhRules:forgetEnRules"
        class="register-form"
      >
        <h3 class="title"  v-text="language==='zh'?zh_list.forgetpassword:'forget password'"></h3>

            <div>
              <el-form-item prop="username" >
                <el-input
                  v-model="forgetForm.username"
                  type="text"
                  auto-complete="off"
                  :placeholder="language==='zh'?zh_list.Pleaseenteryourusername:'Username'"
                ></el-input>
              </el-form-item>

                             <el-form-item prop="password">
                <el-input
                  v-model="forgetForm.password"
                  type="password"
                  auto-complete="new-password"
                  :placeholder="language==='zh'?zh_list.Pleaseenterpassword:'Password'"
                  @keyup.native="passwordStrength()"
                  maxlength="20"
                  show-password
                ></el-input>
                <el-progress
                  :style="styleVlue"
                  :percentage="this.percentage"
                  :format="format"
                  :color="this.customColors"
                  v-if="progressFlag"
                ></el-progress>
              </el-form-item>


              <el-form-item prop="confirmPassword">
                <el-input
                  v-model="forgetForm.confirmPassword"
                  type="password"
                  auto-complete="new-password"
                  :placeholder="language==='zh'?zh_list.OKpassword:'OK password'"
                  maxlength="20"
                  show-password
                ></el-input>
              </el-form-item>
            </div>

        <el-form-item style="width: 100%">
          <el-button
            :loading="loading"
            size="medium"
            style="width: 48%"
            @click="back"
          >
            <span v-text="language==='zh'?zh_list.back:'back'"></span>
          </el-button>
          <el-button
            :loading="loading"
            size="medium"
            type="primary"
            style="width: 48%"
            @click.native.prevent="handleReset"
          >
            <span v-if="!loading" v-text="language==='zh'?zh_list.confirm:'confirm'"></span>
            <span v-else v-text="language==='zh'?zh_list.confirming:'confirm...'"></span>
          </el-button>
        </el-form-item>
      </el-form>
    </div>
            </div>
</template>
<script>
import { getEmailCode, reset } from "@/api/login";
import { checkUserEmail } from "@/api/system//user";
import { getAction } from "@/api/manage";
import { zh_list } from "@/data/constant";
export default {
  name: "Register",
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.registerForm.password !== value) {
        let messageZh=zh_list.Thetwopasswordsaredifferent
        let messageEn="The two passwords are different"
        let message;
        if(this.language==="zh"){
          message=messageZh
        }
        else{
          message=messageEn
        }
        callback(new Error(message));
      } else {
        callback();
      }
    };
    const checkUserName = (rele, value, cb) => {
      const reg = /^[a-zA-Z0-9_@-]{2,20}$/;
      if (reg.test(value)) {
        checkUserEmail({ userName: this.forgetForm.username, email: this.forgetForm.email }).then((res) =>{
        if (res.code == 200 && !res.data){
          let messageZh=zh_list.Usernameisnotregistered
        let messageEn="User name is not registered"
        let message;
        if(this.language==="zh"){
          message=messageZh
        }
        else{
          message=messageEn
        }
        cb(new Error(message));

        }else{
          cb();
        }
        })
      } else {
        let messageZh=zh_list.UsernameCheck
        let messageEn="The value can contain only letters, digits, underscores (_), @, and hyphens (-). The value can contain 2 to 20 characters"
        let message;
        if(this.language==="zh"){
          message=messageZh
        }
        else{
          message=messageEn
        }
        cb(new Error(message));
      }
    };

    const checkPassword = (rule, value, cb) => {


      const illegalCharReg = /[^0-9a-zA-Z`~!@#$^&*()=|{}':;',.<>《》/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？\\\[\]]/;
      if (illegalCharReg.test(value)) {
        let messageZh=zh_list.checkPassword1
        let messageEn="The value can contain only digits, lowercase letters, uppercase letters, and special characters"
        let message;
        if(this.language==="zh"){
          message=messageZh
        }
        else{
          message=messageEn
        }
        cb(new Error(message));
        return;
      }
      let count = 0;
      if(/\d/.test(value)){
        count++;
      }
      if(/[a-z]/.test(value)){
        count++;
      }
      if(/[A-Z]/.test(value)){
        count++;
      }
      if(/[`~!@#$^&*()=|{}':;',.<>《》/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？\\\[\]]/.test(value)){
        count++;
      }
      if (count>1 && (value.length>=6 && value.length<=20)) {
        cb();
      } else {
        let messageZh=zh_list.checkPassword2
        let messageEn="The value can contain two types of digits, uppercase letters, lowercase letters, and special characters. The value is a string of 6 to 20 characters"
        let message;
        if(this.language==="zh"){
          message=messageZh
        }
        else{
          message=messageEn
        }
        cb(new Error(message));
      }
    };
    return {
      zh_list:zh_list,
      language: process.env.VUE_APP_LANGUAGE,
      codeUrl: "",
      forgetForm: {
        password: "",
        confirmPassword: "",
        code: "",
        uuid: "",
        email: "",
        phoneNumber: "",
        pwdFlag:false,
      },
      forgetZhRules: {
        username: [
          { required: true, trigger: "blur", message: zh_list.Pleaseenteryourusername2 },
          { validator: checkUserName, trigger: "blur" },
        ],

        password: [
          { required: true, trigger: "blur", message: zh_list.Pleaseenterpassword2 },
          {
            min: 6,
            max: 20,
            message: zh_list.checkPassword2,
            validator: checkPassword,
            trigger: "blur",
          },
        ],
        confirmPassword: [
          { required: true, trigger: "blur", message: zh_list.enteryourpasswordagain },
          { required: true, validator: equalToPassword, trigger: "blur" },
        ],
        code: [{ required: true, trigger: "change", message: zh_list.entertheverificationcode }],
      },

      forgetEnRules: {
        username: [
          { required: true, trigger: "blur", message: "Please enter your username" },
          { validator: checkUserName, trigger: "blur" },
        ],

        password: [
          { required: true, trigger: "blur", message: "Please Enter your password" },
          {
            min: 6,
            max: 20,
            message: "The value can contain two types of digits, uppercase letters, lowercase letters, and special characters. The value is a string of 6 to 20 characters",
            validator: checkPassword,
            trigger: "blur",
          },
        ],
        confirmPassword: [
          { required: true, trigger: "blur", message: "Please enter your password again" },
          { required: true, validator: equalToPassword, trigger: "blur" },
        ],
        code: [{ required: true, trigger: "change", message: "Please enter the verification code" }],
      },
      loading: false,
      captchaEnabled: true,

      options: [
        {
          value: "+86",
          label: "+86",
        },
        {
          value:"+852",
          label:"+852"
        },
        {
          value: "+853",
          label: "+853",
        },
        {
          value: "+886",
          label: "+886",
        },
        {
          value: "+82",
          label: "+82",
        },
        {
          value: "+81",
          label: "+81",
        },
        {
          value: "+1",
          label: "+1",
        },
        {
          value: "+49",
          label: "+49",
        },
        {
          value: "+39",
          label: "+39",
        },
        {
          value: "+44",
          label: "+44",
        },
        {
          value: "+33",
          label: "+33",
        },
        {
          value: "+7",
          label: "+7",
        }
      ],
      value: "+86",

      validateButtonText: zh_list.getcode,
      totalTime: 30,
      canClick: false,
      percentage: 0,
      customColors: [
        { color: "#f56c6c", percentage: 34 },
        { color: "#e6a23c", percentage: 67 },
        { color: "#5cb87a", percentage: 101 },
      ],
      progressFlag:false,
      state: {
        time: 30,
        smsSendBtn: false,
        text: zh_list.getcode,
      }
    };
  },
  created() {},
  methods: {
    getCode() {
      let validateFieldList = [];
      this.$refs.forgetForm.validateField(

        ["username"],
        async (valid) => {
          if (!valid) {

            validateFieldList.push(valid);
            if (
              validateFieldList.length == 1 &&
              validateFieldList.every((item) => item === "")
            ) {
              checkUserEmail({
                email: this.forgetForm.email,
              }).then((res) => {
                if (res.code == 200 && res.data) {

                  if (this.canClick) return;
                  this.canClick = true;
                  this.validateButtonText = this.totalTime + this.zh_list.ssend;
                  let clock = window.setInterval(() => {
                    this.totalTime--;
                    this.validateButtonText = this.totalTime + this.zh_list.ssend;
                    if (this.totalTime < 0) {
                      window.clearInterval(clock);
                      this.validateButtonText = this.zh_list.Resendtheverificationcode;
                      this.totalTime = 30;
                      this.canClick = false;
                    }
                  }, 1000);

                  getEmailCode({ email: this.forgetForm.email, type: 1 }).then(
                    (res) => {

                      if (res.code == 200) {
                        this.forgetForm.uuid = res.data;
                        this.$message.success(this.zh_list.sentsuccessfully);
                      } else {
                        this.$message.error(this.zh_list.sentFailed);
                      }
                    }
                  );
                } else {
                  this.$message.error(this.zh_list.Thisemailisnotregistered);
                }
              });
            }

            return;
          }
        }
      );
    },
    getCaptcha(e) {

      e.preventDefault();
      let validateFieldList = [];
      let that = this;
      that.$refs.forgetForm.validateField(["phoneNumber"], (valid) => {
        if (!valid) {
          validateFieldList.push(valid);

          if (
            validateFieldList.length == 1 &&
            validateFieldList.every((item) => item === "")
          ) {
            if (that.state.smsSendBtn) return;
            checkUserEmail({
              email: this.forgetForm.phoneNumber,
            }).then((res) => {
              if (res.code == 200 && res.data) {
                that.state.smsSendBtn = true;
                that.state.text = that.state.time + this.zh_list.ssend;
                let clock = window.setInterval(() => {
                  that.state.time--;
                  that.state.text = that.state.time + this.zh_list.ssend;
                  if (that.state.time < 0) {
                    window.clearInterval(clock);
                    that.state.text = this.zh_list.Resendtheverificationcode;
                    that.state.time = 30;
                    that.state.smsSendBtn = false;
                  }
                }, 1000);

                let smsParams = {};
                smsParams.mobile = that.forgetForm.phoneNumber;
                smsParams.type = '1';
                getAction("/system/sms", smsParams)
                  .then((res) => {
                    if (!res.success) {
                      that.$message.error(res.message);
                    }
                    console.log(res);
                  })
                  .catch((err) => {
                    clearInterval(clock);
                    that.state.time = 30;
                    that.state.text = this.zh_list.Resendtheverificationcode;username
                    that.state.smsSendBtn = false;
                  });
              } else {
                this.$message.error(this.zh_list.Thisphoneisnotregistered);
              }
            });
          }
        }
      });
    },
    handleReset() {
      this.$refs.forgetForm.validate((valid) => {
        this.loading = true;
        if (valid) {
          reset(this.forgetForm)
            .then((res) => {
              if (res.code == 200) {
                const username = this.forgetForm.username;
                let messageZh="<font color='black'>"+this.zh_list.yourUsername+username +this.zh_list.Passwordchangedsuccessfully+"！</font>"
                let messageEn="<font color='black'>The password of your account " +username +" was successfully changed!</font>"
                let titleZh=this.zh_list.systemprompt
                let titleEn="system prompt"
                let message;
                let title;
                if(this.language==="zh"){
                  message=messageZh
                  title=titleZh
                }
                else{
                  message=messageEn
                  title=titleEn
                }
                this.$alert(
                  message,
                  title,
                  {
                    dangerouslyUseHTMLString: true,
                    type: "success",
                  }
                )
                  .then(() => {
                    const params = {
                      username: this.forgetForm.username,
                      password: this.forgetForm.password,
                    };
                    this.$router.push({ name: "login", params: params });
                    this.loading = false;
                  })
                  .catch(() => {
                    this.loading = false;
                  });
              } else {
                this.$message.error(res.msg);
              }
            })
            .catch(() => {
              this.loading = false;
            });
        } else {
          this.loading = false;
        }
      });
    },
    handleClick(tab, event) {
      this.forgetForm.email= ''
      this.forgetForm.phoneNumber= ''
    },
    back() {
      this.$router.push("/login");
    },
    format(percentage) {
      this.forgetForm.password;
      if (percentage === 0) {
        return "";
      } else if (percentage === 33) {
        if(this.language==="zh"){
          return this.zh_list.weak;
        }else{
          return "weak";
        }

      } else if (percentage === 66) {
        if(this.language==="zh"){
          return this.zh_list.medium;
        }else{
          return "medium";
        }
      } else if (percentage === 100) {
        if(this.language==="zh"){
          return this.zh_list.medium;
        }else{
          return "strong";
        }
      }
    },
    passwordStrength() {
      let password = this.forgetForm.password;
      if (password.length < 6 || password.length > 20) {
        this.percentage = 0;
        this.progressFlag = false;
        return;
      }
      const illegalCharReg = /[^0-9a-zA-Z`~!@#$^&*()=|{}':;',.<>《》/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？\\\[\]]/;
      if (illegalCharReg.test(password)) {
        this.percentage = 0;
        this.progressFlag = false;
        return;
      }
      let count = 0;
      if(/\d/.test(password)){
        count++;
      }
      if(/[a-z]/.test(password)){
        count++;
      }
      if(/[A-Z]/.test(password)){
        count++;
      }
      if(/[`~!@#$^&*()=|{}':;',.<>《》/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？\\\[\]]/.test(password)){
        count++;
      }

      if(password.length>=10 && count >= 4){
        this.percentage = 100;
        this.progressFlag = true;
      }else if(password.length>=8 && count >=3){
        this.percentage = 66;
        this.progressFlag = true;
      }else if(password.length>=6 && count >=2){
        this.percentage = 33;
        this.progressFlag = true;
      }else{
        this.progressFlag = false;
      }


    },
  },
  computed:{
    styleVlue(){
      return{
        '--lineWidth': this.format(this.percentage).length*15+'px'
      }
    }
  },
};
</script>

<style rel="stylesheet/scss" lang="scss">
.register {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;

  background-size: cover;
}
.title {
  margin: 20px auto 24px 0;
  text-align: left;
  font-size: 20px;
  font-weight: 400;
  color: #333;
}

.register-form {
  border-radius: 6px;
  background: #ffffff;
  width: 596px;
  padding: 25px 100px 5px 100px;
  .el-input {
    height: 38px;
    margin-top: 14px;
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
.register-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.register-code {
  width: 36%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-register-footer {
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
.register-code-img {
  height: 38px;
}
.el-progress{
  width: 440px;
  .el-progress-bar{
    width: calc(100% - var(--lineWidth));
  }
}
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
  font-size: 20px;
  font-weight: 400;
  color: #333;
}
.login-flex {
  display: flex;
  align-items: center;
  border-radius: 5px;
  position: relative;
  box-shadow: 0px 2px 2px 0px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  background: #ffffff;
  min-height: 574px;
  max-width: 990px;
  padding-left: 396px;
  .login-bac {
    width: 396px;
    height: 574px;
    position: absolute;
    left: 0;
    top: 0;
  }
  .login-bac-a {
    width: 396px;
    height: 247px;
    position: absolute;
    left: 0;
    bottom: 150px;
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

<template>
  <div class="login">
    <div class="login-flex">
      <el-form ref="registerForm" :model="registerForm" :rules="registerRules" class="register-form">
        <h3 class="title">register</h3>
        <el-tabs v-model="registerType" @tab-click="handleClick">
          <el-tab-pane label="Mobile number registration" name="1">
            <div>
              <el-form-item prop="username">
                <el-input v-model="registerForm.username" type="text" maxlength="20" auto-complete="off"
                  placeholder="Please enter your username"></el-input>
              </el-form-item>
              <el-form-item prop="phone" v-if="registerType === '1'">
                <el-select filterable v-model="value" size="medium" style="width: 20%" placeholder="please choose">
                  <el-option v-for="item in options" :key="item.value" :label="item.label"
                    :value="item.value"></el-option>
                </el-select>
                <el-input v-model="registerForm.phone" type="text" style="width: 80%" auto-complete="off"
                  placeholder="Please enter your phone number"></el-input>
              </el-form-item>

              <el-form-item prop="code" v-if="captchaEnabled">
                <el-input v-model="registerForm.code" auto-complete="off" placeholder="Please enter the verification code"
                  style="width: 60%"></el-input>
                <div class="register-code">
                  <el-button type="primary" style="align-items: center" plain :disabled="state.smsSendBtn"
                    @click.stop.prevent="getCaptcha"
                    v-text="state.text">
                  </el-button>
                </div>
              </el-form-item>
              <el-form-item prop="password">
                <el-input
                  v-model="registerForm.password"
                  type="password"
                  auto-complete="new-password"
                  placeholder="enter your PIN"
                  @keyup.native="passwordStrength()"
                  show-password
                  maxlength="20"></el-input>
                <el-progress
                  :style="styleVlue"
                  :percentage="this.percentage"
                  :format="format"
                  :color="this.customColors"
                  v-if="progressFlag"
                ></el-progress>
              </el-form-item>
              <el-form-item prop="confirmPassword">
                <el-input v-model="registerForm.confirmPassword" type="password" auto-complete="new-password"
                  maxlength="20" placeholder="Please confirm the password"></el-input>
              </el-form-item>
            </div>
          </el-tab-pane>
          <el-tab-pane label="Email registration" name="0">
            <div>
              <el-form-item prop="username">
                <el-input v-model="registerForm.username" type="text" auto-complete="off" maxlength="20"
                  placeholder="Please enter your username"></el-input>
              </el-form-item>
              <el-form-item prop="email" v-if="registerType == '0'">
                <el-input v-model="registerForm.email" type="text" auto-complete="off" placeholder="Please enter your email address"></el-input>
              </el-form-item>

              <el-form-item prop="code" v-if="captchaEnabled">
                <el-input v-model="registerForm.code" auto-complete="off" placeholder="Please enter your email verification code"
                  style="width: 60%"></el-input>
                <div class="register-code">
                  <el-button type="primary" style="align-items: center" plain @click="getCode" v-text="validateButtonText"
                    :disabled="canClick"></el-button>
                </div>
              </el-form-item>
              <el-form-item prop="password">
                <el-input v-model="registerForm.password" type="password" auto-complete="new-password" placeholder="enter your PIN"
                  @keyup.native="passwordStrength()" maxlength="20" show-password></el-input>
                <el-progress :style="styleVlue" :percentage="this.percentage" :format="format" :color="this.customColors"
                  v-if="progressFlag"></el-progress>
              </el-form-item>


              <el-form-item prop="confirmPassword">
                <el-input v-model="registerForm.confirmPassword" type="password" auto-complete="new-password" placeholder="Please confirm the password"
                  maxlength="20" show-password></el-input>
                  <span style="font-size:12px;color:#aaa">The value can contain two types of digits, uppercase letters, lowercase letters, and special characters. The value is a string of 6 to 20 characters</span>
              </el-form-item>
            </div>
          </el-tab-pane>
        </el-tabs>
        <el-form-item style="width: 100%">
          <el-button :loading="loading" size="medium" style="width: 48%" @click="back">
            <span>back</span>
          </el-button>
          <el-button :loading="loading" size="medium" type="primary" style="width: 48%"
            @click.native.prevent="handleRegister">
            <span v-if="!loading">register</span>
            <span v-else>register...</span>
          </el-button>
        </el-form-item>
      </el-form>
    </div>
            </div>
</template>

<script>
import { getEmailCode, register } from "@/api/login";
import { checkUserEmail } from "@/api/system/user";
import { getAction } from '@/api/manage';
export default {
  name: "Register",
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.registerForm.password !== value) {
        callback(new Error("The two passwords are different"));
      } else {
        callback();
      }
    };
    const checkEmail = (rule, value, cb) => {
      const reg =
        /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
      if (reg.test(value)) {
        cb();
      } else {
        cb(new Error("Mailbox format error"));
      }
    };
    const checkPhoneNumber = (rule, value, cb) => {
      const reg =
        /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
      if (reg.test(value)) {
        cb();
      } else {
        cb(new Error("The phone number format is incorrect"));
      }
    };
   const checkUserName = (rele, value, cb) => {
      const reg = /^[a-zA-Z0-9_@-]{2,20}$/;
      if (reg.test(value)) {
        cb();
      } else {
        cb(new Error('The value can contain only 2 to 20 letters, digits, underscores (_), @, and hyphens (-)'));
      }
    };
    const checkPassword = (rule, value, cb) => {
      // const reg =
      // /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,16}$/;
      const illegalCharReg = /[^0-9a-zA-Z`~!@#$^&*()=|{}':;',.<>《》/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？\\\[\]]/;
      if (illegalCharReg.test(value)) {
        cb(new Error("The value can contain only digits, lowercase letters, uppercase letters, and special characters"));
        return;
      }
      let count = 0;
      if (/\d/.test(value)) {
        count++;
      }
      if (/[a-z]/.test(value)) {
        count++;
      }
      if (/[A-Z]/.test(value)) {
        count++;
      }
      if (/[`~!@#$^&*()=|{}':;',.<>《》/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？\\\[\]]/.test(value)) {
        count++;
      }
      if (count > 1 && (value.length >= 6 && value.length <= 20)) {
        cb();
      } else {
        cb(new Error("The value can contain two types of digits, uppercase letters, lowercase letters, and special characters. The value is a string of 6 to 20 characters"));
      }
    };
    return {
      codeUrl: "",
      registerForm: {
        username: "",
        password: "",
        confirmPassword: "",
        code: "",
        uuid: "",
        email: "",
        phone: "",
        pwdFlag: false,
      },
      registerRules: {
        username: [
          { required: true, trigger: "blur", message: "Please enter your account number" },
          {
            min: 2,
            max: 20,
            message: 'The value can contain only 2 to 20 letters, digits, underscores (_), @, and hyphens (-)',
            trigger: "blur",
          }, { validator: checkUserName, trigger: "blur" }
        ],
        phone: [
          { required: true, trigger: "blur", message: "Please enter your phone number" },
          { validator: checkPhoneNumber, trigger: "blur" },
        ],
        email: [
          { required: true, trigger: "blur", message: "Please enter your email address" },
          { validator: checkEmail, trigger: "blur" },
        ],
        password: [
          { required: true, trigger: "blur", message: "please enter your PIN" },
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

      registerType: "1",
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

      validateButtonText: "get code",
      totalTime: 30,
      canClick: false,
      percentage: 0,
      customColors: [
        { color: "#f56c6c", percentage: 34 },
        { color: "#e6a23c", percentage: 67 },
        { color: "#5cb87a", percentage: 101 },
      ],
      progressFlag: false,
      state: {
        time: 30,
        smsSendBtn: false,
        text: "get code",
      },
    };
  },
  created() {
  },
  methods: {
    getCode() {
      let validateFieldList = [];
      this.$refs.registerForm.validateField(
        ["username", "email"],
        async (valid) => {
          if (!valid) {
            validateFieldList.push(valid);
            if (
              validateFieldList.length == 3 &&
              validateFieldList.every((item) => item === "")
            ) {
              if (this.canClick) return;
              checkUserEmail({ userName: this.registerForm.username, email: this.registerForm.email }).then((res) => {
                if (res.code == 200 && !res.data) {
                  this.canClick = true;
                this.validateButtonText = this.totalTime + "Send the file again";
                let clock = window.setInterval(() => {
                  this.totalTime--;
                  this.validateButtonText = this.totalTime + "Send the file again";
                  if (this.totalTime < 0) {
                    window.clearInterval(clock);
                    this.validateButtonText = "Resend the verification code";
                    this.totalTime = 30;
                    this.canClick = false;
                  }
                }, 1000);

                getEmailCode({ email: this.registerForm.email, type: 0 }).then((res) => {
                  if (res.code == 200) {
                    this.registerForm.uuid = res.data;
                    this.$message.success("The verification code is sent successfully. Procedure");
                  } else {
                    this.$message.error("Failed to send the verification code. Procedure");
                  }
                });
              } else {
                  this.$message.error(res.msg);
                }
              });
            }
            return;
          }
        }
      );
    },
    getCaptcha(e) {
      //return
      e.preventDefault();
      let validateFieldList = [];
      let that = this;
      that.$refs.registerForm.validateField(['username','phone'], (valid) => {
        if (!valid) {
          validateFieldList.push(valid);
          if (
            validateFieldList.length == 3 &&
            validateFieldList.every((item) => item === "")
          ) {
            if (that.state.smsSendBtn) return;
            checkUserEmail({
              userName: that.registerForm.username,
              email: that.registerForm.phone,
            }).then((res) => {
              if (res.code == 200 && !res.data) {
              that.state.smsSendBtn = true;
              that.state.text = that.state.time + "Send the file again";
              let clock = window.setInterval(() => {
                that.state.time--;
                that.state.text = that.state.time + "Send the file again";
                if (that.state.time < 0) {
                  window.clearInterval(clock);
                  that.state.text = "Resend the verification code";
                  that.state.time = 30;
                  that.state.smsSendBtn = false;
                }
              }, 1000);

              let smsParams = {};
              smsParams.mobile = that.registerForm.phone;
              smsParams.type = '0';
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
                  that.state.text = "Resend the verification code";
                  that.state.smsSendBtn = false;
                })
              } else {
                this.$message.error(res.msg);
              }
            })
          }
        }
      });
    },
    handleRegister() {
      this.$refs.registerForm.validate((valid) => {
        this.loading = true;
        if (valid) {
          this.registerForm.registerType = this.registerType
          register(this.registerForm)
            .then((res) => {
              if (res.code == 200) {
                const username = this.registerForm.username;
                this.$alert(
                  "<font color='black'>Your account " +
                    username +
                    " password has been successfully changed！</font>",
                  "system prompt",
                  {
                    dangerouslyUseHTMLString: true,
                    type: "success",
                  }
                )
                  .then(() => {
                    const params = {
                      username: this.registerForm.username,
                      password: this.registerForm.password,
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
      console.log(tab, event);
    },
    back() {
      this.$router.push("/login");
    },
    format(percentage) {
      this.registerForm.password;
      if (percentage === 0) {
        return "";
      } else if (percentage === 33) {
        return "weak";
      } else if (percentage === 66) {
        return "medium";
      } else if (percentage === 100) {
        return "strong";
      }
    },

    passwordStrength() {
      let password = this.registerForm.password;
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
      if (/\d/.test(password)) {
        count++;
      }
      if (/[a-z]/.test(password)) {
        count++;
      }
      if (/[A-Z]/.test(password)) {
        count++;
      }
      // let symbol = "`~!@#$^&*()=|{}':;',\\[\\].<>《》/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？ "
      if (/[`~!@#$^&*()=|{}':;',.<>《》/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？\\\[\]]/.test(password)) {
        count++;
      }

      if (password.length >= 10 && count >= 4) {
        this.percentage = 100;
        this.progressFlag = true;
      } else if (password.length >= 8 && count >= 3) {
        this.percentage = 66;
        this.progressFlag = true;
      } else if (password.length >= 6 && count >= 2) {
        this.percentage = 33;
        this.progressFlag = true;
      } else {
        this.progressFlag = false;
      }


    },
  },
  computed: {
    styleVlue() {
      return {
        '--lineWidth': this.format(this.percentage).length * 15 + 'px'
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
  // background-image: url("../assets/images/login-background.jpg");
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

.el-progress {
  width: 440px;

  .el-progress-bar {
    width: calc(100% - var(--lineWidth));
  }
}

.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  // background-image: url("../assets/images/login-background.jpg");
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

  .login-bac {
    height: 574px;
    position: absolute;
    left: 0;
    top: 0;
  }

  .login-bac-a {
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

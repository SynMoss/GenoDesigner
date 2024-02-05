<template>
  <div class="new-design">
    <el-dialog
      :close-on-click-modal="false"
      :title="model.id ? 'Edit The Linked Account Information' : 'Create A Linked Account'"
      :visible.sync="visible"
      width="520px"
      append-to-body
      @closed="handleClose"
    >
      <el-form
        label-position="top"
        v-loading="getLoading"
        ref="form"
        :model="model"
        :rules="rules"
        status-icon
        label-width="80px"
        size="small"
        class="demo-ruleForm"
      >
        <el-form-item label="ExternalSystemName" prop="externalSystemName">
          <el-select
            v-model="model.externalSystemName"
            placeholder="Please Choose"
            style="width: 100%"
            @change="titleChange()"
            :disabled="model.id"
          >
            <el-option
              v-for="item in options"
              :key="item"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="loginTitle" prop="externalSystemUserName">
          <el-input
            v-model="model.externalSystemUserName"
            @blur="model.externalSystemUserName = $event.target.value.trim()"
            placeholder="Please Enter"
            maxlength="100"
          />
        </el-form-item>
        <el-form-item label="ExternalSystemPassword" prop="externalSystemPassword">
          <el-input
            type="password"
            v-model="model.externalSystemPassword"
            autocomplete="off"
            @blur="model.externalSystemPassword = $event.target.value.trim()"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel" :disabled="subLoading" :loading="subLoading"
          >Cancel</el-button
        >
        <el-button
          type="primary"
          @click="submitForm"
          :disabled="subLoading"
          :loading="subLoading"
          >{{ bottonText }}</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {
  saveExternalPaccout,
  updateExternalPaccout,
  verifyToken,
} from "@/api/system/externalpaccout";
export default {
  dicts: ["gene_sys_name"],
  name: "ExternalPaccoutDialog",
  data() {
    return {
      subLoading: false,
      getLoading: false,
      visible: false,
      model: { externalSystemUserName: "", externalSystemPassword: "" },
      modelDefault: {},
      bottonText: "",
      rules: {
        externalSystemPassword: [
          { required: true, message: "ExternalSystemPassword Cannot Be Empty", trigger: "blur" },
        ],
        externalSystemName: [
          { required: true, message: "ExternalSystemName Cannot Be Empty", trigger: "blur" },
        ],
        externalSystemUserName: [
          { required: true, message: "ExternalSystemUserName Cannot Be Empty", trigger: "blur" },
        ],
      },
      options: [],
      tableData: [],
      loginTitle: "Account",
    };
  },
  computed: {},
  created() {

    this.modelDefault = JSON.parse(JSON.stringify(this.model));
  },
  methods: {
    add(tableData) {
      this.options = this.removeDuplicates(
        this.dict.type.gene_sys_name,
        tableData
      );
      this.tableData = tableData;
      this.model = Object.assign({}, this.modelDefault);
      this.bottonText = "Bind";
      this.visible = true;
    },
    edit(row) {
      let sysNameArr = [];
      this.dict.type.gene_sys_name.forEach((e) => {
        sysNameArr.push(e.value);
      });
      this.options = sysNameArr;
      this.model = Object.assign({}, row);
      this.bottonText = "OK";
      this.visible = true;
    },

    removeDuplicates(sysNames, tableData) {
      let sysNameArr = [];
      let tableNameArr = [];
      sysNames.forEach((e) => {
        sysNameArr.push(e.value);
      });
      tableData.forEach((e) => {
        tableNameArr.push(e.externalSystemName);
      });
      let notBindNames = sysNameArr.filter(
        (item) => !tableNameArr.includes(item)
      );
      return notBindNames;
    },
    cancel() {
      this.$refs.form.clearValidate();
      this.visible = false;
    },
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.subLoading = true;
          if (this.model.id) {
            verifyToken({
              username: this.model.externalSystemUserName,
              password: this.model.externalSystemPassword,
              system: this.model.externalSystemName
            })
              .then((res) => {
                if (res.code == 200) {
                  updateExternalPaccout(this.model).then((response) => {
                    this.$modal.msgSuccess("Edit Success");
                    this.visible = false;
                    this.$emit("ok");
                  });
                } else {
                  this.$modal.msgSuccess("Edit Failed");
                  this.visible = false;
                }
              })
              .finally(() => {
                this.subLoading = false;
              });
          } else {
            for (let i = 0; i < this.tableData.length; i++) {
              if (
                this.model.externalSystemName ==
                this.tableData[i].externalSystemName
              ) {
                this.$modal.msgSuccess("There Is Already An Associated Account For The System");
                this.visible = false;
                setTimeout(()=>{
                  this.subLoading = false
                },500)
                return;
              }
            }
            verifyToken({
              username: this.model.externalSystemUserName,
              password: this.model.externalSystemPassword,
              system: this.model.externalSystemName,
            })
              .then((res) => {
                if (res.code == 200) {
                  saveExternalPaccout(this.model).then((response) => {
                    this.$modal.msgSuccess("Bind Success");
                    this.visible = false;
                    this.$emit("ok");
                  });
                } else {
                  this.$message.error("Association Failed");
                  this.visible = false;
                }
              })
              .finally(() => {
                this.subLoading = false;
              });
          }
        }
      });
    },
    handleClose(done) {
      this.$refs.form.clearValidate();
    },

    titleChange() {
      let name = this.model.externalSystemName;
      if (name == "11") {
        this.loginTitle = "Email";
        this.rules.externalSystemUserName = [
          { required: true, message: "Email CAnnot Be Empty", trigger: "blur" },
          {
            pattern:
              /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/, //正则
            message: "The Mailbox Is Not Legitimate",
          },
        ];
      } else {
        this.loginTitle = "Account";
        this.rules.externalSystemUserName = [
          { required: true, message: "Account Cannot Be Empty", trigger: "blur" },
        ];
      }
    },
  },
};
</script>
<style lang="scss" scoped></style>

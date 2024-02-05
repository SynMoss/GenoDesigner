<template>
  <div>
    <el-dialog
      :close-on-click-modal="false"
      :title="language==='zh'?zh_list.AAi:'Save as a Template'"
      :visible.sync="visible"
      width="550px"
      append-to-body
      :before-close="handleClose"
    >
      <el-form
        label-position="top"
        ref="form"
        :model="model"
        :rules="rules"
        label-width="80px"
        size="small"
      >
        <el-form-item :label="language==='zh'?zh_list.nameA:'Name'" prop="name">
          <el-input
            v-model="model.name"
            @blur="model.name = $event.target.value.trim()"
            :placeholder="language==='zh'?'Please enter':'Please enter'"
            maxlength="100"
          />
        </el-form-item>
        <el-form-item :label="language==='zh'?zh_list.AAj:'Template Type'" v-if="this.$store.state.user.userId==1">
          <el-radio-group v-model="model.openType">
            <el-radio :label="0">{{language==='zh'?zh_list.AAk:'Private'}}</el-radio>
            <el-radio :label="1">{{language==='zh'?zh_list.AAl:'Public'}}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel"> {{language==='zh'?zh_list.cancelA:'Cancel'}} </el-button>
        <el-button
          type="primary"
          @click="submitForm"
          :disabled="subLoading"
          :loading="subLoading"
        >
        {{language==='zh'?zh_list.confirmA:'OK'}}
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { zh_list } from "@/data/constant";
import { saveTemplate, checkTemplateName } from "@/api/system/template";
export default {
  name: "SaveTemplateDialog",
  components: {},
  data() {
    var checkname = (rule, value, callback) => {
      checkTemplateName({ name: this.model.name }).then((res) => {
        if (value != "") {
          if (res.data) {
            callback(new Error(process.env.VUE_APP_LANGUAGE==='zh'?'The template name is already in use. Please replace it.':"The template name is already in use. Please replace it."));
          } else {
            callback();
          }
        }
        callback();
      });
    };
    return {
      zh_list,
      language: process.env.VUE_APP_LANGUAGE,
      model: { name: "" ,openType:0},
      modelDefault: {},
      visible: false,
      subLoading: false,
      rules: {
        name: [
          { required: true, message: process.env.VUE_APP_LANGUAGE==='zh'?'The template name cannot be left blank':"The template name cannot be left blank.", trigger: "blur" },
          { validator: checkname, trigger: "blur" },
        ],
      },
    };
  },
  computed: {},
  created() {
  },
  methods: {
    showDialog(row) {
      this.visible = true;
      this.model.programmeId = row.id;
      this.model.packFileId = row.packFileId;
    },
    cancel() {
      this.$refs.form.clearValidate();
      this.visible = false;
    },
        handleClose(done) {
      this.$refs.form.clearValidate();
      done();
    },
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.subLoading = true;
          console.log(this.model);
          saveTemplate(this.model)
            .then((response) => {
              this.$modal.msgSuccess(process.env.VUE_APP_LANGUAGE==='zh'?"success":"Save successfully");
              this.visible = false;
              this.$emit("ok");
            })
            .finally(() => {
              this.subLoading = false;
            });
        }
      });
    },
  },
};
</script>
<style lang="scss" scoped></style>

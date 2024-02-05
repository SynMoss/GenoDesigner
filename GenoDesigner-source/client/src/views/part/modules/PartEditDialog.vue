<template>
  <div class="new-design">

    <el-dialog
      :close-on-click-modal="false"
      :title="title"
      :visible.sync="visible"
      width="800px"
      append-to-body
      @closed="handleClose"
    >
      <div class="table-f">
        <el-form
          label-position="top"
          v-loading="getLoading"
          ref="form"
          :model="model"
          :rules="rules"
          label-width="80px"
          size="small"
        >

          <el-form-item :label="language==='zh'?zh_list.L:'Name'" prop="name">
            <el-input
              @blur="model.programmeName = $event.target.value.trim()"
              v-model="model.name"
              :placeholder="language==='zh'?zh_list.M:'Please Type in...'"
              maxlength="100"
              clearable
            />
          </el-form-item>
          <el-form-item :label="language==='zh'?zh_list.N:'Type'" prop="type" v-if="!model.id">
            <el-select v-model="model.type" :placeholder="language==='zh'?zh_list.O:'Please Choose Type'">
              <el-option label="plasmid" value="plasmid"></el-option>
              <el-option label="parts" value="parts"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item :label="language==='zh'?zh_list.P:'Description'">
          <el-input
              type="textarea"
              :rows="4"
              show-word-limit
              maxlength="512"
              :placeholder="language==='zh'?zh_list.M:'Please Input'"
              v-model="model.description"
            >
            </el-input>
          </el-form-item>
          <el-form-item :label="language==='zh'?zh_list.Q:'Sequence'" prop="content">
            <el-input
              type="textarea"
              :rows="5"
              show-word-limit
              :placeholder="language==='zh'?zh_list.M:'Please Input'"
              v-model="model.content"
            >
            </el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel" :disabled="subLoading" :loading="subLoading"
          >{{language==='zh'?zh_list.R:'Cancel'}}</el-button
        >
        <el-button
          type="primary"
          @click.prevent="submitForm"
          :disabled="subLoading"
          :loading="subLoading"
          >{{language==='zh'?zh_list.S:'OK'}}</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { addPart, editPart, checkPartName } from "@/api/system/part";
import { ONLY_Char_ARRAY } from "@/data/constant";
import { zh_list } from "@/data/constant";
export default {
  name: "PartEditDialog",
  data() {
    var checkname = (rule, value, callback) => {
      checkPartName({ name: this.model.name, id: this.model.id }).then(
        (res) => {
          if (value != "") {
            if (res.data) {
              callback(new Error(process.env.VUE_APP_LANGUAGE==='zh'?zh_list.T:"Name Already Exists, Please Re-Enter"));
            } else {
              callback();
            }
          }
          callback();
        }
      );
    };
    return {
      language: process.env.VUE_APP_LANGUAGE,
      zh_list,
      subLoading: false,
      getLoading: false,
      visible: false,
      model: { name: "" },
      modelDefault: {},
      rules: {
        name: [
          { required: true, message: process.env.VUE_APP_LANGUAGE==='zh'?zh_list.U:"Name Cannot be Empty", trigger: "blur" },
          { validator: checkname, trigger: "blur" },
        ],
        type: [
          {
            required: true,
            message: process.env.VUE_APP_LANGUAGE==='zh'?zh_list.V:"Type Cannot be Empty",
            trigger: "visible-change",
          },
        ],
        content: [
          {
            required: true,
            message: process.env.VUE_APP_LANGUAGE==='zh'?zh_list.W:"Content Cannot be Empty",
            trigger: "blur",
          },
        ],
      },
      onlyCharArray: ["A", "T", "C", "G", "N", "a", "t", "c", "g", "n"],
    };
  },
  computed: {
    title(){
      if(this.language==='zh'){
        return this.model.id ? this.zh_list.X : this.zh_list.Y
      }else{
        return this.model.id ? 'Edit' : 'New Parts'
      }
    }
  },
  created() {
        this.modelDefault = JSON.parse(JSON.stringify(this.model));
  },
  methods: {
    add() {
      this.model = Object.assign({}, this.modelDefault);
      this.visible = true;
    },
    edit(record) {
      this.model = Object.assign({}, record);
      this.visible = true;
    },
    cancel() {
      this.$refs.form.clearValidate();
      this.visible = false;
    },
    submitForm() {
      this.$refs["form"].validate((valid) => {
        const content = this.model.content;
        for (const j of content) {
          if (ONLY_Char_ARRAY.indexOf(j) === -1) {
            this.$message.warning(this.language==='zh'?this.zh_list.Z+ONLY_Char_ARRAY.toString():"Can Only Input "+ONLY_Char_ARRAY.toString());
            return;
          }
        }
        if (valid) {
          this.subLoading = true;
          if (this.model.id) {
            editPart(this.model)
              .then((response) => {
                this.$modal.msgSuccess("Update Success");
                this.visible = false;
                this.$emit("ok");
              })
              .finally(() => {
                this.subLoading = false;
              });
          } else {
            addPart(this.model)
              .then((response) => {
                this.$modal.msgSuccess("Add Success");
                this.visible = false;
                this.$emit("ok");
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
  },
};
</script>
<style lang="scss" scoped>
.el-form .el-select {
  width: 100%;
}
.table-f {
  height: 50vh;
  overflow: auto;
}
</style>

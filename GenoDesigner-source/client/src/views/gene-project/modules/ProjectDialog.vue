<template>
  <div class="new-design">
    <el-dialog
      :close-on-click-modal="false"
      :title="model.id?'edit':'new'"
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
            label-width="80px"
            size="small"
          >
            <el-form-item label="Name" prop="projectName">
              <el-input
                v-model="model.projectName"
                placeholder="Please input"
                maxlength="100"
              />
            </el-form-item>
            <el-form-item label="Desc">
              <el-input
                type="textarea"
                :rows="4"
                show-word-limit
                maxlength="512"
                placeholder="Please input"
                v-model="model.projectDescription"
              >
              </el-input>
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
          >Ok</el-button
        >

      </div>
    </el-dialog>
  </div>
</template>
<script>
import {saveProject,updateProject} from '@/api/system/project'
export default {
    name: 'ProjectDialog',
  data() {
    return {
      subLoading: false,
      getLoading: false,
      visible: false,
      model: {},
      modelDefault: {},
      rules: {
        projectName: [
          { required: true, message: 'Name cannot be empty', trigger: 'blur' }
        ],
      }
    }
  },
  computed: {},
  created() {
        this.modelDefault = JSON.parse(JSON.stringify(this.model))
  },
  methods: {
    add() {
      this.model = Object.assign({}, this.modelDefault)
      this.visible = true
    },
    edit(record) {
      this.model = Object.assign({}, record)
      this.visible = true
    },
    cancel() {
      this.$refs.form.clearValidate()
      this.visible = false
    },
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.subLoading = true
          if (this.model.id) {
            updateProject(this.model).then(response => {
              this.$modal.msgSuccess("success");
              this.visible = false;
              this.$emit('ok')
            }).finally(()=>{
              this.subLoading = false
            });
          } else {
            saveProject(this.model).then(response => {
              this.$modal.msgSuccess("success");
              this.visible = false;
              this.$emit('ok')
            }).finally(()=>{
              this.subLoading = false
              this.$router.push({ path: "geneProjectList"})
            });
          }
        }
      })
    },
    handleClose(done){
      this.$refs.form.clearValidate()
    }
  }
}
</script>
<style lang="scss" scoped>
</style>

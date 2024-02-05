<template>
  <el-dialog
    :close-on-click-modal="false"
    :title="language==='zh'?zh_list.AAu:'Window Settings'"
    :visible.sync="visible"
    width="400px"
    append-to-body
  >
    <div>
      <el-form
        label-position="top"
        v-loading="getLoading"
        ref="form"
        :model="model"
        :rules="rules"
        label-width="80px"
        size="small"
      >
        <el-form-item :label="language==='zh'?zh_list.AAt:'Maximum Features To Show'">
          <el-input-number
            v-model="model.maxFeaturesToShow"
            :min="0"
            :max="999999"
            :controls="false"
          >
          </el-input-number>
        </el-form-item>
        <el-form-item :label="language==='zh'?zh_list.AAs:'Maximum Sequence Length to Show'">
          <el-input-number
            v-model="model.sequenceLengthToShow"
            :min="100"
            :max="99999999"
            :controls="false"
          >
          </el-input-number> bp
                    </el-form-item>
               </el-form>
    </div>
    <div slot="footer">
      <div>
        <el-button @click="cancel">{{language==='zh'?zh_list.cancelA:'Cancel'}}</el-button>
        <el-button
          type="primary"
          @click="submitForm"
          :disabled="subLoading"
          :loading="subLoading"
          >{{language==='zh'?zh_list.confirmA:'OK'}}</el-button
        >
      </div>
    </div>
  </el-dialog>
</template>
<script>
import { zh_list } from "@/data/constant";
import { getAction, putAction } from '@/api/manage'
export default {
  name: 'ReplaceDialog',
  props: {
    programmeId: {
      type: String,
      default: function(params) {
        return null
      }
    }
  },
  data() {
    return {
      zh_list,
      language: process.env.VUE_APP_LANGUAGE,
      subLoading: false,
      getLoading: false,
      visible: false,
      input: '',
      rules: {
        maxFeaturesToShow: [
          { required: true, message: 'Please input', trigger: 'blur' }
        ],
        sequenceLengthToShow: [
          { required: true, message: 'Please input', trigger: 'blur' }
        ]
      },
      model: {
        maxFeaturesToShow: 2000,
        sequenceLengthToShow: 30000,
      },
            modelSource:{},
      sequenceLengthStart:0,
      saveFeature: false
    }
  },
  computed: {},
  created() {},
  methods: {
    show() {
      this.getLoading = true
      getAction('/system/programme/queryById', { id: this.programmeId }).then(
        (res) => {
          res.data.maxFeaturesToShow != null &&
            (this.model.maxFeaturesToShow = res.data.maxFeaturesToShow)
          res.data.sequenceLengthToShow != null &&
            (this.model.sequenceLengthToShow = res.data.sequenceLengthToShow)
          this.model.saveFeature = res.data.saveFeature
          this.getLoading = false
                    this.modelSource = Object.assign({},this.model)
        }
      )
      this.visible = true
    },
    cancel() {
      this.visible = false
    },
    submitForm() {
      let equal = true
      for (const key in this.model) {
        if(this.model[key]!==this.modelSource[key]){
          equal = false
          break
        }
      }
      if(equal){
        this.visible = false
        return
      }
      this.$refs.form.validate((valid) => {
        if (valid) {
            this.subLoading = true
          this.model.id = this.programmeId
          putAction('/system/programme/edit', this.model).then((res) => {
            this.subLoading = false
            this.visible = false
            this.$message.success('Current scheme needs to be reloaded!')
            setTimeout(() => {
              location.reload()
            }, 1500);
                      })

        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
</style>

<template>
  <div class="insert-dialog">
    <el-dialog
      :close-on-click-modal="false"
      :title="language==='zh'?zh_list.aaf:'Open Editing File'"
      :visible.sync="visible"
      width="400px"
      append-to-body
    >
      <el-form
        label-position="top"
        ref="form"
        :model="model"
        :rules="rules"
        label-width="80px"
        size="small"
      >
        <el-form-item >
          <el-checkbox v-model="checkAll">{{language==='zh'?zh_list.aag:'Modify feature name'}}</el-checkbox>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <div>
          <el-button @click="cancel">{{language==='zh'?zh_list.cancelA:'Cancel'}}</el-button>
          <el-button
            v-if="!getLoading"
            type="primary"
            @click="submitForm"
            :disabled="subLoading"
            :loading="subLoading"
            >{{language==='zh'?zh_list.confirmA:'OK'}}</el-button
          >
        </div>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { zh_list } from "@/data/constant";
import { listType,removeByType } from '@/api/system/sequence'
export default {
  name: 'EditFeatureNameDialog',
  props: {
    sequenceId: {
      type: Number,
      default: function(params) {
        return null
      }
    }
  },
  data() {
    return {
      zh_list,
      checkAll: false,
      language: process.env.VUE_APP_LANGUAGE,
      allAlign: null,
      subLoading: false,
      getLoading: false,
      visible: false,
      model: { typeList: [],typeRemainList:[] },
      rules: {
        typeList: [{ required: true, message: "Select at least one item", trigger: "blur" }]
      },
      group: [],
      groupMap:{},
      groupA: [],
      groupB: []
    }
  },
  computed: {},
  created() {},
  methods: {
    changeA(val){
      this.groupB=[]
      this.group.forEach(item=>{
        if(val.indexOf(item)===-1){
          this.groupB.push(item)
        }else {
          let index = this.model.typeRemainList.indexOf(item)
          if(index!==-1){
            this.model.typeRemainList.splice(index,1)
          }
        }
      })
    },
    changeB(val){
      this.groupA=[]
      this.group.forEach(item=>{
        if(val.indexOf(item)===-1){
          this.groupA.push(item)
        }else {
          let index = this.model.typeList.indexOf(item)
          if(index!==-1){
            this.model.typeList.splice(index,1)
          }
        }
      })
    },
    show() {
      this.checkAll = false
      this.visible = true
    },
    cancel() {
      this.visible = false
    },
    submitForm() {
      this.$emit('ok',this.checkAll)
      this.visible = false
    }
  }
}
</script>
<style lang="scss" scoped>
.insert-upload {
  display: none;
}
.insert-dialog-footer {
  display: flex;
  justify-content: space-between;
}
.design-list-top {
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  .design-list-top-right {
    flex: 0 0 240px;
  }
}
.upload-fasta {
  display: flex;
}
</style>

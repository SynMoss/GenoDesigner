<template>
  <div class="insert-dialog">
    <el-dialog
      :close-on-click-modal="false"
      :title="language==='zh'?zh_list.aae:'Replace Stop Codons'"
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
          <el-checkbox v-model="checkAll">{{language==='zh'?zh_list.applyToAllChromosomes:'apply to all chromosomes'}}</el-checkbox>
        </el-form-item>
        <el-form-item v-loading="getLoading" :label="language==='zh'?zh_list.deleteA:'Delete'" prop="codonDelList">
          <el-checkbox-group @change="changeB" v-model="model.codonDelList" >
            <el-checkbox :label="item" v-for="item in groupB" :key="item">{{item}}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item :label="language==='zh'?zh_list.replaceA:'Replace'" prop="codonReplace">
          <el-radio-group @change="changeA" v-model="model.codonReplace" >
            <el-radio :label="item" v-for="item in groupA" :key="item">{{item}}</el-radio>
          </el-radio-group>
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
import { codonReplace,listBySequenceId } from '@/api/system/sequence'
export default {
  name: 'CodonReplace',
  props: {
    sequenceId: {
      type: Number,
      default: function(params) {
        return null
      }
    },
    sequenceName: {
      type: String,
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
      model: { codonDelList:[],codonReplace:null},
      rules: {
        codonReplace:[{ required: true, message: this.language==='zh'?"请输入":"Please enter", trigger: "blur" }],
      },
      group: ['TAG','TGA','TAA'],
      groupA: [],
      groupB: [],
      groupMap:{},
    }
  },
  computed: {},
  created() {},
  methods: {
    changeA(val){
      this.groupB=[]
      this.group.forEach(item=>{
        if(val!==item){
          this.groupB.push(item)
        }
      })
      let index = this.model.codonDelList.indexOf(val)
      if(index!==-1){
        this.model.codonDelList.splice(index,1)
      }
    },
    changeB(val){
      this.groupA=[]
      this.group.forEach(item=>{
        if(val.indexOf(item)===-1){
          this.groupA.push(item)
        }
      })
      let index = val.indexOf(this.model.codonReplace)
      if(index!==-1){
        this.model.codonReplace = null
      }
    },
    show() {
      this.groupA = [...this.group]
      this.groupB = [...this.group]
      this.checkAll = false
      this.model.codonDelList = []
      this.model.codonReplace = ''
      this.model.sequenceId = this.sequenceId
      this.visible = true
    },
    cancel() {
      this.visible = false
    },
    submitForm() {
      this.$refs.form.validate(async (valid) => {
        if (valid) {
          this.subLoading = true
          let sequenceList
          if(this.checkAll){
            await listBySequenceId({sequenceId: this.sequenceId}).then(res=>{
              sequenceList = res.data
            })
          }else{
            sequenceList = [{id:this.sequenceId,name:this.sequenceName}]
          }
          for (let index = 0; index < sequenceList.length; index++) {
            this.model.sequenceId = sequenceList[index].id
            await codonReplace(this.model).then((res) => {
              this.$notify({
                title: 'success',
                message: sequenceList[index].name+' done ',
                type: 'success'
              });
            }).catch(err=>{
              this.$notify.error({
                title: sequenceList[index].name+ ' fail',
                message: err.message,
                duration: 0
              });
            }).finally(err=>{
              if(index===sequenceList.length-1){
                this.$emit('ok')
                this.visible = false
                this.subLoading = false
              }
            })
          }
        }
      })
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

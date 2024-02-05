<template>
  <div class="insert-dialog">
    <el-dialog
      :close-on-click-modal="false"
      :title="language==='zh'?zh_list.AAe:'Delete by Feature Type'"
      :visible.sync="visible"
      width="600px"
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
        <el-form-item v-loading="getLoading" :label="language==='zh'?zh_list.AAd:'Selection'" prop="typeList">
          <el-checkbox-group v-model="model.typeList" @change="changeA">
            <el-row>
              <el-col :span="24" v-for="item in groupA" :key="item">
                <el-checkbox :label="item" :key="item">{{item}}</el-checkbox>
              </el-col>
            </el-row>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item v-loading="getLoading" :label="language==='zh'?zh_list.AAc:'Kept Features'" prop="typeRemainList">
          <el-checkbox-group v-model="model.typeRemainList" @change="changeB">
            <el-row>
              <el-col :span="24" v-for="item in groupB" :key="item">
                <el-checkbox :label="item" :key="item">{{item}}</el-checkbox>
                <el-row v-show="model.typeRemainList.indexOf(item)!==-1">
                  <el-col :span="12">
                    <el-form-item :label="language==='zh'?zh_list.AAb:'Upstream'" prop="frontCount">
                      <el-input-number
                      v-model="groupMap[item][0]"
                      :min="0"
                      :max="99999999"
                      :precision="0"
                      :controls="false"
                      size="mini"
                    >
                    </el-input-number>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item :label="language==='zh'?zh_list.AAa:'Downstream'" prop="beCount">
                      <el-input-number
                        v-model="groupMap[item][1]"
                        :min="0"
                        :max="99999999"
                        :precision="0"
                        :controls="false"
                        size="mini"
                      >
                      </el-input-number>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-col>
            </el-row>
          </el-checkbox-group>
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
import { listType,removeByType,listBySequenceId } from '@/api/system/sequence'
export default {
  name: 'RemoveTypeFeatureDialog',
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
    },
    programmeId: {
      type: String,
      default: function (params) {
        return null;
      },
    },
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
      this.model.typeList = []
      this.model.typeRemainList = []
      this.getLoading = true
      listType({programmeId: this.programmeId}).then(res=>{
        this.group = res.data
        this.groupMap = {}
        this.group.forEach(item=>{
          this.groupMap[item]=[0,0]
        })
        this.groupA = [...this.group]
        this.groupB = [...this.group]
        this.getLoading = false
      })
      this.visible = true
    },
    cancel() {
      this.visible = false
    },
    submitForm() {
      this.$refs.form.validate(async (valid) => {
        if (valid) {
          this.subLoading = true
          let typeRemainMap = {}
          this.model.typeRemainList.forEach(item=>{
            typeRemainMap[item] = this.groupMap[item]
          })
          let subData = {sequenceId: this.sequenceId,typeList:this.model.typeList,typeRemainMap: typeRemainMap}
          let sequenceList
          if(this.checkAll){
            await listBySequenceId({sequenceId: this.sequenceId}).then(res=>{
              sequenceList = res.data
            })
          }else{
            sequenceList = [{id:this.sequenceId,name:this.sequenceName}]
          }
          for (let index = 0; index < sequenceList.length; index++) {
            subData.sequenceId = sequenceList[index].id
            await removeByType(subData)
            .then((res) => {
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
                this.subLoading = false
                this.$emit('ok')
                this.visible = false
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

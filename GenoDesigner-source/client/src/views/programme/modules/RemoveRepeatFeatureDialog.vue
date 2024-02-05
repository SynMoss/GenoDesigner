<template>
  <div class="insert-dialog">
    <el-dialog
      :close-on-click-modal="false"
      :title="language==='zh'?zh_list.aAy:'Delete Redundant Features'"
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
        <el-form-item :label="language==='zh'?zh_list.typeA:'Types'" prop="featureRepeatConditionArray">
          <el-checkbox-group v-model="model.featureRepeatConditionArray" @change="changeF">
            <el-row>
              <el-col :span="24" v-for="item in group" :key="item.key">
                <el-checkbox :label="item.key" :key="item.key">{{item.label}}</el-checkbox>
              </el-col>
            </el-row>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
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
  </div>
</template>
<script>
import { zh_list } from "@/data/constant";
import { removeRepeatFeature,listBySequenceId } from '@/api/system/sequence'
export default {
  name: 'RemoveRepeatFeatureDialog',
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
      visible: false,
      model: { featureRepeatConditionArray: [] },
      rules: {
        featureRepeatConditionArray: [{ required: true, message: "Select at least one item", trigger: "blur" }]
      },
      group: [
        { label: 'Name', key: 'groupByName' },
        { label: 'Start', key: 'groupByStart' },
        { label: 'End', key: 'groupByEnd' },
        { label: 'Source', key: 'groupBySource' },
        { label: 'Type', key: 'groupByType' }
      ]
    }
  },
  computed: {},
  created() {},
  methods: {
    changeF(val){
      console.log(val)
    },
    show() {
      this.checkAll = false
      this.model.featureRepeatConditionArray = []
      this.visible = true
    },
    cancel() {
      this.visible = false
    },
    submitForm() {
      this.$refs.form.validate(async (valid) => {
        if (valid) {
          this.subLoading = true
          let subData = {sequenceId: this.sequenceId}
          this.model.featureRepeatConditionArray.forEach(element => {
            subData[element] = '1'
          });
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
            await removeRepeatFeature(subData)
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

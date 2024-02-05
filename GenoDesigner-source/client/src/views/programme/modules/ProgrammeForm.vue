<template>
  <div>
    <el-form
        style="margin: 10px"
        label-position="top"
        :model="programmeInfo"
        v-loading="getLoading"
        ref="form"
        :rules="rules"
        size="small"
        :inline="false"
        :disabled="readOnly"
      >
        <el-form-item :label="language==='zh'?zh_list.aAi:'Design Information'" prop="programmeName">
          <el-input v-model="programmeInfo.programmeName"
            @blur="programmeInfo.programmeName = $event.target.value.trim()"
            :placeholder="language==='zh'?'Please Input':'Please Input'"
            maxlength="50"
          />
        </el-form-item>
        <el-form-item :label="language==='zh'?zh_list.nameA:'Genome Name'">
          <el-input v-model="programmeInfo.sourceFileNameShow" disabled clearable
            @blur="programmeInfo.sourceFileNameShow = $event.target.value.trim()"/>
        </el-form-item>
        <el-form-item :label="language==='zh'?zh_list.aAu:'Vector'">
          <el-input
            type="textarea"
            :rows="4"
            show-word-limit
            maxlength="512"
            :placeholder="language==='zh'?'Please Input':'Please Input'"
            resize="none"
            v-model="programmeInfo.carrier"
            @blur="programmeInfo.carrier = $event.target.value.trim()"
          ></el-input>
        </el-form-item>
        <el-form-item v-if="programmeInfo.chooseFile===0" :label="language==='zh'?zh_list.aAv:'Annotation Database'" prop="databaseForAnnotation">
          <el-input v-model="programmeInfo.databaseForAnnotation" maxlength="50" clearable
            @blur="programmeInfo.databaseForAnnotation = $event.target.value.trim()"/>
        </el-form-item>
                                  <project-dialog ref="projectDialog" @ok="loadProjectList"></project-dialog>
      </el-form>
      <div slot="footer" class="dialog-footer" style="margin-right: 10px">
        <el-button
          type="primary"
          @click="submitForm"
          :disabled="subLoading"
          :loading="subLoading"
          size="middle"
          style="float:right; margin-top: 80px;"
          v-if="!readOnly"
          >{{language==='zh'?zh_list.confirmA:'OK'}}</el-button
        >
      </div>
  </div>
</template>
<script>
import { zh_list } from "@/data/constant";
import { getAction } from '@/api/manage'
import { editProgramme, checkProjectName } from '@/api/system/programme'
import { getProject, listAllProjects } from '@/api/system/project'
import ProjectDialog from "../../gene-project/modules/ProjectDialog"
export default {
  components: { ProjectDialog },
  props: {
    readOnly: {
      type: Boolean,
      default: function() {
        return true
      }
    },
    isInit: {
      type: Boolean,
      default: function() {
        return false
      }
    }
  },
  data() {
    var checkName = (rule, value, callback) => {
      checkProjectName({ programmeName: this.programmeInfo.programmeName, id: this.programmeInfo.id }).then(
        (res) => {
          if (value != "") {
            if (res.data) {
              callback(new Error(process.env.VUE_APP_LANGUAGE==='zh'?this.zh_list.aAt:"Design Information已存在，请重新输入"));
            } else {
              callback();
            }
          }
          callback();
        }
      )
    }
    return {
      zh_list,
      language: process.env.VUE_APP_LANGUAGE,
      subLoading: false,
      getLoading: false,
      programmeInfo: { programmeName:'', sourceFileNameShow:'', carrier:'', databaseForAnnotation:'' },
      projectList: [],
      project: null,
      rules: {
        programmeName: [
          { required: true, message: process.env.VUE_APP_LANGUAGE==='zh'?'Design Design Information can not be empty':"Design Design Information can not be empty", trigger: "blur" },
          { validator: checkName, trigger: "blur" },
          {
            min: 1,
            max: 50,
            message: "max length is 50",
          }
        ],
        databaseForAnnotation: [
          {
                        pattern:/^[a-zA-Z0-9\u4e00-\u9fa5,.!@#$%^&*()_+-=\[\]{};':"<>/\\|?]+(,[a-zA-Z0-9\u4e00-\u9fa5,.!@#$%^&*()_+-=\[\]{};':"<>/\\|?]+)*$/g,
            message: process.env.VUE_APP_LANGUAGE==='zh'?'The annotation database can only contain Chinese, English, numbers, English periods, and special characters, with an English comma serving as a separator.':'The annotation database can only contain Chinese, English, numbers, English periods, and special characters, with an English comma serving as a separator.',
            trigger: "blur"
          }
        ]
      }
    }
  },
  methods: {
    show(id){
      this.loadData(id)
      this.loadProjectList();
    },
    loadProjectList() {
      listAllProjects().then((res) => {
        this.projectList = res.data;
      })
    },
    loadData(id){
      getAction("/system/programme/queryById",{id:id}).then(res=>{
        this.programmeInfo = res.data
      })
    },
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.subLoading = true
        editProgramme(this.programmeInfo).then(response => {
          this.$modal.msgSuccess("success");
          this.$emit('ok',this.programmeInfo.databaseForAnnotation)
          }).finally(()=>{
            this.subLoading = false
          })
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
a{
    color:blue;
    font-weight:normal;
      }

::v-deep{
  .el-input--small .el-input__inner {
   font-size: 14px !important;
  }
}
</style>

<template>
  <div class="new-programme">
    <el-dialog
      :close-on-click-modal="false"
      :title="language==='zh'?zh_list.aab:'Clone'"
      :visible.sync="visible"
      width="550px"
      append-to-body
      :before-close="handleClose"
    >
      <el-form
        label-position="top"
        v-loading="getLoading"
        ref="form"
        :model="model"
        :rules="rules"
        label-width="80px"
        size="mini"
        id="newProgrammeFont"
      >
        <el-form-item :label="language==='zh'?zh_list.aac:'Design Name'" prop="programmeName">
          <el-input
            v-model="model.programmeName"
            @blur="model.programmeName = $event.target.value.trim()"
            placeholder="Please enter..."
            maxlength="30"
            size="small"
            clearable
          />
        </el-form-item>
               </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel" :loading="subLoading">{{language==='zh'?zh_list.aad:'Cancel'}}</el-button>
        <el-button type="primary" @click="submitForm" :loading="subLoading">{{language==='zh'?zh_list.confirmA:'OK'}}</el-button>
      </div>
    </el-dialog>
    <project-dialog ref="projectDialog" @ok="loadProjectList"></project-dialog>
  </div>
</template>
<script>
import { checkProjectName, cloneProgramme } from "@/api/system/programme";
import { listAllProjects } from "@/api/system/project";
import ProjectDialog from "../../gene-project/modules/ProjectDialog";
import { zh_list } from "@/data/constant";
export default {
  name: "ProgrammeDialog",
  components: { ProjectDialog },
  data() {
    var checkname = (rule, value, callback) => {
      checkProjectName({ programmeName: this.model.programmeName }).then(
        (res) => {
          if (value != "") {
            if (res.data) {
              callback(new Error("Design name already exists, please re-enter"));
            } else {
              callback();
            }
          }
          callback();
        }
      );
    };
    return {
      zh_list,
      language: process.env.VUE_APP_LANGUAGE,
      confirmRemoveVisible: false,
      getLoading: false,
      visible: false,
      model: { programmeName: "" },
      modelDefault: {},
      rules: {
        programmeName: [
          { required: true, message: "The design name cannot be empty", trigger: "blur" },
          { validator: checkname, trigger: "blur" },
          {
            min: 2,
            max: 30,
            message: "The value ranges from 2 to 30 characters",
          },
        ],
      },
      rowData: {},
      projectList: [],
      templateList: [],
      radio: "blank",
      userId:'',
      projectId: '',
      subLoading: false
    };
  },
  computed: {},
  created() {
        this.modelDefault = JSON.parse(JSON.stringify(this.model));
    this.userId = this.$store.state.user.userId
  },
  methods: {
    loadProjectList() {
      listAllProjects().then((res) => {
        this.projectList = res.data;
      });
    },
    showDialog(record) {
            this.model = Object.assign({}, record)
                              if(this.model.programmeName.length <= 25){
          this.model.programmeName = this.model.programmeName + '_copy'
        }else{
          this.model.programmeName = this.model.programmeName.substr(0,25) + '_copy'
        }
      this.visible = true;
    },
    cancel() {
      this.visible = false;
      this.radio = "blank";
      this.$refs["form"].resetFields();
    },
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.subLoading = true
          cloneProgramme(this.model).then((res) => {
            this.cancel()
            setTimeout(()=>{
              this.subLoading = false
            },500)
            this.$modal.msgSuccess("Add successfully")
            this.$emit('ok')
          }).finally(() => {
            if(this.model.isFromProject){
              this.$router.push({path: "/geneProjectDetails", query: { id: this.model.projectId }});
            }else{
              this.$router.push({ path: "/geneBlackList"});
            }
          })
        }
      })
    },
    handleClose(done) {
      this.$refs["form"].resetFields();
      this.radio = "blank";
      done();
    },
    openProjectDialog() {
      let isFromNewProgrammeDialog = true;
      this.$refs.projectDialog.add(isFromNewProgrammeDialog);
    },
  }
};
</script>
<style lang="scss" scoped>
.span {
  width: 250px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  display: inline-block;
}
.el-form-item__label{font-size: 14px;}
.size-full {
  width: 100%;
}
.project-label {
  width: 510px;
  display: flex;
  justify-content: space-between;
}
.template-scope-class {
  width: 100%;
  display: flex;
  justify-content: space-between;
  i {
    color: orange;
    font-size: 16px;

    &:hover {
      cursor: pointer;
    }
  }
}

#newProgrammeFont >>> .el-form-item__label {
  font-size: 14px;
}

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

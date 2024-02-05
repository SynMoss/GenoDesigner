<template>
  <div class="new-programme">
    <el-dialog
      :close-on-click-modal="false"
      :title="language==='zh'?zh_list.aAo:'New Design'"
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
        <el-form-item :label="language==='zh'?zh_list.aAi:'Name'" prop="programmeName">
          <el-input
            v-model="model.programmeName"
            @blur="model.programmeName = $event.target.value.trim()"
            :placeholder="language==='zh'?'Please enter...':'Please enter...'"
            maxlength="30"
            size="small"
            clearable
          />
        </el-form-item>
                 <el-form-item :label="language==='zh'?zh_list.aAp:'Select Template'">
          <el-radio-group v-model="radio" style="margin-bottom: 10px">
            <el-radio label="blank">{{language==='zh'?zh_list.aAq:'Blank Design'}}</el-radio>
            <el-radio label="template">{{language==='zh'?zh_list.aAr:'Choose a Template'}}</el-radio>
          </el-radio-group>
          <div v-if="radio == 'template'">
            <vxe-table
              border
              ref="tamplateTable"
              height="300px"
              style="width: 100%"
              :row-config="{ isCurrent: true, isHover: true }"
              :data="templateList"
            >
              <vxe-column>
                <template #header>
                  <el-input
                    v-model="nameInput"
                    :placeholder="language==='zh'?zh_list.aAs:'Search a Template'"
                    size="mini"
                    style="width: 380px; margin-right: 10px"
                    @keypress.enter.native="loadTemplateList"
                    clearable
                  ></el-input>
                  <el-button
                    size="mini"
                    type="primary"
                    @click="loadTemplateList"
                    >{{language==='zh'?zh_list.searchA:'Search'}}</el-button
                  >
                </template>
                <template #default="{ row }">
                  <div class="template-scope-class">
                    <span>
                      {{ row.name }}
                      <span style="color: red">{{
                        row.openType == "1" ? "（Open）" : ""
                      }}</span>
                    </span>
                    <i
                      class="el-icon-delete"
                      @click="confirmRemove(row)"
                      v-if="row.openType != '1' || (row.creater == userId || userId==1)"
                    ></i>
                  </div>
                </template>
              </vxe-column>
            </vxe-table>
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel" :disabled="subLoading" :loading="subLoading">
          {{language==='zh'?zh_list.cancelA:'Cancel'}}
        </el-button>
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
    <project-dialog ref="projectDialog" @ok="loadProjectList"></project-dialog>

    <el-dialog
      :close-on-click-modal="true"
      title="Prompt"
      :visible.sync="confirmRemoveVisible"
      width="520px"
      append-to-body
    >
      <div class="diaglog-remove-icon"><i class="el-icon-warning"></i></div>
      <div class="dialog-p">{{ "Are you sure you want to delete this template?" }}</div>
      <div class="dialog-p">
        If you want to use this template later, you can add it as a template using the "Save as Template" function in the "Genome Design Tool" menu.
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button
          @click="confirmRemoveVisible = false"
          :disabled="subLoading"
          :loading="subLoading"
          >{{language==='zh'?zh_list.cancelA:'Cancel'}}</el-button
        >
        <el-button
          type="primary"
          @click="removeTamplate(data)"
          :disabled="subLoading"
          :loading="subLoading"
          >{{language==='zh'?zh_list.confirmA:'OK'}}</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {
  createBlank,
  checkProjectName,
  createByTemplate,
} from "@/api/system/programme";
import { listAllProjects } from "@/api/system/project";
import { getTemplateList, deleteTemplate } from "@/api/system/template";
import { zh_list } from "@/data/constant";
import ProjectDialog from "../../gene-project/modules/ProjectDialog";
export default {
  name: "ProgrammeDialog",
  components: { ProjectDialog },
  data() {
    var checkname = (rule, value, callback) => {
      checkProjectName({ programmeName: this.model.programmeName }).then(
        (res) => {
          if (value != "") {
            if (res.data) {
              callback(new Error(process.env.VUE_APP_LANGUAGE==='zh'?this.zh_list.aAt:"There is already a “Design Name”. Please enter another name."));
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
          { required: true, message: process.env.VUE_APP_LANGUAGE==='zh'?"Please enter a valid name":"Please enter a valid name.", trigger: "blur" },
          { validator: checkname, trigger: "blur" },
          {
            min: 2,
            max: 30,
            message: "The character count should be between 2 and 30.",
          },
        ],
      },
      rowData: {},
      projectList: [],
      templateList: [],
      radio: "blank",
      userId: "",
      projectId: "",
      canCheck: false,
      subLoading: false,
      nameInput: "",
    };
  },
  computed: {},
  created() {
        this.modelDefault = JSON.parse(JSON.stringify(this.model));
    this.loadTemplateList();
    this.userId = this.$store.state.user.userId;
  },
  methods: {
    loadProjectList() {
      listAllProjects().then((res) => {
        this.projectList = res.data;
      });
    },
    loadTemplateList() {
      getTemplateList({ name: this.nameInput }).then((res) => {
        this.templateList = res;
      });
    },
        confirmRemove(row) {
      this.data = row;
      this.confirmRemoveVisible = true;
    },
    showDialog(projectId) {
      this.loadProjectList();
      this.model = Object.assign({}, this.modelDefault);
      if (projectId) {
        this.projectId = projectId;
        this.model.projectId = projectId;
        this.canCheck = true
      }
      this.visible = true;
    },
    cancel() {
      this.visible = false;
      this.radio = "blank";
      this.$refs["form"].resetFields();
    },
    remove(row) {
      removeProject(row).then((res) => {
        this.confirmRemoveVisible = false;
        this.loadData();
        this.$modal.msgSuccess("Deletion successful！");
      });
    },
    removeTamplate(row) {
      deleteTemplate({ id: row.id }).then((res) => {
        if (res.code == 200) {
          this.$message({
            type: "success",
            message: "Deletion successful！",
          });
          this.loadTemplateList();
          this.confirmRemoveVisible = false;
        }
      });
    },
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.radio == "blank") {
            let params = {
              programmeName: this.model.programmeName,
              projectId: this.model.projectId,
            };
            this.subLoading = true;
            createBlank(params)
              .then((res) => {
                if (res.code == 200) {
                  if (!this.projectId) {
                    this.$router.push({
                      path: "programmer",
                      query: { id: res.data },
                    });
                  } else {
                    this.cancel();
                    setTimeout(()=>{
                      this.subLoading = false
                    },500)
                    this.$modal.msgSuccess("Addition successful！");
                    this.$emit("ok");
                  }
                }
              })
              .finally(() => {
                setTimeout(()=>{
                  this.subLoading = false
                },500)
              });
          } else if (this.radio == "template") {
            const record = this.$refs.tamplateTable.getCurrentRecord();
            if (!record) {
              this.$message.error("No template selected!");
            } else {
              this.subLoading = true;
              this.model.templateId = record.id;
              createByTemplate(this.model)
                .then((res) => {
                  if (res.code == 200) {
                    if (!this.projectId) {
                      this.$router.push({
                        path: "programmer",
                        query: { id: res.data },
                      });
                    } else {
                      this.cancel();
                      this.$modal.msgSuccess("Addition successful！");
                      this.$emit("ok");
                    }
                  }
                })
                .finally(() => {
                  setTimeout(()=>{
                    this.subLoading = false
                  },500)
                });
            }
          }
        }
      });
                                                          },
    handleClose(done) {
      this.$refs["form"].resetFields();
      this.radio = "blank";
      done();
    },
        doBuiled(params) {
      savaExamine(params)
        .then((res) => {
          if (res.code == 200) {
            this.$message.success(res.msg);
            this.$emit("ok");
          } else {
            this.$message.error(res.msg);
          }
        })
        .catch((err) => {
          this.$message.error(res.msg);
        })
        .finally(this.cancel());
    },
    openProjectDialog() {
      let isFromNewProgrammeDialog = true;
      this.$refs.projectDialog.add(isFromNewProgrammeDialog);
    },
  },
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
.el-form-item__label {
  font-size: 14px;
}
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

a {
  color: blue;
  font-weight: normal;
  }

::v-deep {
  .el-input--small .el-input__inner {
    font-size: 14px !important;
  }
}
</style>

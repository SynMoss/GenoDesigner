<template>
  <div>
    <el-dialog
      :close-on-click-modal="false"
      :title="language==='zh'?zh_list.aAh:'Download'"
      :visible.sync="visible"
      width="550px"
      append-to-body
    >
      <div class="div" width="500px">
        <div class="text" width="500px">{{language==='zh'?zh_list.aAi:'Name'}}：{{ programmeNames }}</div>
        <div class="text" width="500px">{{language==='zh'?zh_list.aAj:'Number'}}：{{ programmeNumbers }}</div>
         </div>
      <el-form
        label-position="top"
        ref="form"
        :model="model"
        :rules="rules"
        label-width="80px"
        size="small"
      >
        <el-form-item prop="packTypeList">
          <span slot="label">
            <span style="font-size: 14px"><strong>{{language==='zh'?zh_list.aAj:'Package Selection'}}</strong></span>
          </span>
          <el-checkbox-group v-model="model.packTypeList">
                         <el-checkbox label="0" :key="0" style="display: block"
              >FASTA {{language==='zh'?zh_list.aAk:'File'}}</el-checkbox
            >

            <el-checkbox label="1" :key="1" style="display: block"
              >{{language==='zh'?zh_list.aAl:'Genbank File'}}

                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="language==='zh'?zh_list.aAm:'This file must be included in order to proceed with “cloning” and save it as a template operation.'"
                  placement="right"
                >
                  <i class="el-icon-question"></i> </el-tooltip>
            </el-checkbox>

            <el-checkbox label="2" :key="2" style="display: block"
              >{{language==='zh'?zh_list.aAn:'History'}}
                    </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel"> {{language==='zh'?zh_list.cancelA:'Cancel'}} </el-button>
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
  </div>
</template>
<script>
import { zh_list } from "@/data/constant";
import { pack, cancelPack } from "@/api/system/sequence";

export default {
  name: "PackDialog",
  components: {},
  data() {
    return {
      zh_list,
      language: process.env.VUE_APP_LANGUAGE,
      subLoading: false,
      model: {
        programmeIdList: [],
        packTypeList: [],
      },
      token: {},
      visible: false,
      programmeNames: "",
      projectNames: "",
      programmeNumbers: "",
      rules: {
        packTypeList: [{ required: true, message: " ", trigger: "blur" }],
      },
      columnOption: [
        { id: "0", name: "FASTA File" },
        {
          id: "1",
          name: "Annotation prompt: This file needs to be included in order to proceed with sharing and saving it as a template operation.",
        },
              ],
    };
  },
  computed: {},
  created() {},
  methods: {
    showDialog(array) {
      this.packTypeList = [];
      const programmeNames = [];
      const projectNames = [];
      const programmeNumbers = [];
      let idList = [];
      for (let i = 0; i < array.length; i++) {
        programmeNames.push(array[i].programmeName);
        if (array[i].projectName != null) {
          projectNames.push(array[i].projectName);
        }
        programmeNumbers.push(array[i].programmeNumber);
        idList.push(array[i].id);
      }
      this.model.programmeIdList = idList;
      this.model.packTypeList = [];
      this.programmeNames = programmeNames.toString();
      this.projectNames = projectNames.toString();
      this.programmeNumbers = programmeNumbers.toString();
      this.visible = true;
    },
    cancel() {
      if (this.subLoading) {
        cancelPack();
      }
      this.visible = false;
    },
    submitForm() {
      if (this.model.packTypeList.length == 0) {
        this.$modal.msgError("Please choose at least one packaging type.");
      }
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.subLoading = true;
          console.log(this.model);
          pack(this.model)
            .then((res) => {
                            this.visible = false;
              this.$emit("ok");
            })
            .finally(() => {
              setTimeout(()=>{
                this.subLoading = false
              },500)
            });
        }
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.div {
  margin-bottom: 10px;
}
.text {
  font-size: 14px;
  color: #606266;
  line-height: 25px;
  font-weight: bold;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.text:hover {
  text-overflow: inherit;
  overflow: visible;
  white-space: pre-line;
}
.el-icon-question {
  color: #c0c4cc;

}

</style>

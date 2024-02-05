<template>
  <div class="tool-bar" @click="rangeClick">
    <div class="tool">
      <el-dropdown @command="handleCommandOpen" :disabled="readOnly">
        <span
          :class="
            readOnly
              ? 'tool-bar-btn tool-item tool-upload is-disabled'
              : 'tool-bar-btn tool-item tool-upload'
          "
        >
          <svg-icon icon-class="open" /> {{language==='zh'?zh_list.AAw:'File'}}
          <i class="el-icon-arrow-down"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="openLocal">{{language==='zh'?zh_list.AAv:'Open Files'}}</el-dropdown-item>
          <el-dropdown-item divided command="openFeature" v-if="sequenceId"
            >{{language==='zh'?zh_list.AAx:'Open Annotation Files'}}</el-dropdown-item
          >
          <el-dropdown-item divided command="openConfigFileSingle" v-if="sequenceId"
            >{{language==='zh'?zh_list.AAy:'Open Editing File'}}</el-dropdown-item
          >
        </el-dropdown-menu>
      </el-dropdown>
      <span class="tool-bar-line" v-if="sequenceId"></span>
      <el-dropdown v-if="sequenceId" @command="handleCommandB" :disabled="readOnly">
        <span
          :class="
            readOnly
              ? 'tool-bar-btn tool-item tool-upload is-disabled'
              : 'tool-bar-btn tool-item tool-upload'
          "
        >
           {{language==='zh'?zh_list.AAz:'Feature'}}
          <i class="el-icon-arrow-down"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item divided command="openGb"
            >{{language==='zh'?zh_list.AAA:'Import Features from Genbank Files'}}</el-dropdown-item
          >
          <el-dropdown-item divided command="mergeFeature"
            >{{language==='zh'?zh_list.AAB:'Join Features'}}</el-dropdown-item
          >
          <el-dropdown-item divided command="showRemoveFeatureDialog"
            >{{language==='zh'?zh_list.AAC:'Delete Redundant Features'}}</el-dropdown-item
          >

        </el-dropdown-menu>
      </el-dropdown>
      <span class="tool-bar-line" v-if="sequenceId && !showChooseFile"></span>
      <el-dropdown v-if="sequenceId && !showChooseFile" @command="handleCommandC" :disabled="readOnly">
        <span
          :class="
            readOnly
              ? 'tool-bar-btn tool-item tool-upload is-disabled'
              : 'tool-bar-btn tool-item tool-upload'
          "
        >
           {{language==='zh'?zh_list.editA:'Edit'}}
          <i class="el-icon-arrow-down"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="cut">{{language==='zh'?zh_list.cutA:'Cut'}}</el-dropdown-item>
          <el-dropdown-item divided command="insert">{{language==='zh'?zh_list.insertA:'Insert'}}</el-dropdown-item>
          <el-dropdown-item divided command="replace">{{language==='zh'?zh_list.replaceA:'Replace'}}</el-dropdown-item>
          <el-dropdown-item divided command="copy">{{language==='zh'?zh_list.copyA:'Copy'}}</el-dropdown-item>
          <el-dropdown-item divided command="paste">{{language==='zh'?zh_list.pasteA:'Paste'}}</el-dropdown-item>


        </el-dropdown-menu>
      </el-dropdown>
             <span class="tool-bar-line" v-if="sequenceId"></span>
       <el-dropdown v-if="sequenceId" @command="handleCommandA" :disabled="readOnly">
        <span
          :class="
            readOnly
              ? 'tool-bar-btn tool-item tool-upload is-disabled'
              : 'tool-bar-btn tool-item tool-upload'
          "
        >
          {{language==='zh'?zh_list.AAD:'Global_Operation'}}
          <i class="el-icon-arrow-down"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item divided command="showRemoveTypeFeatureDialog"
            >{{language==='zh'?zh_list.AAE:'Delete by Feature Type'}}</el-dropdown-item
          >
          <el-dropdown-item divided command="showRemoveTypeFeatureBetweenDialog"
            >{{language==='zh'?zh_list.AAF:'Truncate Genome'}}</el-dropdown-item
          >
          <el-dropdown-item divided command="showCodonReplaceDialog" v-if="sequenceId"
            >{{language==='zh'?zh_list.AAG:'Replace Stop Codons'}}</el-dropdown-item
          >
          <el-dropdown-item divided command="showInsertDialog" v-if="sequenceId"
            >{{language==='zh'?zh_list.AAH:'Insert by Feature Type'}}</el-dropdown-item
          >
        </el-dropdown-menu>
      </el-dropdown>
      <span class="tool-bar-line"></span>
      <div style="cursor: pointer" @click="$refs.settingDialog.show()">
        <i class="el-icon-setting"></i> <span>{{language==='zh'?zh_list.AAI:'Display_settings'}}</span>
      </div>

      <remove-repeat-feature-dialog
      :sequenceName="sequenceName"
        :sequenceId="sequenceId"
        ref="removeRepeatFeatureDialog"
        @ok="removeRepeatFeatureOk"
      ></remove-repeat-feature-dialog>
      <remove-type-feature-between-dialog
      :sequenceName="sequenceName"
        :sequenceId="sequenceId"
        :programmeId="programmeId"
        ref="removeRepeatFeatureBetweenDialog"
        @ok="removeRepeatFeatureOk"
      ></remove-type-feature-between-dialog>
      <remove-type-feature-dialog
        :sequenceId="sequenceId"
        :sequenceName="sequenceName"
        :programmeId="programmeId"
        ref="removeTypeFeatureDialog"
        @ok="removeRepeatFeatureOk"
      ></remove-type-feature-dialog>
      <codon-replace-dialog
      ref="codonReplaceDialog"
      :sequenceName="sequenceName"
      :sequenceId="sequenceId"
      @ok="removeRepeatFeatureOk"
    ></codon-replace-dialog>
    <merge-feature-dialog
      ref="mergeFeatureDialog"
      :sequenceId="sequenceId"
      @ok="mergeFeatureOk"
    ></merge-feature-dialog>
    <import-feature-from-gb-dialog
      ref="importFeatureFromGbDialog"
      :sequenceId="sequenceId"
      @ok="importFeatureFromGbOk"
    ></import-feature-from-gb-dialog>
    <import-feature-dialog
      ref="importFeatureDialog"
      :sequenceId="sequenceId"
      @ok="importFeatureOk"
    ></import-feature-dialog>
      <programme-dialog
          ref="programmeDialog"
          @ok="newProgrammeOk"
        ></programme-dialog>
    <setting-dialog
      ref="settingDialog"
      :programmeId="programmeId"
    ></setting-dialog>
    <edit-feature-name-dialog ref="editFeatureNameDialog" @ok="editFeatureNameOk"></edit-feature-name-dialog>
    </div>
        <el-button
          type="primary"
          @click="$refs.programmeDialog.showDialog()"
          size="small"
        >
          {{language==='zh'?zh_list.aAo:'New Design'}}
        </el-button>
  </div>
</template>
<script>
import EditFeatureNameDialog from "./EditFeatureNameDialog";
import SettingDialog from "./SettingDialog";
import { queryList } from "@/api/system/modelInfo";
import ProgrammeDialog from "./ProgrammeDialog";
import RemoveRepeatFeatureDialog from "./RemoveRepeatFeatureDialog";
import RemoveTypeFeatureDialog from "./RemoveTypeFeatureDialog"
import MergeFeatureDialog from "./MergeFeatureDialog"
import ImportFeatureFromGbDialog from "./ImportFeatureFromGbDialog"
import ImportFeatureDialog from "./ImportFeatureDialog"
import CodonReplaceDialog from "./CodonReplaceDialog"
import RemoveTypeFeatureBetweenDialog from "./RemoveTypeFeatureBetweenDialog"
import { closeFeatureDialog } from "@/utils/hmzhkj";
import { zh_list } from "@/data/constant";

export default {
  name: "ToolBar",
  props: {
    showChooseFile: {
      type: Boolean,
      default: function (params) {
        true;
      },
    },
    programmeId: {
      type: String,
      default: function (params) {
        return null;
      },
    },
    sequenceId: {
      type: Number,
      default: function (params) {
        return null;
      },
    },
    openDisabled: {
      type: Boolean,
      default: function (params) {
        return false;
      },
    },
    replaceDisabled: {
      type: Boolean,
      default: function () {
        return true;
      },
    },
    insertDisabled: {
      type: Boolean,
      default: function () {
        return true;
      },
    },
    copyDisabled: {
      type: Boolean,
      default: function () {
        return true;
      },
    },
    pasteDisabled: {
      type: Boolean,
      default: function () {
        return true;
      },
    },
    saveLoading: {
      type: Boolean,
      default: function () {
        return false;
      },
    },
    sequenceList: {
      type: Array,
      default: function (params) {
        return [];
      },
    },
    sequenceId: {
      type: Number,
      default: function (params) {
        return null;
      },
    },
    sequenceName: {
      type: String,
      default: function (params) {
        return null;
      },
    },
    selectedInfo: {
      type: Object,
      default: function (params) {
        return {};
      },
    },
    sequenceData: {
      type: Object,
      default: function (params) {
        return {};
      },
    },
    readOnly: {
      type: Boolean,
      default: function () {
        return true;
      },
    },
  },
  components: {
    EditFeatureNameDialog,
    SettingDialog,
    ProgrammeDialog,
    RemoveRepeatFeatureDialog,
    RemoveTypeFeatureDialog,
    RemoveTypeFeatureBetweenDialog,
    CodonReplaceDialog,
    MergeFeatureDialog,
    ImportFeatureFromGbDialog,
    ImportFeatureDialog
  },
  data() {
    return {
      zh_list,
      language: process.env.VUE_APP_LANGUAGE,
      modelInfoList: [],
    };
  },
  mounted() {
    queryList().then((res) => {
      this.modelInfoList = res.data;
    });
  },
  methods: {
    rangeClick(){
      closeFeatureDialog()
    },
    handleCommandA(command){
      if(command==='showRemoveFeatureDialog'){
        this.showRemoveFeatureDialog()
      }
      if(command==='showRemoveTypeFeatureDialog'){
        this.showRemoveTypeFeatureDialog()
      }
      if(command==='showRemoveTypeFeatureBetweenDialog'){
        this.showRemoveTypeFeatureBetweenDialog()
      }
      if(command==='showInsertDialog'){
        this.showInsertDialog()
      }
      if(command==='showCodonReplaceDialog'){
        this.showCodonReplaceDialog()
      }
      if(command === 'mergeFeature'){

        this.$refs.mergeFeatureDialog.show()
      }
    },
    handleCommandB(command){
      if(command==='showRemoveFeatureDialog'){
        this.showRemoveFeatureDialog()
      }
      if(command==='showRemoveTypeFeatureDialog'){
        this.showRemoveTypeFeatureDialog()
      }
      if(command==='showRemoveTypeFeatureBetweenDialog'){
        this.showRemoveTypeFeatureBetweenDialog()
      }
      if(command==='showInsertDialog'){
        this.showInsertDialog()
      }
      if(command==='showCodonReplaceDialog'){
        this.showCodonReplaceDialog()
      }
      if(command === 'mergeFeature'){

        this.$refs.mergeFeatureDialog.show()
      }
      if(command === 'openGb'){
        this.$refs.importFeatureFromGbDialog.show()
      }
    },
    handleCommandC(command){
      if(command==='insert'){
        this.insert()
      }
      if(command==='cut'){
        this.cut()
      }
      if(command==='copy'){
        this.copy()
      }
      if(command==='paste'){
        this.paste()
      }
      if(command==='replace'){
        this.replace()
      }
    },
    showRemoveFeatureDialog() {
      this.$refs.removeRepeatFeatureDialog.show(this.sequenceId);
    },
    showRemoveTypeFeatureDialog() {
      this.$refs.removeTypeFeatureDialog.show(this.sequenceId);
    },
    showRemoveTypeFeatureBetweenDialog() {
      this.$refs.removeRepeatFeatureBetweenDialog.show(this.sequenceId);
    },
    editFeatureNameOk(data){
      this.$emit('edit-feature-name-ok',data)
    },
    newProgrammeOk(id) {
      this.$router.replace({ path: "/", query: { id: id } });
      location.reload();
    },
                handleCommand(command) {
      if (!this.toolInsertBtnDisabled) {

        if (command.number === "M0000006") {
          this.$refs.geneExpressionDialog.showDialog(command);
        } else {
          this.$refs.rangeDialog.showDialog(command);
        }
      }
    },
    handleCommandOpen(command) {
      if (command === "openLocal") {
        if(this.sequenceId){
          this.$confirm(this.language==='zh'?this.zh_list.AAJ:"Do you want to proceed regardless of whether you wish to overwrite the current file?", this.language==='zh'?'提示':"Prompt", {
            confirmButtonText: this.language==='zh'?this.zh_list.confirmA:"OK",
            cancelButtonText: this.language==='zh'?zh_list.cancelA:"Cancel",
            type: "warning",
          }).then(() => {
            document.querySelector("#open-local input").click();
          });
        }else{
          document.querySelector("#open-local input").click();
        }

      }
      if (command === "openGenePool") {
        this.$emit("openGenePool");
      }
      if (command === "openConfigFile") {
        this.$confirm(this.language==='zh'?this.zh_list.AAJ:"Do you want to proceed regardless of whether you wish to overwrite the current file?", this.language==='zh'?'提示':"Prompt", {
          confirmButtonText: this.language==='zh'?this.zh_list.confirmA:"OK",
          cancelButtonText: this.language==='zh'?zh_list.cancelA:"Cancel",
          type: "warning",
        }).then(() => {
          document.querySelector("#open-config input").click();
        });
      }
      if (command === "openConfigFileSingle") {
         this.$refs.editFeatureNameDialog.show()
              }
      if(command === 'openFeature'){
          this.$refs.importFeatureDialog.show()
      }
      if(command === 'openGb'){
        this.$refs.importFeatureFromGbDialog.show()
      }
    },
    showInsertDialog(){
      this.$emit("insert",true);
    },
    showCodonReplaceDialog(){
      this.$refs.codonReplaceDialog.show(this.sequenceId)
    },
    removeRepeatFeatureOk() {
      this.$emit("remove-repeat-feature-ok");
    },
    mergeFeatureOk(data){
      this.$emit("merge-feature-ok",data)
    },
    importFeatureFromGbOk(data){
      this.$emit('import-feature-from-gb-ok',data)
    },
    importFeatureOk(){
      this.$emit('import-feature-ok',data)
    },
    importFeatureOk(data){
      this.$emit('import-feature-ok',data)
    },
    save() {
      this.$emit("save");
    },
    replace() {
      if (this.replaceDisabled) {
        return;
      }
      this.$emit("replace");
    },
    uploadPart(e) {
      const file = e.target.files[0];
    },
    open() {
      this.$emit("open");
    },
    copy() {
      if (!this.copyDisabled) {
        this.$emit("copy");
      }
    },
    cut() {
      if (this.copyDisabled) {
        return;
      }
      this.$emit("cut");
    },
    paste() {
      if (this.pasteDisabled) {
        return;
      }
          },
    insert() {
      if (this.insertDisabled) {
        return;
      }
      this.$emit("insert");
    },
    showAnalyse(text) {
      let one = "";

      for (let item of this.modelInfoList) {
        if (item.modelName == text) {
          one = item;
        }
      }

      if (one.number === "M0000006") {
        this.$refs.geneExpressionDialog.showDialog(one);
      } else {
        this.$refs.rangeDialog.showDialog(one);
      }
    },
    rangeDialogData(data) {
      this.$emit(
        "getFeature",
        { id: +data.chr, start: data.start },
        null,
        data
      );
    },
  },
};
</script>
<style>
.tool-upload {
  position: relative;
  cursor: pointer;
}
.tool-upload-input {
  position: absolute;
  width: 90px;
  height: 100%;
  left: 0;
  opacity: 0;
  z-index: -1;
  cursor: pointer;
}
</style>
<style scoped lang="scss">
@import "@/assets/styles/variables.scss";
.tool-bar {
  height: $tool-height;
  display: flex;
  background-color: $tool-bar-background;
  border-top: 1px solid #ededed;
  border-bottom: 1px solid #ededed;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  .tool-bar-left {
    display: flex;
    align-items: center;
  }
  .tool-bar-right {
    display: flex;
    align-items: center;
  }
  .tool {
    display: flex;
    align-items: center;
    font-size: 14px;
    .svg-icon {
      width: 1.2em;
      height: 1.2em;
    }
    .design-tast-save-time {
      font-size: 12px;
      font-weight: 400;
      color: #666666;
    }
    .design-tast-lock {
      background-color: #fef5e4;
      color: #f7a102;
      font-size: 12px;
      margin-left: 20px;
      padding: 0 6px;
      border-radius: 6px;
    }
    .tool-bar-line {
      height: 16px;
      border-left: 1px solid #ededed;
      margin-left: 15px;
      margin-right: 15px;
    }
    .tool-bar-btn {
      color: #656565;
      font-size: 14px;
      cursor: pointer;
    }
    .is-disabled {
      color: #a3a4b2;
      opacity: 0.5;
      cursor: not-allowed;
    }
    .tool-item {
      margin-right: 15px;
    }
  }
}
</style>

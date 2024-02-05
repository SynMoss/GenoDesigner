<template>
  <div class="design">
    <div @click="hideDrawer">
      <tool-bar
        ref="toolBar"
        @copy="triggerClipboardCommand('copy')"
        @paste="triggerClipboardCommand('paste')"
        @cut="triggerClipboardCommand('cut')"
        @insert="showInsertDialog"
        @replace="showReplaceDialog"
        @show-insert-dialog="showInsertDialog"
        @merge-feature-ok="mergeFeatureOk"
        :saveLoading="saveLoading"
        :copyDisabled="copyDisabled"
        :insertDisabled="insertDisabled"
        :replaceDisabled="replaceDisabled"
        :showChooseFile="showChooseFile"
        :programmeId="id"
        :sequenceList="sequenceList"
        :sequenceId="sequenceId"
        :sequenceName="sequenceName"
        :readOnly="readOnly"
        :selectedInfo="insertAndSelectObj"
        :sequenceData="sequenceData"
        :pasteDisabled="pasteDisabled"
        @getFeature="loadSequence"
        @edit-feature-name-ok="editFeatureNameOk"
        @import-feature-from-gb-ok="openImportFeatureFromGb"
        @import-feature-ok="openImportFeature"
        @remove-repeat-feature-ok="
          sequenceChooseOk({ id: sequenceId, name: sequenceName })
        "
      ></tool-bar>
    </div>
    <div
      class="content"
      v-loading="loading"
      :element-loading-text="language==='zh'?zh_list.B:'Loading...'"
      element-loading-spinner="el-icon-loading"
      ref="content"
      @click="hideDrawer"
    >
      <range
        :sequenceLengthToShow="sequenceLengthToShow"
        :showChooseFile="showChooseFile"
        ref="range"
        :sourceFileNameShow="sourceFileNameShow"
        :sequenceList="sequenceList"
        @search-ok-high="searchOkHigh"
        @search-ok="loadSequence"
        :bpLength="bpLength"
      ></range>
      <div class="ove-f" od="ove-f" ref="oveF">
        <div v-show="!showChooseFile" class="content-ove" ref="ove"></div>
        <file-sequence-choose
          v-if="showChooseFile"
          ref="fileSequenceChoose"
          :programmeId="id"
        ></file-sequence-choose>
      </div>
    </div>
    <div class="right-menu">
      <div
        class="right-menu-item"
        :class="drawerIndex === 0 && 'checked'"
        @click="clickShowDrawer(0)"
      >
        <el-tooltip
          class="item"
          effect="dark"
          :content="language==='zh'?zh_list.vv:'Information'"
          placement="left"
          ><svg-icon icon-class="right02"
        /></el-tooltip>
      </div>
      <div
        class="right-menu-item"
        :class="drawerIndex === 1 && 'checked'"
        @click="clickShowDrawer(1)"
      >
        <el-tooltip
          class="item"
          effect="dark"
          :content="language==='zh'?zh_list.ww:'Annotation'"
          placement="left"
          ><svg-icon icon-class="right01"
        /></el-tooltip>
      </div>
      <div
        class="right-menu-item"
        :class="drawerIndex === 2 && 'checked'"
        @click="clickShowDrawer(2)"
      >
        <el-tooltip
          class="item"
          effect="dark"
          :content="language==='zh'?zh_list.xx:'History'"
          placement="left"
          ><svg-icon icon-class="right03"
        /></el-tooltip>
      </div>
      <div
        class="right-menu-item"
        :class="drawerIndex === 3 && 'checked'"
        @click="clickShowDrawer(3)"
      >
        <el-tooltip
          class="item"
          effect="dark"
          :content="language==='zh'?zh_list.yy:'Result'"
          placement="left"
          ><svg-icon icon-class="job"
        /></el-tooltip>
      </div>
    </div>
    <div class="design-drawer" :class="drawerVisibleClass">
      <div v-show="drawerIndex === 0">
        <div class="drawer-title">{{language==='zh'?zh_list.vv:"Information"}}</div>
        <programme-form
          ref="programmeForm"
          :readOnly="readOnly"
          @ok="programmeFormOk"
          :isInit="isInit"
        ></programme-form>
      </div>
      <div v-show="drawerIndex === 1">
        <el-tooltip
          class="item"
          effect="dark"
          content="drawer-title"
          placement="right"
        >
          <div slot="content" v-if="language==='zh'">
            {{ this.zh_list.zz }}<br />

            {{ this.zh_list.aaa }}
          </div>
          <div slot="content" v-else>
          In a GenBank file, the priority order for parsing keyword-value
            pairs is as follows:<br />

            If a source ID value is available, it takes priority over the
            keywords value in the parsing of a GenBank file. If no reference
            value is available, the file name should be used instead.
            </div>
          <span
            style="
              color: #333;
              font-size: 16px;
              font-weight: 700;
              display: -webkit-box;
              display: -ms-flexbox;
              -webkit-box-pack: justify;
              -ms-flex-pack: justify;
              justify-content: space-between;
              padding: 14px 20px;
              padding-top: 14px;
              padding-right: 20px;
              padding-bottom: 14px;
              padding-left: 20px;
              border-bottom: 1px solid #ededed;
              border-bottom-width: 1px;
              border-bottom-style: solid;
              border-bottom-color: rgb(237, 237, 237);
              margin-bottom: 20px;
              display: inline-block;
            "
            >{{language==='zh'?zh_list.ww:'Annotation'}}<i class="el-icon-question"></i
          ></span>
        </el-tooltip>

        <div>
          <el-radio-group v-model="annotation">
            <el-row>
              <el-col :span="24" v-for="item in annotationList" :key="item.key">
                <el-radio
                  :disabled="!showChooseFile || !item.isValid"
                  :label="item.key"
                >
                  <span v-if="item.source" style="padding-left: 25px">{{
                    item.source
                  }}</span>
                  <span v-else-if="item.keyWords" style="padding-left: 25px">{{
                    item.keyWords
                  }}</span>
                  <span v-else>{{ item.sourceFileName }}</span>
                </el-radio>
              </el-col>
            </el-row>
          </el-radio-group>
        </div>
        <el-button
          v-if="showChooseFile"
          size="mini"
          @click="confirmAnnotation"
          :disabled="this.annotation === null || chooseFileLoading"
          :loading="chooseFileLoading"
          style="margin-top: 10px"
          >{{language==='zh'?zh_list.OK:'OK'}}</el-button
        >
      </div>
      <div v-show="drawerIndex === 2">
        <div class="drawer-title">{{language==='zh'?zh_list.xx:'History'}}</div>
        <history
          :sequenceName="sequenceName"
          :programmeInfo="programmeInfo"
          :readOnly="readOnly"
          ref="history"
          :sequenceId="sequenceId"
          @rollback="rollback"
          @refresh="refresh"
        ></history>
      </div>
      <div v-show="drawerIndex === 3">
        <div class="drawer-title">{{language==='zh'?zh_list.yy:'Result'}}</div>
        <vxe-table
          border="inner"
          height="300"
          :scroll-y="{enabled: true}"
          class="table-self"
          header-cell-class-name="table-header-self"
          cell-class-name="table-cell-self"
          :data="searchResult"
          ref="projectTable"
        >
        <vxe-column type="seq" :title="language==='zh'?zh_list.bbb:'NO'" width="80px"></vxe-column>
          <vxe-column field="start" :title="language==='zh'?zh_list.ccc:'Start'" width="80px"></vxe-column>
          <vxe-column field="strand" title="strand" width="80px"></vxe-column>
          <vxe-column field="j" :title="language==='zh'?zh_list.n:'Operation'">
            <template #default="{ row }">
              <a
                @click="jumpSearchResult(row)"
                type="text"
                size="small"
                class="table-opera"
              >
              {{language==='zh'?zh_list.ddd:'Jump'}}
              </a>
            </template>
          </vxe-column>
          <template #empty>
            <el-empty :description="language==='zh'?zh_list.C:'No Results'"></el-empty>
          </template>
        </vxe-table>
      </div>
    </div>
    <insert-dialog
      :sequenceId="sequenceId"
      :programmeId="id"
      ref="insertDialog"
      @ok="insertOk"
    ></insert-dialog>
    <replace-dialog
      ref="replaceDialog"
      :bpLength="bpLength"
      @ok="insertOk"
    ></replace-dialog>
    <sequence-choose-dialog
      ref="sequenceChooseDialog"
      @ok="sequenceChooseOk"
    ></sequence-choose-dialog>
    <slice-upload
      :sequenceId="sequenceId"
      :sequenceName="sequenceName"
      :programmeId="id"
      :openImportFeatureFromGbCheckAll="openImportFeatureFromGbCheckAll"
      :isEditFeatureName="isEditFeatureName"
      :openImportFeatureCheckAll="openImportFeatureCheckAll"
      ref="sliceUpload"
      @ok="sliceUploadOk"
      @begin="sliceUploading"
      @cancel="cancelSliceUpload"
    ></slice-upload>
         <div class="init-programmer" v-show="showEmpty">
      <div>
        <el-empty
          :description="language==='zh'?zh_list.eee:'Please Upload a Genome'"
          :image="require('../../assets/images/03@2x.png')"
        ></el-empty>
        <div class="btn" v-loading="uploading">
          <span @click="changeUploadType(0)" id="open-local"
            >{{language==='zh'?'打开本地文件':'Open Local Files'}}</span
          >
                     <span @click="changeUploadType(1)" id="open-config" v-show="false"
            >{{language==='zh'?zh_list.fff:'Open Configuration Files'}}</span
          >
          <span @click="changeUploadType(4)" id="open-config-single" v-show="false"
            >{{language==='zh'?zh_list.fff:'Open Configuration Files'}}</span
          >
          <span @click="changeUploadType(2)" id="open-feature" v-show="false"
            >{{language==='zh'?zh_list.ggg:'import feature from file'}}</span
          >
          <span @click="changeUploadType(3)" id="open-gb" v-show="false"
            >{{language==='zh'?zh_list.hhh:'import feature from file'}}</span
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Range from "./modules/Range";
import FileSequenceChoose from "./modules/FileSequenceChoose";
import ProgrammeForm from "./modules/ProgrammeForm";
import { queryList } from "@/api/system/modelInfo";
import SliceUpload from "./modules/SliceUpload";
import History from "./modules/History";
import SequenceChooseDialog from "./modules/SequenceChooseDialog";
import InsertDialog from "./modules/InsertDialog";
import ReplaceDialog from "./modules/ReplaceDialog";
import ToolBar from "./modules/ToolBar";
import { ONLY_Char_ARRAY,OPERATE_TYPE,DEFAULT_BP_LENGTH} from "@/data/constant";
import { uploadFile, postAction, getAction, deleteAction } from "@/api/manage";
import { mergeFeature,listBySequenceId } from '@/api/system/sequence'
import {
  listSequence,
  getSequence,
  saveOperate,
  operateRollback,
  chooseFile,
  saveFeatureList,
  insertSequenceByFeatureTypeEnd3,
} from "@/api/system/sequence";
import { debounce } from "xe-utils";

import { zh_list } from "@/data/constant";
export default {
  name: "Design",
  components: {
    ToolBar,
    InsertDialog,
    ReplaceDialog,
    History,
    SequenceChooseDialog,
    SliceUpload,
    Range,
    ProgrammeForm,
    FileSequenceChoose,
  },
  data() {
    return {
      zh_list,
      language: process.env.VUE_APP_LANGUAGE,
      dialogTitle: "",
      dialogVisible: false,
      dialogTipList: [],
      OPERATE_TYPE,
      insertDisabled: true,
      replaceDisabled: true,
      copyDisabled: true,
      drawerIndex: null,
      drawerTitle: "",
      drawerVisibleClass: null,
      insertAndSelectObj: null,
      oveEditor: null,
      sourceFeatureKeys: [],
      sequenceData: {},
      sliderValue: 0,
      value1: "",
      value2: "",
      selectionSequence: "",
      annotationList: [],
      annotation: null,
      chooseFileLoading: false,
      showEmpty: true,
      showChooseFile: false,
      id: null,
      sequenceList: [],
      uploading: false,
      saveLoading: false,
      loading: true,
      hasFile: false,
      info: null,
      bpLength: null,
      sequenceId: null,
      sequenceName: null,
      currentSeqIndex: 0,
      programmeInfo: {},
      modelInfoList: [],
      modelNameList: [],
      oveDisabled: false,
      readOnly: false,
      isCreate: true,
      isInit: false,
      openImportFeatureFromGbCheckAll: false,
      isEditFeatureName: false,
      openImportFeatureCheckAll: false,
      panelsShown: [
        [
          {
            id: "rail",
            name: "Linear Map",
            active: true,
          },
          {
            id: "circular",
            name: "Circular Map",
            active: false,
          },
          {
            id: "properties",
            name: "Table Map",
            active: false,
          },
          {
            id: "sequence",
            name: "Sequence Map",
            active: false,
          },
        ],
      ],
      initSelectionLayer: { start: -1, end: -1 },
      showMessageAfterLoadOve: "",
      sequenceLengthToShow: DEFAULT_BP_LENGTH,
      isCopy: false,
      searchResult:[],
    };
  },
  created() {
    this.id = this.$route.query.id;
    queryList().then((res) => {
      this.modelInfoList = res.data;
    });
    this.initData();
  },
  mounted() {
    queryList().then((res) => {
      this.modelInfoList = res.data;
      let modelInfoList = [];
      modelInfoList = res.data;
      let modelNameList = [];
      modelInfoList.forEach((item) => {
        let modelName = {};
        modelName.text = item.modelName;
        modelNameList.push(modelName);
      });
      this.modelNameList = modelNameList;
      this.modelNameList.forEach((item) => {
        item.onClick = () => {
          this.showAnalyseDialog(item.text);
        };
      });
    });
  },
  computed: {
    sourceFileNameShow() {
      let str = this.programmeInfo.sourceFileNameShow;
      if (!str) {
        return "";
      }
      return str.substring(str.indexOf("/") + 1);
    },
    pasteDisabled() {
      if (
        this.insertAndSelectObj &&
        this.insertAndSelectObj.caretPosition === -1 &&
        this.isCopy
      ) {
        return false;
      }
      return true;
    },
  },
  methods: {
    initData() {
      getAction("/system/programme/getProgrammeAndSequence", {
        id: this.id,
        removeTemFeature: 1,
      }).then((res) => {
        this.loading = false;
        this.programmeInfo = res.data;
        this.id = this.programmeInfo.id;
        let count =
          res.data.maxFeaturesToShow === null ? 50 : res.data.maxFeaturesToShow;
        let annotationLimits = {
          features: count,
          parts: 50,
          primers: 50,
          warnings: 50,
          assemblyPieces: 50,
          lineageAnnotations: 50,
          cutsites: 100,
        };
        this.$cache.local.setJSON("annotationLimits", annotationLimits);
        res.data.sequenceLengthToShow !== null &&
          (this.sequenceLengthToShow = res.data.sequenceLengthToShow);
        res.data.sequenceList && (this.sequenceList = res.data.sequenceList);
        this.annotationList = [];
        if (res.data.programmeState === 0 || !this.isCreate) {
          this.readOnly = true;
        }
        if (res.data.chooseFile) {
          this.annotationList = res.data.annotationList;
          this.showEmpty = false;
          this.showChooseFile = true;
          let sourceFileName = res.data.sequenceList[0].sourceFileName;
          let sequenceList = [];
          let unValidFile = [];
          res.data.sequenceList.forEach((item) => {
            if (item.sourceFileName === sourceFileName) {
              sequenceList.push(item);
            }
          });
          if (unValidFile.length) {
            this.$message.warning(this.language==='zh'?this.zh_list.jjj:"Invalid file" + unValidFile.toString());
          }
          this.sequenceId = sequenceList[0].id;
          this.sequenceName = sequenceList[0].name;
          this.bpLength = sequenceList[0].bpLength;
          this.$refs.range.changeSequenceId({
            id: this.sequenceId,
            name: this.sequenceName,
          });
          this.$nextTick(() => {
            this.$refs.fileSequenceChoose.show({
              name: sequenceList[0].name,
              start: 0,
              currentSeqIndex: 0,
            });
          });
          this.sequenceList = sequenceList;
        } else if (res.data.sourceFileName) {
          let databaseForAnnotation = res.data.databaseForAnnotation;
          this.annotationList.push({
            key: databaseForAnnotation,
            source: databaseForAnnotation,
            isValid: 0,
          });
          this.showEmpty = false;
          this.sequenceList = res.data.sequenceList;
          this.$refs.sequenceChooseDialog.show(this.sequenceList);
        }
      });
    },
    calBtnDisabled() {
      if (
        this.oveEditor &&
        this.insertAndSelectObj &&
        this.insertAndSelectObj.caretPosition !== -1 &&
        this.insertAndSelectObj.selectionLayer.end === -1 &&
        !this.readOnly
      ) {
        this.insertDisabled = false;
      } else {
        this.insertDisabled = true;
      }
      if (
        this.oveEditor &&
        this.insertAndSelectObj &&
        this.insertAndSelectObj.selectionLayer.start !== -1 &&
        this.insertAndSelectObj.caretPosition === -1 &&
        !this.readOnly
      ) {
        this.replaceDisabled = false;
      } else {
        this.replaceDisabled = true;
      }
      if (
        this.oveEditor &&
        this.insertAndSelectObj &&
        this.insertAndSelectObj.selectionLayer.start !== -1 &&
        this.insertAndSelectObj.caretPosition === -1 &&
        !this.readOnly
      ) {
        this.copyDisabled = false;
      } else {
        this.copyDisabled = true;
      }
    },
    sliceUploading() {
      this.uploading = true;
      this.loading = true;
    },
    cancelSliceUpload() {
      this.uploading = false;
      this.loading = false;
      this.initData();
    },
    sliceUploadOk(sequenceList, chooseFile) {
      this.uploading = false;
      this.loading = false;
      this.sequenceList = sequenceList;
      if (chooseFile !== undefined) {
        this.showChooseFile = chooseFile ? true : false;
        if (this.showChooseFile && this.hasFile) {
          location.reload();
        }
      }
      this.initData();
    },
    changeUploadType(type) {
      this.$refs.sliceUpload.changeUploadType(type, this.sequenceId);
    },
    openImportFeatureFromGb(checkAll){
      this.openImportFeatureFromGbCheckAll = checkAll
      document.querySelector("#open-gb input").click()
    },
    editFeatureNameOk(data){
      this.isEditFeatureName = data
      document.querySelector("#open-config-single input").click();
    },
    openImportFeature(checkAll){
      this.openImportFeatureCheckAll = checkAll
      document.querySelector("#open-feature input").click()
    },
    readFile(file) {
      return new Promise(function (resolve, reject) {
        const reader = new FileReader();
        reader.onload = function fileReadCompleted() {
          resolve(reader.result);
        };
        reader.readAsText(file);
      });
    },
    uploadConfigurationPart(e) {
      if (this.hasFile) {
        this.$confirm(
          "Do you want to proceed regardless of whether you wish to overwrite the current file?",
          "Prompt",
          {
            confirmButtonText: "OK",
            cancelButtonText: "Cancel",
            type: "warning",
          }
        )
          .then(() => {
            const configFile = e.target.files[0];
            const txtFile = e.target.files[1];
            const formData = new FormData();
            this.uploading = true;
            formData.append("configFile", configFile);
            formData.append("programmeId", this.id);
            formData.append("file", txtFile);
            uploadFile("/system/file/openWithConfig", formData).then((res) => {
              this.sequenceList = res.data.fileParseVoList;
              this.uploading = false;
              this.hasFile = true;
              this.$refs.sequenceChooseDialog.show(this.sequenceList);
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "Canceled",
            });
          });
      } else {
        const configFile = e.target.files[0];
        const txtFile = e.target.files[1];
        const formData = new FormData();
        this.uploading = true;
        formData.append("configFile", configFile);
        formData.append("programmeId", this.id);
        formData.append("file", txtFile);
        uploadFile("/system/file/openWithConfig", formData).then((res) => {
          this.sequenceList = res.data.fileParseVoList;
          this.uploading = false;
          this.hasFile = true;
          this.$refs.sequenceChooseDialog.show(this.sequenceList);
        });
      }
    },
    async sequenceChooseOk({ id, name }) {
      await getAction("/system/programme/queryById", { id: this.id }).then(
        (res) => {
          this.programmeInfo = res.data;
          if (res.data.chooseFile) {
            this.showChooseFile = true;
          } else {
            this.showChooseFile = false;
          }
        }
      );
      this.loadSequence({ id: id, start: 0 });
      this.$refs.range.changeSequenceId({ id: id, name: name||this.sequenceName });
    },
    async mergeFeatureOk(checkAll){
      this.loading = true
      let subData = {sequenceId: this.sequenceId}
      let sequenceList
      if(checkAll){
        await listBySequenceId({sequenceId: this.sequenceId}).then(res=>{
          sequenceList = res.data
        })
      }else{
        sequenceList = [{id:this.sequenceId,name:this.sequenceName}]
      }
      for (let index = 0; index < sequenceList.length; index++) {
        subData.sequenceId = sequenceList[index].id
        await mergeFeature(subData)
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
            this.$refs.range.rangeOk();
            // this.loading = false;
          }
        })
      }
    },
    searchOkHigh(sequence) {
      this.initOve(sequence, sequence.selection);
      this.searchResult = sequence.searchList
    },
    jumpSearchResult(selection){
      this.$refs.range.jump(selection)
    },
    // handleMaxFeatures(result){
    //   let annotationLimits ={"features":result.maxFeaturesToShow,"parts":50,"primers":50,"warnings":50,"assemblyPieces":50,"lineageAnnotations":50,"cutsites":100}
    //   this.$cache.local.setJSON('annotationLimits',annotationLimits)
    //   this.sequenceLengthToShow = result.sequenceLengthToShow
    //   this.$refs.range.init()
    //   this.loadSequence({id: this.sequenceId,start:this.currentSeqIndex})
    // },
    async loadSequence({ id, start }, selection, feature) {
      this.hideDrawer();
      this.sequenceId = id;
      if (this.sequenceLengthToShow >= this.bpLength) {
        start = 0;
      }
      this.currentSeqIndex = start;
      if (this.showChooseFile) {
        let seq = this.sequenceList.find((item) => item.id === id);
        this.sequenceName = seq.name;
        this.bpLength = seq.bpLength;
        this.$refs.fileSequenceChoose.show({
          name: this.sequenceName,
          start: start,
          currentSeqIndex: this.currentSeqIndex,
        });
      } else {
        this.loading = true;
        // if (feature) {
        //   feature.sequenceId = id;
        //   await saveFeatureList([feature]);
        // }
        getSequence({ id, start })
          .then((res) => {
            this.initOve(res.data, selection);
          })
          .finally((err) => {
            this.loading = false;
          });
      }
    },
    initOve(sequence, selection) {
      this.currentSeqIndex = sequence.start;
      this.showEmpty = false;
      this.hasFile = true;
      this.sequenceName = sequence.name;
      this.bpLength = sequence.bpLength;
      sequence.features.forEach((feat) => {
        if (!feat.locations) {
          feat.locations = [{ start: feat.start, end: feat.end }];
        }
        feat.start = feat.start - this.currentSeqIndex;
        feat.end = feat.end - this.currentSeqIndex;
        feat.locations.forEach((location) => {
          location.start = location.start - this.currentSeqIndex;
          location.end = location.end - this.currentSeqIndex;
        });
      });
      this.sequenceData = sequence;
      if (this.oveEditor) {
        this.panelsShown = this.oveEditor.getState().panelsShown;
      }
      this.loadOve(selection);
    },
    newFeature({ name, start, end }) {
      this.sequenceData.features.push({ name, start, end });
      this.updateOve();
    },
    loadOve(selection) {
      let array = document.getElementsByClassName("bp3-portal ove-portal");
      for (let i = array.length - 1; i >= 0; i--) {
        array[i].parentNode.removeChild(array[i]);
      }
      this.oveEditor = null;
      let timer = setInterval(() => {
        if (window.createVectorEditor) {
          clearInterval(timer);
          if (selection) {
            this.initSelectionLayer.start =
              selection.start - this.currentSeqIndex;
            this.initSelectionLayer.end = selection.end - this.currentSeqIndex;
          } else {
            this.initSelectionLayer.start = -1;
            this.initSelectionLayer.end = -1;
          }
          this.createOve();
          this.insertAndSelectObj = window.insertAndSelectObj;
          this.insertAndSelectObj.selectionLayer = this.initSelectionLayer;
          this.insertAndSelectObj.caretPosition = -1;
          this.calBtnDisabled();
          this.loading = false;
          if (this.showMessageAfterLoadOve) {
            this.$message.success(this.showMessageAfterLoadOve);
            this.showMessageAfterLoadOve = "";
          }
        }
      }, 500);
    },
    createOve() {
      const oveDom = this.$refs.ove;
      this.oveEditor = window.createVectorEditor(oveDom, {
        withPreviewMode: false,
        editorName: "Sequence",
        showMenuBar: false,
        disableSetReadOnly: false,
        showReadOnly: false,
        onSelectionOrCaretChanged: ({
          caretPosition,
          insertAndSelectObj,
          selectionLayer,
        }) => {
          if (!this.insertAndSelectObj) {
            this.insertAndSelectObj = insertAndSelectObj;
          }
          this.insertAndSelectObj.caretPosition = caretPosition;
          this.insertAndSelectObj.selectionLayer = selectionLayer;
          if (caretPosition === -1) {
            let seq = this.oveEditor.getState().sequenceData.sequence;
            this.selectionSequence = seq.substring(
              selectionLayer.start,
              selectionLayer.end + 1
            );
            if (
              selectionLayer.end < selectionLayer.start &&
              this.bpLength > this.sequenceLengthToShow
            ) {
              setTimeout(() => {
                let end = this.sequenceLengthToShow;
                if (seq.length < end) {
                  end = seq.length;
                }
                this.selectionSequence = seq.substring(
                  selectionLayer.start,
                  end
                );
                this.initSelectionLayer = {
                  start: selectionLayer.start,
                  end: end - 1,
                };
                this.updateOve();
              }, 50);
            }
          } else {
            this.selectionSequence = "";
            if (caretPosition > this.sequenceData.sequence.length) {
              caretPosition = this.sequenceData.sequence.length;
              setTimeout(() => {
                this.caretPosition = this.sequenceData.sequence.length;
                this.updateOve();
              }, 50);
            }
            if (caretPosition === this.sequenceData.sequence.length) {
              if (caretPosition + this.currentSeqIndex === this.bpLength) {
                storage("lastCaretPosition", 1 - this.currentSeqIndex);
              } else {
                storage("lastCaretPosition", caretPosition + 1);
              }
              setTimeout(() => {
                storage.remove("lastCaretPosition");
              }, 50);
            }
          }
          this.calBtnDisabled();
        },
        onRename: () => {},
        onNew: () => {},
        onSave: () => {},
        beforeSequenceInsertOrDelete: (
          sequenceDataToInsert,
          existingSequenceData,
          caretPositionOrRange,
          options
        ) => {
          if (!sequenceDataToInsert.size) {
            let start = caretPositionOrRange.start,
              end = caretPositionOrRange.end + 1;
            if (end === 1) {
              end = existingSequenceData.bpLength;
            }
            // if(start>end){
            //   return
            // }
            this.newHistoryObj({
              type: OPERATE_TYPE.CUT,
              // preSequence: existingSequenceData.sequence.substring(start,end),
              start: start,
              end: end,
            });
          }
        },
        onCopy: (event, copiedSequenceData, editorState) => {
          const clipboardData = event.clipboardData;
          clipboardData.setData(
            "application/json",
            JSON.stringify(copiedSequenceData)
          );
          this.clearSequenceDataHistory();
          this.isCopy = true;
          event.preventDefault();
        },
        onPaste: (event, editorState) => {
          // the onPaste here must return sequenceData in the teselagen data format
          const clipboardData = event.clipboardData;
          let jsonData = clipboardData.getData("application/json");
          if (jsonData) {
            jsonData = JSON.parse(jsonData);
          }
          let sequenceData;
          let features=[]
          if (jsonData) {
            sequenceData = { sequence: jsonData.sequence };
            for (const key in jsonData.features) {
              let f= jsonData.features[key]
              f.id = null
              features.push(f)
            }
          } else {
            sequenceData = { sequence: clipboardData.getData("text/plain") };
          }
          if (!this.checkBig(sequenceData.sequence.length)) {
            return;
          }
          if (this.selectionSequence) {
            // replace
            // const start = this.insertAndSelectObj.selectionLayer.start
            // this.newHistoryObj({
            //   type: OPERATE_TYPE.REPLACE,
            //   sequence: sequenceData.sequence,
            //   preSequence: this.selectionSequence,
            //   start: start,
            // })
          } else {
            // insert
            this.newHistoryObj({
              type: OPERATE_TYPE.INSERT,
              sequence: sequenceData.sequence,
              features
            });
          }
          return "";
        },
        ToolBarProps: {
          toolList: [],
        },
        PropertiesProps: {
          propertiesList: ["features"],
        },
        clickOverrides: {
          editorClicked: () => {},
        },
        rightClickOverrides: {
          selectionLayerRightClicked: (
            items,
            { annotation },
            insertAndSelectObj
          ) => {
            if (this.readOnly) {
              return [];
            }
            setTimeout(() => {
              let array = document.getElementsByClassName(
                "bp3-menu-item-label"
              );
              for (let i = array.length - 1; i >= 0; i--) {
                array[i].parentNode.removeChild(array[i]);
              }
            }, 50);
            // createMenuHolder
            return [
              "newFeature",
              {
                text: "Cut",
                onClick: (e, ctxInfo) => {
                  this.cutSequence();
                },
              },
              "copy",
              {
                text: "Replace",
                onClick: (e, ctxInfo) => {
                  this.showReplaceDialog();
                },
              },

            ];
          },
          backgroundRightClicked: (items, shiftHeld, insertAndSelectObj) => {
            if (this.readOnly) {
              return [];
            }
            setTimeout(() => {
              let array = document.getElementsByClassName(
                "bp3-menu-item-label"
              );
              for (let i = array.length - 1; i >= 0; i--) {
                array[i].parentNode.removeChild(array[i]);
              }
            }, 50);
            return [
              "newFeature",
              {
                // insertAndSelectObj here get passed directly to blueprintjs MenuItems
                text: "Insert",
                onClick: (e, ctxInfo) => {
                  this.showInsertDialog();
                },
              },
              {
                text: "Paste",
                onClick: (e, ctxInfo) => {
                  console.log("paste")
                },
              },

            ];
          },
          featureRightClicked: (items) => {
            console.log(items);
            return [
              "deleteFeature",
            ];
          },
        },
      });
      this.updateOve();
    },
    updateOve: debounce(function () {
      let selectionLayer = Object.assign({}, this.initSelectionLayer);
      let activeIndex = 0;
      let panelsShown = this.panelsShown
      if (this.oveEditor) {
        let s = this.oveEditor.getState()
        if(s && s.panelsShown){
          panelsShown = s.panelsShown
        }
      }
      for (let i = 0; i < panelsShown[0].length; i++) {
        const element = panelsShown[0][i];
        if (element.active) {
          activeIndex = i;
        }
      }
      this.oveEditor.updateEditor({
        sequenceData: this.sequenceData,
        readOnly: this.readOnly,
        panelsShown: panelsShown,
        selectionLayer: selectionLayer,
        caretPosition: this.caretPosition,
      });
      setTimeout(() => {
        if (this.sequenceLengthToShow < this.bpLength) {
          document.querySelector(
            ".veStatusBar .veStatusBarItem .bp3-button"
          ).style.display = "none";
        } else {
          document.querySelector(
            ".veStatusBar .veStatusBarItem .bp3-button"
          ).style.display = "";
        }
        let tabsDom = document.getElementsByClassName('ve-draggable-tabs')
        this.loading = false
        if (tabsDom && tabsDom.length) {
          if (tabsDom[0].childNodes.length) {
            if(activeIndex===1){
              this.loading = true
              tabsDom[0].childNodes[0].click()
              setTimeout(() => {
                tabsDom[0].childNodes[activeIndex].click()
                this.loading = false
              }, 50);
            }else{
              tabsDom[0].childNodes[activeIndex].click()
            }

          }
        }
      }, 50);
    }, 100),
    triggerClipboardCommand(type) {
      var wrapper = document.querySelector(".veVectorInteractionWrapper");

      if (!wrapper) {
        return window.toastr.info(
          "Cannot trigger a " + type + " in the current view"
        );
      }
      var hiddenInput = wrapper.querySelector("input.clipboard");
      hiddenInput.focus();
      var worked = document.execCommand(type);
      wrapper.focus();

      if (!worked) {
        var keys = {
          paste: "Cmd/Ctrl+V",
          cut: "Cmd/Ctrl+X",
          copy: "Cmd/Ctrl+C",
        };

        if (keys[type]) {
          // TODO maybe improve msg with HTML
          window.toastr.info("Press " + keys[type] + " to " + type);
        } else {
          console.warn(
            "The " +
              type +
              " command did not work. document.execCommand(" +
              type +
              ") didn't work"
          );
        }
      }
    },
    rollback(obj) {
      this.loading = true;
      operateRollback({ id: obj.id, sequenceId: obj.sequenceId }).then(
        (res) => {
          this.$refs.range.rangeOk();
          this.$refs.history.show();
        }
      );
    },
    programmeFormOk(databaseForAnnotation) {
      this.annotationList[0].source = databaseForAnnotation;
    },
    cutSequence() {
      this.newHistoryObj({
        type: OPERATE_TYPE.CUT,
      });
    },
    showInsertDialog(showType) {
      this.$refs.insertDialog.show(showType);
    },
    checkBig(newSequceceLength) {
      if (!newSequceceLength) {
        newSequceceLength = 0;
      }
      const sequence = this.oveEditor.getState().sequenceData.sequence;
      if (sequence.length + newSequceceLength > 1024 * 1024 * 5) {
        this.$message.warning(this.language==='zh'?this.zh_list.kkk:"Sequence too long");
        return false;
      }
      return true;
    },
    changeAnnotation(array) {},
    confirmAnnotation() {
      this.language==='zh'?
      this.$confirm(this.zh_list.lll, this.zh_list.mmm, {
        confirmButtonText: this.zh_list.nnn,
        cancelButtonText: this.zh_list.ooo,
        type: "warning",
      }):
      this.$confirm("After selection, it cannot be changed. Continue?", "Prompt", {
        confirmButtonText: "OK",
        cancelButtonText: "Cancel",
        type: "warning",
      })
        .then(() => {
          let obj = this.annotationList.find(
            (item) => item.key === this.annotation
          );
          this.chooseFileLoading = true;
          chooseFile({ id: this.id, sourceFileName: obj.key }).then((res) => {
            this.chooseFile = false;
            this.$message.success(this.language==='zh'?this.zh_list.ppp:"Operation succeed!");
            setTimeout(() => {
              location.reload();
            }, 1200);
          });
        })
        .catch(() => {});
    },
    showReplaceDialog() {
      const start =
        this.insertAndSelectObj.selectionLayer.start + this.currentSeqIndex + 1;
      let end =
        this.insertAndSelectObj.selectionLayer.end + this.currentSeqIndex + 1;
      if (end === 1 && end <= start) {
        end = this.currentSeqIndex + this.sequenceData.sequence.length;
      }
      const position = `${start}..${end}`;
      this.$refs.replaceDialog.show({
        sourceSequence: this.selectionSequence,
        position,
      });
    },
    async insertOk(subData) {
      // if (!this.checkBig(content.length)) {
      //   return;
      // }
      let { content, showType,strand } = subData
      if (showType) {
        this.loading = true;
        delete subData.content
        subData.sequenceId = this.sequenceId
        let sequenceList
        if(subData.checkAll){
          await listBySequenceId({sequenceId: this.sequenceId}).then(res=>{
            sequenceList = res.data
          })
        }else{
          sequenceList = [{id:this.sequenceId,name:this.sequenceName}]
        }
        for (let index = 0; index < sequenceList.length; index++) {
          subData.sequenceId = sequenceList[index].id
          subData.strand = strand?-1:1
          await insertSequenceByFeatureTypeEnd3(subData)
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
              this.$refs.range.rangeOk();
              // this.loading = false;
            }
          })
        }

      } else {
        let text = "Inserted";
        let type = OPERATE_TYPE.INSERT;
        if (this.insertAndSelectObj.caretPosition === -1) {
          text = "Replaced";
          type = OPERATE_TYPE.REPLACE;
        }
        this.newHistoryObj({
          type: type,
          sequence: content,
          strand
        });
      }
    },
    clearSequenceDataHistory() {
      this.oveEditor.getState().sequenceDataHistory = {};
    },
    newHistoryObj({ type, preSequence, sequence,features,strand }) {
      if (sequence) {
        for (let j = 0; j < sequence.length; j++) {
          if (ONLY_Char_ARRAY.indexOf(sequence[j]) === -1) {
            this.$message.warning(this.language==='zh'?this.zh_list.qqq+ONLY_Char_ARRAY.toString():"Can only input “ATCGN “ or “atcgn”");
            return;
          }
        }
      }

      const obj = {
        // time: toDateString(new Date()),
        createTime: "",
        type,
        preSequence,
        sequence,
        programmeId: this.id,
        sequenceId: this.sequenceId,
      };
      if (this.insertAndSelectObj.caretPosition === -1) {
        obj.start =
          this.insertAndSelectObj.selectionLayer.start +
          this.currentSeqIndex +
          1;
        obj.end =
          this.insertAndSelectObj.selectionLayer.end + this.currentSeqIndex + 2;
      } else {
        obj.start =
          this.insertAndSelectObj.caretPosition + this.currentSeqIndex + 1;
        obj.end = obj.start + 1;
      }
      // if(start>end){
      //   return
      // }
      delete obj.preSequence;
      obj.features = features
      obj.strand = strand?-1:1
      this.loading = true;
      saveOperate(obj)
        .then((res) => {
          this.showMessageAfterLoadOve = this.language==='zh'?this.zh_list.ppp:"Operation succeed!";
          this.$refs.range.rangeOk();
        })
        .finally(() => {
          this.loading = false;
        });
    },
    hideDrawer() {
      if (this.drawerIndex !== null && this.drawerIndex !== undefined) {
        this.drawerIndex = null;
        this.drawerVisible = false;
        this.drawerVisibleClass = "hide-drawer";
      }
      // let featureDialogDoms = document.getElementsByClassName('tg-bp3-dialog-resizable-draggable')
      // if(featureDialogDoms!=null&&featureDialogDoms.length){
      //   featureDialogDoms[0].parentElement.removeChild(featureDialogDoms[0])
      // }
    },
    clickShowDrawer(index) {
      if (this.drawerIndex === index) {
        this.hideDrawer();
        return;
      }
      this.showDrawer(index);
    },
    showDrawer(index) {
      this.drawerVisibleClass = "show-drawer";
      this.drawerIndex = index;
      switch (index) {
        case 0:
          let arr = [];
          for (let i = 0; i < this.annotationList.length; i++) {
            if (this.annotationList[i].keyWords != null) {
              arr.push(this.annotationList[i].keyWords);
            }
          }
          this.$refs.programmeForm.show(this.id, arr.toString());
        case 1:
          break;
        case 2:
          this.$refs.history.show();
          break;
        case 3:
          break;
        default:
          break;
      }
      this.drawerVisible = true;
    },

    refresh() {
      getAction("/system/programme/queryById", { id: this.id }).then((res) => {
        this.programmeInfo = res.data;
      });
    },

    showAnalyseDialog(text) {
      this.$refs.toolBar.showAnalyse(text);
    },

    // getFeature(data){
    //   this.loadSequence({id:+data.chr,start:data.start},null,data)
    // }
  },
  watch: {
    showChooseFile(val) {
      if (val) {
        this.$message.info(
          this.language==='zh'?this.zh_list.rrr:"In browse mode, select Enter Edit Mode at the Comment Message on the right sidebar"
        );
      }
    },
    readOnly(val) {
      if (val) {
        this.$message.info({ message: this.language==='zh'?this.zh_list.sss:"View only, no editing allowed", duration: 4000 });
      }
    },
    sequenceId(val) {
      sessionStorage.setItem("currentSequenceId", val);
      this.searchResult = []
    },
  },
};
</script>
<style>
.ve-propertiesPanel .bp3-tab-list {
  display: none;
}
.ve-propertiesPanel .data-table-header {
  display: none;
}
.design .el-form--label-top .el-form-item--small .el-form-item__label {
  font-size: 14px;
  font-weight: 500;
}
.design .el-select {
  width: 100%;
}
.el-icon-question {
  color: #a3a4b2;
}
.programmer-search-select .el-input__inner {
  border-top-right-radius: 0;
  border-bottom-right-radius: 0;
}
.programmer-search-input .el-input__inner {
  border-top-left-radius: 0;
  border-bottom-left-radius: 0;
  border-top-right-radius: 0;
  border-bottom-right-radius: 0;
}
.programmer-search-btn.el-button {
  border-top-left-radius: 0;
  border-bottom-left-radius: 0;
}
.ve-draggable-tabs {
  background-color: rgb(230, 236, 254);
}
.veStatusBar {
  background-color: #fff;
}
.veLinearView {
  overflow-y: auto !important;
  overflow-x: hidden !important;
}
</style>
<style scoped lang="scss">
@import "@/assets/styles/variables.scss";
.design {
  position: relative;
  width: 100%;
  $right-menu-width: 33px;
  $design-drawer-width: 850px;
  $content-top: 136px;
  $range-height: 73px;
  $search-height: 55px;
  $tool-height: 50px;
  $bottom-content-height: 40%;
  height: calc(100vh - #{$content-top - $tool-height});
  .init-programmer {
    background-color: #fff;
    width: calc(100% - #{$right-menu-width} - 30px);
    position: absolute;
    z-index: 899;
    height: calc(
      100vh - #{$content-top + $range-height + $search-height} - 20px
    );
    top: #{$tool-height + 15px};
    left: 15px;
    display: flex;
    justify-content: center;
    align-items: center;
    .btn {
      display: flex;
      justify-content: center;
      cursor: pointer;
      span {
        font-size: 12px;
        text-align: center;
        color: #999999;
        padding: 5px 10px;
      }
      span:nth-child(1) {
        border: 1px solid #e4e4e4;
        border-radius: 4px 0px 0px 4px;
        padding-left: 15px;
        padding-right: 15px;
      }
      span:nth-child(2) {
        border-top: 1px solid #e4e4e4;
        border-bottom: 1px solid #e4e4e4;
      }
      span:nth-child(3) {
        border: 1px solid #e4e4e4;
        border-radius: 0px 4px 4px 0px;
        padding-right: 15px;
        padding-left: 15px;
      }
    }
    .tool-upload-input {
      display: none;
    }
  }
  .content {
    width: calc(100% - #{$right-menu-width} - 30px);
    margin-left: 15px;
    margin-top: 15px;
    background-color: #fff;
    // .ove{
    //   height: calc(100vh - #{$content-top + $range-height + $search-height});
    // }
    .content-ove {
      height: calc(
        100vh - #{$content-top + $range-height + $search-height} - 20px
      );
    }
    .ove-f {
      padding: 0 10px;
      overflow-y: none;
      height: calc(
        100vh - #{$content-top + $range-height + $search-height} - 20px
      );
    }
    .design-flex {
      display: flex;
      height: calc(100% - 10px);
      overflow-x: auto;
    }
    .design-table-bottom-plus {
      max-width: 100%;
      i {
        font-size: 14px;
        margin-right: 5px;
      }
      cursor: pointer;
      color: #2f54eb;
      border: 1px dashed #2f54eb;
      text-align: center;
      margin-top: 5px;
      font-size: 12px;
      line-height: 17px;
      padding: 7px 0;
    }
  }
  .right-menu {
    width: $right-menu-width;
    position: absolute;
    right: 0;
    top: $tool-height;
    height: calc(100vh - #{$content-top});
    background-color: $tool-bar-background;
    border-top: 1px solid #e8e9ed;
    border-left: 1px solid #e8e9ed;
    z-index: 901;
    display: flex;
    flex-direction: column;
    align-items: center;
    color: #979797;
    font-size: 18px;
    .right-menu-item {
      width: 100%;
      text-align: center;
      padding: 10px 0;
      svg {
        fill: #666;
        color: #666;
      }
    }
    .right-menu-item.checked {
      background-color: $base-color;
      svg {
        fill: #fff;
        color: #fff;
      }
    }
  }
  .bottom.show-bottom {
    animation: showBottom 1s;
    animation-fill-mode: forwards;
  }
  .bottom.hide-bottom {
    animation: hideBottom 1s;
    animation-fill-mode: forwards;
  }
  .design-drawer {
    position: fixed;
    right: $right-menu-width - $design-drawer-width;
    top: $content-top;
    width: $design-drawer-width;
    background-color: $tool-bar-background;
    height: calc(100vh - #{$content-top});
    overflow-y: auto;
    border-top: 1px solid #e8e9ed;
    border-bottom: 1px solid #e8e9ed;
    padding: 20px;
    z-index: 900;
    .drawer-title {
      color: $drawer-title-color;
      font-size: 16px;
      font-weight: bold;
      display: flex;
      justify-content: space-between;
      padding: 14px 20px;
      border-bottom: 1px solid #ededed;
      margin-bottom: 20px;
      .drawer-title-right {
        cursor: pointer;
      }
    }
    .drawer-form {
      margin: 20px;
      .eugene-rule {
        overflow: hidden;
        width: 200px;
        height: 300px;
      }
      .part-tag {
        padding-bottom: 20px;
        margin-bottom: 20px;
        border-bottom: 1px solid #e4e4e4;
      }
    }
  }
  .design-drawer.show-drawer {
    animation: showDrawer 1s;
    animation-fill-mode: forwards;
  }
  .design-drawer.hide-drawer {
    animation: hideDrawer 1s;
    animation-fill-mode: forwards;
  }
  @keyframes showDrawer {
    from {
      right: $right-menu-width - $design-drawer-width;
    }
    to {
      right: $right-menu-width;
    }
  }
  @keyframes hideDrawer {
    from {
      right: $right-menu-width;
    }
    to {
      right: $right-menu-width - $design-drawer-width;
    }
  }
}
</style>

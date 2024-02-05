<template>
  <div class="programmer-list">
    <div class="top-top">
      <div>
        <span>{{language==='zh'?zh_list.z:'Name'}}：</span>
        <el-input
          size="small"
          :placeholder="language==='zh'?zh_list.aa:'Type your “Design Name” here '"
          v-model="programmeName"
          maxlength="20"
          class="input-default"
          clearable
          @clear="pragrammeNameSearch"
          @blur="programmeName = $event.target.value.trim()"
          @keypress.enter.native="pragrammeNameSearch"
        >
          <template v-slot:suffix >
            <i class="el-input__icon el-icon-search" @click="pragrammeNameSearch"></i>
          </template>
        </el-input>
      </div>

      <div>
        <span>{{language==='zh'?zh_list.bb:'Date'}}：</span>
        <el-date-picker
          v-model="updateDate"
          type="daterange"
          range-separator="-"
          :start-placeholder="language==='zh'?zh_list.cc:'Starting'"
          :end-placeholder="language==='zh'?zh_list.dd:'Ending' "
          value-format="yyyy-MM-dd"
          suffix-icon="el-icon-date">
        </el-date-picker>
      </div>
      <vxe-button status="primary" :content="language==='zh'?zh_list.f:'Search'" @click="queryProgramme"></vxe-button>
      <vxe-button :content="language==='zh'?zh_list.g:'Resetting'" @click="reset"></vxe-button>
    </div>
    <div class="app-container">
      <div class="design-list-top">
        <div>{{language==='zh'?zh_list.ee:'List'}}</div>
        <div class="design-list-top-right">
          <vxe-button status="primary" :content="language==='zh'?zh_list.ff:'New Design'" @click="openDialog('programmeDialog')" v-hasPermi="['programme:programme:add']"></vxe-button>
        </div>
      </div>
      <div class="list table-f" ref="scrollRef">
        <div class="card" v-for="(item) in tableData"  :key="item.id">
          <div class="title" @click="toInfo(item)">{{item.programmeName}}</div>
          <div class="desc">{{language==='zh'?zh_list.gg:'Genome Design'}} {{item.programmeName}}</div>
          <div class="bottom">
            <div class="btn">
              <el-tooltip :content="language==='zh'?zh_list.hh:'Download'" placement="top-start" >
                <el-button size="mini" plain type="primary" icon="el-icon-download"  @click="toPack(item)" v-hasPermi="['programme:programme:download']"></el-button>
              </el-tooltip>
              <el-tooltip :content="language==='zh'?zh_list.ii:'Copy'" placement="top-start" >
                <el-button size="mini" plain  icon="el-icon-document-copy"  @click="clone(item)" v-hasPermi="['programme:programme:clone']"></el-button>
              </el-tooltip>
              <el-tooltip :content="language==='zh'?zh_list.jj:'Save as a Template'" placement="top-start" >
                <el-button size="mini" plain  icon="el-icon-document-add"  @click="$refs.saveTemplateDialog.showDialog(item)" v-hasPermi="['programme:programme:saveBlank']"></el-button>
              </el-tooltip>
                             <el-tooltip :content="language==='zh'?zh_list.r:'Delete'" placement="top-start" >
                <el-button size="mini" plain type="danger" icon="el-icon-delete"  @click="remove(item)" v-hasPermi="['programme:programme:remove']"></el-button>
              </el-tooltip>
            </div>
            <div class="time">{{item.createTime}}</div>
          </div>
        </div>

      <el-empty :image-size="200" :description="language==='zh'?zh_list.C:'No Results'" v-if="tableData.length==0" style="height:100%"></el-empty>
      </div>

      <div class="pager-flex">
        <div class="pager-flex-total" v-text="totalShow()"></div>
        <vxe-pager
          class-name="pager-self"
          size="small"
          border
          :loading="tableLoading"
          :current-page="tablePage.currentPage"
          :page-size="tablePage.pageSize"
          :total="tablePage.totalResult"
          :layouts="['PrevPage', 'JumpNumber', 'NextPage', 'Sizes', 'FullJump']"
          @page-change="pageChangeEvent"
        >
        </vxe-pager>
      </div>
      <programme-dialog ref="programmeDialog" @ok="newProgrammeOk"></programme-dialog>
      <clone-dialog ref="cloneDialog" @ok="reset"></clone-dialog>
      <save-template-dialog ref="saveTemplateDialog" @ok="$refs.programmeDialog.loadTemplateList()"></save-template-dialog>
      <pack-dialog ref="packDialog" @ok="packOk"></pack-dialog>
           </div>
  </div>
</template>
<script>
import { listProgramme,deleteBatch } from "@/api/system/programme";
import { downFile } from "@/api/manage";
import { download } from "@/utils/hmzhkj";
import { table } from "@/mixins/table";
import ProgrammeDialog from "./modules/ProgrammeDialog";
import CloneDialog from "./modules/CloneDialog";
import SaveTemplateDialog from "./modules/SaveTemplateDialog";
import PackDialog from "./modules/PackDialog";
import { zh_list } from "@/data/constant";
export default {
  dicts: [],
  name: "ReportList",
  mixins: [table],
  components: {
    ProgrammeDialog,
    CloneDialog,
    SaveTemplateDialog,
    PackDialog,
  },
  data() {
    PackDialog
    return {
      zh_list,
      language: process.env.VUE_APP_LANGUAGE,
      subLoading: false,
      allAlign: null,
      tableLoading: false,
      tablePage: {
        currentPage: 1,
        pageSize: 10,
        totalResult: 100,
      },
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      confirmRemoveVisible: false,
      tableData: [{}],
      ids: '',
      programmeName: "",
      updateDate: [],
      projectName:"",
      disableHandle:true,
      selectChecked: [],
      currentRecord: null
    };
  },
  created() {
    this.loadData();
  },
  mounted() {
    setTimeout(() => {
      const $table = this.$refs.reportTable;
      if ($table) {

        const stateColumn = $table.getColumnByField("state");
        if (stateColumn) {
          $table.setFilter(stateColumn, this.dict.type.report_state);
        }
      }
    }, 500);
  },
  methods: {
    newProgrammeOk(id){
      this.$router.push({ path: "/",query: { id: id }})
    },

    reset() {
      this.tablePage.currentPage = 1;
      this.tablePage.pageSize = 10;
      this.programmeName = ''
      this.updateDate = []
      this.projectName = ''
      this.queryParams = {}
      this.selectChecked = []
      this.$refs.scrollRef.scrollTop= 0;
      this.loadData();
    },
    packOk(){
      this.downloadFile(this.currentRecord)
    },

    loadData() {
      this.queryParams.pageNum = this.tablePage.currentPage;
      this.queryParams.pageSize = this.tablePage.pageSize;
      this.tableLoading = true;
      this.disableHandle=true;
      listProgramme(this.queryParams)
        .then((res) => {
          this.tablePage.totalResult = res.total;
          this.tableData = res.rows;
        })
        .finally(() => {
          this.tableLoading = false;
        });
    },
    pageChangeEvent({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.loadData();
    },
    sortChangeEvent({ property, order }) {
      console.log(property);
      console.log(order);
      this.tablePage.currentPage = 1;
      this.queryParams.orderByColumn = 'p.'+property;
      this.queryParams.isAsc = order;
      this.loadData();
    },
    filterChangeEvent({ property, values }) {
      if (property == "packState") {
        this.queryParams.packState = values;
      }
      this.loadData();
    },
    toPack(item){
      this.currentRecord = item
      if (item.sourceFileName) {
        this.$refs.packDialog.showDialog([item],item.id)
      }else {
        if(this.language==='zh'){
          this.$message.warning(this.zh_list.kk + item.programmeName + this.zh_list.ll);
        }else{
          this.$message.warning("Design 【" + item.programmeName + " 】has empty content and cannot be packaged！");
        }

      }
    },
    clone(row){
      if(!row.sourceFileName){
        if(this.language==='zh'){
          this.$message.warning(this.zh_list.mm);
        }else{
          this.$message.warning("The current design file has empty content and cannot be cloned!");
        }
      }else{
        this.$refs.cloneDialog.showDialog(row);
      }
    },
    toInfo(row) {
      // if(row.packState == 1){
        this.$router.push({path:'/programmer',query:{id:row.id}})
      // }
    },
    // remove() {
    //   this.tableLoading = true;
    //   deleteBatch({ids:this.ids}).then((res) => {
    //     if(res.code==200){
    //       this.closeDiaLog();
    //       this.loadData();
    //       this.ids = '';
    //       this.tableLoading = false
    //     }else{
    //       this.tableLoading = false
    //     }
    //   });
    // },

    remove(row) {
        this.$confirm(this.language==='zh'?this.zh_list.nn:'The design will be permanently deleted after this action. Do you wish to proceed?', this.language==='zh'?'提示':'Prompt', {
          confirmButtonText: this.language==='zh'?this.zh_list.oo:'OK',
          cancelButtonText: this.language==='zh'?this.zh_list.R:'Cancel',
          type: 'warning'
        }).then(() => {
          deleteBatch({ids:row.id}).then((res) => {
            if(res.code==200){
              this.closeDiaLog();
              this.loadData();
              this.ids = '';
              this.tableLoading = false
              this.$message.success(this.language==='zh'?this.zh_list.pp:"Deletion Completed! ");
            }else{
              this.tableLoading = false
              this.$message.error(this.language==='zh'?this.zh_list.qq:"Deletion failed!");
            }
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: this.language==='zh'?this.zh_list.rr:'Cancel Deletion'
          });
        });
      },
    idsSplice(selectRecords){
      let ids = '';
      selectRecords.forEach(element => {
        ids = ids + element.id + ','
      })
      ids = ids.substring(0,ids.length-1)
      this.ids = ids
      console.log(this.ids)
    },
    openDeleteDialog(){
      let selectRecords = this.$refs.programmeTabel.getCheckboxRecords();
      if(selectRecords.length){
        this.confirmRemoveVisible = true;
      }else{
        this.$message.error(this.language==='zh'?this.zh_list.ss:"No design selected for deletion!");
      }
    },
    closeDiaLog() {
      this.confirmRemoveVisible = false;
      this.ids = '';
    },
    handleCommand(command, row) {
      console.log(command);
      this.ids = row.id;
      if (command === "programmeDelete") {
        this.confirmRemoveVisible = true;
      }else if(command === 'saveTemplate'){
        this.$refs.saveTemplateDialog.showDialog(row)
      }else if(command === 'pack'){
        let data = []
        data.push(row)
        this.$refs.packDialog.showDialog(data,this.ids)
      }
    },
    formatterState({ cellValue }) {
      // const item = this.dict.type.report_state.find(
      //   (item) => item.value == cellValue
      // );
      // return item ? item.label : "";
      let state;
      if (cellValue == 0) {
        state = "● packaged";
      } else if (cellValue == 1) {
        state = "● Not packed";
      } else {
        state = "";
      }
      return state;
    },
    pragrammeNameSearch() {
      this.programmeName = this.programmeName.trim();
      this.queryParams.programmeName = this.programmeName;
      this.loadData();
    },
    cellStyle({ row, column }) {
      if (column.field === "packState") {
        let state = row.packState;
        if (state == 0) {
          return { color: "#1890FF" };
        } else if (state == 1 ) {
          return { color: "#BFBFBF" };
        }
      }
    },
    toBuild(row) {
      this.$refs.reportDialog.visible = true;
      this.$refs.reportDialog.rowData = row;
    },

    test() {
      const data = this.dict.type.design_type;
    },
    totalShow() {
      let total = this.tablePage.totalResult;

      return this.language==='zh'?this.zh_list.tt+total+this.zh_list.uu:total+" items in total";
    },

    selectAllEvent ({ checked }) {
      const records = this.$refs.programmeTabel.getCheckboxRecords()
      this.selectChecked = records
      if(checked){
        this.idsSplice(records)
      }else{
        this.ids=''
      }
      if (records && records.length) {
          this.disableHandle = false
      } else{
          this.disableHandle = true
      }
    },
    selectChangeEvent ({ checked }) {
      const records = this.$refs.programmeTabel.getCheckboxRecords()
      this.selectChecked = records
      this.idsSplice(records)
      if (records && records.length) {
          this.disableHandle = false
      } else{
          this.disableHandle = true
      }
    },
    queryProgramme(){
      this.queryParams.programmeName = this.programmeName
      this.queryParams.projectName = this.projectName
      this.queryParams.updateTimeStart = this.updateDate[0]
      this.queryParams.updateTimeEnd = this.updateDate[1]
      this.loadData()
    },
    openDialog(dialogName){
      this.$refs[dialogName].showDialog()
    },
    sendRow(dialogName,row){
      this.$refs[dialogName].rowData = row
      this.openDialog(dialogName)
    },
    downloadFile(row){
      this.tableLoading = true;
        downFile("/system/sequence/pack/download", { programmeId: row.id }).then(
          (res) => {
            download(res, row.programmeName+ ".zip");
            this.tableLoading = false;
          }
        );
    },
  },
  computed: {
    getStartIndex: function () {
      return (this.tablePage.currentPage - 1) * this.tablePage.pageSize;
    },
  },
};
</script>
<style></style>
<style lang="scss" scoped>
.programmer-list{
  .list{
    .card{
      .title{
        font-size: 16px;
        font-weight: 500;
        margin-bottom: 10px;
        cursor: pointer;
      }
      .desc{
        font-size: 12px;
        color: #aaa;
        margin-bottom: 10px;
      }
      .bottom{
        display: flex;
        justify-content: space-between;
      }
      padding-bottom: 20px;
      border-bottom: 1px solid #ddd;
      margin-bottom: 20px;

    }

  }
}


.design-list-top {
  margin-bottom: 20px;
  display: flex;
  text-align: left;
  justify-content: space-between;
}

.input-default {
  width: 300px;
}
.default-else-el {
  color: #cccccc;
  margin-right: 10px;
  cursor: auto;
  text-decoration: none;
}
.download-failed-icon {
  color: #ff3300;
}
.top-top {
  border-top: 1px solid #e8e9ed;
  padding: 10px 20px;
  font-size: 14px;
  display: flex;
  align-items: center;
  background-color: #fff;
  div {
    margin-right: 10px;
  }
}
.button-group{
  a{
    text-decoration: none;
  }
  $button : var(--button);
  .unusable{
    color: #cccccc;
    &:hover{
      cursor:text
    }
  }

}


.table-f {
  height: calc(100vh - 300px);
  overflow:auto;
}
.time{
  margin-right: 20px;
}
</style>

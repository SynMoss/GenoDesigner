<template>
  <div>
    <vxe-table
      @menu-click="contextMenuClickEvent"
      :menu-config="tableData.length == 0 ? '' : tableMenu"
      :sort-config="{ remote: true }"
      :filter-config="{ remote: true }"
      class="table-self"
      cell-class-name="table-cell-self"
      header-cell-class-name="table-header-self"
      :data="tableData"
      :loading="tableLoading"
      ref="designTable"
      show-overflow
      :empty-text="language==='zh'?zh_list.aah:'No Results'"
    >
      <vxe-column field="order" title="Step" width="50px"></vxe-column>
      <vxe-column field="createTime" title="Time" width="100px"></vxe-column>
      <vxe-column field="type" title="Type" width="200px"></vxe-column>
      <vxe-column field="sequenceName" title="Chr" width="100px">
        <template #default="{ row }">
          {{ sequenceName }}
        </template>
      </vxe-column>
      <vxe-column field="start" title="Start"></vxe-column>
      <vxe-column field="end" title="End">
        <template #default="{ row }">
          {{ row.type === "INSERT" ? null : row.end }}
        </template>
      </vxe-column>
      <vxe-column field="operation" title="Operation" v-if="!readOnly" width="110px">
        <template #default="{ row, rowIndex }">
          <el-button
            type="primary"
            size="mini"
            @click="rollback(row)"
            v-if="!row.errExcelPath && row.canRollback"
          >
          Rollback
          </el-button>
          <el-dropdown v-if="row.errExcelPath"
            @command="(command) => handleCommand(command, row)"
          >
            <span class="el-dropdown-link">
              {{language==='zh'?zh_list.op:"Option"}}<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item v-if="row.canRollback"
                command="rollback"
                >rollback</el-dropdown-item
              >
              <el-dropdown-item v-if="row.errExcelPath.indexOf('xlsx')!==-1"
                command="errExcel"
                >excel</el-dropdown-item
              >
              <el-dropdown-item v-if="row.errExcelPath.indexOf('txt')!==-1"
                command="txt"
                >txt</el-dropdown-item
              >
            </el-dropdown-menu>
          </el-dropdown>


                   </template>
      </vxe-column>

    </vxe-table>
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
        :layouts="['PrevPage', 'JumpNumber', 'NextPage']"
        @page-change="pageChangeEvent"
      ></vxe-pager>
    </div>
  </div>
</template>
<script>
import { zh_list } from "@/data/constant";
import { listSequenceOperate } from "@/api/system/sequence";
import { resultDownload ,delStepFiles} from "@/api/system/programme";
import { download } from "@/utils/hmzhkj";
import { downFile } from "@/api/manage";
import {
  hasBindDesign,
  getDesignToken,
  insertProject,
  insertDesign,
} from "@/api/system/externalpaccout";
export default {
  components: {
  },
  props: {
    readOnly:{
      type: Boolean,
      readOnly: function(params) {
        return false
      }
    },
    sequenceName: {
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
    programmeInfo: {
      type: Object,
      default: function (params) {
        return null;
      },
    },
  },
  data() {
    return {
      zh_list,
      language: process.env.VUE_APP_LANGUAGE,
      tableData: [],
      tableLoading: false,
      tablePage: {
        currentPage: 1,
        pageSize: 10,
        totalResult: 0,
      },
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      tableMenu: {
                                                                                                                                                                                                                                                                      },
    };
  },
  created() {
  },
  computed: {
    getStartIndex: function () {
      return (this.tablePage.currentPage - 1) * this.tablePage.pageSize;
    },
  },
  methods: {
    show() {
      this.tablePage.currentPage = 1;
      this.loadData();
    },
    handleCommand(command, row) {
      switch (command) {
        case "rollback":
          this.rollback(row);
          break;
        case "errExcel":
          this.downloadExcel(row);
          break;
        case "txt":
          this.downloadTxt(row)
        default:
          break;
      }
    },
    loadData() {
      this.queryParams.pageNum = this.tablePage.currentPage;
      this.queryParams.pageSize = this.tablePage.pageSize;
      this.queryParams.sequenceId = this.sequenceId;
      this.tableLoading = true;
      listSequenceOperate(this.queryParams)
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
                                                            totalShow() {
    let total = this.tablePage.totalResult;

    return this.language==='zh'?""+total+" ":total+" items in total";
    },
    rollback(row) {
      const h = this.$createElement;
      this.$confirm(this.language==='zh'?this.zh_list.aai:"Are you sure to perform this operation?", {
        title: this.language==='zh'?this.zh_list.aaj:"Are you sure to perform this operation?",
        message: h("div", [
          h("p", this.language==='zh'?this.zh_list.aak:"1. After rollback, not recoverable!"),
          h("p", this.language==='zh'?"2." + row.order + this.zh_list.aal:"2. All operations after step " + row.order + " will be rolled back!"),
        ]),
        confirmButtonText: this.language==='zh'?this.zh_list.confirmA:"OK",
        cancelButtonText: this.language==='zh'?this.zh_list.cancelA:"Cancel",
        type: "warning",
      })
        .then(() => {
          this.$emit("rollback", row);
                    let steps=[]
          for(let item of this.tableData){
            let step = this.sequenceName + '_' + item.order + '_' + item.createTime.replace(/[-\s:]/g, '')
            steps.push(step)
            if(item == row){
              break
            }
          }
          let params = {step:steps,programmeId:this.programmeInfo.id}
          delStepFiles(params)
        })
        .catch(() => ({}));
    },

    downloadExcel(row){
      downFile("/system/sequence/errExcel/download", { programmeId: this.programmeInfo.id,excelPath: row.id+".xlsx" }).then(
          (res) => {
            download(res,this.programmeInfo.programmeName+"_"+row.id+".xlsx");
            this.tableLoading = false;
          }
        );
    },
    downloadTxt(row){
      downFile("/system/sequence/errExcel/download", { programmeId: this.programmeInfo.id,excelPath: row.id+".txt" }).then(
          (res) => {
            download(res,this.programmeInfo.programmeName+"_"+row.id+".txt");
            this.tableLoading = false;
          }
        );
    },
    isHas(row) {
      if (!this.programmeInfo.runningState) {
        return true;
      }
      const flag =
        this.programmeInfo.runningState[
          this.sequenceName + '_' + row.order + '_' + row.createTime.replace(/[-\s:]/g, '')
        ];
      return flag == null || JSON.stringify(flag) == "{}";
    },
    downloadFile(row) {
      let params = {
        programmeId: this.programmeInfo.id,
        step: this.sequenceName + '_' + row.order + '_' + row.createTime.replace(/[-\s:]/g, ''),
      };

      let fileName = this.programmeInfo.programmeName +'_'+ params.step + ".zip";
      resultDownload(params)
        .then((res) => {
          download(res, fileName);
        })
        .catch((err) => {
          this.$message.error("The downloaded file does not exist!");
          this.refresh();
        });
    },

    refresh() {
      this.$emit("refresh");
      console.log(this.programmeInfo);
      this.show();
    },

    contextMenuClickEvent({ menu, row, column, rowIndex, columnIndex }) {
      console.log(this.programmeInfo)
      if (this.programmeInfo.runningState) {
        if (
          this.programmeInfo.runningState[
          this.sequenceName + '_' + row.order + '_' + row.createTime.replace(/[-\s:]/g, '')
          ] == "0"
        ) {
          this.$message.warning("");
          return;
        }
      }
            if (menu.code == "design") {
        this.doDesign(row);
      } else {
        this.$refs[menu.code].showDialog(row);
      }
    },
  },
  watch: {},
};
</script>

<style lang="scss" scoped>
.icon-button {
  font-size: 10px;
  width: 30px;
}
</style>

<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
    >
      <el-form-item label="Title" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="Please Enter Title"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
          @blur="queryParams.title = $event.target.value.trim()"
          @clear="handleQuery"
        />
      </el-form-item>
      <el-form-item label="OperName" prop="operName">
        <el-input
          v-model="queryParams.operName"
          placeholder="Please Enter OperName"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
          @blur="queryParams.operName = $event.target.value.trim()"
          @clear="handleQuery"
        />
      </el-form-item>
      <el-form-item label="BusinessType" prop="businessType">
        <el-select
          v-model="queryParams.businessType"
          placeholder="BusinessType"
          clearable
          @keyup.enter.native="handleQuery"
          @clear="handleQuery"
          style="width: 240px"
        >
          <el-option
            v-for="dict in dict.type.sys_oper_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="Status" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="Status"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="dict in dict.type.sys_common_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="Operating Time">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="Start Date"
          end-placeholder="End Date"
          clearable

        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery" style="font-size: 14px;padding: 10px 20px;">Search</el-button>
        <el-button @click="resetQuery" style="font-size: 14px;padding: 10px 20px;">Reset</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
                  </el-row>
    <div class="table-f">
      <vxe-table
        ref="SysOperlogList"
        :data="list"
        max-height="100%"
        v-loading="loading"
        @checkbox-all="selectChangeEvent"
        @checkbox-change="selectChangeEvent"
        show-overflow
        :sort-config="{ remote: true }"
        @sort-change="sortChangeEvent"
        border="inner"
        :current-page="tablePage.currentPage"
        :seq-config="{
          startIndex:
            (this.tablePage.currentPage - 1) * this.tablePage.pageSize,
        }"
      >
        <vxe-column type="checkbox" min-width="50px" fixed="left"></vxe-column>
        <vxe-column
          field="operId"
          type="seq"
          title="Seq"
          min-width="50px"
          fixed="left"
          show-overflow="tooltip"
        ></vxe-column>
        <vxe-column
          field="title"
          title="Title"
          min-width="100px"
          show-overflow="tooltip"
        ></vxe-column>
        <vxe-column
          field="businessType"
          title="BusinessType"
          min-width="100px"
          show-overflow="tooltip"
        >
        <template  #default="{ row }">
            <dict-tag
              :options="dict.type.sys_oper_type"
              :value="row.businessType"
            /> </template
        >
      </vxe-column>
        <vxe-column
          field="requestMethod"
          title="RequestMethod"
          min-width="100px"
          show-overflow="tooltip"
        ></vxe-column>
        <vxe-column
          field="operName"
          title="OperName"
          sortable
          show-overflow="tooltip"
          min-width="130px"
        ></vxe-column>
        <vxe-column
          field="operIp"
          title="OperIp"
          min-width="100px"
          show-overflow="tooltip"
        ></vxe-column>
        <vxe-column
          field="status"
          title="Status"
          min-width="100px"
          show-overflow="tooltip"
        >
          <template slot-scope="scope">
            <dict-tag
              :options="dict.type.sys_common_status"
              :value="scope.row.status"
            /> </template
        ></vxe-column>
        <vxe-column
          field="operTime"
          title="OperTime"
          sortable
          show-overflow="tooltip"
          min-width="130px"
        ></vxe-column>
        <vxe-column title="Operate" min-width="100px" show-overflow="tooltip">
          <template slot-scope="scope">
            <a
              @click="handleView(scope.row, scope.index)"
              type="text"
              size="small"
              class="table-opera"
              v-hasPermi="['system:operlog:query']"
              >Detailed</a
            >
          </template>
        </vxe-column>
      </vxe-table>
    </div>
    <div class="pager-flex">
      <div class="pager-flex-total" v-text="totalShow()"></div>
      <vxe-pager
        class-name="pager-self"
        background
        size="small"
        :loading="loading"
        :current-page="tablePage.currentPage"
        :page-size="tablePage.pageSize"
        :total="tablePage.totalResult"
        :layouts="['PrevPage', 'JumpNumber', 'NextPage', 'Sizes', 'FullJump']"
        @page-change="pageChangeEvent"
      >
      </vxe-pager>
    </div>

         <el-dialog
      title="Operation Log Is Detailed"
      :visible.sync="open"
      width="700px"
      append-to-body
    >
      <el-form ref="form" :model="form" label-width="100px" size="mini">
        <el-row>
          <el-col :span="12">
            <el-form-item label="Operational Modules:"
              >{{ form.title }} / {{ typeFormat(form) }}</el-form-item
            >
            <el-form-item label="Login Information:"
              >{{ form.operName }} / {{ form.operIp }}</el-form-item
            >
          </el-col>
          <el-col :span="12">
            <el-form-item label="The Address Of The Request：">{{ form.operUrl }}</el-form-item>
            <el-form-item label="How The Request Is Made：">{{
              form.requestMethod
            }}</el-form-item>
          </el-col>
                     <el-col :span="24">
            <el-form-item label="Request Parameters：">{{ form.operParam }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Response Parameters:">{{
              form.jsonResult
            }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Status：">
              <div v-if="form.status === 0">Normal:</div>
              <div v-else-if="form.status === 1">Fail:</div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Operating Time:：">{{
              parseTime(form.operTime)
            }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Exception Information:：" v-if="form.status === 1">{{
              form.errorMsg
            }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="open = false">Close</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { list, delOperlog, cleanOperlog, } from "@/api/system/operlog";

export default {
  name: "Operlog",
  dicts: ["sys_oper_type", "sys_common_status"],
  data() {
    return {
     
      loading: true,
    
      ids: [],
    
      multiple: true,
      
      showSearch: true,
     
      total: 0,
      
      list: [],
   
      open: false,
    
      dateRange: [],
      
      form: {},
   
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: '',
        operName: '',
        businessType: undefined,
        status: undefined,
      },
      subLoading: false,
      allAlign: null,
      tableLoading: false,
      tablePage: {
        currentPage: 1,
        pageSize: 10,
        totalResult: 999999,
      },
      columnOption: [
        "Title",
        "BusinessType",
        "How The Request Is Made",
        "OperName",
        "OperId",
        "Status",
        "OperTime",
        "Operate",
      ],
      //已选列
      columnSelected: [],
      columnDefault: [
        "Title",
        "BusinessType",
        "How The Request Is Made",
        "OperName",
        "OperId",
        "Status",
        "OperTime",
        "Operate",
      ],
    };
  },
  created() {
    this.getList();
  },
  methods: {
   
    pageChangeEvent({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getList();
    },
   
    totalShow() {
      let total = this.tablePage.totalResult;
      let str = "Total ";
      let query = this.queryParams;
      if (
        query.projectName ||
        (query.createStaffNoList && query.createStaffNoList.length != 0)
      ) {
        str = "Searched ";
      }
      return str + total + " Items";
    },
    
    selectChangeEvent({ checked }) {
      const records = this.$refs.SysOperlogList.getCheckboxRecords();
      let arr = [];
      for (let item of records) {
        arr.push(item.operId);
      }
      this.ids = arr;
      
      if (records && records.length) {
        this.multiple = false;
      } else {
        this.multiple = true;
      }
    },
    getSelectEvent() {
      let selectRecords = this.$refs.SysOperlogList.getCheckboxRecords();
      VXETable.modal.alert(selectRecords.length);
    },
    
    getList() {
      this.queryParams.pageNum = this.tablePage.currentPage;
      this.queryParams.pageSize = this.tablePage.pageSize;
      this.loading = true;
      list(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.list = response.rows;
          this.tablePage.totalResult = response.total;
          this.loading = false;
        }
      );
    },
   
    typeFormat(row, column) {
      return this.selectDictLabel(
        this.dict.type.sys_oper_type,
        row.businessType
      );
    },
    
    handleQuery() {
      this.queryParams.title = this.queryParams.title.trim()
      this.queryParams.operName = this.queryParams.operName.trim()
      this.tablePage.currentPage = 1
      this.getList();
    },
   
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      //this.$refs.tables.sort(this.defaultSort.prop, this.defaultSort.order);
      this.handleQuery();
    },
   
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.operId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
   
    sortChangeEvent({ property, order }) {
      console.log(property);
      console.log(order);
      this.tablePage.currentPage = 1;
      this.queryParams.orderByColumn = property;
      this.queryParams.isAsc = order;
      this.getList();
    },
    
    handleView(row) {
      this.open = true;
      this.form = row;
    },
    
    handleDelete(row) {
      const operIds = row.operId || this.ids;
      this.$modal
        .confirm('Check Whether To Confirm That The Deletion Log Number Is Set To"' + operIds + '"Data Itmes？')
        .then(function () {
          return delOperlog(operIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("Delete Success");
        })
        .catch(() => {});
    },
    dataChange() {
      if(this.dateRange == null){
      this.getList();
      }
    },
    
    handleClean() {
      this.$modal
        .confirm("Do You Confirm That All Operation Log Data Items Are Cleared?")
        .then(function () {
          return cleanOperlog();
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("Clear Success");
        })
        .catch(() => {});
    },
    

    handleExport() {
      (this.queryParams.ids = this.ids),
        this.download(
          "system/operlog/export",
          {
            ...this.queryParams,
          },
          `operlog_${new Date().getTime()}.xlsx`
        );
    },
  },
};
</script>
<style scoped>

.el-table .el-table__header .ascending,
.el-table .el-table__header .descending {
  /* background-color: #f5f7fa; */
  box-shadow: none;
  /* color: #303133; */
  font-size: 14px;
  margin-top: 2px;
  /* padding-left: 1px;
    padding-right: 10px; */
}

.text-red {
  color: red;
  background-color: aqua;
}
.table-f {
  height: calc(90vh - 210px);
}
</style>

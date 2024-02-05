<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"

    >
      <el-form-item label="IpAddr" prop="ipaddr">
        <el-input
          v-model="queryParams.ipaddr"
          placeholder="Please Enter IpAddr"
          clearable
          style="width: 240px"
          @blur="queryParams.ipaddr = $event.target.value.trim()"
          @keyup.enter.native="handleQuery"
          @clear="handleQuery"
        />
      </el-form-item>
      <el-form-item label="UserName" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="Please Enter UserName"
          clearable
          style="width: 240px"
          @blur="queryParams.userName = $event.target.value.trim()"
          @keyup.enter.native="handleQuery"
          @clear="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Status" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="Sign In Status"
          clearable
          @keyup.enter.native="handleQuery"
          @clear="handleQuery"
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
      <el-form-item label="Sign In Time">
        <el-date-picker
          clearable
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="Start Date"
          end-placeholder="End Date"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery" style="font-size: 14px;padding: 10px 20px;">Search</el-button>
        <el-button @click="resetQuery" style="font-size: 14px;padding: 10px 20px;">Reset</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
             <el-col :span="1.5">
        <el-button
          :disabled="single"
          @click="handleUnlock"
          v-hasPermi="['system:logininfor:unlock']"
          >Unlock</el-button
        >
      </el-col>
             <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <div class="table-f">
      <vxe-table
        ref="SysLogininforList"
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
        >
        <vxe-column type="checkbox" min-width="50px" fixed="left"></vxe-column>
        <vxe-column
          field="infoId"
          type="seq"
          title="Access Number"
          min-width="100px"
          fixed="left"
          show-overflow="ellipsis"
        >
        </vxe-column>
        <vxe-column
          field="userId"
          title="UserId"
          show-overflow="tooltip"
          min-width="130px"
        ></vxe-column>
        <vxe-column
          field="userName"
          title="UserName"
          sortable
          show-overflow="tooltip"
          min-width="130px"
        ></vxe-column>
        <vxe-column
          field="ipaddr"
          title="IpAddr"
          min-width="100px"
          show-overflow="tooltip"
        ></vxe-column>
        <vxe-column
          field="status"
          title="Sign In Status"
          min-width="100px"
          show-overflow="tooltip"
        >
          <template slot-scope="scope">
            <dict-tag
              :options="dict.type.sys_common_status"
              :value="scope.row.status"
            />
          </template>
        </vxe-column>

        <vxe-column
          field="msg"
          title="Description"
          min-width="100px"
          show-overflow="tooltip"
        ></vxe-column>
        <vxe-column
          field="consumeTime"
          title="ConsumeTime(ms)"
          min-width="100px"
          show-overflow="tooltip"
        ></vxe-column>
        <vxe-column
          field="accessTime"
          title="AccessTime"
          sortable
          show-overflow="tooltip"
          min-width="130px"
        ></vxe-column>
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
  </div>
</template>

<script>
import {
  list,
  isLock,
  delLogininfor,
  cleanLogininfor,
  unlockLogininfor,
} from "@/api/system/logininfor";

export default {
  name: "Logininfor",
  dicts: ["sys_common_status"],
  data() {
    return {
      btnEnable: false,

      loading: true,

      ids: [],

      single: true,

      multiple: true,

      selectName: "",

      showSearch: true,

      total: 0,

      list: [],

      dateRange: [],

      defaultSort: { prop: "loginTime", order: "descending" },

      subLoading: false,
      allAlign: null,
      tableLoading: false,
      tablePage: {
        currentPage: 1,
        pageSize: 10,
        totalResult: 99999999999,
      },
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ipaddr: '',
        userName: '',
        status: undefined,
      },

      columnOption: ["UserName", "IpAddr", "Sign In Status", "Description", "Access Time"],
    };
  },
  created() {
    this.getList();

    this.columnSelected = Array.from(this.columnOption);
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
      return str + total + " Itmes";
    },

    selectChangeEvent({ checked }) {
      const records = this.$refs.SysLogininforList.getCheckboxRecords();
      let arr = [];
      let userNameArr = [];
      for (let item of records) {
        arr.push(item.infoId);
        userNameArr.push(item.userName);
      }
      this.ids = arr;
      this.selectName = userNameArr;

      if (records && records.length) {
        this.multiple = false;
      } else {
        this.multiple = true;
      }

      if (records.length == 1) {
        this.single = false;
      } else {
        this.single = true;
      }
    },
    getSelectEvent() {
      let selectRecords = this.$refs.SysLogininforList.getCheckboxRecords();
      VXETable.modal.alert(selectRecords.length);
    },

    getList() {
      this.queryParams.pageNum = this.tablePage.currentPage;
      this.queryParams.pageSize = this.tablePage.pageSize;
      this.loading = true;
      list(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.list = response.rows;
          this.tablePage.totalResult = response.total
          this.loading = false;
        }
      );
    },

    handleQuery() {
      this.queryParams.ipaddr = this.queryParams.ipaddr.trim()
      this.queryParams.userName = this.queryParams.userName.trim()
      this.tablePage.currentPage = 1
      this.getList();
    },

    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.getList;
      this.handleQuery();
    },

    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.infoId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
      this.selectName = selection.map((item) => item.userName);
    },

    handleSortChange(column, prop, order) {
      this.queryParams.orderByColumn = column.prop;
      this.queryParams.isAsc = column.order;
      this.getList();
    },

    sortChangeEvent({ property, order }) {
      console.log(property);
      console.log(order);
      this.tablePage.currentPage = 1;
      this.queryParams.orderByColumn = property;
      this.queryParams.isAsc = order;
      this.getList();
    },
    dataChange() {
      if(this.dateRange == null){
      this.getList();
      }
    },

    handleDelete(row) {
      const infoIds = row.infoId || this.ids;
      this.$modal
        .confirm('Check Whether To Confirm That The Deletion Access Number Is Set To"' + infoIds + '"Data Items？')
        .then(function () {
          return delLogininfor(infoIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("Delete Success");
        })
        .catch(() => {});
    },

    handleClean() {
      this.$modal
          .confirm("Check Whether To Clear All Login Log Data Items？")
        .then(function () {
          return cleanLogininfor();
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("Clear Success");
        })
        .catch(() => {});
    },

    handleUnlock() {
      const username = this.selectName;

      isLock(username).then((res)=>{
        if(res.msg=="Locked"){
          this.$modal
          .confirm('Check Whether To Confirm That The Unlocked UserName is"' + username + '"User?')
          .then(function () {
            return unlockLogininfor(username);
          })
          .then(() => {
            this.$modal.msgSuccess("User" + username + "Unlocked Success");
          })
          .catch(() => {});
        }else{
          this.$alert("The User Is Unlocked And Can Log In Directly")
        }

      })

    },

    handleExport() {
      (this.queryParams.ids = this.ids),
        this.download(
          "system/logininfor/export",
          {
            ...this.queryParams,
          },
          `logininfor_${new Date().getTime()}.xlsx`
        );
    },
  },
};
</script>
<style scoped>
.table-f {
  height: calc(90vh - 250px);
}
</style>
